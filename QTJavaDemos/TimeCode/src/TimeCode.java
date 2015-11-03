/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: � 1996-1999 Apple Computer, Inc.

	Special Thanks to Darren Gibbs <darreng@cinebase.com> for the TimeCodeMedia code
 */

import java.awt.*;
import java.awt.event.*;

import quicktime.*;
import quicktime.io.*;
import quicktime.qd.*;
import quicktime.std.*;
import quicktime.std.image.*;
import quicktime.std.movies.*;
import quicktime.std.movies.media.*;
import quicktime.std.qtcomponents.*;
import quicktime.util.*;
import quicktime.app.view.*;

public class TimeCode extends Frame {	
	
	public static void main (String args[]) {
		try {
			QTSession.open ();			
			TimeCode tc = new TimeCode("QT in Java");
				// this will lay out and resize the Frame to the size of the Movie
			tc.pack();
			tc.show();
			tc.toFront();
		} catch (QTException e) {
				// catch a userCanceledErr and just exit the program
			if (e.errorCode() == Errors.userCanceledErr) {
				QTSession.close();
				System.exit(0);
			}
				// some other error occured - print out a stack trace
				// and close the QTSession
			e.printStackTrace();
			QTSession.close();
		}
	}

	TimeCode (String title) throws QTException {
		super (title);
		
			// prompt the user to select a movie file
		qtf = QTFile.standardGetFilePreview(QTFile.kStandardQTFileTypes);
			
			// open the selected file and make a Movie from it
		OpenMovieFile movieFile = OpenMovieFile.asRead(qtf);
		theMovie = Movie.fromFile (movieFile);
			
			// make a MovieController from the resultant Movie
			// enabling the keys so the user can interact with the movie with the keyboard
		mc = new MovieController (theMovie);
		mc.setKeysEnabled (true);
			
			// make a QTComponent so that the MovieController has somewhere to draw
			// and add it to the Frame
		qtc = QTFactory.makeQTComponent(mc);
		add ((Component) qtc);
				
			// add a file menu to add/remove time code to the movie
		new FileMenu (this);

			// add a Window Listener to this frame 
			// that will close down the QTSession, dispose of the Frame
			// which will close the window - where we exit
		addWindowListener(new WindowAdapter () {
			public void windowClosing (WindowEvent e) {
				QTSession.close();
				dispose();
			}

			public void windowClosed (WindowEvent e) { 
				System.exit(0);
			}
		});
	}
	
MovieController mc;
Movie theMovie;
QTComponent qtc;
QTFile qtf;

	public void goAway () {
		QTSession.close();
		dispose();
		System.exit(0);
	}

