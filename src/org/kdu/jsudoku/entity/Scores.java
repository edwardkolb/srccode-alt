package org.kdu.jsudoku.entity;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class Scores implements Serializable {
	public static String DIV = ";";
	
	protected LinkedList<String> scores;
	
	protected Scores() {
		scores = new LinkedList<String>();
	}
	
	public void addScore(String value) {
		scores.add(value);
	}
	
	public LinkedList<String> getScores() {
		return scores;
	}
}
