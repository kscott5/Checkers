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

	private Paint activePlayerPaint = new Paint();
	private Paint playerPaint = new Paint();
	private Paint kingPaint = new Paint();

	private Paint fillPaint = new Paint();
	private Paint borderPaint = new Paint();
	
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

		kingPaint.setColor(Color.WHITE);		
		
		final float size = 0; //this.getRatioSize();

		// First coordinates 
		float left=0, top=0, right=left+size, bottom=top+size, centerx=right/2, centery=bottom/2, radius=(right/2)-2 ;

		for(int row=0; row<CheckersEngine.ROWS; row++) {
			for(int col=0; col<CheckersEngine.COLUMNS; col++) {
				BoardSquareInfo square = BoardActivity.gameEngine.getData(row,col);

//				this.updateCanvasDrawingTools(square);

//				canvas.drawRect(/*xAxis*/ left, /*yAxis*/ top, /*xAxis*/ right, /*yAxis*/ bottom, this.fillPaint);
//				canvas.drawRect(/*xAxis*/ left, /*yAxis*/ top, /*xAxis*/ right, /*yAxis*/ bottom, this.borderPaint);

				switch(square.state) {
					case CheckersEngine.PLAYER1_STATE:
					case CheckersEngine.PLAYER2_STATE:
//						canvas.drawCircle(/*axis*/ centerx, /*axis*/ centery, radius, this.playerPaint);
//						canvas.drawCircle(/*axis*/ centerx, /*axis*/ centery, radius, this.activePlayerPaint);	// Highlight
				
						if(square.isKing) {
							canvas.drawText("K", centerx, centery, kingPaint);
						}
				
						break;
				
					case CheckersEngine.LOCKED_STATE:
					case CheckersEngine.EMPTY_STATE:
						default:
						break;
				} //end switch

				 left=row*size; top=col*size; right=left+size; bottom=top+size; centerx=right/2; centery=bottom/2; radius=(right/2)-2 ;

			} // end for col
		} // end for row
	} // end onDraw

	public void updateCanvasDrawingTools(BoardSquareInfo square) {
		// TODO: Do this once. Remove these values from BoardSquareInfo
		this.fillPaint.setColor(square.fillColor);
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		this.borderPaint.setColor(square.borderColor);
		this.borderPaint.setStyle(Paint.Style.STROKE);			

		this.playerPaint.setColor(square.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(square.activeColor);
		this.activePlayerPaint.setStrokeWidth(CheckersEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);
		
	} // end updateCanvas
	
	/**
	 * Draws this @link BoardSquare @link BoardSquarePieceType
	 * @param canvas
	 */
	public void drawBoardSquarePiece(Canvas canvas, BoardSquareInfo square){
		switch(square.state) {
			case CheckersEngine.PLAYER1_STATE:
			case CheckersEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, this.playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, this.activePlayerPaint);	// Highlight
				
				if(square.isKing) {
					canvas.drawText("K",this.getWidth()/2, this.getHeight()/2, kingPaint);
				}
				
				break;
				
			case CheckersEngine.LOCKED_STATE:
			case CheckersEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end drawBoardSquare
	
	protected void updateViewForRedraw(BoardSquareInfo square) {	
		this.playerPaint.setColor(square.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(square.activeColor);
		this.activePlayerPaint.setStrokeWidth(CheckersEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // updateViewForRedraw

}
