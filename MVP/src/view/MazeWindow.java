package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import algorithms.mazeGenerators.Position;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import algorithms.search.State;

public class MazeWindow extends BaseWindow implements View, Observer {

	private MazeDisplay mazeDisplay = null;
	private Character character;
	private int currentFloor = 0;
	private int[][] crossSection;
	private Maze3d currentMaze;
	private String mazeName; 
	
	// ask guy 
	private volatile Timer timer;

	@Override
	protected void initWidgets() {
		
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);

		Text ttt =  new Text(buttons, SWT.NONE);
		ttt.setText("current Floor : " + currentFloor);
		
		
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");

		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow();
				win.addObserver(MazeWindow.this);
				win.start(display);
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);
		this.setMazeDisplay(mazeDisplay);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
		
		
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		btnSolveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currentMaze!= null){
				SolveWindow win = new SolveWindow();
				win.addObserver(MazeWindow.this);
				win.start(display);
			}else{
				JOptionPane.showMessageDialog(null,
						"Iligal input" + " there are no maze to solved",
						"Error", JOptionPane.WARNING_MESSAGE);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		Button btnSaveMaze = new Button(buttons, SWT.PUSH);
		btnSaveMaze.setText("Save maze");

		btnSaveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mazeName != null){
				notifyObservers("save_maze "+ mazeName +" " + mazeName +".maz" );
				}else{
					JOptionPane.showMessageDialog(null,
					"Iligal input" + " there are no maze to saved",
					"Error", JOptionPane.WARNING_MESSAGE);
					}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		Button btnLoadMaze = new Button(buttons, SWT.PUSH);
		btnLoadMaze.setText("Load maze");

		btnLoadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				notifyObservers("load_maze "+ mazeName +".maz" +" " + mazeName );

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		Button btnExitMaze = new Button(buttons, SWT.PUSH);
		btnExitMaze.setText("Exit");

		btnExitMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				notifyObservers("exit" );
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		this.mazeDisplay.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					mazeDisplay.moveRight();
					break;

				case SWT.ARROW_LEFT:
					mazeDisplay.moveLeft();
					break;

				case SWT.ARROW_UP:
					mazeDisplay.moveUp();
					break;

				case SWT.ARROW_DOWN:
					mazeDisplay.moveDown();
					break;

				case SWT.PAGE_UP:
					moveFloorUp();
					break;
					
				case SWT.PAGE_DOWN:
					moveFloorDown();
					break;
				}

				mazeDisplay.redraw();
			}
		});

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public MazeDisplay getMazeDisplay() {
		return mazeDisplay;
	}

	public void setMazeDisplay(MazeDisplay mazeDisplay) {
		int[][] mazeData = { { 0, 0, 0 },
							 { 0, 0, 0 }, 
							 { 0, 0, 0 } };
		this.mazeDisplay.setMazeData(mazeData);
	
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	@Override
	public void notifyMazeIsReady(String name) {
		this.mazeName =name;

	}

	@Override
	public void displayMaze(Maze3d maze, String name) {
		currentMaze = maze;
		mazeDisplay.setMaze(currentMaze);
		Position startPos = maze.getStartPosition();
		
		//System.out.println("start Position of the maze : "+ startPos);
		
        System.out.println(maze);
		this.crossSection = maze.getCrossSectionByZ(currentFloor);
		mazeDisplay.setCurrentFloor(currentFloor) ;
		int[][] mazeData = this.crossSection;
		mazeDisplay.setMazeData(mazeData);
        
        //System.out.println("number of mazeData rows : "+maze.getRows());
		//System.out.println("number of mazeData cols : "+maze.getCols());
		
//		System.out.println("MazeData : ");
//        	for (int x = 0;x < maze.getCols(); x++){
//        		for (int y = 0; y < maze.getRows(); y++){
//        		System.out.print(mazeData[y][x] + " ");
//        		}
//        	System.out.println();
//}
		
		Position2D char2DPos = new Position2D(startPos.getX(), startPos.getY());
		//System.out.println(char2DPos);
		mazeDisplay.setCharacterPos(char2DPos);
	}

	@Override
	public void dir(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySolution(Solution sol) {
		
		Stack<Position> stack = new Stack<>();		
		ArrayList<State<Position>> states = sol.getStates();
	
		for (State<Position> state : states)
		{
			stack.push(state.getValue());
		}
		
		drawMazeAsync(stack);
		
	}	

	@Override
	public void printCrossSectionBy(int[][] arr, int a, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMessage(String name) {
		notifyObservers("display " + name);

	}

	@Override
	public void update(Observable o, Object arg) {
		notifyObservers(arg);
	}
	
	private void moveFloorUp()
	{
		Position2D pos = mazeDisplay.getCharacter().getPos();

		if (currentFloor + 2 < currentMaze.getFloors()){
				if (currentMaze.getValue(pos.x, pos.y, currentFloor + 1) != 1) {
			currentFloor+=2;
			mazeDisplay.setCurrentFloor(currentFloor) ;
			crossSection = currentMaze.getCrossSectionByZ(currentFloor);
			int[][] mazeData = crossSection;
			mazeDisplay.setMazeData(mazeData);
			mazeDisplay.redraw();
//			mazeDisplay.drawMazeAsync();
			}
		}
	}

	private void moveFloorDown()
	{
		Position2D pos = mazeDisplay.getCharacter().getPos();
		if (currentFloor - 2 >= 0) {
				if (currentMaze.getValue(pos.x, pos.y, currentFloor - 1) != 1) {
			currentFloor-=2;
			mazeDisplay.setCurrentFloor(currentFloor) ;
			crossSection = currentMaze.getCrossSectionByZ(currentFloor);
			int[][] mazeData = crossSection;
			mazeDisplay.setMazeData(mazeData);
			mazeDisplay.redraw();
		//	mazeDisplay.drawMazeAsync();
			}
		}
	}

	@Override
	public void displaySaveMessage(String name) {
		MessageBox message = new MessageBox(shell, SWT.OK);
		message.setText("Save maze");
		
		message.setMessage("Maze "+ name + " save successfully");
		
	}
	
	public void drawMazeAsync(Stack<Position> stack)
	{
		TimerTask task = new TimerTask() {
				
		 @Override
		 public void run() {
		 mazeDisplay.getDisplay().syncExec(new Runnable() {
		
		 @Override
		 public void run() {
			 if (!stack.isEmpty())
			 {
				 Position nextPos = stack.pop();
				 if (nextPos != null)
				 {
					 int z = nextPos.getZ();
					 
					 if (z > mazeDisplay.getCurrentFloor())
					 {
						 moveFloorUp();
					 }
					 else if (z < mazeDisplay.getCurrentFloor())
					 {
						 moveFloorDown();
					 }
					 
					 //System.out.println("row: " + nextPos.x + " col: " + nextPos.y + " floor: " + nextPos.z);
					 Position2D charNextPos = new Position2D(nextPos.x, nextPos.y);
					 mazeDisplay.setCharacterPos(charNextPos);
					 mazeDisplay.redraw();
				 }					 
			 }
			 else
			 {
				 timer.cancel();
			 }
		 }
		 });
		
		 }
		 };
		 timer = new Timer();
		 timer.scheduleAtFixedRate(task, 0, 200);
		 		 
	}
}
