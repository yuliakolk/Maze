package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

/**
* @author Chen Hamdani & Yulia Kolk
* @version 1.0
* @since   2016-09-19
* 
*  <h1>CLI</h1>
*/

public class CLI extends Observable {
	private BufferedReader in;
	private PrintWriter out;
	
	public CLI(BufferedReader in, PrintWriter out) { //Ctor
		this.in = in;
		this.out = out;		
	}
	
	private void printMenu() {
		out.print("Choose command: (");
//		for (String command : commands.keySet()) {
//			out.print(command + ",");
//		}
//		out.println(")");
		out.flush();
	}
	
	/**
	 * overriding the start method of thread
	 */
	public void start() {
		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				while (true) {
				
					printMenu();
					
					try {
						
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);			
							
							if ((commandLine.equals("exit"))||(commandLine.equals("EXIT"))){
								out.println("bye bye");
								
								in.close();
								out.close();
								break;
							}
						
					} catch (IOException e) {
						e.printStackTrace();
					}}}
			});
		
		thread.start();		
	}
	
	


	
}
