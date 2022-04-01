import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import controller.Controller;
import controller.FigureController;
import controller.StaticController;
import model.Config;
import model.Grid;
import view.GUIView;

public class Tetris {
	public static void main(String[] args) {
		Grid grid = new Grid();
		
		FigureController fc = new FigureController(grid);
		StaticController sc = new StaticController(grid);

		Controller controller = new Controller(fc, sc, grid);
		
		GUIView view = new GUIView(controller);
		
		javax.swing.SwingUtilities.invokeLater(view);
		
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (controller.blocksTouchTopEdge()) {
					int score = controller.getScore();
					int scoreLinesDeleted = controller.getScoreLinesDeleted();
					view.gameOver(score, scoreLinesDeleted);
					((Timer)evt.getSource()).stop();
				}
				controller.deleteLines();
				if (!controller.controlsFigure()) {
					controller.createFigure();
				}
				controller.moveFigureDown();
			}
		};
		new Timer(Config.SPEED, taskPerformer).start();
		
		System.out.println("GAME OVER");
	}
}
