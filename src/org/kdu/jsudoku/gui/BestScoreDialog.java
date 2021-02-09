package org.kdu.jsudoku.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.kadu.xtra.AbsoluteConstraints;
import org.kadu.xtra.AbsoluteLayout;

public class BestScoreDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfName;
	private JButton btOk;
	
	public BestScoreDialog(Mainframe parent, String time) {
		super(parent, "Congratulations! Your time was '" + time + "'", true);

		tfName = new JTextField();
		btOk = new JButton("OK");
		btOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		getContentPane().setLayout(new AbsoluteLayout());
		
		JLabel label = new JLabel("Fill you name below:");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		getContentPane().add(label, new AbsoluteConstraints(6, 5, 150, 20));
		getContentPane().add(tfName, new AbsoluteConstraints(5, 30, 246, 20)); 
		getContentPane().add(btOk, new AbsoluteConstraints(100, 55, 60, 20));
		
		setSize(260, 110);
		setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public JTextField getTfName() {
		return tfName;
	}
	
}
