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
	
	protected BoardSquareInfo square;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	private int engine;
	
	protected BoardSquare(Context context, int engine, BoardSquareInfo square) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.engine = engine;
		
		// TODO: Are parameters by references
		square.setOnChangeListener( new BoardSquare.OnSquareInfoChangeListener());
		this.square = square;
	}

	/**
	 * Creates the @link BoardSquare for the specific @link BoardGameEngineType
	 * @param context
	 * @param engineType @link BoardGameEngineType 
	 * @return @BoardSquare
	 */
	public static BoardSquare instance(Context context, int engine, BoardSquareInfo square) {		
		switch(engine) {
			case BoardGameEngine.CHECKERS_ENGINE:
				return new CheckerBoardSquare(context, square);
			default:
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
		// TODO: Do this once. Remove these values from BoardSquareInfo
		this.fillPaint.setColor(square.fillColor);
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		this.borderPaint.setColor(square.borderColor);
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
		
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), fillPaint);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), borderPaint);

		drawBoardSquarePiece(canvas);
	} // end onDraw
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.square.equals(view.square);
	}

	public final BoardSquareInfo getInformation() {
		return this.square;
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