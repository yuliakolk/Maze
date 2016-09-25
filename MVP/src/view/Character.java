package view;
import java.awt.Graphics;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

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
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
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
