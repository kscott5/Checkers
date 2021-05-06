package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.R;

import java.lang.Math;

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
		int historySize = event.getHistorySize();

		int action = event.getActionMasked();
		int id = event.getPointerId(0);
		float x = event.getX(id), y = event.getY(id);
		int width = this.getColumnWidth();
		boolean hasMore = (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE);
	
		BoardSquareInfo square = BoardActivity.gameEngine.getData(y,x,width);
		Log.d(LOG_TAG, "On Intercept Touch [" + event.actionToString(action) + ":" + ((hasMore)? "hasmore" : "done") + "] width: "+ width +" square->" + square);
		
		if(action == MotionEvent.ACTION_CANCEL) {
			BoardActivity.gameEngine.saveSelectionReset();
		} else { 
			BoardActivity.gameEngine.updateGameBoard(square.id, hasMore);
		}

		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}

