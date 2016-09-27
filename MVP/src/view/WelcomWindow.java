//package view;
//
//import java.util.Observable;
//import java.util.Observer;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.RowLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
//
//import algorithms.mazeGenerators.Maze3d;
//import algorithms.search.Solution;
//
//public class WelcomeWindom implements View, Observer {
//
//	private MazeWindow MazeWindow = null;
//	
//	protected void initWidgets() {
//	GridLayout grid = new GridLayout(2, false);
//	shell.setLayout(grid);
//
//	Composite buttons = new Composite(shell, SWT.NONE);
//	RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
//	buttons.setLayout(rowLayout);
//
//	Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
//	btnGenerateMaze.setText("Generate maze");
//
//	btnGenerateMaze.addSelectionListener(new SelectionListener() {
//
//		@Override
//		public void widgetSelected(SelectionEvent arg0) {
//			GenerateMazeWindow win = new GenerateMazeWindow();
//			win.addObserver(WelcomeWindom.this);
//			win.start(display);
//
//		}
//
//		@Override
//		public void widgetDefaultSelected(SelectionEvent arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	});
//
//	MazeWindow = new MazeWindow();
//	this.setMazeWindow(MazeWindow);
////	MazeWindow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
////	MazeWindow.setFocus();
//
//	Button btnSolveMaze = new Button(buttons, SWT.PUSH);
//
//	btnSolveMaze.setText("Solve maze");
//
//	btnSolveMaze.addSelectionListener(new SelectionListener() {
//
//		@Override
//		public void widgetSelected(SelectionEvent arg0) {
//			SolveWindow win = new SolveWindow();
//			win.addObserver(WelcomeWindom.this);
//			win.start(display);
//		}
//
//		@Override
//		public void widgetDefaultSelected(SelectionEvent arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	});
//
//}
//
//	private void setMazeWindow(view.MazeWindow mazeWindow2) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(Observable o, Object arg) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void notifyMazeIsReady(String name) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void displayMaze(Maze3d maze, String name) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void dir(String path) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void displaySolution(Solution sol) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void printCrossSectionBy(int[][] arr, int a, int b) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void displayMessage(String msg) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//		
//	}
//}
