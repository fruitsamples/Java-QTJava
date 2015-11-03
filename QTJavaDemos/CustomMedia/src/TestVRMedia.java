/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
import quicktime.*;
import quicktime.io.*;
import quicktime.std.movies.*;
import quicktime.std.movies.media.*;

import quicktime.app.*;
import quicktime.std.*;

import com.vr.*;
public class TestVRMedia extends java.applet.Applet implements StdQTConstants {
	public static void main (String[] args) {
		new TestVRMedia().init();
	}
	
	public void init () {
		try { 
			QTSession.open();
			int [] mf = { kQTFileTypeMovie };
            QTFile qtf = null;
            try {
                qtf = QTFile.standardGetFilePreview(mf);
			}catch (QTIOException ex) {
                System.out.println ("Application needs media file to run. Quitting....");
                QTSession.close();
                System.exit(1);
            }
            
            
			OpenMovieFile movieFile = OpenMovieFile.asRead(qtf);
			Movie mov = Movie.fromFile (movieFile);

			mov.preroll (0, 1);
			int n = mov.getTrackCount();
			int nNC = 0;
				
			System.out.println ("numTracks:" + n);
			VRMedia.registerMediaType();
			for (int i = 1; i <= n; i++) {
				Media m = Media.getTrackMedia (mov.getIndTrack (i));
				System.out.println (m);
				SampleDescription sd = m.getSampleDescription (1);
				System.out.println (sd);
			}
	 	} catch (Exception e) {
				e.printStackTrace();
		} finally {
			QTSession.close();
		}
	}
}		
