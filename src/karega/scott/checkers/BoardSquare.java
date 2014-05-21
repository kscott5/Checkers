package karega.scott.checkers;

import karega.scott.checkers.BoardSquareInfo.OnChangeListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
@SuppressLint("NewApi")
public abstract class BoardSquare extends View {
	private static final String LOG_TAG = "BoardSquare";
	
	protected BoardSquareInfo info;
	
	private Paint fillPaint;
	private Paint borderPaint;
	
	private int engine;
	
	protected BoardSquare(Context context, int engine, BoardSquareInfo info) {
		super(context);

		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.engine = engine;
		
		// TODO: Are parameters by references
		info.setOnChangeListener( new BoardSquare.OnBoardSquareChangeListener(this));
		this.info = info;
	}

	/**
	 * Creates the @link BoardSquare for the specific @link BoardGameEngineType
	 * @param context
	 * @param engineType @link BoardGameEngineType 
	 * @return @BoardSquare
	 */
	public static BoardSquare instance(Context context, int engine, ViewGroup parent, BoardSquareInfo square) {
		if( !(parent instanceof GridView))
			return null;
		
		GridView grid = (GridView)parent;
		int width= grid.getColumnWidth(); // Added Lint.xml to resolve NewApi issue. Should remove

		BoardSquare view = null;
		switch(engine) {
			case BoardGameEngine.CHECKERS_ENGINE:
				view = new CheckerBoardSquare(context, square);
				break;
				
			default:
				return null;
		}
		
		view.setLayoutParams( new GridView.LayoutParams(width, width));
		return view;
	} // end instance
	
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
	 * Updates the view's paint object for redraw
	 * @param refresh Flag used to refresh @link BoardSquare @link BoardSquareInfo
	 */
	protected abstract void updateViewForRedraw();

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

		updateViewForRedraw();
		
		super.invalidate();
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
		return this.info.equals(view.info);
	}

	public final BoardSquareInfo getInformation() {
		return this.info;
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