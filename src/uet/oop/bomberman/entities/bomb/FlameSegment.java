package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


public class FlameSegment extends Entity {

    protected boolean _last;
    protected int _direction;

    /**
     * @param x
     * @param y
     * @param direction
     * @param last      cho biết segment này là cuối cùng của Flame hay không,
     *                  segment cuối có sprite khác so với các segment còn lại
     */
    public FlameSegment(int x, int y, int direction, boolean last) {
        _x = x;
        _y = y;
        _last = last;
        _direction = direction;

        switch (direction) {
            case 0: {
                _sprite = Sprite.explosion_vertical2;
                break;
            }

            case 1: {
                _sprite = Sprite.explosion_horizontal2;
                break;
            }

            case 2: {
                _sprite = Sprite.explosion_vertical2;
                break;
            }

            case 3: {
                _sprite = Sprite.explosion_horizontal2;
                break;
            }
        }
    }

    public void setLast(boolean last) {
        this._last = last;
        switch (_direction) {
            case 0: {
                _sprite = Sprite.explosion_vertical_top_last2;
                break;
            }

            case 1: {
                _sprite = Sprite.explosion_horizontal_right_last2;
                break;
            }

            case 2: {
                _sprite = Sprite.explosion_vertical_down_last2;
                break;
            }

            case 3: {
                _sprite = Sprite.explosion_horizontal_left_last2;
                break;
            }

            default: {
                _sprite = Sprite.explosion_vertical_top_last2;
                break;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        int xt = (int) _x << 4;
        int yt = (int) _y << 4;

        screen.renderEntity(xt, yt, this);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý khi FlameSegment va chạm với Character
        if (e instanceof Character) {
            ((Character) e).kill();
            return false;
        }
        return true;
    }
}
