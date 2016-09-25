package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

/**
* @author  Chen Hamdani and Yulia Kolk
* @version 1.0
* @since   19/09/2016 
* 
* <h1>model</h1>
* model interface 
* this interface responsible to all the calculating 
*/

public interface Model {
	void generateMaze (String name, int cols, int rows, int floors);
	Maze3d displayMaze(String name);
	Solution displaySolution(String name);
	void solve(String name, String algo);
	void exit();
	void getCrossSectionBy(String name, String t, int num);
	
}
