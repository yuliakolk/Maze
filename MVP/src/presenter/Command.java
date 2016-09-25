package presenter;

/**
* @author Chen Hamdani & Yulia Kolk 
* @version 1.0
* @since   15/09/2016 
* 
* <h1>Command</h1>
* Each class that will implements this interface must override the doCommand method
*/
public interface Command {
	
	void doCommand(String[] args);
	
}
