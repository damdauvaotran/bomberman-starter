package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

    protected int _points;

    protected double _speed;
    protected AI _ai;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double _steps;

    protected int _finalAnimation = 30;
    protected Sprite _deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        _points = points;
        _speed = speed;

        MAX_STEPS = Game.TILES_SIZE / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;

        _timeAfter = 20;
        _deadSprite = dead;
    }

    @Override
    public void update() {
        animate();

        if (!_alive) {
            afterKill();
            return;
        }

        if (_alive)
            calculateMove();
    }

    @Override
    public void render(Screen screen) {

        if (_alive)
            chooseSprite();
        else {
            if (_timeAfter > 0) {
                _sprite = _deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }

        }

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển

        double x = this._x;
        double y = this._y;

        this._direction = _ai.calculateDirection();
        switch (this._direction) {
            case 0: {
                y -= Game.getEnemySpeed();
                break;
            }
            case 1: {
                x += Game.getEnemySpeed();
                break;
            }
            case 2: {
                y += Game.getEnemySpeed();
                break;
            }
            case 3: {
                x -= Game.getEnemySpeed();
                break;
            }
            default: {
                break;
            }
        }


        this.move(x, y);
    }

    @Override
    public void move(double xa, double ya) {
        if (_alive && this.canMove(xa, ya)) {
            _y = ya;
            _x = xa;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double dependedDirectionX1 = x;
        double dependedDirectionY1 = y;

        double dependedDirectionX2 = x;
        double dependedDirectionY2 = y;

        switch (this._direction) {

            case 0: {
                dependedDirectionX1 += 1;
                dependedDirectionY1 -= Game.getCharacterWidth();
                dependedDirectionX2 += Game.getCharacterWidth() - 1;
                dependedDirectionY2 -= Game.getCharacterHeight();
                break;
            }

            case 1: {
                dependedDirectionX1 += Game.getCharacterWidth();
                dependedDirectionY1 -= Game.getCharacterHeight() - 1;
                dependedDirectionX2 += Game.getCharacterWidth();
                dependedDirectionY2 -= 1;
                break;
            }

            case 2: {
                dependedDirectionX1 += 1;
                dependedDirectionY1 -= 1;
                dependedDirectionX2 += Game.getCharacterWidth() - 1;
                dependedDirectionY2 -= 1;
//                System.out.println(this._x + " " + this._y + " " + dependedDirectionY1 + " " + dependedDirectionY2);
                break;
            }

            case 3: {
                dependedDirectionY1 -= 1;
                dependedDirectionY2 -= Game.getCharacterHeight() - 1;
                break;
            }

            default: {
                break;
            }
        }


        Entity entity1 = _board.getEntity(Coordinates.pixelToTile(dependedDirectionX1), Coordinates.pixelToTile(dependedDirectionY1), this);
        Entity entity2 = _board.getEntity(Coordinates.pixelToTile(dependedDirectionX2), Coordinates.pixelToTile(dependedDirectionY2), this);


//        System.out.print( " " + Coordinates.pixelToTile(dependedDirectionX1) + " " + Coordinates.pixelToTile(dependedDirectionY1));
//        System.out.println( " " + Coordinates.pixelToTile(d
// ependedDirectionX2) + " " + Coordinates.pixelToTile(dependedDirectionY2));
//
//        System.out.print(" " + Coordinates.pixelToTile(dependedDirectionX1) + " " + Coordinates.pixelToTile(dependedDirectionY1));
//        System.out.println(" " + Coordinates.pixelToTile(dependedDirectionX2) + " " + Coordinates.pixelToTile(dependedDirectionY2));


        System.out.println(entity1.collide(this) + " " + entity2.collide(this));

        return !(entity1.collide(this) || entity2.collide(this));
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber

        if (e instanceof Flame) {
            this.kill();
            return true;
        }

        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return true;
        }

        return true;
        // Long but easy to understand
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;

        _board.addPoints(_points);

        Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
        _board.addMessage(msg);
    }


    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if (_finalAnimation > 0) --_finalAnimation;
            else
                remove();
        }
    }

    protected abstract void chooseSprite();
}
