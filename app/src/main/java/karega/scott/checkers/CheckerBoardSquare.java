package karega.scott.checkers;

import karega.scott.checkers.BoardSquareInfo.OnChangeListener;
import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
@SuppressLint("NewApi")
public class CheckerBoardSquare extends View {	
	private BoardSquareInfo info;

	private Paint playerPaint;
	private Paint activePlayerPaint;
	private Paint kingPaint;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	private int engine;
	
	public CheckerBoardSquare(Context context, int engine, BoardSquareInfo info) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.engine = engine;
		
		// TODO: Are parameters by references
		info.setOnChangeListener( new BoardSquare.OnBoardSquareChangeListener(this));
		this.info = info;
		this.kingPaint = new Paint();
		this.kingPaint.setColor(Color.WHITE);
		
		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();

		this.invalidate();
	}

	/**
	 * Listener used when this view's {@link BoardSquareInfo} has changed
	 * @author Administrator
	 *
	 */
	public final class OnBoardSquareChangeListener implements OnChangeListener {		
		private BoardSquare square;
		public OnBoardSquareChangeListener(BoardSquare square) {
			this.square = square;
		}
		
		@Override
		public void OnSquareInformationChange() {
			Log.d(LOG_TAG, "Square information changed for BoardSquare");

			BoardGameEngine.handleSquareChanged(square);
		}		
	} // end OnSquarInfoChangeListener

	/**
	 * Creates the CheckerBoardSquare
	 * @param context
	 * @param engineType @link BoardGameEngineType 
	 * @return @BoardSquare
	 */
	public static CheckerBoardSquare instance(Context context, int engine, ViewGroup parent, BoardSquareInfo square) {
		if( !(parent instanceof GridView))
			return null;
		
		GridView grid = (GridView)parent;
		int width= grid.getColumnWidth(); // Added Lint.xml to resolve NewApi issue. Should remove

		BoardSquare view = new CheckerBoardSquare(context, square);
		view.setLayoutParams( new GridView.LayoutParams(width, width));

		return view;
	} // end instance
	

	@Override
	public final void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), fillPaint);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), borderPaint);

		switch(this.info.state) {
			case BoardGameEngine.PLAYER1_STATE:
			case BoardGameEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight
				
				if(this.info.isKing) {
					canvas.drawText("K",this.getWidth()/2, this.getHeight()/2, kingPaint);
				}
				
				break;
				
			case BoardGameEngine.LOCKED_STATE:
			case BoardGameEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.info.equals(view.info);
	}

	/**
	 * Updates the @link BoardSquare paint objects
	 * @param refresh Flag to invalidate the @link BoardSquare
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
		this.activePlayerPaint.setStrokeWidth(BoardGameEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);
		
		super.invalidate();
	} // end updateView
	
	/**
	 * Draws this @link BoardSquare @link BoardSquarePieceType
	 * @param canvas
	 */
	public void drawBoardSquarePiece(Canvas canvas){
		switch(this.info.state) {
			case BoardGameEngine.PLAYER1_STATE:
			case BoardGameEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight
				
				if(this.info.isKing) {
					canvas.drawText("K",this.getWidth()/2, this.getHeight()/2, kingPaint);
				}
				
				break;
				
			case BoardGameEngine.LOCKED_STATE:
			case BoardGameEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	protected void updateViewForRedraw() {	
		this.playerPaint.setColor(this.info.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.info.activeColor);
		this.activePlayerPaint.setStrokeWidth(BoardGameEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // updateViewForRedraw
}
