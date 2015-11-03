/*
 * QuickTime for Java SDK Sample Code

   Usage subject to restrictions in SDK License Agreement
 * Copyright: © 1996-1999 Apple Computer, Inc.

 */
import java.awt.*;
import java.awt.event.*;

import quicktime.*;
import quicktime.std.StdQTConstants;
import quicktime.std.image.GraphicsImporter;
import quicktime.io.QTFile;

import quicktime.app.view.*;

/** QuickTime Graphics Importer and Codec demo */
public class ImageFileDemo extends Frame implements StdQTConstants {		
	
	public static void main (String args[]) {
		try {
			QTSession.open();

			int[] fileTypes = { kQTFileTypeGIF, kQTFileTypeJPEG, kQTFileTypePicture };
			QTFile qtf = QTFile.standardGetFilePreview (fileTypes);

			ImageFileDemo ifd = new ImageFileDemo (qtf);
			ifd.pack();
			ifd.show();
			ifd.toFront();
		} catch (QTException e) {
			if (e.errorCode() != Errors.userCanceledErr)
				e.printStackTrace();
			QTSession.close();
		}
	}

	ImageFileDemo (QTFile qtf) throws QTException {
		super (qtf.getName());
		
                GraphicsImporter gi = new GraphicsImporter(qtf);
                QTComponent qtComponent = QTFactory.makeQTComponent(gi);

                add ((Component) qtComponent, "Center");

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
}
