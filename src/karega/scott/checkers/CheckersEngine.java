package karega.scott.checkers;

import android.content.Context;
import android.util.Log;

/**
 * Game engine for checkers
 * 
 * @author Administrator
 * 
 *         NOTE: Player 2 is top-left = {0,0} Player 1 is bottom-right = {7,7}
 * 
 * @remarks Why didn't I use enumerated values? Is this android best practice?
 */
public class CheckersEngine extends BoardGameEngine {
	private static final String LOG_TAG = "CheckersEngine";

	private BoardSquareInfo[][] squares;
	private BoardSquareInfo activeSquare;

	private int activeState;
    
	public CheckersEngine(Context context, boolean vsComputer) {
		super(context, CHECKERS_ENGINE, vsComputer);
	}

	@Override
	public void loadGame() {
		Log.d(LOG_TAG, "Loading an existing game");

		activeState = loadCurrentState(/* for new game */false);
		activeSquare = null;

		squares = loadSquares(/* for new game */false);
	} // end loadGame

	@Override
	public void newGame() {
		Log.d(LOG_TAG, "Loading a new game");

		activeState = PLAYER1_STATE;
		activeSquare = null;

		if (squares == null) {
			squares = loadSquares(/* for new game */true);
		} else {
			for (int i = 0; i < getSize(); i++) {
				getData(i).reset();
			} // end for
		} // end if
	} // end newGame

	@Override
	public void saveGame() {
		Log.d(LOG_TAG, "Saving game");

		throw new Error("Save Game Not implemented");
	}

	@Override
	public void switchPlayer() {
		Log.d(LOG_TAG, "Switching players");

		if (activeSquare != null) {
			activeSquare.deactivate();
		}

		activeSquare = null;
		activeState = (activeState == PLAYER2_STATE) ? PLAYER1_STATE : PLAYER2_STATE;

		moveSquareForComputer();
	} // end switchPlayer

	@Override
	public BoardSquareInfo getData(int id) {
		Log.v(LOG_TAG, String.format("Get data for id[%s]", id));
		try {
			int row = id / 8;
			int col = id % 8;

			BoardSquareInfo info = squares[row][col];

			if (id != info.id)
				throw new Error("Get data for id " + id + ", not found");

			return info;
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(LOG_TAG, String.format(
					"Get data array index out of bounds for id[%s]", id));
		}

		return null;
	} // end getData

	@Override
	public int getSize() {
		return ROWS * COLUMNS;
	} // end getSize

	@Override
	public void moveSquare(BoardSquareInfo target) {
		Log.d(LOG_TAG, "Move square");

		// Starting information must be set
		if (activeState == target.state) {
			
			// Don't allow engine to re-select square
			if(target.state == PLAYER2_STATE && vsComputer) {
				return;
			}
			
			if(activateSquare(target)) {
				Log.d(LOG_TAG, String.format("Square selected for play: %s", target));
			}
			return;
		} // end if

		if (moveActiveSquare(activeSquare, target, PLAYER1_STATE)) {
			return; 
		}
		
		if(moveActiveSquare(activeSquare, target, PLAYER2_STATE)) {
			return;
		}

		Log.d(LOG_TAG, "Nothing was moved");
	} // end moveSquare

	@Override
	protected void moveSquareForComputer() {
		Log.d(LOG_TAG, "Moving square for computer");
		
		if(!vsComputer || activeState != PLAYER2_STATE) { 
			return;
		}
		
		int ubound = ROWS*COLUMNS;
		int tries = 0;
		
		BoardSquareInfo square = null;
		while(!activateSquare(square) && tries++ <= ubound*NUM_OF_TRIES) {
			int id = random.nextInt(ubound);
			square = getData(id);
		} // end while
		
		if(activeSquare == null) {
			Log.e(LOG_TAG, "No available square for the computer to move");
			return;
		} // end if
	
		tries = 0;
		while(activeState == PLAYER2_STATE && tries++ <= ubound*NUM_OF_TRIES) {
			int id = random.nextInt(ubound);
			square = getData(id);
			moveSquare(square);
		} // end while
		
		if(activeSquare != null) {
			Log.e(LOG_TAG, "No available square for the computer to move to");
			return;
		} // end if
	
	} // end moveSquareForComputer
	
	/**
	 * Tries to activate the current square
	 * 
	 * @param target is a square to activate
	 * @return
	 */
	private boolean activateSquare(BoardSquareInfo target) {
		Log.d(LOG_TAG, "Activating square info");

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
				Log.d(LOG_TAG, String.format("Deactivated square: %s",
						activeSquare));
			}

