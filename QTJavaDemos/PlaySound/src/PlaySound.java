/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-2001 Apple Computer, Inc.
 */
 
import quicktime.*;
import quicktime.io.*;
import quicktime.app.time.*;
import quicktime.app.view.*;
import quicktime.std.movies.*;
import quicktime.std.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This sample demonstrates how to play a sound or a midi file without using a QTCanvas.
 * All user interface elements are using AWT.
 */
public class PlaySound extends Frame
{
    Button playMidi, playSnd;
    Movie movie;
    
    static public void main (String[ ] args) {
        try {
            QTSession.open();
            PlaySound theSoundPlayer = new PlaySound();
        }
        catch (Exception e) {
            e.printStackTrace();
            QTSession.close();
        }
    }
    
    /**
     * Create the user interface
     */
    PlaySound () {
    	playMidi = new Button ("Play Midi File");
    	playSnd  = new Button ("Play Sound File");
    	
    	this.add (playMidi);
    	this.add (playSnd );
    	
    	playMidi.addActionListener( new Action() );
    	playSnd.addActionListener( new Action() );
    	
    	this.setLayout (new FlowLayout());
    	setVisible (true);
        
        addWindowListener(new WindowAdapter () {
                public void windowClosing (WindowEvent e) {
                        QTSession.close();
                        dispose();
                }
                public void windowClosed (WindowEvent e) {
                        System.exit(0);
                }
        } );
    
        pack();
    }
    	
    /**
     * The Action class responds to user interaction with the buttons
     */
    class Action implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            Object object = event.getSource();
            try {
                if (object == playMidi) {
                    if (movie != null) {
                        movie.setRate(0);
                        movie.setActive(false);
                        TaskAllMovies.removeMovie();
                    }
                    playFile ("jazz.mid");
                } else if (object == playSnd) {
                    if (movie != null) {
                        movie.setRate(0);
                        movie.setActive(false);
                        TaskAllMovies.removeMovie();
                    }
                    playFile ("sin440.aif");
                }
            }catch (StdQTException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * The playFile method loads and plays an audio file with the given name
     * or partial path. If a sound is already playing, it will be stopped before
     * the new sound is played.
     * @param fileName the name of the sound file to be played
     */
    public void playFile (String fileName) {
    	try {
            String soundLocation = QTFactory.findAbsolutePath(fileName).getPath();
			
            OpenMovieFile fileIn = OpenMovieFile.asRead (new QTFile(soundLocation));
            movie = Movie.fromFile (fileIn);
            TaskAllMovies.addMovieAndStart();
            movie.setActive(true);
            movie.setRate(1);
	 	} catch (IOException ioe) {
	 		ioe.printStackTrace();
	 	} catch (QTException qte) {
	 		qte.printStackTrace();
	 	}
    }
}