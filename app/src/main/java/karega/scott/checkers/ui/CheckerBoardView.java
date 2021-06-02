package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.R;

import java.lang.Math;

import android.util.Log;
import android.util.AttributeSet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class CheckerBoardView extends View implements OnTouchListener {
	public static final String LOG_TAG = "CheckerBoardView";

	private Paint playerPaint;
	private Paint activePlayerPaint;
	private Paint kingPaint;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	public CheckerBoardView(Context context) {
		super(context);
	}

	public CheckerBoardView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}

	public CheckerBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
	}
	
	public CheckerBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context,attrs,defStyleAttr,defStyleRes);
	}

	public boolean onTouch(View view, MotionEvent event) {
		int historySize = event.getHistorySize();

		int action = event.getActionMasked();
		int id = event.getPointerId(0);
		float x = event.getX(id), y = event.getY(id);
		int width = this.getWidth();
		boolean hasMore = (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE);
	
		BoardSquareInfo square = BoardActivity.gameEngine.getData(y,x,width);
		Log.d(LOG_TAG, "On Touch [" + event.actionToString(action) + ":" + ((hasMore)? "hasmore" : "done") + "] width: "+ width +" square->" + square);

		return false;
	}

	@Override
	public final void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		int startX = 0;
		int startY = 0;

		final float minWidth = this.getWidth()/BoardActivity.gameEngine.ROWS;
		final float minHeight = this.getHeight()/BoardActivity.gameEngine.COLUMNS;

		for(int row=0; row<CheckersEngine.ROWS; row++) {
			for(int col=0; col<CheckersEngine.COLUMNS; col++) {
				BoardSquareInfo info = BoardActivity.gameEngine.getData(row,col);

				canvas.drawRect(/*left*/ 0, /*top*/ 0, /*right*/ minWidth, /*bottom*/ minHeight, fillPaint);
				canvas.drawRect(/*left*/ 0, /*top*/ 0, /*right*/ minWidth, /*bottom*/ minHeight, borderPaint);

				switch(info.state) {
					case CheckersEngine.PLAYER1_STATE:
					case CheckersEngine.PLAYER2_STATE:
						canvas.drawCircle(minWidth/2, minHeight/2, (minWidth/2)-2, playerPaint);
						canvas.drawCircle(minWidth/2, minHeight/2, (minWidth/2)-2, activePlayerPaint);	// Highlight
				
						if(info.isKing) {
							canvas.drawText("K",minWidth/2, minHeight/2, kingPaint);
						}
				
						break;
				
					case CheckersEngine.LOCKED_STATE:
					case CheckersEngine.EMPTY_STATE:
						default:
						break;
				}
			}
		}
	} // end onDraw
	
}
