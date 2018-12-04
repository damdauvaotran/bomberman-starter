package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
	Board _board;

	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		_board = board;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
		if (e instanceof Bomber && Game.getNumberOfEnemy()==0 ){
			_board.nextLevel();
		}
		return false;
	}

}
