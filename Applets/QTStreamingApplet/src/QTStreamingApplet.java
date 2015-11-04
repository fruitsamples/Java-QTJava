/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-2006 Apple Computer, Inc.

 */
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import quicktime.*;
import quicktime.std.*;
import quicktime.io.QTFile;
import quicktime.app.view.*;
import quicktime.std.movies.*;
import quicktime.std.movies.media.*;

public class QTStreamingApplet extends Applet {

	//this fix is for applets on Win 95/98 using 1.3 plugin. 
	//We create a lock that we would synchronize with the QTSession.open call in init() and
	//QTSession.close in destroy()  calls. this would make sure that QTSession.open() of the applet gets initialized
	//properly everytime it enters init().
	static Object lock =  new Object();
    private Vector urlTable;
    private TextField urlTextField;
    private PopupMenu pm;
    private QTComponent myQTCanvas;
    
	public void init () {
		try {
			synchronized(QTStreamingApplet.lock) {
				if (QTSession.isInitialized() == false)
					QTSession.open();
			}		
				// set up a QTCanvas which will disply its content 
				// at its original size of smaller and centered in the space given
				// to the QTCanvas when the applet is layed out
			setLayout (new BorderLayout());
			urlTable = new Vector();
			add (bottomPanel(), "South");
						
		} catch (QTException qtE) {
				//in this case the only QTException is in QTSession.open()
			throw new RuntimeException (qtE.getMessage());		
		}
	}	
	
	public void start () { 
	}
	
	public void stop () { 
            try{
		if (myQTCanvas != null)
			myQTCanvas.setMovie(null);
		} catch (QTException qtE) {
				//in this case the only QTException is in QTSession.open()
			throw new RuntimeException (qtE.getMessage());		
		}
	}
	
	public void destroy () {
		synchronized(QTStreamingApplet.lock) {
			QTSession.close();
		}	
	}
		
		
	/**
	 * creates drawable object from the URL the user has entered
	 * or from the menu item chosen
	 */
	private void createNewMovieFromURL(String selURL) throws QTException {
		String url = urlTextField.getText();	
                DataRef dRef = new DataRef(selURL);
                Movie mov = Movie.fromDataRef (dRef,StdQTConstants.newMovieActive);
                MovieController mc = new MovieController(mov);
                if (myQTCanvas == null) {
                    myQTCanvas = QTFactory.makeQTComponent(mc);
                    add((Component)myQTCanvas, "Center");
				}else {
                    myQTCanvas.setMovieController (mc);
                }
				// We have just added a component. 
				// so we must revalidate the Applet's Layout.
				validate();
	}
	
	public Component bottomPanel () {
		Panel row2 =  new Panel();
		row2.setLayout(new FlowLayout(FlowLayout.CENTER));
		row2.setBackground(Color.white);	
			
		urlTextField = new TextField ("rtsp://... Enter a URL to a remote movie", 40); //Enter URL to movie here
		urlTextField.setFont (new Font ("Dialog", Font.PLAIN, 10));
		urlTextField.setEditable (true);
		urlTextField.addActionListener (new ActionListener () {
			
			public void actionPerformed (ActionEvent ae) {
					try {
						createNewMovieFromURL(urlTextField.getText());
					} catch (QTException e) {
							//probably a non-fatal error that the Applet 
							//should deal with more informatively
						e.printStackTrace();
					}
			}
		});

		row2.add(urlTextField);
		
		return row2;
	}
	
    //workaround for java 1.3 bug - throws IllegalStateException on quit
    public java.awt.im.InputContext getInputContext() {
        return null;
    }

}
