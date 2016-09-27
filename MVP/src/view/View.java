
package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import presenter.Command;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   15/09/2016 
* 
* <h1>View</h1>
* this interface  manage the viewing on MVC design pattern
*/

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze,String name);
	void dir(String path);
	void displaySolution(Solution sol);
	void start();
	void printCrossSectionBy(int[][] arr, int a, int b);
	void displayMessage(String msg);
	void displaySaveMessage(String msg);
}
