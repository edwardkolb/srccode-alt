package org.kdu.jsudoku.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.kadu.xtra.AbsoluteConstraints;
import org.kadu.xtra.AbsoluteLayout;
import org.kdu.jsudoku.Sudoku;
import org.kdu.jsudoku.control.Clock;
import org.kdu.jsudoku.control.Controller;
import org.kdu.jsudoku.entity.MyTextField;

public class Mainframe extends JFrame implements KeyListener {

	private static final long serialVersionUID = -1754206106111596316L;
	
	private Controller controller;
	private MyTextField fields[][] = new MyTextField[Sudoku.SIZE][Sudoku.SIZE];
	private JPanel pnGame;
	private int[][] current = null;
	private JButton btVerify;
	private int currentLevel;
	private JLabel clock;
	private Clock clockThread;
	
	public Mainframe (Controller controller) {
		this.controller = controller;
		setTitle("jSudoku");
		setResizable(false);
		
		setJMenuBar(createMenubar());
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		this.getContentPane().setLayout(new AbsoluteLayout());
		
		pnGame = new JPanel();
		pnGame.setLayout(new AbsoluteLayout());
		pnGame.setBorder(new LineBorder(Color.BLACK, 1));
		this.getContentPane().add(pnGame, new AbsoluteConstraints(2, 2, 184, 184));
		
		btVerify = new JButton("Verify");
		btVerify.setFont(new Font("Tahoma", Font.BOLD, 11));
		btVerify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verifyValues();
			}
		});
		
		this.getContentPane().add(btVerify, new AbsoluteConstraints(2, 188, 145, 20));
		
		clock = new JLabel("00:00");
		clock.setFont(new Font("Tahoma", Font.BOLD, 11));
		clock.setEnabled(false);
		this.getContentPane().add(clock, new AbsoluteConstraints(154, 186, 35, 22));
		
		createTextFields();
		
		setSize(195, 260);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	protected void verifyValues() {
		/*if (!allDone()) {
			JOptionPane.showMessageDialog(this, "Please, complete all fields before verifying again!");
			return;
		}*/
		//LinkedList<String[]> wrong = controller.verify(current, fields);
		//if (wrong.size() == 0) {
			clockThread.setStopped(true);
			String time = clock.getText();
			JOptionPane.showMessageDialog(this, "Congratulations! You've finished in " + time + "!");
			if (controller.isBestScore(currentLevel, time)) {
				BestScoreDialog best = new BestScoreDialog(this, time);
				best.setVisible(true);
				System.out.println(best.getTfName().getText());
			}
			return;
		//}
		
		/*for (String[] place : wrong) {
			int i = Integer.parseInt(place[0]);
			int j = Integer.parseInt(place[1]);
			
			fields[i][j].setForeground(Color.RED);
		}
		
		JOptionPane.showMessageDialog(this, "Fields in RED show your mistakes. You've got " + (wrong.size()) + " mistake(s)!");*/
	}

	private boolean allDone() {
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
				if (fields[i][j].getText().equalsIgnoreCase("")){
					return false;
				}
			}
		}
		return true;
	}

	private void createTextFields() {
		final int width = 20;
		final int height = 20;
		int y = -height + 2;
		int x = 2;
		for (int i = 0; i < Sudoku.SIZE; i++) {
			y = y + height;
			x = 2;
			for (int j = 0; j < Sudoku.SIZE; j++) {
				fields[i][j] = new MyTextField(1);
				fields[i][j].addKeyListener(this);
				pnGame.add(fields[i][j], new AbsoluteConstraints(x, y, width, height));
				x = x + width;
			}
		}
		clearFields();
	}

	private JMenuBar createMenubar() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu game = new JMenu("Game");
		JMenu help = new JMenu("Help");
		
		JMenu newGame = new JMenu("New Game");
		
		JMenuItem easy = new JMenuItem("Easy");
		easy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateGame(Sudoku.EASY);
			}
		});
		JMenuItem medium = new JMenuItem("Medium");
		medium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateGame(Sudoku.MEDIUM);
			}
		});
		JMenuItem hard = new JMenuItem("Hard");
		hard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateGame(Sudoku.HARD);
			}
		});
		
		newGame.add(easy);
		newGame.add(medium);
		newGame.add(hard);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JMenuItem scores = new JMenuItem("Best Scores");
		scores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showScores();
			}
		});
		
		game.add(newGame);
		game.add(scores);
		game.addSeparator();
		game.add(exit);
		
		menubar.add(game);
		menubar.add(help);
		
		return menubar;
	}

	protected void showScores() {
		
	}

	protected void generateGame(int level) {
		clearFields();
		current = controller.generateMatrix();
		int i = 0;
		while (i < level) {
			int row = (int) (Math.random() * 9);
			int column = (int) (Math.random() * 9);
			if (!fields[row][column].isFixed()) {
				fields[row][column].setText(String.valueOf(current[row][column]));
				fields[row][column].setForeground(Color.BLUE);
				fields[row][column].setFixed(true);
				fields[row][column].setValue(current[row][column]);
				//fields[row][column].setEditable(false);
				i++;
			}
		}
		
		adjustTime("00:00");
		clock.setEnabled(true);
		
		if (level == Sudoku.HARD) {
			btVerify.setEnabled(false);
		} else {
			btVerify.setEnabled(true);
		}
		
		currentLevel = level;
		
		if (clockThread != null && clockThread.isAlive()) {
			clockThread.setStopped(true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		clockThread = new Clock(this);
		clockThread.start();
	}

	public void adjustTime(String string) {
		clock.setText(string);
	}
	
	public void increaseTime() {
		String[] parts = clock.getText().split(":");
		int mins = Integer.parseInt(parts[0]);
		int secs = Integer.parseInt(parts[1]);
		
		if (secs == 59) {
			secs = 0;
			mins++;
		} else {
			secs++;
		}
		
		String toBeSet = "";
		
		if (mins < 10) {
			toBeSet += "0";
		} 
		
		toBeSet += mins + ":";
		
		if (secs < 10) {
			toBeSet += "0";
		} 
		
		toBeSet += secs;
		
		clock.setText(toBeSet);
	}

	private void clearFields() {
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
				fields[i][j].setText("");
				fields[i][j].setForeground(Color.BLACK);
				fields[i][j].setFixed(false);
				fields[i][j].setValue(-1);
				fields[i][j].setFont(new Font("Tahoma", Font.BOLD, 15));
				if (i > 2 && i < 6) {
					if (j > 2 && j < 6) {
						fields[i][j].setBackground(new Color(220, 220, 220));
					}
				} else {
					if (j < 3 || j > 5) {
						fields[i][j].setBackground(new Color(220, 220, 220));
					}
				}
			}
		}
		btVerify.setEnabled(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		MyTextField obj = (MyTextField) e.getSource();
		
		if (current == null) {
			obj.setText("");
			return;
		}
		
		if (obj.isFixed()) {
			obj.setText(String.valueOf(obj.getValue()));
		} else {
			if (obj.getForeground().equals(Color.RED)) {
				obj.setForeground(Color.BLACK);
			}
		}
		
		if (obj.getText().length() > 1) {
			obj.setText(obj.getText().substring(0, obj.getText().length() - 1));
			return;
		}
		
		String text = obj.getText();
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e1) {
			obj.setText("");
		}
		
		if (currentLevel == Sudoku.HARD && allDone()) {
			LinkedList<String[]> wrong = controller.verify(current, fields);
			if (wrong.size() == 0) {
				JOptionPane.showMessageDialog(this, "Congratulations! You've won!");
			} else {
				JOptionPane.showMessageDialog(this, "You've got " + (wrong.size()) + " mistake(s)!");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
