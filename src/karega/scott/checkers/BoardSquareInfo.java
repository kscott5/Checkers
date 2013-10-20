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

	private final int id;
	private final Point point;
	private final BoardSquareStateType baseStateType;
	private final BoardSquarePieceType basePieceType;
	
	private BoardSquarePieceType pieceType;
	private BoardSquareStateType stateType = BoardSquareStateType.EMPTY;
	private int fillColor = Color.GRAY;
	private int borderColor = Color.BLACK;
	private int playerColor = Color.TRANSPARENT;
	private int activePlayerColor = Color.TRANSPARENT;
	
	public BoardSquareInfo(int id, Point point, 
			BoardSquareStateType baseStateType, 
			BoardSquarePieceType basePieceType) {
		this.id = id;
		this.point = point;
		this.baseStateType = baseStateType;
		this.basePieceType = basePieceType;
		this.pieceType = basePieceType;
	}
	/**
	 * Listener used when {@link BoardSquareInfo} state changes
	 * @author Administrator
	 *
	 */
	public interface OnChangeListener {
		public void OnSquareInformationChange();
	}
	
	private OnChangeListener changeListener;
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
	
	public void deactivatePlayer() {
		this.activePlayerColor = this.playerColor;
		invokeOnChangeListener();
	}
	
	public void activatePlayer() {
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
		deactivatePlayer();
		invokeOnChangeListener();
	}

	public int getActivePlayerColor() { return this.activePlayerColor; }
	
	public BoardSquareStateType getStateType() { return this.stateType; }
	public void setStateType(BoardSquareStateType value) {
		this.stateType = value;
		invokeOnChangeListener();
	}
	
	public Point getPoint() { return point; }	
	public int getId() { return this.id; }
	public BoardSquareStateType getBaseSquareStateType() { return this.baseStateType; }
	
	public boolean getIsKing() { return this.pieceType == BoardSquarePieceType.KING; }
	public void makeKing() { 
		this.pieceType = BoardSquarePieceType.KING;
		invokeOnChangeListener();
	}

	/**
	 * Returns the {@link BoardSquareInfo} to its initial state
	 */
	public void reset() {
		this.pieceType = this.basePieceType;
		this.borderColor = Color.BLACK;
				
		switch(this.baseStateType) {
			case EMPTY:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.TRANSPARENT;
				this.activePlayerColor = Color.TRANSPARENT;
				this.stateType = BoardSquareStateType.EMPTY;
				break;
				
			case LOCKED:
				this.fillColor = Color.GRAY;
				this.playerColor = Color.TRANSPARENT;
				this.activePlayerColor = Color.TRANSPARENT;
				this.stateType = BoardSquareStateType.EMPTY;
				break;
				
			case PLAYER1:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.RED;
				this.activePlayerColor = Color.RED;
				this.stateType = BoardSquareStateType.PLAYER2;
				break;
			
			case PLAYER2:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.BLUE;
				this.activePlayerColor = Color.BLUE;
				this.stateType = BoardSquareStateType.PLAYER1;
				break;
		} //end switch
		
		invokeOnChangeListener();
	} //end reset
	
	/**
	 * Swap current information with {@link BoardSquareInfo} who {@link BoardSquareStateType} is EMPTY
	 * @param value
	 */
	public boolean swap(BoardSquareInfo value) {
		if(value.getStateType() == BoardSquareStateType.EMPTY)	{
			value.pieceType = this.pieceType;
			value.fillColor = this.fillColor;
			value.borderColor = this.borderColor;
			value.playerColor = this.playerColor;
			value.stateType = this.stateType;
			value.activePlayerColor = this.playerColor;
			value.invokeOnChangeListener();
			
			makeEmpty();
			return true;
		}
		
		return false;
	} // end swapInformation
	
	/**
	 * Change the current {@link BoardSquareInfo} to {@link BoardSquareStateType}.EMPTY
	 */
	public void makeEmpty() {
		this.pieceType = BoardSquarePieceType.NONE;
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
		builder.append(String.format("id=%s, ", this.id));
		builder.append(String.format("Point=%s, ", this.point.toString()));
		builder.append(String.format("=%s, ", this.pieceType));
		builder.append(String.format("PieceType=%s, ", this.basePieceType));
		builder.append(String.format("StateType=%s, ", this.baseStateType));
		builder.append(String.format("fillColor=%s, ", this.fillColor));
		builder.append(String.format("borderColor=%s", this.borderColor));
		builder.append(String.format("playerColor=%s", this.playerColor));
		builder.append(String.format("activePlayerColor=%s", this.activePlayerColor));
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
				this.stateType == info.stateType &&
				this.baseStateType == info.baseStateType &&
				this.basePieceType == info.basePieceType &&
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

