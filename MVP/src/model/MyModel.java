package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellSelector;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import properties.Properties;
import properties.PropertiesLoader;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   19/09/2016 
* 
* <h1>MyModel</h1>
* MyModel class will manage all the calculating commands such as solving a maze, saving, loading Etc.
* MyModel recognizes only the Controller on the MVP
*/
public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	
	private Properties properties;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String,Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
//	private List<Thread> threads = new ArrayList<Thread>();

	public MyModel() {
		//properties = PropertiesLoader.getInstance().getProperties();
		//executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		executor = Executors.newFixedThreadPool(50);
		loadSolutions();
	}	
	
///	class GenerateMazeRunnable implements Runnable {
//
//		private int floors, rows, cols;
//		private String name;
//		private GrowingTreeGenerator generator;
//		
//		public GenerateMazeRunnable(int cols, int rows, int floors, String name) {
//			this.cols = cols;
//			this.rows = rows;
//			this.floors = floors;
//			this.name = name;
//		}
//		
//		@Override
//		public void run() {
//			generator = new GrowingTreeGenerator();
//			Maze3d maze = generator.generate(cols, rows, floors);
//			mazes.put(name, maze);	
//		}
//		
//		public void terminate() {
//			generator.setDone(true);
//		}		
//	}

	//Parameters of the crossSection
		private int [][] arr;
		private int a;
		private int b;
		
	public int[][] getArr(){
		return arr;
	}
	
	public int getA(){
		return a;
	}
	
	public int getB(){
		return b;
	}
	
	
	/**
	 * @param name of the maze
	 * @param number of columns
	 * @param number of rows
	 * @param number of floors
	 * creates a maze by the received parameters and adds it to the mazes Hashmap
	 */
	@Override
	public void generateMaze(String name, int cols, int rows, int floors) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				Maze3d maze = generator.generate(cols,rows, floors);
				mazes.put(name, maze);
				
				setChanged();
				notifyObservers("maze_ready " + name);		
				return maze;
			}
			
		});
	}

	/**
	 * returns the maze by the received name
	 */
	@Override
	public Maze3d displayMaze(String name) {
		return mazes.get(name);
	}
	
	/**
	 * returns the solution by the received name
	 */
	@Override
	public Solution displaySolution(String name) {
		return solutions.get(name);
	}
	
	public void getCrossSectionBy(String name,String t,int num){

		if (t.equals("x")||t.equals("X")) {
			arr = displayMaze(name).getCrossSectionByX(num);
			a = displayMaze(name).getRows();
			b = displayMaze(name).getFloors();
		}
		if (t.equals("y")||t.equals("Y")) {
			arr = displayMaze(name).getCrossSectionByY(num);
			a = displayMaze(name).getCols();
			b = displayMaze(name).getFloors();
		}
		if (t.equals("z")||t.equals("Z")) {
			arr = displayMaze(name).getCrossSectionByZ(num);
			a = displayMaze(name).getCols();
			b = displayMaze(name).getRows();
		}
		setChanged();
		notifyObservers("cross_section_ready");
	}
	
	/**
	 * @param maze's name
	 * @param algo to use for solving the maze - BFS/DFS
	 * solves the maze by the received name, solves it by the received algo (BFS or DFS)
	 */
	public void solve(String name, String algo){
		
		Solution sol=new Solution();
		
		if ((algo.equals("BFS"))||(algo.equals("bfs"))||(algo.equals("Bfs")))
		{
			//creating a searcher with BFS searcher
			Searcher tester=new BFS();
			//saving the solution with the BFS algorithm on local var "sol"
			sol=tester.search(new SearchableMaze3d(mazes.get(name)));
		}
		else
			if (algo.equals("dfs")||algo.equals("DFS")||algo.equals("Dfs")) 
				{
				//creating a searcher with DFS searcher
				Searcher tester=new DFS();
				//saving the solution with the DFS algorithm on local var "sol"
				sol=tester.search(new SearchableMaze3d(mazes.get(name)));
				}
		solutions.put(name, sol);
		
		setChanged();
		notifyObservers("solution_ready " + name);
	}

	/**
	 * saves  the maze by the received name to the file with the received filename
	 * @param name of the maze
	 * @param fileName name of the file to save the maze to
	 * @throws IOException
	 */ 
	public void saveMaze(String name, String fileName) throws IOException{
		OutputStream out=new MyCompressorOutputStream(new FileOutputStream(fileName));
		Maze3d maze= mazes.get(name);
		out.write(maze.toByteArray());
		out.flush();
		out.close();
}
	
	/**
	 * loads the maze from the file with the received filename, and creates it with the received name
	 * @param name of the maze
	 * @param fileName name of the file to load the maze from
	 * @throws IOException
	 */ 
	public void loadMaze(String name, String fileName) throws IOException{
		InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName));
		int size = in.read();
		byte b[]=new byte[size];
		in.read(b);
		in.close();
		Maze3d loaded=new Maze3d(b);
		mazes.put(name, loaded);
}
	
	@SuppressWarnings("unchecked")
	private void loadSolutions() {
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
			mazes = (Map<String, Maze3d>)ois.readObject();
			solutions = (Map<String, Solution<Position>>)ois.readObject();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * exits the application while closing all open files and threads
	 */
	public void exit() {
		executor.shutdownNow();
		saveSolutions();
		}
	}

	


