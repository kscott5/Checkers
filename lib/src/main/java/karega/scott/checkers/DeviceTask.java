package karega.scott.checkers;


import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Task allows this device to take it turn. 
 * @author Karega Scott
 *
 */
public class DeviceTask extends TimerTask {
	private final CheckersEngine engine;
	private final AtomicBoolean locked;
	
	public DeviceTask(CheckersEngine engine) {
		this.locked = new AtomicBoolean(false);
		this.engine = engine;		
	}
	
	@Override
	public void run() {
		while(engine.isDevice() && !locked.compareAndSet(false, true)) {			
			engine.deviceMove(); // perform some work
			locked.set(false); // prepare to unlock
		} // end while
	} // end run
} // end DeviceTimerTask
