=============================================================================
QuickTime for Java SDK                                  Updated: 1 April 2006

Read Me Notes to "Duke Movie" Demo and Sample Code

=============================================================================
This demo program shows how to display any QuickTime content within a java.awt display space using a QTComponent. 

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
Media requirements for this Sample Code are:

(1) Tx.gif in the duke directory (where X is a number indicative of a frame order)
(2) QuickTime media files of the user's choice

=============================================================================
Notes & Comments

The duke animation at the top of the window is from the Duke animation sample applet.

The user selects any QuickTime media - the file is displayed using the QTFactory.makeQTComponent method.  

=============================================================================
General Comments

- QTSession.open and close:

A QTSession.open will perform a gestalt check to ensure that QuickTime is present and is initialized. This is a required call before any QuickTime Java classes can be used.

When the user closes the window the program will quit, first calling QTSession.close to terminate QuickTime. It is necessary for programs to call QTSession.close if they have previously called QTSession.open in order to shut down QuickTime properly.


=============================================================================

QuickTime and QuickTime for Java are trademarks of Apple Computer, Inc.
(c) 1998 - 2006 Apple Computer Inc. All rights reserved.
