/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;

/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.
 */

import quicktime.*;
import quicktime.io.*;
import quicktime.std.*;
import quicktime.std.movies.*;
import quicktime.app.players.*;
import quicktime.app.display.*;


public class FileMenu {
	TimeCode myTimeCode;
	
	public FileMenu (TimeCode src) {
		this.myTimeCode = src;
		
		// make the menu bar up
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu ("File");
		
		MenuItem addMenuItem = new MenuItem ("Add TimeCode Track");
		MenuItem removeMenuItem = new MenuItem("Remove TimeCode Track");
		MenuItem quitMenuItem = new MenuItem("Quit");
		
		fileMenu.add(addMenuItem);
		fileMenu.add(removeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(quitMenuItem);
			
		menuBar.add (fileMenu);
		myTimeCode.setMenuBar (menuBar);
					
		addMenuItem.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent event) {
		 		myTimeCode.addTimecodeToMovie();
		 	}
		});
		removeMenuItem.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent event) {
		 		myTimeCode.deleteTimeCodeTracks();
		 	}
		});
		quitMenuItem.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent event) {
					// closes down QT and quits
				myTimeCode.goAway();
		 	}
		});
	}
}			
