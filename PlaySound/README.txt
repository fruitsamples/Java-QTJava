=============================================================================
QuickTime for Java SDK                                 Updated: 1 April 2006
Read Me Notes to "PlaySound" Demo and Sample Code

=============================================================================
This sample demonstrates how to play a sound or a midi file without using a QTComponent. All user interface elements are using AWT
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
Media requirements for this Sample Code:

(1) QuickTime movies "jazz.mid" and "sin440.aif"

=============================================================================
Notes & Comments

The code displays a frame with two AWT buttons in a JAVA Frame. The Play Midi button would play midi media file upon clicking and the Play Sound button would stop the current midi file if its playing and play a sound file.
=============================================================================
General Comments

- QTSession.open and close:

A QTSession.open will perform a gestalt check to ensure that QuickTime is present and is initialized. This is a required call before any QuickTime Java classes can be used.

When the user closes the window the program will quit, first calling QTSession.close to terminate QuickTime. It is necessary for programs to call QTSession.close if they have previously called QTSession.open in order to shut down QuickTime properly.


=============================================================================

QuickTime and QuickTime for Java are trademarks of Apple Computer, Inc.
(c) 1998 - 2006 Apple Computer Inc. All rights reserved.
