package view;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class DialogWindow extends BaseWindow{
	protected Shell shell;	
	
	protected abstract void initWidgets ();
	
	public void start(Display display) {		
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
	}
}