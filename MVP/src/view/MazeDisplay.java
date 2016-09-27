package view;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;


public class MazeDisplay extends Canvas{
	
	private int[][] mazeData;
	private Maze3d maze;
	private Position2D startPos;
	private Position2D goalPos;
	private Character character;
	private int currentFloor;

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		
		character = new Character();
		character.setPos(new Position2D(1,1));

						
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;
				
				   e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));

				   int width=getSize().x;
				   int height=getSize().y;

				   
				   int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

//				   System.out.println("number of mazeData cols : "+mazeData.length);
//				   System.out.println("number of mazeData rows : "+mazeData[0].length);
				   
				   for(int i=0;i<mazeData.length;i++)
					 for(int j=0;j<mazeData[i].length;j++){ 
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				          
				      }
				   character.draw(w, h, e.gc);
				
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   
				   int width = getSize().x;
				   int height = getSize().y;
				   
				   int w = width / mazeData[0].length;
				   int h = height / mazeData.length;

				   Position2D pos;
				   Image imgUp = new Image(null, "images/down.png");
				   Image imgDown = new Image(null, "images/up.png");
				   Image imgUpAndDown = new Image(null, "images/upAndDown.jpg");
				   
//				   System.out.println("number of mazeData cols : "+mazeData.length);
//				   System.out.println("number of mazeData rows : "+mazeData[0].length);
				   
				   for(int i=0;i<mazeData.length;i++)
						 for(int j=0;j<mazeData[i].length;j++){ 
					          int x=j*w;
					          int y=i*h;
					          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
					          else{
				          pos = new Position2D(j, i);
				          if((pos.validUp(maze, currentFloor))&&(pos.validDown(maze, currentFloor))){
				        	  //System.out.println("validUpAndDown "+pos);
				        	  e.gc.drawImage(imgUpAndDown, 0, 0, imgUpAndDown.getBounds().width, imgUpAndDown.getBounds().height, 
										w * pos.x, h * pos.y, w, h);
				          }
				          else if (pos.validUp(maze, currentFloor)){
				        	  //System.out.println("validUp "+pos);
				        	  e.gc.drawImage(imgDown, 0, 0, imgDown.getBounds().width, imgDown.getBounds().height, 
										w * pos.x, h * pos.y, w, h);
				          }
				          else if(pos.validDown(maze, currentFloor)){
				        	  //System.out.println("validDown " +pos);
				        	  e.gc.drawImage(imgUp, 0, 0, imgUp.getBounds().width, imgUp.getBounds().height, 
										w * pos.x, h * pos.y, w, h);
				          }
					          }
				      }
				   character.draw(w, h, e.gc);
				
			}
		});
		
		
	
//		TimerTask task = new TimerTask() {
//			
//			@Override
//			public void run() {	
//				getDisplay().syncExec(new Runnable() {					
//
//					@Override
//					public void run() {
//						
//						character.moveRight();
//						redraw();
//					}
//				});
//				
//			}
//		};
//		Timer timer = new Timer();
//		timer.scheduleAtFixedRate(task, 0, 500);
	}

	public int[][] getMazeData() {
		return mazeData;
	}

	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		try
		{
			getDisplay().syncExec(new Runnable() {					

				@Override
				public void run() {
										
					redraw();
				}
			});					
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}

	public Position2D getStartPos() {
		return startPos;
	}

	public void setStartPos(Position2D startPos) {
		this.startPos = startPos;
	}

	public Position2D getGoalPos() {
		return goalPos;
	}

	public void setGoalPos(Position2D goalPos) {
		this.goalPos = goalPos;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Position2D getCharacterPos() {
		return character.getPos();
	}

	public void setCharacterPos(Position2D startPos2) {
		this.character.setPos(startPos2);
	}	
	
	public void moveRight() {
		
		Position2D pos = character.getPos();
		if((mazeData[0].length > pos.x + 1) &&
		   mazeData[pos.y][pos.x + 1] != 1)
		{
			character.moveRight();	
		}		
	}
	
	public void moveLeft() {
		Position2D pos = character.getPos();
		if((0 <= pos.x - 1) &&
		   mazeData[pos.y][pos.x - 1] != 1)
		{
			character.moveLeft();
		}
	}
	
	public void moveUp() {
		Position2D pos = character.getPos();
		if((0 < pos.y - 1) &&
		   mazeData[pos.y - 1][pos.x] != 1)
		{
			character.moveUp();
		}
	}
	
	public void moveDown() {
		Position2D pos = character.getPos();
		if((mazeData.length > pos.y + 1) &&
		   mazeData[pos.y+1][pos.x] != 1)
		{					
			character.moveDown();
		}	
	}

	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public void displaySolution(Solution sol) {
		//Position2D pos = new Position2D(x, y);
		//ArrayList<State<Position>> states = sol.getStates();
		
//		for ( State<Position> state : states ) {
//			int row = state.getValue().getY();
//			int col = state.getValue().getX();
//			
//			character.setPos(new Position2D(col, row));
//			redraw();
//		}
		
//		for	(int i = states.size() - 1; i >=0; i--)
//		{
//			if (states.get(i) != null)
//			{
//				int row = states.get(i).getValue().getY();
//				int col = states.get(i).getValue().getX();
//				int floor = states.get(i).getValue().getZ();
//				
//				if 
//				drawCharacterAsync(new Position2D(col,row));
//			}
//		}
		
	}
	
	
	public void drawMazeAsync(Position2D pos)
	{
		 TimerTask task = new TimerTask() {
			
			 int cpinter;
			 @Override
			 public void run() {
			 getDisplay().syncExec(new Runnable() {
			
			 @Override
			 public void run() {	
				 System.out.println(pos);
				 character.setPos(pos);
				 redraw();
				 cancel();
			 }
			 });
			
			 }
			 };
			 Timer timer = new Timer();
			 timer.scheduleAtFixedRate(task, 0, 1500);
			 //timer.cancel();
	}
}
