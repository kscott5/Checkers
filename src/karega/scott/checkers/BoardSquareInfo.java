package karega.scott.checkers;

import android.graphics.Color;
import android.util.Log;

public class BoardSquareInfo {
	private static final String LOG_TAG = "BoardSquareInfo";
	
	private final int id;
	private final int row;
	private final int column;
	
	private final int initialChip;
	private final int initialState;
	
	private int chip;
	private int state;
	
	private int fillColor = Color.GRAY;
	private int borderColor = Color.BLACK;
	private int inactiveColor = Color.TRANSPARENT;
	private int activeColor = Color.TRANSPARENT;
	
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
	
	public void deactivate() {
		this.activeColor = this.inactiveColor;
		invokeOnChangeListener();
	}
	
	public void activate() {
		this.activeColor = Color.WHITE;
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
	
	public int getInactiveColor() { return this.inactiveColor; }
	public void setInactiveColor(int value) {  
		this.inactiveColor = value;
		deactivate();
		invokeOnChangeListener();
	}

	public int getActiveColor() { return this.activeColor; }
	
	public int getState() { return this.state; }
	public void setState(int value) {
		this.state = value;
		invokeOnChangeListener();
	}
	
	public int getChip() { return this.chip; }
	
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
				this.inactiveColor = Color.TRANSPARENT;
				this.activeColor = Color.TRANSPARENT;
				break;
				
			case BoardGameEngine.LOCKED_STATE:
				this.fillColor = Color.GRAY;
				this.inactiveColor = Color.TRANSPARENT;
				this.activeColor = Color.TRANSPARENT;
				break;
				
			case BoardGameEngine.PLAYER2_STATE:
				this.fillColor = Color.DKGRAY;
				this.inactiveColor = Color.RED;
				this.activeColor = Color.RED;
				break;
			
			case BoardGameEngine.PLAYER1_STATE:
				this.fillColor = Color.DKGRAY;
				this.inactiveColor = Color.BLUE;
				this.activeColor = Color.BLUE;
				break;
		} //end switch
		
		invokeOnChangeListener();
	} //end reset
	
	/**
	 * Swap current information with {@link BoardSquareInfo} who {@link BoardSquareStateType} is EMPTY
	 * @param value
	 */
	public boolean swap(BoardSquareInfo value) {
		Log.d(LOG_TAG, "Swapping square information");

		if(this.state == BoardGameEngine.LOCKED_STATE)
			return false;

		if(value.state != BoardGameEngine.EMPTY_STATE)
			return false;
		
		value.chip = this.chip;
		value.fillColor = this.fillColor;
		value.borderColor = this.borderColor;
		value.inactiveColor = this.inactiveColor;
		value.state = this.state;
		value.activeColor = this.inactiveColor;			
		value.deactivate();
		
		this.makeEmpty();
		return true;
	} // end swapInformation
	
	/**
	 * Change the current {@link BoardSquareInfo} to {@link BoardSquareStateType}.EMPTY
	 */
	public void makeEmpty() {
		Log.d(LOG_TAG, "Making this square empty");
		
		this.chip = BoardGameEngine.EMPTY_CHIP;
		this.state = BoardGameEngine.EMPTY_STATE;
		this.fillColor = Color.DKGRAY;
		this.borderColor = Color.BLACK;
		this.inactiveColor = Color.TRANSPARENT;
		this.activeColor = Color.TRANSPARENT;
		
		this.deactivate();	
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
		builder.append(String.format("player color=%s, ", this.inactiveColor));
		builder.append(String.format("active player color=%s", this.activeColor));
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
				this.inactiveColor == info.inactiveColor &&
				this.activeColor == info.activeColor &&
				this.fillColor == info.fillColor &&
				this.borderColor == info.borderColor);
	}
} // end BoardSquareInfo

