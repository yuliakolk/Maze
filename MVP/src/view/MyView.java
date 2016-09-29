package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   19/09/2016 
* 
* <h1>MyView</h1>
* this class manage the viewing on MVC design pattern
*/
public class MyView extends Observable implements View, Observer {

	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	
	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
		cli.addObserver(this);
	}
	
@SuppressWarnings("static-access")
	
	/**
	 * this method will get an input from the user and will return the input to the controller
	 * in this layer in mvc design pattern the view layer responsibility on the user input
	 * @return the input 
	 */
	public int OnInputRecieved()
	{
		
			String input = null;
			try {
				input=in.readLine();
				notifyObservers(input);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		return new Integer(0).parseInt(input);
		
	}
	
	/**
	 * this method will get an string input from the user and will return the input to the controller
	 * in this layer in mvc design pattern the view layer responsibility on the user input
	 * @return the input 
	 */
	public String OnStringRecieved()
	{

		String input = null;
		try {
			input=in.readLine();
			notifyObservers(input);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	return input;
	}
	

	
	@Override
	public void notifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready");
		out.flush();

	}

	@Override
	public void displayMaze(Maze3d maze, String name) {
		out.println(maze);
		out.flush();
	}
	
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}
	
	/**
	 * @param path
	 * this method will print all file in the path
	 */
	public void dir (String path)
	{
        File directory = new File(path);
        //get all the files from a directory
        File[] filesList = directory.listFiles();
        if (filesList==null){
        	out.println("There are no files in the path");
        }
        else{
        	for (File file : filesList){
        		out.println(file.getName());
        	}
        }
    }

	/**
	 * 
	 * @param input- witch cross by you want to use x,y,z
	 * @param name of the maze you want to cross by
	 */
	public void printCrossSectionBy(int[][] arr, int a, int b){
		for(int i=0; i<a; i++){
			for(int j=0;j<b;j++){
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}

	
	public void displaySolution(Solution sol){
		for (int i = sol.getStates().size()-1; i >= 0; i--) 
		{
			State<Position> state = (State<Position>)sol.getStates().get(i);
			
			if (state != null)
			{
				out.println(state.getValue());
				out.flush();
			}
		}
}
	
	@Override
	public void displayMessage(String msg) {
		out.println(msg);
		out.flush();		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}

	@Override
	public void displaySaveMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MazesReady(String mazes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayHint(Solution sol) {
		// TODO Auto-generated method stub
		
	}
}

		