			activeSquare = target;
			activeSquare.activate();
			Log.d(LOG_TAG, String.format("Activated square: %s", target));

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
		Log.d(LOG_TAG, String.format("Is square movable (recursive) square: %s, state: %s, isKing: %s",target,state,isKing));

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
		Log.d(LOG_TAG, "Ensure square movable");

		// NOTE: THIS METHOD HELPS REDUCE DUPLICATE CODE
		
		// See note at class level
		int row = (state == PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		BoardSquareInfo square = getData(target.row + row, target.column + col);		
		if (square != null) {
			Log.d(LOG_TAG, String.format("Square: %s", square));
			
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
	 * 
	 * @return false is successful, otherwise false
	 */
	private boolean moveActiveSquare(BoardSquareInfo start,	BoardSquareInfo target, int state) {
		Log.d(LOG_TAG, String.format("Move active square (recursive) - start: %s, target: %s, state: %s",start,target,state));

		if (start == null || target == null)
			return false;

		// No need to check the opposite direction
		if (!activeSquare.isKing && state != activeState)
			return false;

		if(searchBoardForTarget(start, target, state, /*backwards*/ true)) {
			return true;
		} 

		if(searchBoardForTarget(start, target, state, /*backwards*/ false)) {
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
	 *  
	 * @return false is successful, otherwise false
	 */
	private boolean searchBoardForTarget(BoardSquareInfo start, BoardSquareInfo target, int state, boolean backwards) {
		Log.d(LOG_TAG, "Search board for target");
		
		// NOTE: THIS METHOD HELPS REDUCE DUPLICATE CODE
		
		// See note at class level
		int row = (state == PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		// Check  side
		BoardSquareInfo square = getData(start.row + row, start.column + col);
		if (square == null)
			return false;

		Log.d(LOG_TAG, String.format("Square: %s", square));
		
		// Found it
		if (target.equals(square)) {
			activeSquare.swap(target);
			switchPlayer();
			return true;
		} // end if

		// STOP, square states are the same
		if(square.state == activeState)
			return false;
		
		// Peek add next square. 
		BoardSquareInfo peek = getData(square.row + row, square.column + col);
		if(peek == null) {
			if(!activeSquare.isKing) return false;
			
			if(searchBoardForTarget(start, target, state, !backwards)) 
				return true;
			
			return false;
		} // end if
		
		
		// We know now that we can't jump move to peek
		// STOP, we can never find target on this path
		if(peek.state == activeState)
			return false;
		
		// King allow to move over two consecutive empty squares
 		if(activeSquare.isKing 
				&& peek.state == EMPTY_STATE 
				&& peek.state == square.state  
				&& moveActiveSquare(square,target,state))
			return true;
		
		// We know peek isn't an empty square
		// STOP, never jump over two square with same state 
		if(peek.state == square.state)
			return false;
		
		// Remove opponent
		if (square.state != EMPTY_STATE && moveActiveSquare(square, target, state)) {
			square.makeEmpty();
			return true;
		}

		// Continue moving
		if(square.state == EMPTY_STATE && moveActiveSquare(square,target,state))
			return true;
				
		return false;
	} // end searchBoardForTarget
	
	/**
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean isEmpty(int row, int col) {
		try {
			BoardSquareInfo info = squares[row][col];
			if (info.state == EMPTY_STATE) {
				Log.d(LOG_TAG, String.format("Is empty for row[%s] col[%s]", row, col));
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.d(LOG_TAG, String.format("Is empty array out of bounds for row[%s] col[%s]", row, col));
			return false;
		}

		Log.d(LOG_TAG, String.format("Not is empty for row[%s] col[%s]", row, col));
		return false;
	} // end isEmpty

	/**
	 * Gets the square at these coordinates.
	 * 
	 * @param row
	 * @param col
	 * @return Square information or null for LOCKED_STATE
	 */
	private BoardSquareInfo getData(int row, int col) {
		Log.d(LOG_TAG, String.format("Get data row[%s] col[%s]", row, col));

		try {
			BoardSquareInfo data = squares[row][col];
			if (data.state == LOCKED_STATE) {
				Log.e(LOG_TAG, String.format(
						"Get data for row[%s] and col[%s] is locked", row, col));
				return null;
			}

			return data;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	} // end getData
} // end CheckersEngine
