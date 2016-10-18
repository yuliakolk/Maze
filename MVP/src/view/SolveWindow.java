package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;


/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 *<h1>SolveWindow</h1>
 */
public class SolveWindow extends DialogWindow {
	
	private boolean bfs;
	private boolean dfs;
	
	
	@Override
	protected void initWidgets() {
		shell.setText("Solve window");
		shell.setSize(250, 100);		
		
		Image img = new Image(null, "images/back.jpg");
		shell.setBackgroundImage(img);	
		
		shell.setLayout(new GridLayout(2, false));	
				
		Button[] radios = new Button[2];

		radios[0]= new Button(shell, SWT.RADIO); 
	    radios[0].setSelection(true);
	    radios[0].setText("BFS");
	    radios[0].setBackgroundImage(img);	
	    radios[0].setBounds(10, 5, 75, 30);

	    radios[1] = new Button(shell, SWT.RADIO);
	    radios[1].setText("DFS");
	    radios[1].setBackgroundImage(img);	
	    radios[1].setBounds(10, 30, 75, 30);

		Button btnSolveMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnSolveMaze);
		btnSolveMaze.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		btnSolveMaze.setText("solve");
		
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				
				bfs = radios[0].getSelection();
				dfs =  radios[1].getSelection();

				if (bfs){
					notifyObservers("solve " + name + " bfs");
				}
				if (dfs){
					notifyObservers("solve " + name + " dfs");
				}
				
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
	
			}
		});
	}
	


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
		}
