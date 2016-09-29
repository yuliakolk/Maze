package view;

import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;


/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 *<h1>GenerateMazeWindow</h1>
 */
public class GenerateMazeWindow extends DialogWindow {
	
	public int rows;
	public int cols;
	public int floors;
	
	@Override
	protected void initWidgets() {
		shell.setText("Generate maze window");
		shell.setSize(300, 200);		
				
		Image img = new Image(null, getClass().getClassLoader().getResourceAsStream("images/back1.jpg"));
//		Image img = new Image(null, "images/back1.jpg");
		shell.setBackgroundImage(img);	
		
		shell.setLayout(new GridLayout(2, false));	
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setBackgroundImage(img);
		lblName.setText("Maze's name: ");
		
		Text txtName = new Text(shell, SWT.NONE);
		
		Label lblSpace1 = new Label(shell, SWT.NONE);
		lblSpace1.setBackgroundImage(img);
		Label lblSpace2 = new Label(shell, SWT.NONE);
		lblSpace2.setBackgroundImage(img);
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setBackgroundImage(img);
		lblRows.setText("Rows: ");
		
		//Text txtRows = new Text(shell, SWT.BORDER);
		Spinner spiRows = new Spinner(shell,SWT.NONE);
		spiRows.setMinimum(1);
		spiRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setBackgroundImage(img);
		lblCols.setText("Cols: ");
		
		//Text txtCols = new Text(shell, SWT.BORDER);
		Spinner spiCols = new Spinner(shell, SWT.NONE);
		spiCols.setMinimum(1);
		spiCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setBackgroundImage(img);
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
				msg.setText("Generate maze");
				name = txtName.getText();
				rows = Integer.parseInt(spiRows.getText());
				cols = Integer.parseInt(spiCols.getText());
				floors = Integer.parseInt(spiFloors.getText());

				if ((cols != 1) && (rows != 1) && (floors != 1)){
					notifyObservers("generate_3d_maze "+ name+ " " + cols + " " + rows + " " + floors );
					msg.setMessage("Generating maze "+ name + " with cols: " + cols +" rows: " +rows +" floors: "+ floors);
					msg.open();
					setChanged();

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

	public static String getName(){
		return name;
	}

}
