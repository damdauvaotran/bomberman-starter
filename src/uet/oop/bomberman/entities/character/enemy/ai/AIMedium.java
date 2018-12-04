package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
    Bomber _bomber;
    Enemy _e;

    public AIMedium(Bomber bomber, Enemy e) {
        _bomber = bomber;
        _e = e;
    }

    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        int bomberX = _bomber.getXTile();
        int bomberY = _bomber.getYTile();
        int enemyX = _e.getXTile();
        int enemyY = _e.getYTile();
        int diffX = bomberX - enemyX;
        int diffY = bomberY - enemyY;
        if (random.nextInt(2) == 0) {
            if (diffX> 0) {
                return 1;
            } else if (diffX < 0) {
                return 3;
            } else {
                if (diffY > 0) {
                    return 2;
                } else {
                    return 0;
                }
            }
        } else {
            if (diffY > 0) {
                return 2;
            } else if (diffY < 0) {
                return 0;
            } else {
                if (diffX > 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        }
    }

}
