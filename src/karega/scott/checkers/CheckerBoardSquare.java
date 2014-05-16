package karega.scott.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public class CheckerBoardSquare extends BoardSquare {	
	private static final String LOG_TAG = "CheckerBoardSquare";
	
	private Paint playerPaint;
	private Paint activePlayerPaint;
	
	protected CheckerBoardSquare(Context context, BoardSquareInfo squareInfo) {
		super(context, BoardGameEngine.CHECKERS_ENGINE, squareInfo);

		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();

		this.updateView(true);
	}

	@Override
	public void drawBoardSquarePiece(Canvas canvas){
		Log.d(LOG_TAG, "Drawing board square piece");
		
		switch(this.squareInfo.getState()) {
			case BoardGameEngine.PLAYER1_STATE:
			case BoardGameEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight		
				break;
				
			case BoardGameEngine.LOCKED_STATE:
			case BoardGameEngine.EMPTY_STATE:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	protected void updateViewForRedraw() {
		Log.d(LOG_TAG, "Updating view for redraw");
		
		this.playerPaint.setColor(this.squareInfo.getInactiveColor());
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.squareInfo.getActiveColor());
		this.activePlayerPaint.setStrokeWidth(BoardGameEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // end updateBoardSquarePiece
}
