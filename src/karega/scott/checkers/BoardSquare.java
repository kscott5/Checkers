package karega.scott.checkers;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*
 * A view used to create squares or checkered look on {@link BoardActivity}. 
 */
public class BoardSquare extends View {	
	private BoardSquareInfo boardSquareInfo;
	Paint playerPaint;
	Paint fillPaint;
	Paint borderPaint;
	
	/**
	 * State of the Square
	 * @author Administrator
	 *
	 */
	public enum StateType {
		EMPTY(0), 
		PLAYER1(1), 
		PLAYER2(2), 
		LOCKED(3);
		
		public final int value;
		private StateType(int value) { this.value = value; }
		
		public static StateType valueOf(int value) {
			for(StateType type : StateType.values()) {
				if(type.value == value)
					return type;
			}
			
			return StateType.EMPTY;
		} // end valueOf			
	};
	
	/**
	 * Initialize the {@link karega.scott.checkers.BoardSquare}
	 * @param context
	 */
	public BoardSquare(Context context)  {
		super(context);
		
		initBoardSquareInfo();
	} // end constructor

	public BoardSquare(Context context, BoardSquareInfo info) {
		super(context);
		
		this.boardSquareInfo = info;
	}
	/**
	 * Initialize the {@link karega.scott.checkers.BoardSquare}
	 * @param context
	 * @param attrs
	 */
	public BoardSquare(Context context, AttributeSet attrs) {		
		this(context);
		
		initBoardSquareInfo();
		
		if(attrs != null) {
			// Initialize array
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
			
			try { // Get attributes
				this.boardSquareInfo.stateType = StateType.valueOf(a.getInteger(
					R.styleable.SquareView_stateType, StateType.EMPTY.value));
								
				this.setBorderColor(a.getColor(R.styleable.SquareView_borderColor, Color.BLACK));
				this.setFillColor(a.getColor(R.styleable.SquareView_fillColor, Color.GRAY));
			} finally {
				a.recycle();
			}
		} // end if
		
	} // end constructor

	/*
	 * Initialize this board's square information
	 */
	public void initBoardSquareInfo() {
		this.playerPaint = new Paint();
		this.fillPaint = new Paint();
		this.borderPaint = new Paint();
		this.boardSquareInfo = new BoardSquareInfo();
		this.boardSquareInfo.stateType = StateType.EMPTY;
		this.boardSquareInfo.xAxis = 0;
		this.boardSquareInfo.yAxis = 0;
		this.boardSquareInfo.isDirty = true;
		this.boardSquareInfo.fillColor = Color.GRAY;
		this.boardSquareInfo.borderColor = Color.BLACK;
	}


	@Override
	public void onDraw(Canvas canvas){
		Log.v("SquareView.onDraw", this.boardSquareInfo.stateType.toString());
		super.onDraw(canvas);
		
		canvas.drawRect(0, 0, BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT, fillPaint);	
		canvas.drawRect(0, 0, BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT, borderPaint);

		switch(this.boardSquareInfo.stateType) {
			case PLAYER1:
			case PLAYER2:
				canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, playerPaint);
				break;
				
			case LOCKED:
			case EMPTY:
			default:
				break;
		}
	} // end onDraw
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.v("BoardSquare.onTouchEvent", this.boardSquareInfo.toString());
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean equals(Object value) {
		if(value == null || !(value instanceof BoardSquare))
			return false;
		
		BoardSquare view = (BoardSquare)value;
		return this.getId() == view.getId() &&
				this.getXAxis() == view.getXAxis() &&
				this.getYAxis() == view.getYAxis() &&
				this.getIsDirty() == view.getIsDirty() &&
				this.getFillColor() == view.getFillColor() &&
				this.getBorderColor() == view.getBorderColor() &&
				this.getStateType() == view.getStateType();
	}
	
	public int getFillColor() { return this.boardSquareInfo.fillColor; }
	public void setFillColor(int value) { 
		this.boardSquareInfo.fillColor = value;
		
		this.fillPaint.setColor(value);
		this.fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	}
	
	public int getBorderColor() { return this.boardSquareInfo.borderColor;}
	public void setBorderColor(int value) { 
		this.boardSquareInfo.borderColor = value;

		this.borderPaint.setColor(value);
		this.borderPaint.setStyle(Paint.Style.STROKE);			
	}
	
	public int getPlayerColor() { return this.boardSquareInfo.playerColor; }
	public void setPlayerColor(int value) {  
		this.boardSquareInfo.playerColor = value;

		this.playerPaint.setColor(value);
		this.playerPaint.setStyle(Paint.Style.FILL_AND_STROKE);			
	}

	
	public StateType getStateType() { return this.boardSquareInfo.stateType; }
	public void setStateType(StateType value) { this.boardSquareInfo.stateType = value; }
	
	public int getXAxis() { return this.boardSquareInfo.xAxis; }
	public void setXAxis(int value) { this.boardSquareInfo.xAxis = value; }
	
	public int getYAxis() { return this.boardSquareInfo.yAxis; }
	public void setYAxis(int value) { this.boardSquareInfo.yAxis = value; }
	
	public boolean getIsDirty() { return this.boardSquareInfo.isDirty; }
	public void setIsDirty(boolean value) { this.boardSquareInfo.isDirty = value; }
}
