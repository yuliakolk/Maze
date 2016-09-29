package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import algorithms.search.State;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 * The main window (VIEW)
 * 
 *<h1>MazeWindow</h1>
 */

public class MazeWindow extends BaseWindow implements View, Observer {
	private MazeDisplay mazeDisplay = null;
	private Character character;
	private int currentFloor = 0;
	private int[][] crossSection;
	private Maze3d currentMaze;
	private String mazeName;
	private ArrayList<String> mazesNames;

	private volatile Timer timer;

	public MazeWindow() {
		loadMazes();
	}

	private void loadMazes() {
		notifyObservers("getMazes");
	}

	@Override
	protected void initWidgets() {

		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);

		Image imgWin = new Image(null, "images/backWin.jpg");
		shell.setBackgroundImage(imgWin);

		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		buttons.setBackgroundImage(imgWin);

		// Add properties button
		Button btnProperties = new Button(buttons, SWT.PUSH);
		btnProperties.setText("Open properties");

		// Add open listener
		btnProperties.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				setChanged();
				notifyObservers("open_xml " + selected);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		//Add label for space
		Label lblSpace = new Label(buttons, SWT.NONE);
		lblSpace.setBackgroundImage(imgWin);
		
		// Add generate button
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		
		// Add generate listener
		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow();
				win.addObserver(MazeWindow.this);
				win.start(display);
				currentFloor= 0;
				mazeDisplay.count = 0;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		
		// Add hint button
		Button btnHintMaze = new Button(buttons, SWT.PUSH);
		btnHintMaze.setText("Hint");

