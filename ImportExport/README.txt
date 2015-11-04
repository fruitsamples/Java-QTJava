=============================================================================
QuickTime for Java SDK                                 Updated: 1 April 2006

Read Me Notes to "ImportExport" Demo and Sample Code

=============================================================================
This demo program shows how to export a movie, using the user dialog to customise export settings, import a media file and reference a media file in a movie.
=============================================================================
The minimum build requirements for this Sample Code are:

- Common:
	- QuickTime 7 or later

- Mac OS X:
	- XCode 2.2 or later
	- Java 1.4.2 or later, 1.5.0 recommended

- Windows:
	- Windows 2000, XP or later
	- Java 1.4.2 or later, 1.5.0 recommended
	- JRE/JDK from Sun Microsystems, Inc. recommended
	- PATH environment variable includes java and javac

=============================================================================
Media requirements for this Sample Code - user supplied movie

=============================================================================
Notes & Comments
This code shows how to : export a movie, reference a already existing media and import media.

This code shows how to export a movie, using the user dialog to customise export settings. There are two ways this can be done - by far the easiest way is to use the Movie.convertToFile call which will allow you to show the default progress proc for a movie to provide visual feedback to the user of the progress of the export. Otherwise the application can call the MovieExporter directly to export movie data and define their own custom progress handling. Note that the movie export takes place on another thread, as the main Java AWT event thread should not be blocked and export can take some time.

A reference to already existing movie is created by adding the resources of that movie to a new movie. The new movie as all the tracks and other timing information of the referenced movie.Shortcut movies are movies that just contain a reference to another movie, we create this by passing a a Data ref out of a URL that references the movie to the call createShortcutMovieFile. If the application wanted to remove the dependency it could flatten the existing movie.

The media import is done by opening media files using OpenMovieFile.asRead.  A new Movie is created using Movie.fromFile which imports the media from the file. A MovieController is then created using the Movie, and a new QTComponent is created using QTFactory.makeQTComponent. The returned QTComponent is added to the Java AWT frame.

=============================================================================
General Comments

- QTSession.open and close:

A QTSession.open will perform a gestalt check to ensure that QuickTime is present and is initialized. This is a required call before any QuickTime Java classes can be used.

QTSession.close is called after the program has finished exporting


=============================================================================

QuickTime and QuickTime for Java are trademarks of Apple Computer, Inc.
(c) 1998 - 2006 Apple Computer Inc. All rights reserved.
