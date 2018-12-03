package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

    protected Board _board;
    protected int _direction;
    private int _radius;
    protected int xOrigin, yOrigin;
    protected FlameSegment[] _flameSegments;

    /**
     * @param x         hoành độ bắt đầu của Flame
     * @param y         tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius    độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius, Board board) {
        xOrigin = x;
        yOrigin = y;
        _x = x;
        _y = y;
        _direction = direction;
        _radius = radius;
        _board = board;
        createFlameSegments();
    }

    /**
     * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        int flameSegmentsLength = calculatePermitedDistance();
        _flameSegments = new FlameSegment[flameSegmentsLength];

        /**
         * biến last dùng để đánh dấu cho segment cuối cùng
         */
        boolean last;

        // TODO: tạo các segment dưới đây

        int calculatedX = (int) _x;
        int calculatedY = (int) _y;
        for (int i = 0; i < flameSegmentsLength; i++) {
            switch (_direction) {
                case 0: {
                    calculatedY--;
                    break;
                }
                case 1: {
                    calculatedX++;
                    break;
                }
                case 2: {
                    calculatedY++;
                    break;
                }
                case 3: {
                    calculatedX--;
                    break;
                }
                default: {
                    break;
                }
            }
            _flameSegments[i] = new FlameSegment(calculatedX, calculatedY, _direction, false);
        }

        // In case of NullPointerException

        if (flameSegmentsLength > 0) {
            _flameSegments[flameSegmentsLength - 1].setLast(true);
        }
    }

    /**
     * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
     *
     * @return
     */
    private int calculatePermitedDistance() {
        // TODO: thực hiện tính toán độ dài của Flame
        int flameLength = 0;
        int calculatedX = (int) _x;
        int calculatedY = (int) _y;
        for (; flameLength <= _radius; flameLength++) {
            switch (_direction) {
                case 0: {
                    calculatedY--;
                    break;
                }
                case 1: {
                    calculatedX++;
                    break;
                }
                case 2: {
                    calculatedY++;
                    break;
                }
                case 3: {
                    calculatedX--;
                    break;
                }
                default: {
                    break;
                }
            }

            // Kill character actually here
            Entity entityAtFlame = _board.getEntity(calculatedX, calculatedY, null);
            if (entityAtFlame.collide(this)) {
                break;
            }


        }
        return flameLength;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (int i = 0; i < _flameSegments.length; i++) {
            if (_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
                return _flameSegments[i];
        }
        return null;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
        for (int i = 0; i < _flameSegments.length; i++) {
            _flameSegments[i].render(screen);
        }
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ

        return true;
    }
}
