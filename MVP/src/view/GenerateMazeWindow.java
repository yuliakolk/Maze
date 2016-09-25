package view;
import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

public class GenerateMazeWindow extends DialogWindow {
	
	//private MazeDisplay mazeDisplay;
	public int rows;
	public int cols;
	public int floors;
	
	@Override
	protected void initWidgets() {
		shell.setText("Generate maze window");
		shell.setSize(300, 200);		
				
		shell.setLayout(new GridLayout(2, false));	
				
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		
		//Text txtRows = new Text(shell, SWT.BORDER);
		Spinner spiRows = new Spinner(shell,SWT.NONE);
		spiRows.setMinimum(1);
		spiRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		
		//Text txtCols = new Text(shell, SWT.BORDER);
		Spinner spiCols = new Spinner(shell, SWT.NONE);
		spiCols.setMinimum(1);
		spiCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		
		//Text txtFloors = new Text(shell, SWT.BORDER);
		Spinner spiFloors = new Spinner(shell, SWT.NONE);
		spiFloors.setMinimum(1);
		spiFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Title");
				//msg.setMessage("Button was clicked");
				rows = Integer.parseInt(spiRows.getText());
				cols = Integer.parseInt(spiCols.getText());
				floors = Integer.parseInt(spiFloors.getText());
				
				notifyObservers("generate_3d_maze chen " + cols + " " + rows + " " + floors );
				msg.setMessage("Generating maze with cols: " + cols +" rows: " +rows +" floors: "+ floors);
				
				msg.open();
				
				if ((cols != 1) && (rows != 1) && (floors != 1)){
					//Maze3d maze = new Maze3d(cols, rows, floors);
					
					setChanged();
					
					//notifyObservers("generate_3d_maze");
					//maze.play = true;
					//maze.setStartPosition(maze.getStartPosition());
				}
				else{
					JOptionPane.showMessageDialog(null,
							"Iligal input" + " :enter cols, rows and floors in range: min 1 for each paramater",
							"Error", JOptionPane.WARNING_MESSAGE);
				}
				
				shell.close();
		}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
