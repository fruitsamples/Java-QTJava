/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: � 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.event.*;

import quicktime.*;
import quicktime.app.view.*;

public class JavaDrawing extends Frame {
	public static void main (String args[]) {
		try { 
			QTSession.open();
			JavaDrawing jd = new JavaDrawing("QT in Java");
			jd.pack();
			jd.show();
			jd.toFront();
		} catch (Exception e) {
			e.printStackTrace();
			QTSession.close();
		}
	}
		
	JavaDrawing (String title) throws Exception {
		super (title);
		
		setBackground (Color.lightGray);
					
// add a WindowListener to close the program down
		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent e) {
				QTSession.close();
				dispose();
			}
			public void windowClosed (WindowEvent e) { 
				System.exit(0);
			}
		});

		JavaPainter jp = new JavaPainter (this, QTFactory.findAbsolutePath ("duke/T3.gif").getCanonicalPath());
		jp.prepareImage();
		QTImageDrawer qid = new QTImageDrawer (jp, new Dimension (160, 110), Redrawable.kSingleFrame);
              
                QTComponent qtComponent = QTFactory.makeQTComponent(qid);
                add("Center", (Component) qtComponent);
	}
}
