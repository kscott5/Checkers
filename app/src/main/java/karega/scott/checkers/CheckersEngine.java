package karega.scott.checkers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

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
public class CheckersEngine  {
	private static final String LOG_TAG = "CheckersEngine";
	public static final String VS_DEVICE = "you vs computer";
	
	protected static final Random random = new Random();
	
	protected static final int NUM_OF_TRIES = 6;
	
	public static final int TOP_ROW = 0;
	public static final int BOTTOM_ROW = 7;
	protected static final int ROWS = 8;
	protected static final int COLUMNS = 8;
	
	public static final int SQUARE_HEIGHT = 30;
	public static final int SQUARE_WIDTH = 30;
	
	public static final int SQUARE_CHIP_START_ANGLE = 0;
	public static final int SQUARE_CHIP_SWEEP_ANGLE = 360;
	public static final int SQUARE_CHIP_STROKE_WIDTH = 2;
	public static final boolean SQUARE_CHIP_USE_CENTER = false;

	public static final int CHECKERS_ENGINE = 1;
	public static final int CHESS_ENGINE = 2;
	
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	
	public static final int PLAYER1_STATE = 1;
	public static final int PLAYER2_STATE = 2;
	public static final int EMPTY_STATE = 3;
	public static final int LOCKED_STATE = 4;

	public static final int EMPTY_CHIP = 1;
	public static final int PAWN_CHIP = 2;
		
	protected final int engineId;
	protected final boolean vsDevice;
	
	protected BoardSquareInfo[][] engineSquares;
	protected BoardSquareInfo activeSquare;
	protected int activeState;
	
	private final int CHECKERS_ENGINE_ROWS = 8;
	private final int CHECKERS_ENGINE_COLUMNS = 8;

	public CheckersEngine(boolean vsDevice) {
		this.vsDevice = vsDevice;
		this.engineId = CHECKERS_ENGINE;
		this.activeState = PLAYER1_STATE;
		this.activeSquare = null;

		this.initialBoardSquares();
	}

	/**
	 * Gets the square at these coordinates.
	 * 
	 * @param row
	 * @param col
	 * @return Square information or null for LOCKED_STATE
	 */
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
	public BoardSquareInfo getData(int position) {
		if(position < 0 || position >= CHECKERS_ENGINE_ROWS*CHECKERS_ENGINE_COLUMNS) return null;

		final int divisor = 8;
		int row = (position/divisor);
		int col = position-(row*divisor);

		BoardSquareInfo square = this.engineSquares[row][col];
		if(square.id == position) return square;

		return null;
	} // end getData

	/**
	 * Returns the size of the game engine board. Must call newGame() to load board first.
	 * @return
	 */
	public int getSize() {
		try {
			return this.engineSquares.length*this.engineSquares[0].length;
		} catch(Exception e) {
			return 0;
		}
	} // end getSize
	
	/**
	 * Handles the player on touch by selecting and/or moving the active square 
	 * @param square
	 */
	public boolean handleOnTouch(BoardSquareInfo square) {
		if(square == null)
			return true;
	
		if(!isDevice()) { 
			moveSquare(square);
		}
		
		return true;
	} // end handleOnTouch
	
	protected void determineWinner() {
		int player1=0, player2=0;
		for(int row=0; row<ROWS; row++) {
			for(int col=0; col<COLUMNS; col++) {
				BoardSquareInfo square = this.getData(row,col);
			
				if(square.state == PLAYER1_STATE)
					player1++;
			
				if(square.state == PLAYER2_STATE)
					player2++;
			}
		}
		
		// TODO: return the winner!
	} // end determineWinner
	
	/**
	 * Exit the game
	 */
	public void exitGame() {
	} // end exitGame
	
	public void newGame() {
		this.activeState = PLAYER1_STATE;
		this.activeSquare = null;
		this.selectionIds = new int[10];
		
		for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {
			for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				this.engineSquares[row][col].reset();
			}
		}
	} // end newGame
	
	private int[] selectionIds;

	/*
	 * Save the id of the @link BoardSquareInfo
	 *
	 */
	public boolean saveSelection(int id) {
		BoardSquareInfo square = this.getData(id);
		if(square == null || square.id != id || square.state == LOCKED_STATE) return false;

		if(selectionIndex < 0 /* then initial selection list first. */) {
		   	if(this.activeState == square.state || square.state == EMPTY_STATE) return false;
			
			selectionIndex = 0;
			selectionIds[selectionIndex++] = square.id;
			return true;
		}

		BoardSquareInfo ppSquare = this.getData(selectionIds[selectionIndex-1]);
		if(ppSquare == null) return false;

		// Never allow two or more of the same type of square
	   	if(ppSquare.state == square.state) return false;

		selectionIds[selectionIndex++] = square.id;
		return true;
	}

	public boolean verifySelectionIds() {
		if(selectionIndex < 0) return false;

		return false;
	}
	
	/**
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isEmpty(int row, int col) {
		if(row < 0 || row >= ROWS) return false;
		if(col < 0 || col >= COLUMNS) return false;

		BoardSquareInfo info = this.engineSquares[row][col];
		if (info.state == EMPTY_STATE) return true;

		return false;
	} // end isEmpty

	/**
	 * Is the square locked at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isLocked(int row, int col) {
		if(row < 0 || row >= ROWS) return false;
		if(col < 0 || col >= COLUMNS) return false;

		BoardSquareInfo info = this.engineSquares[row][col];
		if (info.state == LOCKED_STATE) return true;

		return false;
	}

	/**
	 * Is player 1 moving square
	 * @return
	 */
	public boolean isPlayer1() {
		return (activeState == PLAYER1_STATE);
	} // end isPlayer1

	/**
	 * Is player 2 moving square
	 * @return
	 */
	public boolean isPlayer2() {
		return (activeState == PLAYER2_STATE);
	} // end isPlayer2
	
	/**
	 * Returns true if the device is move square
	 * @return
	 */
	public boolean isDevice() {
		return (isPlayer2() && vsDevice);
	} // end isDevice
	
	/*
	 * Allows the other player to take turn
	 */
	public void switchPlayer() {
		if (activeSquare != null) {
			activeSquare.deactivate();
		}

		activeSquare = null;
		activeState = (activeState == PLAYER2_STATE) ? PLAYER1_STATE : PLAYER2_STATE;		
	} // end switchPlayer

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
	
	public void moveSquare(BoardSquareInfo[] squares) {
	}

	public void moveSquare(BoardSquareInfo target) {
	} // end moveSquare

	protected void moveSquareForDevice() {
	} // end moveSquareForDevice
} // end CheckersEngine
