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
	
	protected BoardSquareInfo squareInfo;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	private int engine;
	
	protected BoardSquare(Context context, int engine, BoardSquareInfo squareInfo) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.engine = engine;
		
		// TODO: Are parameters by references
		squareInfo.setOnChangeListener( new BoardSquare.OnSquareInfoChangeListener());
		this.squareInfo = squareInfo;
	}

	/**
	 * Creates the @link BoardSquare for the specific @link BoardGameEngineType
	 * @param context
	 * @param engineType @link BoardGameEngineType 
	 * @return @BoardSquare
	 */
	public static BoardSquare instance(Context context, int engine, BoardSquareInfo info) {		
		switch(engine) {
			case BoardGameEngine.CHECKERS_ENGINE:
				Log.d(LOG_TAG, "Created instance of checker board square");
				return new CheckerBoardSquare(context, info);
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
			Log.d(LOG_TAG, "Square information changed for BoardSquare");
			
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
		
		// TODO: Do this once. Remove these values from BoardSquareInfo
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
		Log.d(LOG_TAG, "On draw");
		
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), fillPaint);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), borderPaint);

		drawBoardSquarePiece(canvas);
	} // end onDraw
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.squareInfo.equals(view.squareInfo);
	}

	public final BoardSquareInfo getInformation() {
		return this.squareInfo;
	}
	
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