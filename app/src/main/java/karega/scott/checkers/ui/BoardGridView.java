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

	int row = -1, column = -1, viewWidth = 38;
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

		int viewWidth = 38; // Must use width from CheckerBoardSquare View
		int tmpx = (int)Math.floor(y/viewWidth); // Sequence, order, of events
		int tmpy = (int)Math.floor(x/viewWidth); // are important
			
		BoardAdapter adapter = (BoardAdapter)this.getAdapter();

		switch(action) {
			case MotionEvent.ACTION_DOWN:
				if(row == -1 && column == -1) {
					row = tmpx; column = tmpy;
					BoardSquareInfo square = BoardActivity.gameEngine.getData(row,column);
					Log.d(LOG_TAG, "Down->" + square);
					BoardActivity.gameEngine.updateGameBoard(square.id, /*hasMore*/ true);
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if(tmpx != row && tmpy != column) {
					row = tmpx; column = tmpy;
					BoardSquareInfo square = BoardActivity.gameEngine.getData(row,column);
					Log.d(LOG_TAG, "Move->" + square);
					BoardActivity.gameEngine.updateGameBoard(square.id, /*hasMore*/ true);
				}
				break;

			case MotionEvent.ACTION_UP:
				if(tmpx != row && tmpy != column) {
					row = tmpx; column = tmpy;
					BoardSquareInfo square = BoardActivity.gameEngine.getData(row,column);
					Log.d(LOG_TAG, "Up->" + square);
					BoardActivity.gameEngine.updateGameBoard(square.id, /*hasMore*/ false);
				}

				row = -1; column = -1;
				break;

			case MotionEvent.ACTION_CANCEL:
			default:
				row = -1; column = -1;
				Log.d(LOG_TAG, "Cancel");
				break;
		}

		event.setAction(MotionEvent.ACTION_OUTSIDE);
		return super.onInterceptTouchEvent(event);
	}
}

