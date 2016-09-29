package view;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 * 
 *<h1>DialogWindow</h1>
 */
public abstract class DialogWindow extends BaseWindow{
	
	protected static String name;
	protected ArrayList<String> mazesNames = null;
	protected Shell shell;	
	
	protected abstract void initWidgets ();
	
	public void start(Display display) {		
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
	}
	
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}