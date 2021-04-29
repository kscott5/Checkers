package karega.scott.checkers.ui;

import karega.scott.checkers.DeviceTask;

import java.util.Random;
import java.util.Timer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Base game engine
 * @author Karega Scott
 *
 * 
 * Player 2 is top-left = {0,0}
 * Player 1 is bottom-right = {7,7}
 */
public abstract class BoardGameEngine {
	public static final int INVALIDATE_VIEW_MESSAGE_HANDLER = 1;
	
	private static Handler handler;	
	private Timer deviceTimer;
	
	protected boolean vsDevice;	

	protected BoardGameEngine(DeviceTask task, boolean vsDevice) {		
		this.vsDevice = vsDevice;
		
		if(this.vsDevice) {
			initializeHandler();

//			DeviceTask task = new DeviceTask(/*this references CheckersEngine*/);
			this.deviceTimer = new Timer();
			this.deviceTimer.schedule(task, 100, 1000);
		}
	} // end constructor

	@Override
	public void finalize() {
		if(this.deviceTimer != null) {
			deviceTimer.cancel();
			deviceTimer = null;
		}
	} // end finalize
	
	private static void initializeHandler() {
		if(handler == null) {
			handler = new Handler(new BoardGameCallback());
		}
	} // end initializeHandler
	
	/**
	 * Handler for executing messages on the main looper (Thread)
	 * @return
	 */
	private static void handleMessage(int what, Object object) {
		if(object == null)
			return;
		
		initializeHandler();
		
		Message msg = Message.obtain(handler, what, object);
		msg.sendToTarget();
	} // end handler
	
	/**
	 * Handles the square changes by calling invalidating its view.
	 * @param square
	 */
	public static void handleSquareChanged(CheckerBoardSquare square) {
		// When there is no looper, changes are on 
		// secondary thread used to active and move 
		// square for this device play
		if(Looper.myLooper() == null) {
			handleMessage(BoardGameEngine.INVALIDATE_VIEW_MESSAGE_HANDLER, square);
		} else {
			square.invalidate();
		}
	} // end handleSquareChanged
	
	/**
	 * Pauses the current thread
	 * @param seconds
	 */
	protected void pause(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch(Exception e) {
		}
	} // end pause
} // end BoardGameEngine
