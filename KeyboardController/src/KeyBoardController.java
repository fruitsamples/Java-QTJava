/*
 * QuickTime for Java SDK Sample Code
 *
 * Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.
 *
 */ 
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import quicktime.std.StdQTConstants;
import quicktime.*;
import quicktime.qd.*;
import quicktime.io.*;
import quicktime.std.image.*;
import quicktime.std.movies.*;

import quicktime.app.view.*;

public class KeyBoardController extends Frame implements StdQTConstants, QDConstants {
	
	public static void main(String args[]) {
		try { 
			QTSession.open();
			
			KeyBoardController te = new KeyBoardController("KeyBoard Controller");
			te.pack();
			te.show();
			te.toFront();	
		} catch (Exception e) {
			e.printStackTrace();
			QTSession.close();
		}
		
	}
	
	private Movie moov;
	private QTComponent qtComponent;
	private float savedRate = 1;
	
	KeyBoardController(String title) throws Exception {
		super (title);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				QTSession.close();
				dispose();
			}
			
			public void windowClosed (WindowEvent e) { 
				System.exit(0);
			}
		});
		
		QTFile qtFile = new QTFile (QTFactory.findAbsolutePath ("jumps.mov"));		
		OpenMovieFile movieFile = OpenMovieFile.asRead(qtFile);
		moov = Movie.fromFile (movieFile);
		
		qtComponent = QTFactory.makeQTComponent(moov);
		add("Center", (Component) qtComponent);
		
		// if the window gains focus, pass it on to the qtComponent
		addFocusListener( new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				((Component)qtComponent).requestFocus();
			}
		});
		
		((Component)qtComponent).addKeyListener(new KeyAdapter() {
			public void keyPressed (KeyEvent e) {
				try {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_SPACE:
						if (moov.getRate() != 0) 
							moov.setRate (0);
						else
							moov.setRate (savedRate);
						break;
					case KeyEvent.VK_UP:
						moov.setTimeValue (moov.getDuration());
						break;
					case KeyEvent.VK_DOWN:
						moov.setTimeValue (0);
						break;
					case KeyEvent.VK_LEFT:
						moov.setRate (-1);
						savedRate = -1;
						break;
					case KeyEvent.VK_RIGHT:
						moov.setRate (1);
						savedRate = 1;
						break;
					default:
						break;
				}
			} catch (QTException ee) {
				throw new QTRuntimeException (ee);
			}
		}
		public void keyReleased (KeyEvent e) {}
		public void keyTyped (KeyEvent e) {}
		});
		moov.setRate(1);
	}	
}	