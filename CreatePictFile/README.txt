=============================================================================
QuickTime for Java SDK                                 Updated: 1 April 2006

Read Me Notes to "Create Pict File" Demo and Sample Code

=============================================================================
This demo program shows how to create a PICT and PICT file from drawing operations into a QDGraphics 

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

A QuickTime image file of the user's choice

=============================================================================
Notes & Comments

This code shows how to create a Pict file by  "recording" the results of a draw operation into a Pict object. Once the user selects an image file file it opens and displays the image in a new window. Clicking on the button at the top of the window saves the image as a pict file called results.pict in the application startup directory.

Every time the QDGraphics that is the recording source is drawn to this must be set to be the current GWorld or else the resultant PICT is corrupted. 

=============================================================================
General Comments

- QTSession.open and close:

A QTSession.open will perform a gestalt check to ensure that QuickTime is present and is initialized. This is a required call before any QuickTime Java classes can be used.

When the user closes the window the program will quit, first calling QTSession.close to terminate QuickTime. It is necessary for programs to call QTSession.close if they have previously called QTSession.open in order to shut down QuickTime properly.


=============================================================================

QuickTime and QuickTime for Java are trademarks of Apple Computer, Inc.
(c) 1998 - 2006 Apple Computer Inc. All rights reserved.
