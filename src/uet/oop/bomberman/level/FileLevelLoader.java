package uet.oop.bomberman.level;

import sun.util.resources.cldr.bas.CalendarData_bas_CM;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) throws LoadLevelException {

        String levelFilePathName = "/levels/Level" + level + ".txt";
        InputStream in = FileLevelLoader.class.getResourceAsStream(levelFilePathName);
        if (in == null) {
            System.out.println(levelFilePathName);
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String firstLine;
        String[] levelInfo;
        try {
            firstLine = reader.readLine();
            levelInfo = firstLine.split(" ");
            if (levelInfo.length == 3) {
                this._level = Integer.parseInt(levelInfo[0]);
                this._height = Integer.parseInt(levelInfo[1]);
                this._width = Integer.parseInt(levelInfo[2]);
            } else {
                throw new LoadLevelException("Level is corrupted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        _map = new char[this._height][this._width];

        try {
            for (int i = 0; i < this._height; i++) {
                for (int j = 0; j < this._width; j++) {
                    _map[i][j] = (char) reader.read();

                }
                reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoadLevelException("Level is corrupted");
        }


    }

    @Override
    public void createEntities() {


        for (int y = 0; y < this._height; y++) {
            for (int x = 0; x < this._width; x++) {
                int position = y * _width + x;
                switch (_map[y][x]) {

                    case ' ': { // Grass tile
                        _board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    }

                    case '#': { // Wall tile
                        _board.addEntity(position, new Grass(x, y, Sprite.wall));
                        break;
                    }

                    case '*': { // Brick tile
                        _board.addEntity(position,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick))
                        );
                        break;
                    }

                    case '1': { // Balloon boy
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    }

                    case '2': { // Grass tile
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    }

                    case 'p': { // Player
                        _board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(x) + Game.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    }

                    case 'b': { // More boom, more fun
                        _board.addEntity(position,
                                new LayeredEntity(
                                        x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new BombItem(x, y, Sprite.powerup_bombs),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    }

                    case 'f': { // Flames item
                        _board.addEntity(position,
                                new LayeredEntity(
                                        x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new BombItem(x, y, Sprite.powerup_flames),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    }

                    case 's': { // Speed item
                        _board.addEntity(position,
                                new LayeredEntity(
                                        x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new BombItem(x, y, Sprite.powerup_speed),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    }

                    case 'x': { // Portal tile
                        _board.addEntity(position, new LayeredEntity(
                                x, y,
                                new Portal(x, y, Sprite.portal),
                                new Brick(x, y, Sprite.portal)
                        ));
                        break;
                    }

                    default: { // Load grass default
                        _board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    }
                }

            }
        }

//        //         thêm Wall
//        for (int x = 0; x < 20; x++) {
//            for (int y = 0; y < 20; y++) {
//                int pos = x + y * _width;
//                Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
//                _board.addEntity(pos, new Grass(x, y, sprite));
//            }
//        }

//        // thêm Bomber
//        int xBomber = 1, yBomber = 1;
//        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
//        Screen.setOffset(0, 0);
//        _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
//
//        // thêm Enemy
//        int xE = 2, yE = 1;
//        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
//        _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
//
//        // thêm Brick
//        int xB = 3, yB = 1;
//        _board.addEntity(xB + yB * _width,
//                new LayeredEntity(xB, yB,
//                        new Grass(xB, yB, Sprite.grass),
//                        new Brick(xB, yB, Sprite.brick)
//                )
//        );
//
//        // thêm Item kèm Brick che phủ ở trên
//        int xI = 1, yI = 2;
//        _board.addEntity(xI + yI * _width,
//                new LayeredEntity(xI, yI,
//                        new Grass(xI, yI, Sprite.grass),
//                        new SpeedItem(xI, yI, Sprite.powerup_flames),
//                        new Brick(xI, yI, Sprite.brick)
//                )
//        );
    }

}
