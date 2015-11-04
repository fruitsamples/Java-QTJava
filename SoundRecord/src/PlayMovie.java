/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;

import quicktime.QTException;
import quicktime.io.*;
import quicktime.std.movies.Movie;
import quicktime.std.movies.MovieController;
import quicktime.util.QTUtils;
import quicktime.app.view.*;

public class PlayMovie extends Frame implements WindowListener {	
	
	private QTComponent qtc;

	public PlayMovie (QTFile src) {
		super (src.getName());
		try {
                        OpenMovieFile movieFile = OpenMovieFile.asRead(src);
			MovieController mc = new MovieController (Movie.fromFile (movieFile));
			movieFile.close();
			
			addNotify();
			Insets insets = getInsets();
			setBounds (0, 0, (insets.left + insets.right + mc.getBounds().getWidth()), (insets.top + insets.bottom + mc.getBounds().getHeight()));
			
                        qtc = QTFactory.makeQTComponent(mc);
                        add((Component) qtc);
                        
			addWindowListener(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void windowClosing (WindowEvent e) {
		dispose();
	}

	public void windowIconified (WindowEvent e) {}
	public void windowDeiconified (WindowEvent e) {}
	public void windowClosed (WindowEvent e) {}
	public void windowOpened (WindowEvent e) {}
	public void windowActivated (WindowEvent e) {}
	public void windowDeactivated (WindowEvent e) {}
}
