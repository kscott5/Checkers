package karega.scott.checkers;

import java.util.ArrayList;

/**
 * Game engine for checkers
 * 
 * @author Administrator
 * 
 * NOTE: Player 2 is top-left = {0,0} Player 1 is bottom-right = {7,7}
 * 
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
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	@Override
	public boolean isEmpty(int row, int col) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return false;
		if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return false;

		BoardSquareInfo info = this.engineSquares[row][col];
		if (info.state == EMPTY_STATE) return true;

		return false;
	} // end isEmpty

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

		for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++){
			BoardSquareInfo square = this.engineSquares[row][col];
			if(square.id == position) return square;
		}

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
		if(row%2 == 0 && col%2 == 0) return LOCKED_STATE;
		if(row%2 != 0 && col%2 != 0) return LOCKED_STATE;

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
	public void moveSquare(BoardSquareInfo target) {
		// Starting information must be set
		if (activeState == target.state) {
						
			if(isDevice())
				return;

			activateSquare(target);
			return;
		} // end if

		ArrayList<BoardSquareInfo> path = new ArrayList<BoardSquareInfo>();
		
		if (moveActiveSquare(activeSquare, target, PLAYER1_STATE, path) 
				&& validatePath(activeSquare, target, path)) {
			return; 
		}
		
		if(moveActiveSquare(activeSquare, target, PLAYER2_STATE, path)
				&& validatePath(activeSquare, target, path)) {
			return;
		}

		determineWinner();
	} // end moveSquare

	@Override
	protected void moveSquareForDevice() {
	} // end moveSquareForDevice
	
	/**
	 * Tries to activate the current square
	 * 
	 * @param target is a square to activate
	 * @return
	 */
	private boolean activateSquare(BoardSquareInfo target) {
		boolean active = false;
		if(target == null)
			return active;
		
		if (activeState != target.state)
			return active;

		if((active = target.equals(activeSquare)))
			return active;
		
		// Check if square is movable
		if (isSquareMovable(target, PLAYER1_STATE,	target.isKing) || 
			isSquareMovable(target, PLAYER2_STATE,	target.isKing)) {

			if (activeSquare != null) {
				activeSquare.deactivate();
			}

			activeSquare = target;
			activeSquare.activate();

			active = true;
		} // end if

		return active;
	} // end activateSquare

	/**
	 * Determines if the selected square is movable
	 * 
	 * @param target is square to check
	 * @param state is either PLAYER1_STATE or PLAYER2_STATE and used to determine row search direction
	 * @isKing flag used to determine if search is greater than 1 level
	 *  
	 * @return false is successful, otherwise false
	 */
	private boolean isSquareMovable(BoardSquareInfo target, int state, boolean isKing) {
		// No need to check in opposite direction
		if (!isKing && state != activeState)
			return false;

		if(ensureSquareMovable(target, state, isKing, /*backwards*/ true)) {
			return true;
		}

		if(ensureSquareMovable(target, state, isKing, /*backwards*/ false)) {
			return true;
		}

		return false;
	} // end isSquareMoveable

	/**
	 * Ensures the square is movable
	 * 
	 * @param target is square to check
	 * @param state is either PLAYER1_STATE or PLAYER2_STATE and used to determine row search direction
	 * @isKing flag used to determine if search is greater than 1 level
	 * @backwards flag used to determine column search direction
	 * 
	 * @return false is successful, otherwise false
	 */
	private boolean ensureSquareMovable(BoardSquareInfo target, int state,	boolean isKing, boolean backwards) {
		//Log.d(LOG_TAG, "Ensure square movable");

		// NOTE: THIS METHOD HELPS REDUCE DUPLICATE CODE
		
		// See note at class level
		int row = (state == PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		BoardSquareInfo square = getData(target.row + row, target.column + col);		
		if (square != null) {
			//Log.d(LOG_TAG, String.format("Square: %s", square));
			
			// Square available
			if (square.state == EMPTY_STATE) {
				return true;
			}
			
			// Opponent square
			if(square.state != activeState) {
				// We need to peek at square directly after opponent's square
				boolean peekEmpty = isEmpty(square.row + row, square.column + col);
				if(peekEmpty) { 
					return true;
				}
				
				// Continue checking at next level
				if(isKing && isSquareMovable(square, state, isKing)) {
					return true;
				}
			} // end if
		} // end if

		return false;
	} // end ensureSquareMovable

	/**
	 * Tries to moving the active square to the target square
	 * 
	 * @param start position of the selected square
	 * @param target location for the selected square
	 * @param state is either PLAYER1_STATE or PLAYER2_STATE and used to determine row search direction
	 * @param path is the list of squares that should be modified between start and target squares
	 * @return false is successful, otherwise false
	 */
	private boolean moveActiveSquare(BoardSquareInfo start,	BoardSquareInfo target, int state, ArrayList<BoardSquareInfo> path) {
		//Log.d(LOG_TAG, "Move active square (recursive)");
		//Log.d(LOG_TAG, String.format("*****start: %s",start));
		//Log.d(LOG_TAG, String.format("*****target: %s",target));
		//Log.d(LOG_TAG, String.format("*****state: %s",state));

		if (start == null || target == null)
			return false;

		// No need to check the opposite direction
		if (!activeSquare.isKing && state != activeState)
			return false;

		if(searchBoardForTarget(start, target, state, /*backwards*/ true, path)) {
			return true;
		} 

		if(searchBoardForTarget(start, target, state, /*backwards*/ false, path)) {
			return true;
		} 

		return false;
	} // end moveActiveSquare

	/**
	 * Search the board for the target
	 * 
	 * @param start position of the selected square
	 * @param target location for the selected square
	 * @param state is either PLAYER1_STATE or PLAYER2_STATE and used to determine row search direction
	 * @backwards flag used to determine column search direction
	 * @param path is the list of squares that should be modified between start and target squares
	 *  
	 * @return false is successful, otherwise false
	 */
	private boolean searchBoardForTarget(BoardSquareInfo start, BoardSquareInfo target, int state, boolean backwards, ArrayList<BoardSquareInfo> path) {
		//Log.d(LOG_TAG, "Search board for target");
		//Log.d(LOG_TAG, String.format("*****start: %s",start));
		//Log.d(LOG_TAG, String.format("*****target: %s",target));
		//Log.d(LOG_TAG, String.format("*****state: %s",state));
		//Log.d(LOG_TAG, String.format("*****backwards: %s",backwards));
		
		// NOTE: Revisit code to ensure logic correct, especially if 
		//       I continue to add checks for cases not handled
		
		// See note at class level
		int row = (state == PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		// Check  side
		BoardSquareInfo square = getData(start.row + row, start.column + col);
		if (square == null) {
			//Log.d(LOG_TAG, "*****square evaluate to null");
			return false;
		}

		//Log.d(LOG_TAG, String.format("*****square evaluated: %s", square));
		
		// Found it
		if (target.equals(square)) {
			//Log.d(LOG_TAG, "*****target found");
			return true;
		} // end if

		// STOP, square states are the same
		if(square.state == activeState)
			return false;
		
		// Peek at next square. 
		BoardSquareInfo peek = getData(square.row + row, square.column + col);
		if(peek == null) {
			//Log.d(LOG_TAG, "*****peek is null");
						
			return false;
		} // end if
		
		// We know now that we can't jump move to peek
		// STOP, we can never find target on this path
		if(peek.state == activeState) {
			//Log.d(LOG_TAG, "*****stop, we can never move pass a square with same state");
			return false;
		}
		
		// King allow to move over two consecutive empty squares
 		if(activeSquare.isKing 
				&& peek.state == EMPTY_STATE 
				&& peek.state == square.state  
				&& moveActiveSquare(square,target,state,path)) {
 			//Log.d(LOG_TAG, "*****king moved over two or more consecutive emtpy squares complete");
 			path.add(square);
			return true;
 		}
		
		// We know peek isn't an empty square
		// STOP, never jump over two square with same state 
		if(peek.state == square.state) {
			//Log.d(LOG_TAG, "*****stop, never jump over two squares with same state");
			return false;
		}
		
		// Remove opponent
		if (square.state != EMPTY_STATE && moveActiveSquare(square, target, state,path)) {
			//Log.d(LOG_TAG, String.format("*****removing opponent square: %s", square));
			path.add(square);
			return true;
		}

		// Continue moving
		if(square.state == EMPTY_STATE && moveActiveSquare(square,target,state,path)) {
			//Log.d(LOG_TAG, "*****continue moving done");
			path.add(square);
			return true;
		}
		
		//Log.d(LOG_TAG, "*****target not found on this path");
		return false;
	} // end searchBoardForTarget	

	/**
	 * Search the board for the target
	 * 
	 * @param start position of the selected square
	 * @param target location for the selected square
	 * @param path is the list of squares that should be modified between start and target squares
	 *  
	 * @return false is successful, otherwise false
	 */
	private boolean validatePath(BoardSquareInfo start, BoardSquareInfo target, ArrayList<BoardSquareInfo> path) {
		//Log.d(LOG_TAG, String.format("Validating path: [%s]",path));
		
		// Invalid if start and target row are separated 
		// by 2 spaces and columns are the same
		boolean valid = !(Math.abs(start.row-target.row) == 2 
				&& start.column == target.column);
		
		// Invalid if start and target columns are separated 
		// by 2 spaces and rows are the same
		valid = valid && !(start.row == target.row
				&& Math.abs(start.column-target.column) == 2);
		
		if(valid && path.size() > 1) {
			BoardSquareInfo square = path.get(path.size()-2);
			
			// Invalid if start and target row are separated 
			// by 2 spaces and columns are the same
			valid = valid && !(Math.abs(square.row-target.row) == 2 
					&& square.column == target.column);
			
			// Invalid if start and target columns are separated 
			// by 2 spaces and rows are the same
			valid = valid && !(square.row == target.row
					&& Math.abs(square.column-target.column) == 2);
		}
		
		if(valid) {
			start.swap(target);
			for(BoardSquareInfo square : path) {
				square.makeEmpty();
			}
			switchPlayer();
		}
		
		return valid;
	}
} // end CheckersEngine
