/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: � 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;

import quicktime.qd.*;
import quicktime.*;
import quicktime.std.StdQTConstants;
import quicktime.std.image.GraphicsImporter;
import quicktime.std.movies.*;
import quicktime.io.*;
import quicktime.util.*;

import quicktime.app.view.*;
import quicktime.util.*;
import quicktime.std.image.GraphicsMode;
import javax.swing.*;

import ip.*;

/** Draws images that are produced by the QTImageProducer that uses a MoviePlayer as the source
 */
public class ImageProducing extends JFrame implements MovieDrawingComplete, QDConstants, StdQTConstants {		
	public static void main (String args[]) {
		try {
			QTSession.open();
			QTFile f1 = new QTFile (QTFactory.findAbsolutePath ("jumps.mov").getPath());			
			ImageProducing pm = new ImageProducing (f1);
			pm.pack();
			pm.show();
			pm.toFront();
		} catch (Exception e) {
			e.printStackTrace();
			QTSession.close();
		}
	}
	
	ImageProducing (QTFile movFile) throws QTException {
		super ("Consumer");

		OpenMovieFile openMovieFile = OpenMovieFile.asRead(movFile);
		myMovie = Movie.fromFile (openMovieFile);
		myMovie.getTimeBase().setFlags (loopTimeBase);
		MoviePlayer moviePlayer = new MoviePlayer (myMovie);
		QDRect r = moviePlayer.getDisplayBounds();
		Dimension d = new Dimension (r.getWidth(), r.getHeight());
		imgProducer = new QTImageProducer (moviePlayer, d);

			//this tells us that the movie has redrawn and 
			//we use this to redraw the QTImageProducer - which will 
			//supply more pixel data to its registered consumers
		myMovie.setDrawingCompleteProc (movieDrawingCallWhenChanged, this);

		JPanel panel = new JPanel (new BorderLayout());
		getContentPane().add ("Center", panel);
		panel.add ("South", new ControlPanel (moviePlayer));

		IPJComponent canvas = new IPJComponent (d, imgProducer);
		panel.add("Center", canvas);
                                        
		addWindowListener(new WindowAdapter () {
			public void windowClosing (WindowEvent e) {
				try {
					//have to remove a callback we install
					myMovie.removeDrawingCompleteProc();
				} catch (QTException ex) {}
				QTSession.close();
				dispose();
			}
			public void windowClosed (WindowEvent e) { 
				System.exit(0);
			}
		});
	}	

	QTImageProducer imgProducer;
	Movie myMovie;
	
	public int execute (Movie myMovie) {
		try {
			imgProducer.updateConsumers (null);
		} catch (QTException e) {
			return e.errorCode();
		}
		return 0;
	}

	static class IPJComponent extends JComponent {
		IPJComponent (Dimension prefSize, QTImageProducer ip) {
			pSize = prefSize;
			img = createImage (ip);
			prepareImage (img, this);
		}
		
		private Dimension pSize;
		private Image img;
		
	    public Dimension getPreferredSize() {
	    	return pSize;
	    }

		public void paint (Graphics g) {
			g.drawImage (img, 0, 0, pSize.width, pSize.height, this);
		}
		// stops flicker as we have no background color to erase
		public void update (Graphics g) {
			paint (g);
		}
	}
}

