package org.kdu.jsudoku.control;

import org.kdu.jsudoku.gui.Mainframe;

public class Clock extends Thread {
	private boolean stopped = false;
	private Mainframe parent;
	
	public Clock(Mainframe parent) {
		this.parent = parent;
	}
	
	public void run() {
		while (!stopped) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			parent.increaseTime();
		}
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}
