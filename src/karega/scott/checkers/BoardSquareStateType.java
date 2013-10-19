package karega.scott.checkers;

/**
 * State of the Square
 * @author Administrator
 *
 */
public enum BoardSquareStateType {
	EMPTY(0), 
	PLAYER1(1), // TODO: Should this be in separate enum
	PLAYER2(2), // TODO: Should this be in separate enum
	LOCKED(3);
	
	public final int value;
	private BoardSquareStateType(int value) { this.value = value; }
	
	public static BoardSquareStateType valueOf(int value) {
		for(BoardSquareStateType type : BoardSquareStateType.values()) {
			if(type.value == value)
				return type;
		}
		
		return BoardSquareStateType.EMPTY;
	} // end valueOf			
};
