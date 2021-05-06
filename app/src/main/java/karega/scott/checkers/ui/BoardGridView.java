package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.R;

import android.util.Log;
import android.util.AttributeSet;

import android.content.Context;

import android.view.View;
import android.view.MotionEvent;

import android.widget.GridView;

public class BoardGridView extends GridView {
	public static final String LOG_TAG = "BoardGridView";

	public BoardGridView(Context context) {
		super(context);
	}

	public BoardGridView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}

	public BoardGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
	}
	
	public BoardGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context,attrs,defStyleAttr,defStyleRes);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int id = event.getPointerId(0);
		int action = event.getActionMasked();
		int index = event.getActionIndex();
		int size = event.getHistorySize();
		float x = event.getX(id), y = event.getY(id);

		Log.d(LOG_TAG, "on Intercept Touch:          Id->" + id);
		Log.d(LOG_TAG, "on Intercept Touch:       Index->" + index);
		Log.d(LOG_TAG, "on Intercept Touch: HistorySize->" + size);
		Log.d(LOG_TAG, "on Intercept Touch:           X->" + x);
		Log.d(LOG_TAG, "on Intercept Touch:           Y->" + y);
		Log.d(LOG_TAG, "On Intercept Touch:      height->" + this.getWidth());	
		Log.d(LOG_TAG, "On Intercept Touch:       width->" + this.getWidth());	
		Log.d(LOG_TAG, "On Intercept Touch:      action->" + event.actionToString(action));	


	
		BoardAdapter adapter = (BoardAdapter)this.getAdapter();

		switch(action) {
			case MotionEvent.ACTION_DOWN:
				Log.d(LOG_TAG, "Down");
				break;

			case MotionEvent.ACTION_MOVE:
				Log.d(LOG_TAG, "Move");
				break;

			case MotionEvent.ACTION_UP:
				Log.d(LOG_TAG, "Up");
				break;

			case MotionEvent.ACTION_CANCEL:
			default:
				Log.d(LOG_TAG, "Cancel");
				break;
		}

		event.setAction(MotionEvent.ACTION_OUTSIDE);
		return super.onInterceptTouchEvent(event);
	}
}

