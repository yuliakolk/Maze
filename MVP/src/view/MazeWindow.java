package view;

import java.util.Observable;
import java.util.Observer;
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

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

public class MazeWindow extends BaseWindow implements View, Observer {

	private MazeDisplay mazeDisplay;
	private Character character;
	private int currentFloor = 0;
	private int[][] crossSection;
	private Maze3d currentMaze;

	@Override
	protected void initWidgets() {
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);

		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);

		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");

		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow();
				win.addObserver(MazeWindow.this);
				win.start(display);

				// notifyObservers("generate_3d_maze chen 5 5 3" );

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
				SolveWindow win = new SolveWindow();
				win.addObserver(MazeWindow.this);
				win.start(display);
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
				//Position2D pos = mazeDisplay.getCharacterPos();
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
		int[][] mazeData = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1 }, { 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
				{ 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1 },
				{ 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1 },
				{ 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1 } };
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
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMaze(Maze3d maze, String name) {
		currentMaze = maze;
		Position startPos = maze.getStartPosition();
System.out.println(maze);
		this.crossSection = maze.getCrossSectionByZ(currentFloor);
		int[][] mazeData = this.crossSection;
		mazeDisplay.setMazeData(mazeData);
System.out.println("data");
for (int y = 0; y < maze.getRows(); y++){
	for (int x = 0; x < maze.getCols(); x++)
		System.out.print(mazeData[y][x] + " ");
	System.out.println();
}
		Position2D char2DPos = new Position2D(startPos.getX(), startPos.getY());
		mazeDisplay.setCharacterPos(char2DPos);
	}

	@Override
	public void dir(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printCrossSectionBy(int[][] arr, int a, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMessage(String name) {
		// MessageBox msg = new MessageBox(shell, SWT.OK);
		// msg.setText("Title");
		// msg.setMessage("maze " + name + " is ready");
		notifyObservers("display " + name);

	}

	@Override
	public void update(Observable o, Object arg) {
		notifyObservers(arg);
	}
	
	private void moveFloorUp()
	{
		Position2D pos = mazeDisplay.getCharacter().getPos();

		if ((currentFloor + 1 < currentMaze.getFloors())
				&& (currentMaze.getValue(pos.x, pos.y, currentFloor + 1) != 1)) {
			currentFloor++;
			crossSection = currentMaze.getCrossSectionByZ(currentFloor);
			int[][] mazeData = crossSection;
			mazeDisplay.setMazeData(mazeData);
			mazeDisplay.redraw();
		}
	}

	private void moveFloorDown()
	{
		Position2D pos = mazeDisplay.getCharacter().getPos();
		if ((currentFloor - 1 >= 0) && (currentMaze.getValue(pos.x, pos.y, currentFloor - 1) != 1)) {
			currentFloor--;
			crossSection = currentMaze.getCrossSectionByZ(currentFloor);
			int[][] mazeData = crossSection;
			mazeDisplay.setMazeData(mazeData);
			mazeDisplay.redraw();
		}
	}
}
