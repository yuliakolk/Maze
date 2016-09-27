package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.Model;
import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {
	
		MazeWindow win = new MazeWindow();
		MyModel m = new MyModel(); 
		Presenter p = new Presenter(m, win);
		
//		WelcomeWindom win = new WelcomeWindom();
//		MyModel m = new MyModel(); 
//		Presenter p = new Presenter(m, win);
		
		win.addObserver(p);
		m.addObserver(p);
		
		win.start();
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter out = new PrintWriter(System.out);
//				
//		MyView view = new MyView(in, out);
//		MyModel model = new MyModel();
//		
//		Presenter presenter = new Presenter(model, view);
//		model.addObserver(presenter);
//		view.addObserver(presenter);
//				
//		view.start();
	}

}
