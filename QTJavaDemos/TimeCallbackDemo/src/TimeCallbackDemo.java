import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.IOException;
import quicktime.app.time.*;
import quicktime.std.clocks.*;
import quicktime.*;
import quicktime.io.*;
import quicktime.qd.*;
import quicktime.std.*;
import quicktime.std.movies.*;
import quicktime.std.movies.media.*;
import quicktime.app.view.*;

//Note : When using callback it should be noted that the callbacks need to be properly cleanedup when thier use is over.
// You should take proper care to just cancel the callback if you wish to reschedule it . To do this you should call just cancel() .
// You should take proper care to call cancelAndCleanup() when you are quitting the application itself, this will ensure proper clean up
// of any memory and thread hanging around.
/** 
 * This sample code demonstrates how to use the various TimeCallBack features of QuickTime for Java 
 * including Extremes, rate changes, and time callbacks
 */

public class TimeCallbackDemo extends Frame implements Errors {	
/* main entry point of the application */	
	public static void main(String args[]) {		
		try {			
			System.out.println ("Time Base callback Demo - time callback *ONLY*"); 
			System.out.println ("will work the first time through the movie as eventually");
			System.out.println ("it is rescheduled past the end of the movie and there is"); 
			System.out.println ("no logic to correct that in this code");

			QTSession.open();	
					
			TimeCallbackDemo movieWindow = new TimeCallbackDemo("QT in Java");
			movieWindow.pack();
			movieWindow.show();
			movieWindow.toFront();
		}catch (Exception ex) {
			ex.printStackTrace();
			QTSession.close();
		}			
		
	}
        private QTComponent qtc;	
	private TimeBaseRateCallBack theMoviesRateCallback;
	private TimeBaseExtremesCallBack theMoviesExtremeCallback;
	private TimeBaseTimeJumpCallBack theMoviesTimeJumpCallback;
	private TimeBaseTimeCallBack theMoviesTimeCallback;

	/**
	 * Default constructor 
	 * @param title the title of the window to be created 
	 */
	public TimeCallbackDemo (String title) throws QTException {
		super (title);
		
		createMovie();
		add((Component) qtc);
		pack();

		addWindowListener(new WindowAdapter () {
			public void windowClosing (WindowEvent e) {
				theMoviesRateCallback.cancelAndCleanup();
				theMoviesExtremeCallback.cancelAndCleanup();
				theMoviesTimeJumpCallback.cancelAndCleanup();
				theMoviesTimeCallback.cancelAndCleanup();
				
				QTSession.close();
				System.exit(0);	
			}
			public void windowClosed (WindowEvent e) { 
				System.exit(0);
			}
		});
	}

	/**
	 * Opens a movie from a file and installs the various movie callbacks 
	 */
	public void createMovie() throws QTException {
		Media m = null;

		QTFile qtf = QTFile.standardGetFilePreview(QTFile.kStandardQTFileTypes);
		Movie mov = Movie.fromFile (OpenMovieFile.asRead (qtf));
                MovieController mc = new MovieController(mov);
                qtc = QTFactory.makeQTComponent(mc);
                
		TimeBase theMoviesTimeBase = mov.getTimeBase();
		
		theMoviesRateCallback = new TimeBaseRateCallBack(theMoviesTimeBase, 1.0F, StdQTConstants.triggerRateChange); // this callback is triggered
		theMoviesRateCallback.callMeWhen ();																			 // when the rate of the movie changes

		theMoviesExtremeCallback = new TimeBaseExtremesCallBack(theMoviesTimeBase, StdQTConstants.triggerAtStop );	// this callback is triggered when the movie stops
		theMoviesExtremeCallback.callMeWhen ();
		
		theMoviesTimeJumpCallback = new TimeBaseTimeJumpCallBack(theMoviesTimeBase);					// this callback is triggered when there is a jump in the timebase
		theMoviesTimeJumpCallback.callMeWhen ();
		
			// this schedules the time callback once every 2 seconds
		theMoviesTimeCallback = new TimeBaseTimeCallBack(theMoviesTimeBase, 1, 2, StdQTConstants.triggerTimeEither); // this callback is triggered at a specific time interval
		theMoviesTimeCallback.callMeWhen ();
		
			//Using the Timer class you can get rescheduled properly and get callbacks at the set intervals. It uses the same callback 
			//mechanism of internally of the TimeCallback.
			//Its recomended to use this Timer class to do callbacks , which would take care of the time base time changes and 
			//recscheduling of the tickle method .
		Timer timer = new Timer (1, 2, new Tickler(), mov);		
		timer.setActive(true);
	}
	

	/**
	 * This class extends the RateCallBack class and provides an execute routine that is invoked by the callback
	 * when the rate of the movie changes
	 */

	public class TimeBaseRateCallBack extends RateCallBack {
		public TimeBaseRateCallBack (TimeBase tb, float rate, int flag) throws QTException {
			super(tb, rate, flag);
		}
		public void execute () {
			try {
				System.out.println( "---  RateCallBack@ratechange---" ); 
				cancel();
				callMeWhen ();
			}catch (StdQTException e) {}
		}
	}

	/**
	 * This class implements a method that is called when the movie stops
	 */
	class TimeBaseExtremesCallBack extends quicktime.std.clocks.ExtremesCallBack {
		/** 
		* @param mov The movie to monitor.
		*/
		public TimeBaseExtremesCallBack(TimeBase tb, int flag ) throws QTException {
			super(tb, flag);
		}
              
		public void execute() {
			try {
				System.out.println( "---  ExtremesCallBack@stop---" ); 
		  	    cancel();
				callMeWhen ();
			}catch (StdQTException e) {}
		}
	}       

	/**
	 * This class extends the TimeJumpCallBack class to provide a method that is called when the
	 * timebase of the movie changes (IE, if the user clicks in the movie controller)
	 */
	class TimeBaseTimeJumpCallBack extends quicktime.std.clocks.TimeJumpCallBack {

		public TimeBaseTimeJumpCallBack (TimeBase tb) throws QTException {
			super (tb);
		}
	
		public void execute() {
			try {
				System.out.println( "---  TimeJumpCallBack---" ); 
				cancel();
				callMeWhen ();
			}catch (StdQTException e) {}
		}
	}
	/**
	 * This class extends the TimeCallBack class to provide a method that is called when a
	 * specific interval of time elapses.
	 */
	class TimeBaseTimeCallBack extends quicktime.std.clocks.TimeCallBack {

		public TimeBaseTimeCallBack (TimeBase tb, int scale, int value, int flags) throws QTException {
			super (tb, scale, value, flags);
			period = value;
			theTimeBase = tb;
		}
		
		int period;
		TimeBase theTimeBase;
		
		public void execute() {
			try {
				System.out.println( "---  TimeCallBack@triggerTimeEither--- called at:" + timeWhenCalledMsecs + "msecs"); 
				cancel();
				value += period;
				callMeWhen ();
			}catch (StdQTException e) {}
		}
	}	
	
	public class Tickler implements Ticklish {
	
		public void timeChanged (int newTime) throws QTException {
			System.out.println ("* * * * Timer Class * * * timeChanged at:" + newTime);
		}
		
		public boolean tickle (float er, int time) throws QTException {
			System.out.println ("* * * * Timer Class  * * * tickle at:" + time);
			return true;		
		}		
	}

}	

	