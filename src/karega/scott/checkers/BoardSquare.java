package karega.scott.checkers;


import karega.scott.checkers.BoardSquareInfo.OnChangeListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public class BoardSquare extends View {	
	private BoardSquareInfo squareInfo;
	private Paint playerPaint;
	private Paint activePlayerPaint;
	private Paint fillPaint;
	private Paint borderPaint;
	private RectF container;
	
	public BoardSquare(Context context) {
		super(context);

		this.playerPaint = new Paint();
		this.activePlayerPaint = new Paint();
		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		
		this.container = new RectF();
		this.container.left = 0;
		this.container.top = 0;
		this.container.right = BoardSquareInfo.WIDTH;
		this.container.bottom = BoardSquareInfo.HEIGHT;
				
		this.squareInfo = new BoardSquareInfo();
	}

	/**
	 * Listener used when this view's {@link BoardSquareInfo} has changed
	 * @author Administrator
	 *
	 */
	public class OnSquareInfoChangeListener implements OnChangeListener {

		@Override
		public void OnSquareInformationChange() {
			updateView(true);
		}
		
	}

	@Override
	public void onDraw(Canvas canvas){
		Log.v("SquareView.onDraw", this.squareInfo.getStateType().toString());
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT, fillPaint);
		canvas.drawRect(0, 0, BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT, borderPaint);

		switch(this.squareInfo.getStateType()) {
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
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.squareInfo.equals(view.getSquareInformation());
	}
	
	public BoardSquareInfo getSquareInformation() { return this.squareInfo; }
	public void setSquareInformation(BoardSquareInfo value) { 
		this.squareInfo = value;
		this.updateView(true);
		this.squareInfo.setOnChangeListener( new BoardSquare.OnSquareInfoChangeListener());
	}

	/**
	 * Updates the paint objects before attempting to redraw this view
	 * @param refresh
	 */
	protected void updateView(boolean refresh) {
		this.fillPaint.setColor(squareInfo.getFillColor());
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		this.borderPaint.setColor(squareInfo.getBorderColor());
		this.borderPaint.setStyle(Paint.Style.STROKE);			

		this.playerPaint.setColor(squareInfo.getPlayerColor());
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			

		this.activePlayerPaint.setColor(squareInfo.getActivePlayerColor());
		this.activePlayerPaint.setStrokeWidth(BoardSquareInfo.STROKE_WIDTH);
		this.activePlayerPaint.setStyle(Paint.Style.STROKE);			
		
		if(refresh) {
			this.invalidate();
		}
	}	
}