	public void addTimecodeToMovie () {		
		try {
			Track myTrack = theMovie.getIndTrackType (1, StdQTConstants.timeCodeMediaType, 
														StdQTConstants.movieTrackMediaType );
				//only allow one time code track in movie
			if (myTrack != null) 
				return;


			// Get the (first) visual track; this track determines the width of the new timecode track
			Track theVisualTrack = theMovie.getIndTrackType (1, StdQTConstants.visualMediaCharacteristic, 
															StdQTConstants.movieTrackCharacteristic );
			
			QDDimension dim = null;
			// Get movie and track attributes
			int movieTimeScale = theMovie.getTimeScale();

				// Create the timecode track and media
			if (theVisualTrack == null) {
			 	QDRect r = mc.getBounds();
			 	dim = new QDDimension (r.getWidth(), r.getHeight());
			} else {
				Media theVisualTrackMedia = Media.fromTrack (theVisualTrack);
				dim = theVisualTrack.getSize();
			}
			
			Track theTCTrack = theMovie.newTrack ((float)dim.getWidth(), (float)dim.getHeight(), 0);			
			TimeCodeMedia theTCMedia = new TimeCodeMedia (theTCTrack, movieTimeScale);
			TimeCoder theTimeCoder = theTCMedia.getTimeCodeHandler();

			// Set up a TimeCodeDef
			TimeCodeDef	myTCDef = new TimeCodeDef();
				//30 frames a second time code reading
			int tcdFlags = StdQTConstants.tc24HourMax;
			myTCDef.setFlags (tcdFlags);
			myTCDef.setTimeScale (3000);
			myTCDef.setFrameDuration (100);
			myTCDef.setFramesPerSecond (30);
			/*
				For drop frame 29.97 fps
				tcdFlags |= StdQTConstants.tcDropFrame;
				myTCDef.setTimeScale (2997);
			*/
			// Start the timecode at 0:0:0:0
			TimeCodeTime myTCTime = new TimeCodeTime (0, 0, 0, 0);

			// Change the text options to Green on Black.
			String myTCString = theTimeCoder.timeCodeToString (myTCDef, myTCTime);
			TCTextOptions myTCTextOptions = theTimeCoder.getDisplayOptions();
			int textSize = myTCTextOptions.getTXSize();
			myTCTextOptions.setForeColor (QDColor.green);
			myTCTextOptions.setBackColor (QDColor.black);
			theTimeCoder.setDisplayOptions (myTCTextOptions);

		// Figure out the timecode track geometry
			QDDimension tcDim = theTCTrack.getSize();
			tcDim.setHeight( textSize + 2 );
			theTCTrack.setSize (tcDim);
			if (dim.getHeight() > 0) {
				Matrix TCMatrix = theTCTrack.getMatrix();
				TCMatrix.translate (0, dim.getHeight());
				theTCTrack.setMatrix (TCMatrix);
			}
									
			// add a sample to the timecode track
			//
			// each sample in a timecode track provides timecode information for a span of movie time;
			// here, we add a single sample that spans the entire movie duration

			// the sample data contains a frame number that identifies one or more content frames
			// that use the timecode; this value (a long integer) identifies the first frame that
			// uses the timecode.  For our purposes this will probably always be zero, but it can't
			// hurt to go the full 9.
			int frameNumber = theTimeCoder.toFrameNumber (myTCTime, myTCDef);
			int[] frameNumberAr = { frameNumber };
			QTHandle myFrameNumHandle = new QTHandle (4, false);
			myFrameNumHandle.copyFromArray (0, frameNumberAr, 0, 1);

			// create and configure a new timecode description
			TimeCodeDescription myTCDescription = new TimeCodeDescription ();
			myTCDescription.setTimeCodeDef (myTCDef);			

			// edit the track media
			theTCMedia.beginEdits();	
			
				// since we created the track with the same timescale as the movie,
				// we don't need to convert the duration
				theTCMedia.addSample (myFrameNumHandle, 
										0, 
										myFrameNumHandle.getSize(), 
										theMovie.getDuration(), 
										myTCDescription, 
										1, 
										0);
			theTCMedia.endEdits();	
			
			theTCTrack.insertMedia (0, 0, theMovie.getDuration(), 1.0F);
			
			// this code saves the TimeCode to the movie
		/*
			OpenMovieFile outStream = OpenMovieFile.asWrite (qtf); 
			theMovie.addResource (outStream, movieInDataForkResID, qtf.getName());
			outStream.close();
		*/
		
			// Make the timecode visible
			int tcFlags = theTimeCoder.getFlags();
			tcFlags |= StdQTConstants.tcdfShowTimeCode;
			theTimeCoder.setFlags (tcFlags, StdQTConstants.tcdfShowTimeCode);
			
			changedMovie ();
		} catch (QTException err) {
			err.printStackTrace();
		}
	}

	public void deleteTimeCodeTracks () {
		try {
			Track myTrack = null;
			do {
				myTrack = theMovie.getIndTrackType (1, StdQTConstants.timeCodeMediaType, 
														StdQTConstants.movieTrackMediaType );
				if (myTrack != null) 
					theMovie.removeTrack( myTrack );

			} while (myTrack != null);
			
			// if you previous saved the time code to the movie
			// removing the time code track you also need to update the movie file
		/*
			OpenMovieFile outStream = OpenMovieFile.asWrite (qtf); 
			theMovie.addResource (outStream, movieInDataForkResID, qtf.getName());
			outStream.close();
		*/
			
			changedMovie();
		} catch (QTException err) {
			err.printStackTrace();
		}
	}
	
	private void changedMovie () throws QTException {
		// tell the controller that we have changed the movie
		mc.movieChanged();
                repaint();
		// this will resize the frame to the current (new) size of the movie
		// the QTCanvas will be resized as a result of this call
		pack();
	}
}





