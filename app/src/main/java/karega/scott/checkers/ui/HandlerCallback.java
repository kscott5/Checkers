package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;

import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;

/**
 * Defines callback for handling messages that require main looper
 * to execute such as the UI updates
 * 
 * @author Karega Scott
 *
 */
public class HandlerCallback implements Callback {
	@Override
	public boolean handleMessage(Message msg) {
		switch(msg.what) {
			case CheckersEngine.INVALIDATE_VIEW_MESSAGE_HANDLER:
				return InvalidateView(msg.obj);
			default:
				return false;
		} // end switch
	} // end handleMessage

	private boolean InvalidateView(Object object) {
		if( !(object instanceof View)) 
			return false;
		
		((View)object).invalidate();
		
		return true;
	}
} // end HandlerCallback
