package boot;

import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;

public class Run {

	public static void main(String[] args) {
	
		MyModel m = new MyModel();
		MazeWindow win = new MazeWindow();
		Presenter p = new Presenter(m, win);
		
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
