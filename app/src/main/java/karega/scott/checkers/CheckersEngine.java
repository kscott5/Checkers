package karega.scott.checkers;

import java.util.ArrayList;

/**
 * Game engine for checkers
 * 
 * NOTE: Board layout is  top-left = {0,0}  bottom-right = {7,7}
 * 		 Player 2 top
 *       Player 1 bottom
 * @remarks Why didn't I use enumerated values? Is this android best practice?
 * 
 * http://developer.android.com/training/articles/memory.html
 * Enums often require more than twice as much memory as static constants. 
 * You should strictly avoid using enums on Android.
 */
public class CheckersEngine extends BoardGameEngine {
	private static final String LOG_TAG = "CheckersEngine";

	private final int CHECKERS_ENGINE_ROWS = 8;
	private final int CHECKERS_ENGINE_COLUMNS = 8;

	public CheckersEngine(boolean vsDevice) {
		super(CHECKERS_ENGINE, vsDevice);

		this.initialBoardSquares();
	}

	/**
	 * Gets the square at these coordinates.
	 * 
	 * @param row
	 * @param col
	 * @return Square information or null for LOCKED_STATE
	 */
	@Override
	public BoardSquareInfo getData(int row, int col) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return null;
        if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return null;	
	 
		return this.engineSquares[row][col];	
	} // end getData

	/**
     * Gets the square at the screen position {0..63}
     * 
     * @param position
     * @return Square information or null for LOCKED_STATE
     */
	@Override
	public BoardSquareInfo getData(int position) {
		if(position < 0 || position >= CHECKERS_ENGINE_ROWS*CHECKERS_ENGINE_COLUMNS) return null;

		final int divisor = 8;
		int row = (position/divisor);
		int col = (row*divisor)-position;

		BoardSquareInfo square = this.engineSquares[row][col];
		if(square.id == position) return square;

		return null;
	} // eng getData

	/**
	 * Returns the size of the game engine board. Must call newGame() to load board first.
	 * @return
	 */
	@Override
	public int getSize() {
		try {
			return this.engineSquares.length*this.engineSquares[0].length;
		} catch(Exception e) {
			return 0;
		}
	} // end getSize
	
	@Override
	public void newGame() {
		super.newGame();
		
		for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {
			for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				this.engineSquares[row][col].reset();
			}
		} // end for		
	} // end newGame
	
	public int generateSquareId(int row, int col) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return -1;
		if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return -1;

		final int multiplier = 8;
		return ((multiplier*row)+col);
	}

	public int generateSquareState(int row, int col) {
	 	if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return -1;
        if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return -1;

		// 32 squares or 50% with square state of
		if((row%2 == 0 && col%2 == 0) || 
			(row%2 != 0 && col%2 != 0)) return LOCKED_STATE;

		// 12 squares with square state of
		if((row>=0 && row <=2) && (
			(row%2 == 0 && col%2 != 0) ||
			(row%2 != 0 && col%2 == 0))) return PLAYER2_STATE;

		// 12 squares with square state of
		if((row >=5 && row <=7) && (
			(row%2 == 0 && col%2 != 0) ||
			(row%2 != 0 && col%2 == 0))) return PLAYER1_STATE;

		// 8 squares with square state of
		return EMPTY_STATE;
	}

	public boolean updateSquareState(BoardSquareInfo square, int newState) {
		if(square == null || this.activeState != newState) return false;
		if(square.state == LOCKED_STATE) return false;

		square.state = newState;
		return true;
	}

	public boolean updateSquareState(int row, int col, int newState) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return false;
		if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return false;
		
		if(this.activeState != newState) return false;

		BoardSquareInfo square = this.getData(row,col);
		if(square.state == LOCKED_STATE) return false;

		return this.updateSquareState(square, newState);
	}

	public void setBoardSquaresEmpty() {
        for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {	
            for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				BoardSquareInfo square = this.getData(row,col);

				if(square.state != LOCKED_STATE) {
					square.chip = EMPTY_CHIP;
					square.state = EMPTY_STATE;
				}
            }
        }
	} // setBoardSquaresEmpty

	public void initialBoardSquares() {
		this.engineSquares = new BoardSquareInfo[CHECKERS_ENGINE_ROWS][CHECKERS_ENGINE_COLUMNS];

        for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {	
            for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				int id = this.generateSquareId(row,col);
				
				int state = this.generateSquareState(row,col);
            	int chip = (state == PLAYER1_STATE || state == PLAYER2_STATE)? PAWN_CHIP: EMPTY_CHIP;

	            this.engineSquares[row][col] = new BoardSquareInfo(id,row,col,state,chip);
            }
        }
	} // end initialBoardSquares
	
	@Override
	public void moveSquare(BoardSquareInfo[] squares) {
	}

	@Override
	public void moveSquare(BoardSquareInfo target) {
	} // end moveSquare

	@Override
	protected void moveSquareForDevice() {
	} // end moveSquareForDevice
} // end CheckersEngine
