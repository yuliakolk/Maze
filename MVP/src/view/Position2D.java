package view;

import algorithms.mazeGenerators.Maze3d;

public class Position2D {
	public int x;
	public int y;	
	
	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public boolean validUp(Maze3d maze , int floor){
		if(maze!=null){
		if(floor >= maze.getFloors()-1){
			return false;
		}
		if (maze.getValue(x,y,floor + 1) == 0){
			return true;
		}
		}
		return false;
	}
	
	public boolean validDown(Maze3d maze , int floor){
		if(maze!=null){
		if(floor - 1 < 0){
			return false;
		}
		if (maze.getValue(x, y, floor - 1 ) == 0){
			return true; 
		}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "{" + x + "," + y + "}";
	}
}
