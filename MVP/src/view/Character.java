package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 *<h1>Character</h1>
 */
public class Character {
	private Position2D pos;
	private Image img;
	
	
	public Character() {
		img = new Image(null, "images/character.jpg");
	}

	public Position2D getPos() {
		return pos;
	}

	public void setPos(Position2D pos) {
		this.pos = pos;
	}
	
	/**
	 * draws the character.
	 * @param cellWidth
	 * @param cellHeight
	 * @param gc
	 */
	public void draw(int cellWidth, int cellHeight, GC gc) {

		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	/**
	 * draws the character when it arraives at the goal position.
	 * @param cellWidth
	 * @param cellHeight
	 * @param gc
	 */
	public void drawGoal(int cellWidth, int cellHeight, GC gc) {
		   Image imgGoal = new Image(null, "images/goal.jpg");
		gc.drawImage(imgGoal, 0, 0, imgGoal.getBounds().width, imgGoal.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}

	
	public void moveRight() {
		pos.x++;
	}
	
	public void moveLeft() {
		pos.x--;
	}
	
	public void moveUp() {
		pos.y--;
	}
	
	public void moveDown() {
		pos.y++;
	}

}
