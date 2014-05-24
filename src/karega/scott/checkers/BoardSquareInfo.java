package karega.scott.checkers;

import android.graphics.Color;
import android.util.Log;

public class BoardSquareInfo {
	private static final String LOG_TAG = "BoardSquareInfo";
	
	public final int id;
	public final int row;
	public final int column;
	
	public final int initialChip;
	public final int initialState;
	
	public int chip;
	public int state;
	public boolean isKing;
	
	public int fillColor = Color.GRAY;
	public int borderColor = Color.BLACK;
	public int inactiveColor = Color.TRANSPARENT;
	public int activeColor = Color.TRANSPARENT;
	
	public BoardSquareInfo(int id, int row, int column,	int initialState, int initialChip) {
		this.id = id;
		this.row = row;
		this.column = column;
		this.initialState = initialState;
		this.state = initialState;
		this.initialChip = initialChip;
		this.chip = initialChip;
		this.isKing = false;
		
		this.reset();
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
	
	public void deactivate() {
		this.activeColor = this.inactiveColor;
		invokeOnChangeListener();
	}
	
	public void activate() {
		this.activeColor = Color.WHITE;
		invokeOnChangeListener();
	}
	
	/**
	 * Returns the {@link BoardSquareInfo} to its initial state
	 */
	public void reset() {
		this.chip = this.initialChip;
		this.state = this.initialState;
		this.borderColor = Color.BLACK;
		this.isKing = false;
		
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
	 * Swaps this square with an empty square
	 * @param value
	 */
	public boolean swap(BoardSquareInfo value) {
		Log.d(LOG_TAG, "Swapping square information");

		if(value.state != BoardGameEngine.EMPTY_STATE) {
			Log.d(LOG_TAG, "Swap requires an empty square");
			return false;
		}
		
		value.chip = this.chip;
		value.fillColor = this.fillColor;
		value.borderColor = this.borderColor;
		value.inactiveColor = this.inactiveColor;
		value.state = this.state;
		value.activeColor = this.inactiveColor;
		value.isKing = (this.isKing || value.row == BoardGameEngine.TOP_ROW || value.row == BoardGameEngine.BOTTOM_ROW);
		value.deactivate();
		
		this.makeEmpty();
		return true;
	} // end swapInformation
	
	/**
	 * Change the current {@link BoardSquareInfo} to {@link BoardSquareStateType}.EMPTY
	 */
	public void makeEmpty() {
		Log.d(LOG_TAG, "Making this square empty");
		
		if(this.initialState == BoardGameEngine.LOCKED_STATE) 
			return;
		
		this.chip = BoardGameEngine.EMPTY_CHIP;
		this.state = BoardGameEngine.EMPTY_STATE;
		this.fillColor = Color.DKGRAY;
		this.borderColor = Color.BLACK;
		this.inactiveColor = Color.TRANSPARENT;
		this.activeColor = Color.TRANSPARENT;
		this.isKing = false;
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
		builder.append(String.format("is king=%s, ", this.isKing));
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

