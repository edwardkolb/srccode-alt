package org.kdu.jsudoku.entity;

import javax.swing.JTextField;

public class MyTextField extends JTextField {

	private static final long serialVersionUID = -2960597312030747231L;
	private boolean fixed;
	private int value;
	
	public MyTextField(int i) {
		super(i);
	}
	
	public boolean isFixed() {
		return fixed;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
