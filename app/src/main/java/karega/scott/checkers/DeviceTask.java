package karega.scott.checkers;


import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import android.annotation.SuppressLint;
import android.util.Log;


/**
 * Task allows this device to take it turn. 
 * @author Karega Scott
 *
 */
public class DeviceTask extends TimerTask {
	private static final String LOG_TAG = "DeviceTask";
	
	private final BoardGameEngine engine;
	private final AtomicBoolean locked;
	
	public DeviceTask(BoardGameEngine engine) {
		this.locked = new AtomicBoolean(false);
		this.engine = engine;		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void run() {
		Log.d(LOG_TAG, "Running...");
		
		while(engine.isDevice() && !locked.compareAndSet(false, true)) {			
			engine.deviceMove(); // perform some work
			locked.set(false); // prepare to unlock
		} // end while
	} // end run
} // end DeviceTimerTask
