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
    
	public CheckersEngine(Context context) {
		super(context, BoardGameEngine.CHECKERS_ENGINE);
	}

	@Override
	public void loadGame() {
		Log.d(LOG_TAG, "Loading an existing game");

		this.activeState = this.loadCurrentState(/* for new game */false);
		this.activeSquare = null;

		this.squares = this.loadSquares(/* for new game */false);
	} // end loadGame

	@Override
	public void newGame() {
		Log.d(LOG_TAG, "Loading a new game");

		this.activeState = BoardGameEngine.PLAYER1_STATE;
		this.activeSquare = null;

		if (this.squares == null) {
			this.squares = this.loadSquares(/* for new game */true);
		} else {
			for (int i = 0; i < this.getSize(); i++) {
				this.getData(i).reset();
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

		if (this.activeSquare != null) {
			this.activeSquare.deactivate();
		}

		this.activeSquare = null;

		this.activeState = (this.activeState == BoardGameEngine.PLAYER2_STATE) ? BoardGameEngine.PLAYER1_STATE
				: BoardGameEngine.PLAYER2_STATE;

		// TODO: Ensure no squares are highlighted
	} // end switchPlayer

	@Override
	public BoardSquareInfo getData(int id) {
		Log.v(LOG_TAG, String.format("Get data for id[%s]", id));
		try {
			int row = id / 8;
			int col = id % 8;

			BoardSquareInfo info = squares[row][col];

			if (id != info.getId())
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
		return BoardGameEngine.ROWS * BoardGameEngine.COLUMNS;
	} // end getSize

	@Override
	public void moveSquare(BoardSquareInfo target) {
		Log.d(LOG_TAG, "Move square");

		// Starting information must be set
		if (this.activeState == target.getState()) {
			if(this.activateSquare(target)) {
				Log.d(LOG_TAG, String.format("Square selected for play: %s", target));
			}
			return;
		} // end if

		if (this.moveActiveSquare(this.activeSquare, target, BoardGameEngine.PLAYER1_STATE)) {
			return; 
		}
		
		if(this.moveActiveSquare(this.activeSquare, target, BoardGameEngine.PLAYER2_STATE)) {
			return;
		}

		Log.d(LOG_TAG, "Nothing was moved");
	} // end moveSquare

	/**
	 * Tries to activate the current square
	 * 
	 * @param target is a square to activate
	 * @return
	 */
	private boolean activateSquare(BoardSquareInfo target) {
		Log.d(LOG_TAG, "Activating square info");

		boolean active = false;
		if (this.activeState != target.getState())
			return active;

		if((active = target.equals(this.activeSquare)))
			return active;
		
		// Check if square is movable
		if (this.isSquareMovable(target, BoardGameEngine.PLAYER1_STATE,	target.isKing()) || 
			this.isSquareMovable(target, BoardGameEngine.PLAYER2_STATE,	target.isKing())) {

			if (this.activeSquare != null) {
				this.activeSquare.deactivate();
				Log.d(LOG_TAG, String.format("Deactivated square: %s",
						this.activeSquare));
			}

			this.activeSquare = target;
			this.activeSquare.activate();
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
		if (!isKing && state != this.activeState)
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
		int row = (state == BoardGameEngine.PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		BoardSquareInfo square = this.getData(target.getRow() + row, target.getColumn() + col);		
		if (square != null) {
			Log.d(LOG_TAG, String.format("Square: %s", square));
			
			// Square available
			if (square.getState() == BoardGameEngine.EMPTY_STATE) {
				return true;
			}
			
			// Opponent square
			if(square.getState() != this.activeState) {
				// We need to peek at square directly after opponent's square
				boolean peekEmpty = this.isEmpty(square.getRow() + row, square.getColumn() + col);
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
		if (!this.activeSquare.isKing() && state != this.activeState)
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
		int row = (state == BoardGameEngine.PLAYER1_STATE) ? -1 : +1;
		int col = (backwards)? -1: +1;
		
		// Check  side
		BoardSquareInfo square = this.getData(start.getRow() + row, start.getColumn() + col);
		if (square == null)
			return false;

		Log.d(LOG_TAG, String.format("Square: %s", square));
		
		// Found it
		if (target.equals(square)) {
			this.activeSquare.swap(target);
			this.switchPlayer();
			return true;
		} // end if

		// STOP, square states are the same
		if(square.getState() == this.activeState)
			return false;
		
		// Peek add next square. 
		BoardSquareInfo peek = this.getData(square.getRow() + row, square.getColumn() + col);
		if(peek == null) {
			// OK, peek in the opposite direction
			peek = this.getData(square.getRow() + row, square.getColumn() + (col*-1));
			
			// STOP, we can never find target on this path
			if(peek == null)
				return false;
		}
		
		// We know now that we can't jump move to peek
		// STOP, we can never find target on this path
		if(peek.getState() == this.activeState)
			return false;
		
		// King allow to move over two consecutive empty squares
		if(this.activeSquare.isKing() 
				&& peek.getState() == BoardGameEngine.EMPTY_STATE 
				&& peek.getState() == square.getState()  
				&& moveActiveSquare(square,target,state))
			return true;
		
		// We know peek isn't an empty square
		// STOP, never jump over two square with same state 
		if(peek.getState() == square.getState())
			return false;
		
		// Remove opponent
		if (square.getState() != BoardGameEngine.EMPTY_STATE && moveActiveSquare(square, target, state)) {
			square.makeEmpty();
			return true;
		}

		// Continue moving
		if(square.getState() == BoardGameEngine.EMPTY_STATE && moveActiveSquare(square,target,state))
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
			BoardSquareInfo info = this.squares[row][col];
			if (info.getState() == BoardGameEngine.EMPTY_STATE) {
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
			BoardSquareInfo data = this.squares[row][col];
			if (data.getState() == BoardGameEngine.LOCKED_STATE) {
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
