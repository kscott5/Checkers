package karega.scott.checkers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public class CheckerBoardSquare extends BoardSquare {	
	private Paint playerPaint;
	private Paint activePlayerPaint;
	
	protected CheckerBoardSquare(Context context) {
		super(context, BoardGameEngineType.CHECKERS);

		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();
		
		// Use of double brace initialization found example on stackoverflow
		// http://stackoverflow.com/questions/9108531/is-there-an-object-initializers-in-java
		this.setInformation(new BoardSquareInfo(-1, new Point(){{ x=-1; y=-1;}}, 
				BoardSquareStateType.EMPTY, BoardSquarePieceType.NONE));
	}

	@Override
	public void drawBoardSquarePiece(Canvas canvas){

		switch(this.getInformation().getCurrentPlayer()) {
			case PLAYER1:
			case PLAYER2:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, playerPaint);
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, (this.getWidth()/2)-2, activePlayerPaint);				
				break;
				
			case LOCKED:
			case EMPTY:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	protected void updateViewForRedraw() {
		this.playerPaint.setColor(this.getInformation().getPlayerColor());
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(this.getInformation().getActivePlayerColor());
		this.activePlayerPaint.setStrokeWidth(BoardSquareInfo.STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
	} // end updateBoardSquarePiece
}
