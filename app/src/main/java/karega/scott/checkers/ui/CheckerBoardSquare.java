package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.BoardSquareInfo.OnChangeListener;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;

import android.util.Log;

/**
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
@SuppressLint("NewApi")
public class CheckerBoardSquare extends View implements OnTouchListener {	
	public static final String LOG_TAG = "CheckerBoardSquare";
	public BoardSquareInfo info;

	private Paint playerPaint;
	private Paint activePlayerPaint;
	private Paint kingPaint;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	public CheckerBoardSquare(Context context, BoardSquareInfo info) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		// Inner classes use {Class}.this notation. CheckerBoardSquare.this
		info.setOnChangeListener( new OnChangeListener() {
			@Override
			public void onSquareInformationChange() {
				Log.d(LOG_TAG, "Square changed" + CheckerBoardSquare.this.info);
				// Swipe between square tiles on touch
				// screen causes the a refresh with 
				// different details.
				CheckerBoardSquare.this.invalidate();
			}
		});

		this.info = info;
		this.kingPaint = new Paint();
		this.kingPaint.setColor(Color.WHITE);
		
		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();

		this.invalidate();
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

	/**
	 * Creates the CheckerBoardSquare
	 * @param context
	 * @return @CheckerBoardSquare
	 */
	public static CheckerBoardSquare instance(Context context,  ViewGroup parent, BoardSquareInfo square) {
		if( !(parent instanceof GridView))
			return null;
		
		GridView grid = (GridView)parent;
		int width= grid.getColumnWidth(); // Added Lint.xml to resolve NewApi issue. Should remove

		CheckerBoardSquare view = new CheckerBoardSquare(context, square);
		view.setLayoutParams( new GridView.LayoutParams(width, width));

		return view;
	} // end instance
	

	@Override
	public final void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), fillPaint);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), borderPaint);

		switch(this.info.state) {
			case CheckersEngine.PLAYER1_STATE:
			case CheckersEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight
				
				if(this.info.isKing) {
					canvas.drawText("K",this.getWidth()/2, this.getHeight()/2, kingPaint);
				}
				
				break;
				
			case CheckersEngine.LOCKED_STATE:
			case CheckersEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof CheckerBoardSquare))
			return false;
		
		CheckerBoardSquare view = (CheckerBoardSquare)value;
		return this.info.equals(view.info);
	}

	/**
	 * Updates the @link CheckerBoardSquare paint objects
	 * @param refresh Flag to invalidate the @link CheckerBoardSquare
	 */
	public void invalidate() {
		// TODO: Do this once. Remove these values from BoardSquareInfo
		this.fillPaint.setColor(info.fillColor);
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		this.borderPaint.setColor(info.borderColor);
		this.borderPaint.setStyle(Paint.Style.STROKE);			

		this.playerPaint.setColor(this.info.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.info.activeColor);
		this.activePlayerPaint.setStrokeWidth(CheckersEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);
		
		super.invalidate();
	} // end updateView
	
	/**
	 * Draws this @link BoardSquare @link BoardSquarePieceType
	 * @param canvas
	 */
	public void drawBoardSquarePiece(Canvas canvas){
		switch(this.info.state) {
			case CheckersEngine.PLAYER1_STATE:
			case CheckersEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight
				
				if(this.info.isKing) {
					canvas.drawText("K",this.getWidth()/2, this.getHeight()/2, kingPaint);
				}
				
				break;
				
			case CheckersEngine.LOCKED_STATE:
			case CheckersEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end onDraw
	
	protected void updateViewForRedraw() {	
		this.playerPaint.setColor(this.info.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.info.activeColor);
		this.activePlayerPaint.setStrokeWidth(CheckersEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // updateViewForRedraw
}