		// Add hint listener
		btnHintMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currentMaze != null) {
					Position2D pos = mazeDisplay.getCharacterPos();
					Position curPos = new Position(pos.x, pos.y, currentFloor);
					currentMaze.setStartPosition(curPos);
					notifyObservers("hint " + mazeName);
				} else {
					JOptionPane.showMessageDialog(null, "Iligal input" + " there are no maze to solved", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		
		
		mazeDisplay = new MazeDisplay(shell, SWT.DOUBLE_BUFFERED);
		this.setMazeDisplay(mazeDisplay);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
		mazeDisplay.setBackgroundImage(imgWin);

		// Add solve button
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");

		// Add solve listener
		btnSolveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currentMaze != null) {
					Position currPos = new Position(mazeDisplay.getCharacterPos().x, mazeDisplay.getCharacterPos().x,
							currentFloor);
					currentMaze.setStartPosition(currPos);
					SolveWindow win = new SolveWindow();
					win.addObserver(MazeWindow.this);
					win.start(display);
				} else {
					JOptionPane.showMessageDialog(null, "Iligal input" + " there are no maze to solved", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		//Add labels for space
		Label lblSpace1 = new Label(buttons, SWT.NONE);
		lblSpace1.setBackgroundImage(imgWin);
		Label lblSpace2 = new Label(buttons, SWT.NONE);
		lblSpace2.setBackgroundImage(imgWin);

		// Add solve button
		Button btnSaveMaze = new Button(buttons, SWT.PUSH);
		btnSaveMaze.setText("Save maze");

		// Add solve listener
		btnSaveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mazeName != null) {
					notifyObservers("save_maze " + mazeName + " " + mazeName + ".maz");
				} else {
					JOptionPane.showMessageDialog(null, "Iligal input" + " there are no maze to saved", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		//Add label for space
		Label lblSpace3 = new Label(buttons, SWT.NONE);
		lblSpace3.setBackgroundImage(imgWin);

		//Add text for maze's name to load
		Text fileName = new Text(buttons, SWT.NONE);
		
		// Add load button
		Button btnLoadMaze = new Button(buttons, SWT.PUSH);
		btnLoadMaze.setText("Load maze");

		// Add load listener
		btnLoadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String name = fileName.getText();
				File file = new File(name + ".maz");
				if (!file.exists()) {
					JOptionPane.showMessageDialog(null, "Iligal input" + " The file not exists", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					notifyObservers("load_maze " + name + ".maz " + name);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		//Add label for space
		Label lblSpace4 = new Label(shell, SWT.NONE);
		lblSpace4.setBackgroundImage(imgWin);

		// Add exit button
		Button btnExitMaze = new Button(shell, SWT.PUSH);
		btnExitMaze.setText("Exit");

		// Add exit listener
		btnExitMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.dispose();
				notifyObservers("exit");
				if (timer != null) {
					timer.cancel();
				}
				// timerTask?
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		// Add exit listener (X)
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				btnExitMaze.setSelection(true);
			}
		});

		
		this.mazeDisplay.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {}

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
		int[][] mazeData = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
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
		this.mazeName = name;
	}

	@Override
	public void displayMaze(Maze3d maze, String name) {
		currentMaze = maze;
		mazeDisplay.setMaze(currentMaze);
		Position startPos = maze.getStartPosition();
		Position goalPos = maze.getGoalPosition();
		// System.out.println("start Position of the maze : "+ startPos);

		System.out.println(maze);
		this.crossSection = maze.getCrossSectionByZ(currentFloor);
		mazeDisplay.setCurrentFloor(currentFloor);
		int[][] mazeData = this.crossSection;
		mazeDisplay.setMazeData(mazeData);
		this.mazeName = name;

		// System.out.println("number of mazeData rows : "+maze.getRows());
		// System.out.println("number of mazeData cols : "+maze.getCols());

		// System.out.println("MazeData : ");
		// for (int x = 0;x < maze.getCols(); x++){
		// for (int y = 0; y < maze.getRows(); y++){
		// System.out.print(mazeData[y][x] + " ");
		// }
		// System.out.println();
		// }

		Position2D StartPos2D = new Position2D(startPos.getX(), startPos.getY());
		Position2D GoalPos2D = new Position2D(goalPos.getX(), goalPos.getY());
		// System.out.println(char2DPos);
		mazeDisplay.setStartPos(StartPos2D);
		mazeDisplay.setGoalPos(GoalPos2D);
		mazeDisplay.setCharacterPos(StartPos2D);
		// this.mazesNames.add(name);
	}

	@Override
	public void dir(String path) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displaySolution(Solution sol) {

		Stack<Position> stack = new Stack<>();
		ArrayList<State<Position>> states = sol.getStates();

		for (State<Position> state : states) {
			stack.push(state.getValue());
		}

		drawMazeAsync(stack);

	}

	public void displayHint(Solution sol) {

		Stack<Position> stack = new Stack<>();
		ArrayList<State<Position>> states = sol.getStates();

		for (State<Position> state : states) {
			stack.push(state.getValue());
		}
		
		Position currentPos = stack.pop();
		Position nextPos = stack.pop();

		int z = nextPos.getZ();

		if (z > mazeDisplay.getCurrentFloor()) {
			moveFloorUp();
		} else if (z < mazeDisplay.getCurrentFloor()) {
			moveFloorDown();
		}
		
		
		Position2D charNextPos = new Position2D(nextPos.x, nextPos.y);
		mazeDisplay.setCharacterPos(charNextPos);
		mazeDisplay.redraw();
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

	private void moveFloorUp() {
		Position2D pos = mazeDisplay.getCharacter().getPos();

		if (currentFloor + 2 < currentMaze.getFloors()) {
			if (currentMaze.getValue(pos.x, pos.y, currentFloor + 1) != 1) {
				crossSection = currentMaze.getCrossSectionByZ(currentFloor + 2);
				int[][] mazeData = crossSection;
				mazeDisplay.setCurrentFloor(currentFloor + 2);
				mazeDisplay.setMazeData(mazeData);
				currentFloor += 2;
				mazeDisplay.redraw();
				// mazeDisplay.drawMazeAsync();
			}
		}
	}

	private void moveFloorDown() {
		Position2D pos = mazeDisplay.getCharacter().getPos();
		if (currentFloor - 2 >= 0) {
			if (currentMaze.getValue(pos.x, pos.y, currentFloor - 1) != 1) {
				crossSection = currentMaze.getCrossSectionByZ(currentFloor - 2);
				int[][] mazeData = crossSection;
				mazeDisplay.setCurrentFloor(currentFloor - 2);
				mazeDisplay.setMazeData(mazeData);
				currentFloor -= 2;
				mazeDisplay.redraw();
				// mazeDisplay.drawMazeAsync();
			}
		}
	}

	@Override
	public void displaySaveMessage(String name) {
		MessageBox message = new MessageBox(shell, SWT.OK);
		message.setText("Save maze");

		message.setMessage("Maze " + name + " save successfully");
		message.open();

	}

	public void drawMazeAsync(Stack<Position> stack) {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				mazeDisplay.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (!stack.isEmpty()) {
							Position nextPos = stack.pop();
							if (nextPos != null) {
								int z = nextPos.getZ();

								if (z > mazeDisplay.getCurrentFloor()) {
									moveFloorUp();
								} else if (z < mazeDisplay.getCurrentFloor()) {
									moveFloorDown();
								}

								// System.out.println("row: " + nextPos.x + "
								// col: " + nextPos.y + " floor: " + nextPos.z);
								Position2D charNextPos = new Position2D(nextPos.x, nextPos.y);
								mazeDisplay.setCharacterPos(charNextPos);
								mazeDisplay.redraw();
							}
						} else {
							timer.cancel();
						}
					}
				});

			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 200);

	}

	@Override
	public void MazesReady(String mazes) {
		String names[] = mazes.split("\\s+");

		for (int i = 0; i < names.length; i++) {
			mazesNames.add(names[i]);
		}

	}
}
