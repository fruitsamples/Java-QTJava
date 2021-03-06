=============================================================================
QuickTime for Java SDK                                 Updated: 4 August 2003

Read Me Notes to "TimeCallbackDemo" Demo and Sample Code

=============================================================================
This sample code demonstrates how to use the various TimeCallBack features of QuickTime for Java including Extremes, rate changes, and time callbacks
=============================================================================

The minimum runtime requirements for this Sample Code are:

- Common:
	- QuickTime 5.0.1 or later 
	- QTJava 6.1 (QTJava.zip)
	- Swing

- MacOS:
	- OS X 10.2 or later
	- Java 1.3.1 or later

- Windows:
	- Windows 98, ME, 2000, XP or later
	- Java 1.2.1 or later
	- JRE/JDK from Sun Microsystems, Inc. recommended

=============================================================================
Media requirements for this Sample Code - user supplied movie

=============================================================================
Notes & Comments

This code uses the Movie Callback Feature of QTJava which is used to get notification upon various events that occur when a movie is played back. Ex. the Extreme Callbacks are used to get notification upon start and stop even, the rate callback is used to get notification about a change in rate etc..

=============================================================================
General Comments

- QTSession.open and close:

A QTSession.open will perform a gestalt check to ensure that QuickTime is present and is initialized. This is a required call before any QuickTime Java classes can be used.

When the user closes the window the program will quit, first calling QTSession.close to terminate QuickTime. It is necessary for programs to call QTSession.close if they have previously called QTSession.open in order to shut down QuickTime properly.


=============================================================================

QuickTime and QuickTime for Java are trademarks of Apple Computer, Inc.
(c) 1998-2003 Apple Computer Inc. All rights reserved.
