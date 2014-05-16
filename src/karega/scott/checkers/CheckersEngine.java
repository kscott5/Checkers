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
	public void moveSquare(BoardSquareInfo square) {
		Log.d(LOG_TAG, "Move square");

		// Starting information must be set
		if (this.activeState == square.getState()) {
			this.activateSquare(square);
			return;
		} // end if

		// Try moving square
		if (this.moveActiveSquare(this.activeSquare, square, BoardGameEngine.PLAYER1_STATE) || 
			this.moveActiveSquare(this.activeSquare, square, BoardGameEngine.PLAYER2_STATE)) {
			return;
		} // end if

		Log.d(LOG_TAG, "ERROR: Nothing was moved");
	} // end moveSquare

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
		Log.d(LOG_TAG, String.format("Get data for id[%s]", id));
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

	/**
	 * Tries to activate the current square
	 * 
	 * @param square
	 * @return
	 */
	private boolean activateSquare(BoardSquareInfo square) {
		Log.d(LOG_TAG, "Activating square info");

		boolean active = false;
		if (this.activeState != square.getState())
			return active;

		if((active = square.equals(this.activeSquare)))
			return active;
		
		// Check if square is movable
		if (this.isSquareMovable(square, BoardGameEngine.PLAYER1_STATE,	square.isKing()) || 
			this.isSquareMovable(square, BoardGameEngine.PLAYER2_STATE,	square.isKing())) {

			if (this.activeSquare != null) {
				this.activeSquare.deactivate();
				Log.d(LOG_TAG, String.format("Deactivated square: %s",
						this.activeSquare));
			}

			this.activeSquare = square;
			this.activeSquare.activate();
			Log.d(LOG_TAG, String.format("Activated square: %s", square));

			active = true;
		} // end if

		return active;
	} // end activateSquare

	/**
	 * Determines if the selected square is movable for play
	 * 
	 * @param square
	 * @return
	 */
	private boolean isSquareMovable(BoardSquareInfo square, int state,
			boolean isKing) {
		Log.d(LOG_TAG, "Is square movable (recursive)");

		// No need to check in opposite direction
		if (!isKing && state != this.activeState)
			return false;

		// See note at class level
		int row = (state == BoardGameEngine.PLAYER1_STATE) ? -1 : +1;

		BoardSquareInfo left = this.getData(square.getRow() + row,
				square.getColumn() - 1);
		if (left != null) {
			if (left.getState() == BoardGameEngine.EMPTY_STATE)
				return true;

			// Continue checking at next level
			if (isKing && left.getState() != this.activeState
					&& isSquareMovable(left, state, isKing))
				return true;
		}

		BoardSquareInfo right = this.getData(square.getRow() + row,
				square.getColumn() + 1);
		if (right != null) {
			if (right.getState() == BoardGameEngine.EMPTY_STATE)
				return true;

			// Continue checking at next level
			if (isKing && right.getState() != this.activeState
					&& isSquareMovable(right, state, isKing))
				return true;
		}

		return false;
	} // end isSquareMoveable

	/**
	 * Tries to moving the active square to its target square
	 * 
	 * @param start
	 * @param target
	 * @param state
	 * @return
	 */
	private boolean moveActiveSquare(BoardSquareInfo start,
			BoardSquareInfo target, int state) {
		Log.d(LOG_TAG, "Trying to move square (recursive)");

		if (start == null || target == null)
			return false;

		// No need to check in opposite direction
		if (!this.activeSquare.isKing() && state != this.activeState)
			return false;

		// See note at class level
		int row = (state == BoardGameEngine.PLAYER1_STATE) ? -1 : +1;

		// Check left side
		BoardSquareInfo left = this.getData(start.getRow() + row,
				start.getColumn() - 1);
		if (left == null)
			return false;

		// Found it
		if (target.equals(left)) {
			this.activeSquare.swap(target);
			this.switchPlayer();
			return true;
		} // end if

		// Remove opponent
		if (left.getState() != this.activeState
				&& left.getState() != BoardGameEngine.EMPTY_STATE
				&& moveActiveSquare(left, target, state)) {
			left.makeEmpty();
			return true;
		} // end if

		// Continue traversing
		if (left.getState() == BoardGameEngine.EMPTY_STATE) {
			// This is a peek forward
			boolean peekNotEmpty = !this.isEmpty(left.getRow() + row, left.getColumn() - 1);
			if ((peekNotEmpty || this.activeSquare.isKing()) &&
				moveActiveSquare(left, target, state)) {
					return true;
			}
		} // end if

		// Check right side
		BoardSquareInfo right = this.getData(start.getRow() + row,
				start.getColumn() + 1);
		if (right == null)
			return false;

		// Found it
		if (target.equals(right)) {
			this.activeSquare.swap(target);
			this.switchPlayer();
			return true;
		} // end if

		// Remove opponent
		if (right.getState() != this.activeState
				&& right.getState() != BoardGameEngine.EMPTY_STATE
				&& moveActiveSquare(right, target, state)) {
			right.makeEmpty();
			return true;
		} // end if

		// Continue traversing
		if (right.getState() == BoardGameEngine.EMPTY_STATE) {
			// This is a peek forward
			boolean peekNotEmpty = !this.isEmpty(right.getRow() + row, right.getColumn() + 1); 
			if ((peekNotEmpty || this.activeSquare.isKing()) && moveActiveSquare(right, target, state)) {
				return true;
			}
		} // end if

		return false;
	} // end tryMovingSquare

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
				Log.d(LOG_TAG,
						String.format("Is empty for row[%s] col[%s]", row, col));
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(LOG_TAG, String.format(
					"Is empty array index out of bounds for row[%s] col[%s]",
					row, col));
			return false;
		}

		Log.d(LOG_TAG,
				String.format("Not is empty for row[%s] col[%s]", row, col));
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
			Log.e(LOG_TAG, String.format(
					"Get data array index out of bounds for row[%s] col[%s]",
					row, col));
			return null;
		}
	} // end getData
} // end CheckersEngine
