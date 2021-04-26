package karega.scott.checkers;

public class BoardSquareInfo {
	private static final String LOG_TAG = "BoardSquareInfo";
	
	public final int id;
	public final int row;
	public final int column;
	
	public final int initialChip;
	public final int initialState;
	
	public final BoardSquareSiblings forwardSiblings;
	public final BoardSquareSiblings backwardSiblings;
	
	public int chip;
	public int state;
	public boolean isKing;
	public boolean isActive;

	public int fillColor = -7829368;    // android.graphics.Color.GRAY
	public int borderColor = -16777216; // android.graphics.Color.BLACK
	public int inactiveColor = 0;       // android.graphics.0; // android.graphics.Color.TRANSPARENT
	public int activeColor = 0;         // android.graphics.0; // android.graphics.Color.TRANSPARENT

	public BoardSquareInfo(int id, int row, int column,	int initialState, int initialChip) {
		this.id = id;
		this.row = row;
		this.column = column;
		this.initialState = initialState;
		this.state = initialState;
		this.initialChip = initialChip;
		this.chip = initialChip;
		this.isKing = false;
		this.isActive = false;
		
		this.forwardSiblings = new BoardSquareSiblings(this.id+7,this.id+9); // Down from top-left
		this.backwardSiblings = new BoardSquareSiblings(this.id-9,this.id-7); // Up from bottom-right
		
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
		this.isActive = false;
		this.activeColor = this.inactiveColor;
		invokeOnChangeListener();
	}
	
	public void activate() {
		this.isActive = true;
		this.activeColor = -1; // android.graphics.Color.WHITE
		invokeOnChangeListener();
	}
	
	/**
	 * Returns the {@link BoardSquareInfo} to its initial state
	 */
	public void reset() {
		this.chip = this.initialChip;
		this.state = this.initialState;
		this.borderColor = -16777216; // android.graphics.Color.BLACK
		this.isKing = false;
        this.isActive = false;

		switch(this.initialState) {
			case CheckersEngine.EMPTY_STATE:
				this.fillColor = -12303292; // android.graphics.Color.DKGRAY
				this.inactiveColor = 0; // android.graphics.Color.TRANSPARENT
				this.activeColor = 0; // android.graphics.Color.TRANSPARENT
				break;
				
			case CheckersEngine.LOCKED_STATE:
				this.fillColor = -7829368; // android.graphics.Color.GRAY
				this.inactiveColor = 0; // android.graphics.Color.TRANSPARENT
				this.activeColor = 0; // android.graphics.Color.TRANSPARENT
				break;
				
			case CheckersEngine.PLAYER2_STATE:
				this.fillColor = -12303292; // android.graphics.Color.DKGRAY
				this.inactiveColor = -65536; // android.graphics.Color.RED
				this.activeColor = -65536; // android.graphics.Color.RED
				break;
			
			case CheckersEngine.PLAYER1_STATE:
				this.fillColor = -12303292; // android.graphics.Color.DKGRAY
				this.inactiveColor = -16776961; // android.graphics.Color.BLUE
				this.activeColor = -16776961; // android.graphics.Color.BLUE
				break;
		} //end switch
		
		invokeOnChangeListener();
	} //end reset
	
	/**
	 * Swaps this square with an empty square
	 * @param value
	 */
	public boolean swap(BoardSquareInfo value) {
		//Log.d(LOG_TAG, "Swapping square information");

		if(value.state != CheckersEngine.EMPTY_STATE) {
			//Log.d(LOG_TAG, "Swap requires an empty square");
			return false;
		}
		
		value.chip = this.chip;
		value.fillColor = this.fillColor;
		value.borderColor = this.borderColor;
		value.inactiveColor = this.inactiveColor;
		value.state = this.state;
		value.activeColor = this.inactiveColor;
		value.isActive = this.isActive;
		value.isKing = (this.isKing || value.row == CheckersEngine.TOP_ROW || value.row == CheckersEngine.BOTTOM_ROW);
		value.deactivate();
		
		this.makeEmpty();
		return true;
	} // end swapInformation
	
	/**
	 * Change the current {@link BoardSquareInfo} to {@link BoardSquareStateType}.EMPTY
	 */
	public void makeEmpty() {
		//Log.d(LOG_TAG, "Making this square empty");
		
		if(this.initialState == CheckersEngine.LOCKED_STATE) 
			return;
		
		this.chip = CheckersEngine.EMPTY_CHIP;
		this.state = CheckersEngine.EMPTY_STATE;
		this.fillColor = -12303292; // android.graphics.Color.DKGRAY
		this.borderColor = -16777216; // android.graphics.Color.BLACK
		this.inactiveColor = 0; // android.graphics.Color.TRANSPARENT
		this.activeColor = 0; // android.graphics.Color.TRANSPARENT
		this.isKing = false;
		this.isActive = false;
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
		builder.append(String.format("is active=%s, ", this.isActive));
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
				
				this.isActive == info.isActive &&
				this.isKing == info.isKing &&
				
				this.inactiveColor == info.inactiveColor &&
				this.activeColor == info.activeColor &&
				this.fillColor == info.fillColor &&
				this.borderColor == info.borderColor);
	}
} // end BoardSquareInfo

