package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class SolveWindow extends DialogWindow {
	
	@Override
	protected void initWidgets() {
		shell.setText("Solve window");
		shell.setSize(200, 100);		
				
		shell.setLayout(new GridLayout(2, false));	
				
		
		Button[] radios = new Button[2];

		radios[0]= new Button(shell, SWT.RADIO); 
	    radios[0].setSelection(true);
	    radios[0].setText("BFS");
	    radios[0].setBounds(10, 5, 75, 30);

	    radios[1] = new Button(shell, SWT.RADIO);
	    radios[1].setText("DFS");
	    radios[1].setBounds(10, 30, 75, 30);

		Button btnSolveMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnSolveMaze);
		btnSolveMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnSolveMaze.setText("solve");
	
			
			
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}

}
