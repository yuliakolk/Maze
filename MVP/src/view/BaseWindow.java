package view;

import java.util.Observable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   29/09/2016 
 *  
 *<h1>BaseWindow</h1>
 */
public abstract class BaseWindow extends Observable implements Runnable{
	protected Display display;
	protected Shell shell;	
	
	protected abstract void initWidgets();
	
	public void start() {
		display = new Display();
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
		
		// main event loop
		while(!shell.isDisposed()){ // window isn't closed
			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if(!display.readAndDispatch()){ // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}
		} // shell is disposed
		display.dispose(); // dispose OS components
	}
	
	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}
