package presenter;

import java.util.ArrayList;
import java.util.HashMap;
import model.Model;
import model.MyModel;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.View;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   19/09/2016 
 *
 *<h1>CommandsManager</h1>
 */
public class CommandsManager {
	
	private Model model;
	private View view;
		
	/**
	 * CommandsManager constructor
	 * @param model 
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	/**
	 * @return a hashmap with all of the possible commands
	 */
	public HashMap <String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("dir", new Dir());
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("cross_section_ready", new CrossSectionReadyCommand());
		commands.put("save_maze", new saveMazeCommand());
		commands.put("save_ready", new SaveReadyCommand());
		commands.put("load_maze", new loadMazeCommand());
		commands.put("solve", new solveCommand());
		commands.put("getMazes", new getMazesCommand());
		commands.put("mazes_ready", new MazesReadyCommand());
		commands.put("solution_ready", new SolutionReadyCommand());
		commands.put("display_solution", new displaySolutionCommand());
		commands.put("hint", new HintCommand());
		commands.put("display_hint", new displayHintCommand());
		commands.put("open_xml", new OpenXML());
		commands.put("exit", new ExitCommand());
			
		return commands;
	}
	
	/**
	 * displays all files and folders in the received path 
	 */
	public class Dir implements Command {
		
		@Override
		public void doCommand(String[] args) {	
			String path=args[0];
			view.dir(path);
		}		
	}
	
	/**
	 * generates a maze by the received args	 
	 */
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int cols = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int floors = Integer.parseInt(args[3]);
			((MyModel)model).generateMaze(name, cols, rows, floors);
		}		
	}
	
	/**
	 * displays the maze by the received args (contains the name of the maze) 
	 */
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.displayMaze(name);
			view.displayMaze(maze,name);
		}
		
	}
	
	class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
		    view.notifyMazeIsReady(name);
		}
		
	}
	
	/**
	 * this command will print crossed 2d maze of the original 3d maze by the input parameter x for floor y for line and z for column 
	 */
	public class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String t = args[1];
			int num = Integer.parseInt(args[2]);
			model.getCrossSectionBy(name, t, num);
		}
		
	}
	
	
	public class CrossSectionReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			int[][]arr=((MyModel) model).getArr();
			int a = ((MyModel) model).getA();
			int b = ((MyModel) model).getB();
			view.printCrossSectionBy(arr, a, b);
		}
		
	}
	
	/**
	 * saves maze to a file by the received args - contains name of the maze  
	 *
	 */
	public class saveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args){
			String name = args[0];
			String fileName=args[1];
			try{
			((MyModel)model).saveMaze(name,fileName);
		}
			catch (Exception e) {
				view.displayMessage(e.toString());
			}
		
	}
	}
	
	class SaveReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			//String msg = "maze " + name + " is ready";
		view.displaySaveMessage(name);
		}
		
	}
	
	/**
	 * loads a maze by the received args - contains the name of the file 
	 */
	public class loadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String fileName = args[0];
			String name = args[1];
			try{
				((MyModel)model).loadMaze(name,fileName);
			}
				catch (Exception e) {
					view.displayMessage(e.toString());
				}
		}
		
	}
	
	
	/**	 
	 * solves the maze by the received args - contains the name of the maze
	 * receives an algorthm from the user the solve the maze by (DFS or BFS)
	 */
	public class solveCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String algo= args[1];
			//Position curPos =Position.class.cast(args[2]);
			Position curPos = toPos (args[2]);
			
			model.solve(name,algo,curPos);
		}
		private Position toPos(String str) {
			str=str.substring(1, str.length()-1);
			String[] arr =str.split(",");
			Position pos = new Position(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
			return pos;
		}
	}
	
	public class HintCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			//Position curPos =(Position)args[1];
			Position curPos = toPos (args[1]);
			
			model.solve(name,"hint",curPos);
		}

		private Position toPos(String str) {
			str=str.substring(1, str.length()-1);
			String[] arr =str.split(",");
			Position pos = new Position(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
			return pos;
		}
	}
	
	public class displayHintCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Solution sol = model.displaySolution(name);
			if (sol!=null){
			view.displayHint(sol);
			}
			else{
				view.displayMessage("There are no solution for maze "+name);
			}
		}
	}
	
	/**
	 * displays the solution of the maze in the receives args - contains the name of the maze
	 */
	public class displaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Solution sol = model.displaySolution(name);
			if (sol!=null){
			view.displaySolution(sol);
			}
			else{
				view.displayMessage("There are no solution for maze "+name);
			}
		}
	}
	
	/**
	 * displays the solution of the maze in the receives args - contains the name of the maze
	 */
	public class SolutionReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String msg = "solution of maze " + name + " is ready";
		view.displayMessage(msg);
		}
	}
	
	public class OpenXML implements Command {

		@Override
		public void doCommand(String[] args) {
			// TODO check input
			model.openXML(args[0]);
		}
	}
	
	public class getMazesCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			//String mazes = args[0];
		//view.displayMessage(msg);
			model.getMazes();
		}
	}
	
	public class MazesReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String mazes = args[0];

			view.MazesReady(mazes);
		}
	}
	
	/**
	 * closes the application while closing all open files and threads
	 */
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.exit();
		}
		
	}
}

