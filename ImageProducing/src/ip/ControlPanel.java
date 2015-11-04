/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
package ip;

import java.awt.*;
import java.awt.event.*;

import quicktime.app.view.*;
import quicktime.*;

import javax.swing.*;

public class ControlPanel extends JPanel {

	public ControlPanel (MoviePlayer mp) {
		setLayout (new FlowLayout(FlowLayout.CENTER, 1, 2));
		setFont (new Font("Helvetica", Font.BOLD, 10));

		JButton startButton = new JButton("Start");
		JButton stopButton = new JButton("Stop");

		startButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				try {
					moviePlayer.setRate(1);				
				}
				catch (QTException e) {
					e.printStackTrace();
				}
			}
		});
		stopButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				try {
					moviePlayer.setRate(0);				
				}
				catch (QTException e) {
					e.printStackTrace();
				}
			}
		});
		this.moviePlayer = mp;

		add (startButton);
		add (stopButton);
	}
	
	MoviePlayer moviePlayer;
}
