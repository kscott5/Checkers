package karega.scott.checkers;

import android.graphics.Color;
import android.graphics.Point;

public class BoardSquareInfo {
	private final int id;
	private final int row;
	private final int column;
	
	private final int initialChip;
	private final int initialState;
	
	private int chip;
	private int state;
	
	private int fillColor = Color.GRAY;
	private int borderColor = Color.BLACK;
	private int playerColor = Color.TRANSPARENT;
	private int activePlayerColor = Color.TRANSPARENT;
	
	public BoardSquareInfo(int id, int row, int column,	int initialState, int initialChip) {
		this.id = id;
		this.row = row;
		this.column = column;
		this.initialState = initialState;
		this.state = initialState;
		this.initialChip = initialChip;
		this.chip = initialChip;

		this.reset();
	}
	
	public int getId() { return this.id; }
	public int getRow() { return this.row; }		
	public int getColumn() { return this.column; }
	
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
	
	public int getCurrentState() { return this.state; }
	public void setCurrentState(int value) {
		this.state = value;
		invokeOnChangeListener();
	}
	
	public int getCurrentChip() { return this.chip; }
	
	public boolean isKing() { return this.chip == BoardGameEngine.KING_CHIP; }
	public void makeKing() { 
		this.chip = BoardGameEngine.KING_CHIP;
		invokeOnChangeListener();
	}

	/**
	 * Returns the {@link BoardSquareInfo} to its initial state
	 */
	public void reset() {
		this.chip = this.initialChip;
		this.state = this.initialState;
		this.borderColor = Color.BLACK;
		
		switch(this.initialState) {
			case BoardGameEngine.EMPTY_STATE:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.TRANSPARENT;
				this.activePlayerColor = Color.TRANSPARENT;
				break;
				
			case BoardGameEngine.LOCKED_STATE:
				this.fillColor = Color.GRAY;
				this.playerColor = Color.TRANSPARENT;
				this.activePlayerColor = Color.TRANSPARENT;
				break;
				
			case BoardGameEngine.PLAYER2_STATE:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.RED;
				this.activePlayerColor = Color.RED;
				break;
			
			case BoardGameEngine.PLAYER1_STATE:
				this.fillColor = Color.DKGRAY;
				this.playerColor = Color.BLUE;
				this.activePlayerColor = Color.BLUE;
				break;
		} //end switch
		
		invokeOnChangeListener();
	} //end reset
	
	/**
	 * Swap current information with {@link BoardSquareInfo} who {@link BoardSquareStateType} is EMPTY
	 * @param value
	 */
	public boolean swap(BoardSquareInfo value) {
		if(value.getCurrentState() == BoardGameEngine.EMPTY_STATE)	{
			value.chip = this.chip;
			value.fillColor = this.fillColor;
			value.borderColor = this.borderColor;
			value.playerColor = this.playerColor;
			value.state = this.state;
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
		this.chip = BoardGameEngine.EMPTY_CHIP;
		this.state = BoardGameEngine.EMPTY_STATE;
		this.fillColor = Color.DKGRAY;
		this.borderColor = Color.BLACK;
		this.playerColor = Color.TRANSPARENT;
		this.activePlayerColor = Color.TRANSPARENT;
		this.invokeOnChangeListener();		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append(String.format("id=%s, ", this.id));
		builder.append(String.format("row=%s, ", this.row));
		builder.append(String.format("column=%s, ", this.column));
		builder.append(String.format("initial chip=%s, ", this.initialChip));
		builder.append(String.format("chip=%s, ", this.chip));
		builder.append(String.format("initial state=%s, ", this.initialState));
		builder.append(String.format("state=%s, ", this.state));
		builder.append(String.format("fill color=%s, ", this.fillColor));
		builder.append(String.format("border color=%s, ", this.borderColor));
		builder.append(String.format("player color=%s, ", this.playerColor));
		builder.append(String.format("active player color=%s", this.activePlayerColor));
		builder.append("}");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object value) {
		if(!(value instanceof BoardSquareInfo)) 
			return false;
		
		BoardSquareInfo info = (BoardSquareInfo)value;		
		return (this.id == info.id && 
				this.row == info.row &&
				this.column == info.column &&
				this.state == info.state &&
				this.chip == info.chip &&
				this.playerColor == info.playerColor &&
				this.activePlayerColor == info.activePlayerColor &&
				this.fillColor == info.fillColor &&
				this.borderColor == info.borderColor);
	}
} // end BoardSquareInfo

