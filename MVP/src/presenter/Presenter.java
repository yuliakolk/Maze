package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

/**
* @author  Chen Hamdani and Yulia Kolk
* @version 1.0
* @since   15/09/2016 
* 
* <h1>Prsenter</h1> 

*/
public class Presenter implements Observer {
	
	private View view;
	private Model model;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;
	
	public Presenter(Model model,View view){
		this.view = view;
		this.model = model;
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}
	
		@Override
		public void update(Observable o, Object arg) {
			String commandLine = (String)arg;
			
			String arr[] = commandLine.split(" ");
			String command = arr[0];			
			
			if(!commands.containsKey(command)) {
				view.displayMessage("Command doesn't exist");			
			}
			else {
				String[] args = null;
				if (arr.length > 1) {
					String commandArgs = commandLine.substring(
							commandLine.indexOf(" ") + 1);
					args = commandArgs.split(" ");							
				}
				Command cmd = commands.get(command);
				cmd.doCommand(args);	
			}
		}
	}
	


