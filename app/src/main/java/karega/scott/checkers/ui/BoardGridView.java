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

/*
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
	

		switch(action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				Log.d(LOG_TAG, "Down/Move");
				event.setAction(MotionEvent.ACTION_MOVE);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
			default:
				Log.d(LOG_TAG, "Up/Cancel");
				event.setAction(MotionEvent.ACTION_UP);
		}

		return super.onInterceptTouchEvent(event);
	}
	*/
}

