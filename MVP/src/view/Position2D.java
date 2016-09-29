package view;

import algorithms.mazeGenerators.Maze3d;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 *<h1>Position2D</h1>
 */
public class Position2D {
	public int x;
	public int y;	
	
	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	/**
	 * cheacks if the character can move up 
	 * @param maze - the current maze
	 * @param floor - the current floor
	 * @return true - if the character can move up , false - if the character can't move up
	 */
	public boolean validUp(Maze3d maze , int floor){
		if(maze!=null){
		if(floor+2 > maze.getFloors()-1){
			return false;
		}
		if (maze.getValue(x,y,floor + 1) == 0){
			return true;
		}
		}
		return false;
	}
	
	/**
	 * cheacks if the character can move down 
	 * @param maze - the current maze
	 * @param floor - the current floor
	 * @return true - if the character can move down , false - if the character can't move down
	 */
	public boolean validDown(Maze3d maze , int floor){
		if(maze!=null){
		if(floor - 2 < 0){
			return false;
		}
		if (maze.getValue(x, y, floor - 1 ) == 0){
			return true; 
		}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position2D))
			throw new IllegalArgumentException("Object must be position");
		
		Position2D p = (Position2D)obj;
		return x == p.x && y == p.y;			
	}
	@Override
	public String toString() {
		return "{" + x + "," + y + "}";
	}
}
