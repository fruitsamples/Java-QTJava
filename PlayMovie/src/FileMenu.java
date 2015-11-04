/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;


import quicktime.*;
import quicktime.io.*;
import quicktime.std.*;
import quicktime.std.movies.*;
import quicktime.app.players.*;
import quicktime.app.display.*;


public class FileMenu {
	PlayMovie myPlayMovie;
		
	public FileMenu (PlayMovie src) {
		this.myPlayMovie = src;
		
		// make the menu bar up
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu ("File");
		
		MenuItem openMenuItem = new MenuItem ("Open...");
				
		fileMenu.add(openMenuItem);
			
		menuBar.add (fileMenu);
		myPlayMovie.setMenuBar (menuBar);
					
		openMenuItem.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent event) {
				myPlayMovie.stopPlayer();
					
					// creat a movie through the file-open dialog of QT
				try {
					QTFile qtf = QTFile.standardGetFilePreview(QTFile.kStandardQTFileTypes);
					myPlayMovie.createNewMovieFromURL ("file://" + qtf.getPath());
				} catch (QTException e) {
					if (e.errorCode() != Errors.userCanceledErr)
		 				e.printStackTrace();
		 		}
		 	}
		});
	}
}			
