package karega.scott.checkers;

import karega.scott.checkers.BoardSquareInfo.OnChangeListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public abstract class BoardSquare extends View {
	private static final String LOG_TAG = "BoardSquare";
	
	private BoardSquareInfo squareInfo;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	private int engine;
	
	protected BoardSquare(Context context, int engine) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.engine = engine;
	}

	/**
	 * Creates the @link BoardSquare for the specific @link BoardGameEngineType
	 * @param context
	 * @param engineType @link BoardGameEngineType 
	 * @return @BoardSquare
	 */
	public static BoardSquare instance(Context context, int engine) {		
		switch(engine) {
			case BoardGameEngine.CHECKERS_ENGINE:
				Log.d(LOG_TAG, "Created instance of checker board square");
				return new CheckerBoardSquare(context);
			default:
				Log.d(LOG_TAG, "Could not create board square for this engine");
				return null;
		}
	} // end instance
	
	/**
	 * Listener used when this view's {@link BoardSquareInfo} has changed
	 * @author Administrator
	 *
	 */
	public final class OnSquareInfoChangeListener implements OnChangeListener {
		@Override
		public void OnSquareInformationChange() {
			updateView(true);
		}		
	} // end OnSquarInfoChangeListener

	/**
	 * Updates the view's paint object for redraw
	 * @param refresh Flag used to refresh @link BoardSquare @link BoardSquareInfo
	 */
	protected abstract void updateViewForRedraw();

	/**
	 * Updates the @link BoardSquare paint objects
	 * @param refresh Flag to invalidate the @link BoardSquare
	 */
	public void updateView(boolean refresh) {
		Log.d(LOG_TAG, "Updating view");
		
		this.fillPaint.setColor(squareInfo.getFillColor());
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		this.borderPaint.setColor(squareInfo.getBorderColor());
		this.borderPaint.setStyle(Paint.Style.STROKE);			

		updateViewForRedraw();
		
		if(refresh) {
			this.invalidate();
		}
	} // end updateView
	
	/**
	 * Draws this @link BoardSquare @link BoardSquarePieceType
	 * @param canvas
	 */
	protected abstract void drawBoardSquarePiece(Canvas canvas);
	
	@Override
	public final void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, BoardGameEngine.SQUARE_WIDTH, BoardGameEngine.SQUARE_HEIGHT, fillPaint);
		canvas.drawRect(0, 0, BoardGameEngine.SQUARE_WIDTH, BoardGameEngine.SQUARE_HEIGHT, borderPaint);

		drawBoardSquarePiece(canvas);
	} // end onDraw
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.squareInfo.equals(view.getInformation());
	}
	
	/**
	 * Gets the information for the @link BoardSquare
	 * @return
	 */
	public final BoardSquareInfo getInformation() { return this.squareInfo; }

	/**
	 * Sets the information used for the @link BoardSquare
	 * @param value
	 */
	public final void setInformation(BoardSquareInfo value) { 
		Log.d(LOG_TAG, "Setting information");
		
		this.squareInfo = value;
		this.updateView(true);
		this.squareInfo.setOnChangeListener( new BoardSquare.OnSquareInfoChangeListener());
	} // end setInformation

	/**
	 * Gets the paint object for filling the @link BoardSquare
	 * @return 
	 */
	protected final Paint getFillPaint() { return this.fillPaint; }

	/**
	 * Gets the paint object for drawing the border around the @link BoardSquare
	 * @return 
	 */
	protected final Paint getBorderPaint() { return this.borderPaint; }
	
	/**
	 * Gets the @link BoardGameEngineType for the @link BoardSquare
	 * @return 
	 */
	public final int getEngine() { return this.engine; }	
} // end BoardSquare