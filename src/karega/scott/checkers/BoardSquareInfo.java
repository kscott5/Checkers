package karega.scott.checkers;

import karega.scott.checkers.BoardSquareStateType;
import android.graphics.Color;
import android.graphics.Point;

public class BoardSquareInfo {
	public static final int HEIGHT = 39;
	public static final int WIDTH = 39;	
	public static final int START_ANGLE = 0;
	public static final int SWEEP_ANGLE = 360;
	public static final int STROKE_WIDTH = 2;
	public static final boolean USE_CENTER = false;

	public interface OnChangeListener {
		public void OnSquareInformationChange();
	}
	
	private OnChangeListener changeListener;
	
	private int id = 0;
	private Point point = new Point();
	private boolean isKing = false;
	private BoardSquareStateType stateType = BoardSquareStateType.EMPTY;
	private int fillColor = Color.GRAY;
	private int borderColor = Color.BLACK;
	private int playerColor = Color.TRANSPARENT;
	private int activePlayerColor = Color.TRANSPARENT;

	public void setOnChangeListener(OnChangeListener listener) {
		if(listener != null) {
			changeListener = listener;
		}
	}
	
	private void invokeOnChangeListener() {
		if(changeListener != null) {
			changeListener.OnSquareInformationChange();
		}
	}
	
	public void resetActivePlayerColor() {
		this.activePlayerColor = this.playerColor;
		invokeOnChangeListener();
	}
	
	public void setActivePlayerColor() {
		this.activePlayerColor = Color.WHITE;
		invokeOnChangeListener();
	}
	
	public int getFillColor() { return this.fillColor; }
	public void setFillColor(int value) { 
		this.fillColor = value;
		invokeOnChangeListener();
	}
	
	public int getBorderColor() { return this.borderColor;}
	public void setBorderColor(int value) { 
		this.borderColor = value;
		invokeOnChangeListener();
	}
	
	public int getPlayerColor() { return this.playerColor; }
	public void setPlayerColor(int value) {  
		this.playerColor = value;
		resetActivePlayerColor();
		invokeOnChangeListener();
	}

	public int getActivePlayerColor() { return this.activePlayerColor; }
	
	public BoardSquareStateType getStateType() { return this.stateType; }
	public void setStateType(BoardSquareStateType value) {
		this.stateType = value;
		invokeOnChangeListener();
	}
	
	public Point getPoint() { return point; }
	public void setPoint(Point value) {
		this.point = value;
	}	
	
	public boolean getIsKing() { return this.isKing; }
	public void setIsKing(boolean value) { 
		this.isKing = value;
		invokeOnChangeListener();
	}

	public int getId() { return this.id; }
	public void setId(int value) {
		this.id = value;
	}
	
	/**
	 * Swap current information with {@link BoardSquareInfo} who {@link BoardSquareStateType} is EMPTY
	 * @param value
	 */
	public boolean swapInformation(BoardSquareInfo value) {
		if(value.getStateType() == BoardSquareStateType.EMPTY)	{
			boolean tmpIsKing = value.isKing;
			int tmpFillColor = value.fillColor;
			int tmpBorderColor = value.borderColor;
			int tmpPlayerColor = value.playerColor;
			int tmpActivePlayerColor = value.activePlayerColor;
			
			value.isKing = this.isKing;
			value.fillColor = this.fillColor;
			value.borderColor = this.borderColor;
			value.playerColor = this.playerColor;
			value.stateType = this.stateType;
			value.invokeOnChangeListener();
			
			this.isKing = tmpIsKing;
			this.fillColor = tmpFillColor;
			this.borderColor = tmpBorderColor;
			this.playerColor = tmpPlayerColor;
			this.stateType = BoardSquareStateType.EMPTY;
			this.activePlayerColor = tmpActivePlayerColor;
			this.invokeOnChangeListener();
			
			return true;
		}
		
		return false;
	} // end swapInformation
	
	/**
	 * Change the current {@link BoardSquareInfo} to {@link BoardSquareStateType}.EMPTY
	 */
	public void makeInformationEmpty() {
		this.isKing = false;
		this.fillColor = Color.DKGRAY;
		this.borderColor = Color.BLACK;
		this.playerColor = Color.TRANSPARENT;
		this.activePlayerColor = Color.TRANSPARENT;
		this.stateType = BoardSquareStateType.EMPTY;
		this.invokeOnChangeListener();		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardSquareInfo{");
		builder.append(String.format("id=%s, ", id));
		builder.append(String.format("Point=%s, ", point.toString()));
		builder.append(String.format("isKing=%s, ", isKing));
		builder.append(String.format("stateType=%s, ", stateType));
		builder.append(String.format("fillColor=%s, ", fillColor));
		builder.append(String.format("borderColor=%s", borderColor));
		builder.append(String.format("playerColor=%s", playerColor));
		builder.append(String.format("activePlayerColor=%s", activePlayerColor));
		builder.append("}");
		return "";
	}
	
	@Override
	public boolean equals(Object value) {
		if(!(value instanceof BoardSquareInfo)) 
			return false;
		
		BoardSquareInfo info = (BoardSquareInfo)value;		
		return (this.id == info.id && 
				this.point.x == info.point.x &&
				this.point.y == info.point.y &&
				this.isKing == info.isKing &&
				this.stateType == info.stateType &&
				this.playerColor == info.playerColor &&
				this.activePlayerColor == info.activePlayerColor &&
				this.fillColor == info.fillColor &&
				this.borderColor == info.borderColor);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 22 + this.id;
		hash = hash * 34 + this.point.x;
		hash = hash * 34 + this.point.y;
		hash = hash * 42 + this.stateType.hashCode();
		hash = hash * 43 + this.fillColor + this.borderColor + this.playerColor + this.activePlayerColor;
	
		return hash;
	}
}

