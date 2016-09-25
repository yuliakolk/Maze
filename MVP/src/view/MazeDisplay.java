package view;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class MazeDisplay extends Canvas{
	
	private int[][] mazeData;
	private Position2D startPos;
	private Position2D goalPos;
	private Character character;

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

				   for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				      }

				
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
		
//		this.addKeyListener(new KeyListener() {
//			
//			@Override
//			public void keyReleased(KeyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void keyPressed(KeyEvent e) {
//				Position2D pos = character.getPos();
//				switch (e.keyCode) {
//				case SWT.ARROW_RIGHT:														
//					moveRight();
//					break;
//				
//				case SWT.ARROW_LEFT:					
//					moveLeft();
//					break;
//				
//				case SWT.ARROW_UP:					
//					moveUp();
//					break;
//					
//				case SWT.ARROW_DOWN:					
//					moveDown();
//					break;
//					
//				case SWT.PAGE_UP:					
//					break;
//				}
//				
//				redraw();
//			}
//		});
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
		   mazeData[pos.y][pos.x+1] != 1)
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
		   mazeData[pos.y + 1][pos.x] != 1)
		{					
			character.moveDown();
		}	
	}
}
