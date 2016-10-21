package model;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
	private String generateAlg;
	private String solveAlg;
	private Properties properties;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String,Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	private List<Thread> threads = new ArrayList<Thread>();

	public MyModel() {
		String generateAlg = PropertiesLoader.getInstance().getProperties().getGenerateMazeAlgorithm();
		String solveAlg = PropertiesLoader.getInstance().getProperties().getSolveMazeAlgorithm();
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		//executor = Executors.newFixedThreadPool(50);
		loadMazes();
		loadSolutions();
		
	}	
	
//	class GenerateMazeRunnable implements Runnable {
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
				
//				SimpleMaze3dGenerator generator = new SimpleMaze3dGenerator();
//				Maze3d maze = generator.generate(cols,rows, floors);
//				mazes.put(name, maze);
				
				setChanged();
				notifyObservers("display " + name);		
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
	
	public void solve(String name, String algo, Position curPos){
		
		Solution sol= new Solution();
		Maze3d maze= mazes.get(name);
		maze.setStartPosition(curPos);
		
		if (algo.equals("hint")) 
		{
			//creating a searcher with DFS searcher
			Searcher tester=new DFS();
			//saving the solution with the DFS algorithm on local var "sol"
//			mazes.get(name).setStartPosition(curPos);
			sol=tester.search(new SearchableMaze3d(maze));
			solutions.put(name, sol);
			setChanged();
			notifyObservers("display_hint " + name);
		}
		else
		{
			if ((algo.equals("BFS"))||(algo.equals("bfs"))||(algo.equals("Bfs")))
			{
				//creating a searcher with BFS searcher
				Searcher tester=new BFS();
				//saving the solution with the BFS algorithm on local var "sol"
				sol=tester.search(new SearchableMaze3d(maze));
			}
			else if (algo.equals("dfs")||algo.equals("DFS")||algo.equals("Dfs")) 
			{
				//creating a searcher with DFS searcher
				Searcher tester=new DFS();
				//saving the solution with the DFS algorithm on local var "sol"
				sol=tester.search(new SearchableMaze3d(maze));
			}
			
			solutions.put(name, sol);
			
			setChanged();
			notifyObservers("display_solution " + name);
		} 
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
		setChanged();
		notifyObservers("save_ready "+name);
}
	
	/**
	 * loads the maze from the file with the received filename, and creates it with the received name
	 * @param name of the maze
	 * @param fileName name of the file to load the maze from
	 * @throws IOException
	 */ 
	public void loadMaze(String name, String fileName) throws IOException{
		FileInputStream file = new FileInputStream(fileName);
		InputStream in=new MyDecompressorInputStream(file);
		int size = in.read();
		byte b[]=new byte[size];

		in.read(b);
		in.close();
		Maze3d loaded=new Maze3d(b);
		mazes.put(name, loaded);
		setChanged();
		notifyObservers("display "+name);
}
	
	@SuppressWarnings("unchecked")
	private void loadMazes() {
		
		File file = new File("mazes.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream oisMaze = null;
		Map<String, Maze3d> tmpMazes = new ConcurrentHashMap<String,Maze3d>();

		try {
			FileInputStream fisMaze = new FileInputStream("mazes.dat");
			//fisMaze.flush();
			oisMaze = new ObjectInputStream(new GZIPInputStream(fisMaze));
//			while(true){
//				if(oisMaze.read()==-1) break;
//				tmpMazes = (Map<String, Maze3d>)oisMaze.readObject();
//			}
			
			tmpMazes = (Map<String, Maze3d>)oisMaze.readObject();
			mazes = tmpMazes;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				oisMaze.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadSolutions() {
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream oisSol = null;
		Map<String, Solution<Position>> tmpSolutions = new ConcurrentHashMap<String, Solution<Position>>();
				
		try {
			FileInputStream fisSol = new FileInputStream("solutions.dat");
			//fisSol.close();
			oisSol = new ObjectInputStream(new GZIPInputStream(fisSol));
			
//			while(true){
//				if(oisSol.read()==-1) break;
//				tmpSolutions = (Map<String, Solution<Position>>)oisSol.readObject();
//			}
			
			tmpSolutions = (Map<String, Solution<Position>>)oisSol.readObject();
			solutions = tmpSolutions;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				oisSol.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	private void saveMazes() {
		ObjectOutputStream oosMaze = null;
		try {
			FileOutputStream fosMaze = new FileOutputStream("mazes.dat");
			oosMaze = new ObjectOutputStream(new GZIPOutputStream(fosMaze));
			//oosSol.flush();
			oosMaze.writeObject(mazes);	
			System.out.println(mazes);
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oosMaze.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveSolutions() {
		ObjectOutputStream oosSol = null;
		try {
			FileOutputStream fosSol = new FileOutputStream("solutions.dat");
			oosSol = new ObjectOutputStream(new GZIPOutputStream(fosSol));
			//oosSol.flush();
			oosSol.writeObject(solutions);	
			System.out.println(solutions);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oosSol.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * exits the application while closing all open files and threads
	 */
	public void exit() {
		saveSolutions();
		saveMazes();
		//saveSolAndMaze();
		executor.shutdownNow();
		
		}

	@Override
	public void getMazes() {
		String mazesNames = null ;
		for ( Map.Entry<String, Maze3d> entry : mazes.entrySet()) {
		    String key = entry.getKey();
		    mazesNames = key + " ";
		    }
		notifyObservers("mazes_ready "+ mazesNames);
		}
	

@Override
public void openXML(String file) {
	try {
		File fileName = new File(file);
		if (!fileName.exists())
			return;
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
		Properties loadedProperties = (Properties)decoder.readObject();
		decoder.close();
		Properties globalProperties = PropertiesLoader.getInstance().getProperties();
		globalProperties.setNumOfThreads(loadedProperties.getNumOfThreads());
		globalProperties.setGenerateMazeAlgorithm(loadedProperties.getGenerateMazeAlgorithm());
		this.generateAlg = loadedProperties.getGenerateMazeAlgorithm();
		globalProperties.setSolveMazeAlgorithm(loadedProperties.getSolveMazeAlgorithm());
		this.solveAlg = loadedProperties.getSolveMazeAlgorithm();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}
}

	


