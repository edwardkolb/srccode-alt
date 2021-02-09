package org.kdu.jsudoku.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.kdu.jsudoku.Sudoku;
import org.kdu.jsudoku.entity.EasyScores;
import org.kdu.jsudoku.entity.HardScores;
import org.kdu.jsudoku.entity.MediumScores;
import org.kdu.jsudoku.entity.MyTextField;
import org.kdu.jsudoku.entity.Scores;
import org.kdu.jsudoku.gui.Mainframe;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBluer;

public class Controller {
	private static final String DATA = "data";
	private static final String DATA_SCORES_FILENAME = DATA + "/scores";

	private Mainframe mainframe;
	
	private EasyScores easyScores;
	private MediumScores mediumScores;
	private HardScores hardScores;
	
	public Controller() throws IOException, ClassNotFoundException {
		loadScores();
		mainframe = new Mainframe(this);
		mainframe.setVisible(true);
	}
	
	private void loadScores() throws IOException, ClassNotFoundException {
		File data = new File(DATA_SCORES_FILENAME);
		if (data.exists()) {
			readScores();
		} else {
			easyScores = new EasyScores();
			mediumScores = new MediumScores();
			hardScores = new HardScores();
			
			writeScores();
		}
	}

	private void readScores() throws IOException, ClassNotFoundException {
		FileInputStream fos = new FileInputStream(DATA_SCORES_FILENAME);
		ObjectInputStream oos = new ObjectInputStream(fos);

		easyScores = (EasyScores) oos.readObject();
		mediumScores = (MediumScores) oos.readObject();
		hardScores = (HardScores) oos.readObject();

		oos.close();
		fos.close();
	}

	private void writeScores() throws IOException {
		new File(DATA).mkdirs();
		FileOutputStream fos = new FileOutputStream(DATA_SCORES_FILENAME);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(easyScores);
		oos.writeObject(mediumScores);
		oos.writeObject(hardScores);

		oos.close();
		fos.close();
	}

	public int[][] generateMatrix() {
		return Sudoku.generateNumbers();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		PlasticLookAndFeel lnf = new PlasticLookAndFeel();
        PlasticLookAndFeel.setMyCurrentTheme(new DesertBluer());
        try {
            UIManager.setLookAndFeel(lnf);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
		new Controller();
	}

	public LinkedList<String[]> verify(int[][] right, MyTextField fields[][]) {
		LinkedList<String[]> ret = new LinkedList<String[]>();
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
				int value = Integer.parseInt(fields[i][j].getText());
				if (value != right[i][j]) {
					String[] place = new String[]{String.valueOf(i),String.valueOf(j)};
					ret.add(place);
				}
			}
		}
		return ret;
	}
	
	public boolean isBestScore(int type, String time) {
		String toSearch;
		try {
			if (type == Sudoku.EASY) {
				toSearch = easyScores.getScores().getLast();
			} else if (type == Sudoku.MEDIUM) {
				toSearch = mediumScores.getScores().getLast();
			} else {
				toSearch = hardScores.getScores().getLast();
			}
		} catch (NoSuchElementException e) {
			return true;
		}
		
		String lastTime = toSearch.split(Scores.DIV)[1];
		
		return isBetterTime(lastTime, time);
	}

	private boolean isBetterTime(String lastTime, String time) {
		int lastMins = Integer.parseInt(lastTime.split(":")[0]);
		int lastSecs = Integer.parseInt(lastTime.split(":")[1]);
		int newMins = Integer.parseInt(time.split(":")[0]);
		int newSecs = Integer.parseInt(time.split(":")[1]);
		
		if (newMins < lastMins) {
			return true;
		}
		
		if (newMins == lastMins && newSecs < lastSecs) {
			return true;
		}
		
		return false;
	}
}
