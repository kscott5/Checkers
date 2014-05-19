package karega.scott.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public class CheckerBoardSquare extends BoardSquare {	
	private static final String LOG_TAG = "CheckerBoardSquare";
	
	private Paint playerPaint;
	private Paint activePlayerPaint;
	private Paint kingPaint;
	
	protected CheckerBoardSquare(Context context, BoardSquareInfo square) {
		super(context, BoardGameEngine.CHECKERS_ENGINE, square);

		this.kingPaint = new Paint();
		this.kingPaint.setColor(Color.WHITE);
		
		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();

		this.updateView(true);
	}

	@Override
	public void drawBoardSquarePiece(Canvas canvas){
		switch(this.square.state) {
			case BoardGameEngine.PLAYER1_STATE:
			case BoardGameEngine.PLAYER2_STATE:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);	// Highlight
				
				if(this.square.isKing) {
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
		this.playerPaint.setColor(this.square.inactiveColor);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.square.activeColor);
		this.activePlayerPaint.setStrokeWidth(BoardGameEngine.SQUARE_CHIP_STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // end updateBoardSquarePiece
}
