package karega.scott.checkers.ui;

import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * Defines callback for handling messages that require main looper
 * to execute such as the UI updates
 * 
 * @author Karega Scott
 *
 */
public class BoardGameCallback implements Callback {
	private static final String LOG_TAG = "BoardGrameCallback";
	
	@Override
	public boolean handleMessage(Message msg) {
		Log.d(LOG_TAG, "Handling message");
		
		switch(msg.what) {
			case BoardGameEngine.INVALIDATE_VIEW_MESSAGE_HANDLER:
				return InvalidateView(msg.obj);
			default:
				return false;
		} // end switch
	} // end handleMessage

	private boolean InvalidateView(Object object) {
		Log.d(LOG_TAG, "Invalidating view");
		
		if( !(object instanceof View)) 
			return false;
		
		((View)object).invalidate();
		
		return true;
	}
} // end BoardGameCallback
