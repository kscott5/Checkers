package karega.scott.checkers;

import java.util.Random;
import java.util.Timer;
import java.lang.Math;

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
	// Value in use with android.os.Handler, android.os.Message
	public static final int INVALIDATE_VIEW_MESSAGE_HANDLER = 1;
	
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
		
	protected boolean vsDevice;

	public static final int LOG_TYPE_DEBUG = 0;
	public static final int LOG_TYPE_ERROR = 1;
	public static final int LOG_TYPE_INFO = 2;
	public static final int LOG_TYPE_VERBOSE = 4;

	public Logger log;

	protected BoardSquareInfo[][] engineSquares;
	protected int activePlayerState;
	protected boolean activePlayerIsKing;

	private final int CHECKERS_ENGINE_ROWS = 8;
	private final int CHECKERS_ENGINE_COLUMNS = 8;

	public CheckersEngine() {
		this.vsDevice = true;
		this.activePlayerState = PLAYER1_STATE;
		this.activePlayerIsKing = false;

		this.selectionIndex = -1;
		this.deviceSelectionIndex = -1;
		this.initialBoardSquares();

		this.log = new Logger() {
			public void it(int type, String tag, String message){
			}
		};
	}

	public CheckersEngine(boolean vsDevice) {
		this();
		this.vsDevice = vsDevice;
	}

	public CheckersEngine(Logger log) {
		this();
		this.log = log;
	}
	
	public CheckersEngine(boolean vsDevice, Logger log) {			
		this(vsDevice);
		this.log = log;
	}
	
	/**
	 * Creates the checkers board layout with LOCKED_STATE, EMPTY_STATE, PLAYER1_STATE and PLAYER2_STATE squares.
	 */
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

		// Defines relationship between empty and player squares only, not locked squares.
		for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {
			for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				BoardSquareInfo parent = this.engineSquares[row][col];

				parent.backwardSiblings = this.generateSquareSiblings(parent.id, /*forward*/ false);
				parent.forwardSiblings = this.generateSquareSiblings(parent.id, /*forward*/ true);
			}
		}
	
	} // end initialBoardSquares

	/**
	 * Changes or converts all PLAYER1_STATE and PLAYER2_STATE squares are EMPTY_STATE.
	 * This helps with verification of checkers board rules.
	 */
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

	/**
	 * Creates a unique and prepared square identifier or number.
	 * 
	 * @param row of square location on game board.
	 * @param col column of square location on game board.
	 *
	 * @return the square numeric identifier
	 */
	public int generateSquareId(int row, int col) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return -1;
		if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return -1;

		final int multiplier = 8;
		return ((multiplier*row)+col);
	}

	/**
	 * Creates or sets the square start skin color or type.
	 * 
	 * @param row of square location on game board.
	 * @param col column of square location on game board.
	 *
	 * @return a value of LOCKED_STATE, PLAYER2_STATE, PLAYER1_STATE or EMPTY_STATE
	 */
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

	/**
	 * Creates or sets the relationships between a square possible siblings.
	 * 
	 * @param id of the square that needs possible sibling squares.
	 * @param forward direction of possible sibling square locations
	 *
	 * @return possible sibling board squares
	 */
	public BoardSquareSiblings generateSquareSiblings(int id, boolean forward) {
		BoardSquareInfo parent = this.getData(id);
		if(parent.id != id) return new BoardSquareSiblings(-1,-1);

		final int multiplier = (forward)? +1:-1;
		
		int[] siblingIds = new int[2];
		siblingIds[0] = 7; siblingIds[1] = 9;

		for(int index=0; index<2; index++) {
			BoardSquareInfo sibling = this.getData( parent.id + (multiplier * siblingIds[index]) );

			// Update or change the array value with correct information
			siblingIds[index] = (sibling == null || sibling.row == parent.row || sibling.row == parent.row+2)? -1 : sibling.id;
		}

		return new BoardSquareSiblings(/*left*/ siblingIds[0], /*right*/ siblingIds[1]);
	}

	/**
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row of square location on game board.
	 * @param col column of square location on game board.
	 *
	 * @return true if empty else false.
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
	 * @param row of square location on game board.
	 * @param col column of square location on game board.
	 *
	 * @return true if a lock is on this square else false.
	 */
	public boolean isLocked(int row, int col) {
		if(row < 0 || row >= ROWS) return false;
		if(col < 0 || col >= COLUMNS) return false;

		BoardSquareInfo info = this.engineSquares[row][col];
		if (info.state == LOCKED_STATE) return true;

		return false;
	}

	/**
	 * Is active player 1 the holder of this device?
 	 * @returns true if active player1 else false.
	 */
	public boolean isPlayer1() {
		return (this.activePlayerState == PLAYER1_STATE);
	} // end isPlayer1

	/**
	 * Is active player 2 the holder or remote device?
 	 * @returns true if active player2  else false.
	 */
	public boolean isPlayer2() {
		return (this.activePlayerState == PLAYER2_STATE);
	} // end isPlayer2
	
	/**
	 * Is active player this device?
	 * @returns true if active player is this device, else false.
	 */
	public boolean isDevice() {
		return (this.isPlayer2() && vsDevice);
	} // end isDevice
	
	/**
	 * Allows the other player to take turn
	 */
	public void switchPlayer() {
		this.selectionIndex = -1;
		this.selectionIds = new int[10];

		this.deviceSelectionIndex = -1;
		this.deviceSelectionIds = new int[10];

		this.activePlayerState = (this.activePlayerState == PLAYER2_STATE) ? PLAYER1_STATE : PLAYER2_STATE;		
		this.activePlayerIsKing = false;
	} // end switchPlayer

	/**
	 * Gets the square with this data
	 * @param x axis float point value
	 * @param y axis float point value
	 * @param width system generated value of @link BoardSquareInfo
	 *
	 * @return square information or null if not found.
	 */
	public BoardSquareInfo getData(float x, float y, float width) {
		int row = (int)Math.floor(x/width);
		int col = (int)Math.floor(y/width);

		return this.getData(row,col);
	}

	/**
	 * Gets the square at these coordinates.
	 * 
	 * @param row of square location on game board.
	 * @param col column of square location on game board.
	 *
	 * @return Square information or null if not found.
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
	 * The size of the game engine board. Must call newGame() to load board first.
	 *
	 * @return the total size of game engine board.
	 */
	public int getSize() {
		return this.engineSquares.length*this.engineSquares[0].length;
	} // end getSize

	/**
	 * Updates or changes the engine device play mode.
	 *
	 * @param vsDevice flag
	 */
	public void setDevicePlay(boolean vsDevice) {
		this.vsDevice = vsDevice;
	}

	/**
	 * Updates the game board with active player selection and switch player.
	 *
	 * @return the success flag from changes made on the game board engine.
	 */
	public boolean updateGameBoard(int id, boolean hasMore) {
		this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "Update game board. Square.id-> " + /*append*/ id + /*append*/ " has more: " + /*append*/ hasMore/* to this string.*/) ;

		if(!this.saveSelection(id) /*was bad*/) return false;
		if(hasMore /*save selections*/) return true;

		return this.updateGameBoard();
	}

	/**
	 * Updates the game board with active player selection and switch player.
	 *
	 * @return the success flag from changes made on the game board engine.
	 */
	public boolean updateGameBoard() {
		// NOTE: 
		//
		// An built generic list is not in use with this application. This
		// application use simple data type and structure where possible. However,
		// the application must maintain the correct values where simple arrays of
		// built-in data types are in use. int, char, float, double and more.
		//
		// selectionIndex is zero base
		if(this.selectionIndex <=1) {
			this.selectionIndex = -1;
			return false;
		}

		// Update the engine squares with selection id list
		for(int index=0; index<selectionIndex; index++) {
			this.updateSquareState(selectionIds[index], EMPTY_STATE);
		}

		// The last item in selection ids with active player chip
		// selectionIndex is zero base
		this.updateSquareState(selectionIds[selectionIndex-1], this.activePlayerState);
		this.switchPlayer();

		return true;
	} // end updateGameBoard
	
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
	
	/**
	 * Begins or starts a new game of play
	 */
	public void newGame() {
		this.activePlayerState = PLAYER1_STATE;
		this.activePlayerIsKing = false;
		
		this.selectionIds = new int[10];
		this.selectionIndex = -1;

		this.deviceSelectionIds = new int[10];
		this.deviceSelectionIndex = -1;

		for(int row=0; row<CHECKERS_ENGINE_ROWS; row++) {
			for(int col=0; col<CHECKERS_ENGINE_COLUMNS; col++) {
				this.engineSquares[row][col].reset();
			}
		}
	} // end newGame
	
	private int[] selectionIds;
	private int selectionIndex;

	/**
	 * Save the id of the active player selection path on the game engine board.
	 *
	 * @param id used that identifies the square on the game engine board.
	 * @return the success flag from changes made on the game board engine.
	 */
	public boolean saveSelection(int id) {
		this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "save selection id->" + id);

		BoardSquareInfo square = this.getData(id);
		if(square == null || square.id != id || square.state == LOCKED_STATE) return false;

		if(selectionIndex <= 0 /* then initialize selection list first. */) {
		   	if(this.activePlayerState != square.state || square.state == EMPTY_STATE) return false;
			
			this.activePlayerIsKing = square.isKing;

			if(!this.isDevice()/*its not*/)
				square.activate();

			// Clear the previous first selection preview
			if(this.selectionIndex == 0) { 
				BoardSquareInfo preview = this.getData(this.selectionIds[this.selectionIndex]);
				preview.deactivate();
			}

			this.selectionIndex = 0;
			this.selectionIds = new int[10];
			this.selectionIds[this.selectionIndex++] = square.id;

			this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "save selection firs id->"+id);
			return true; // selection square id saved
		}

		// Previous select square
		BoardSquareInfo psSquare = this.getData(this.selectionIds[this.selectionIndex-1]);
		if(psSquare == null) { /*then*/ this.saveSelectionReset(); /*and*/ return false; }

		// Never allow active player capture own board item
		if(square.state == this.activePlayerState) { /*then*/ this.saveSelectionReset(); /*and*/ return false; }

		// Never allow two or more of the same type of square
	   	if(psSquare.state == square.state) { /*then*/ this.saveSelectionReset(); /*and*/ return false; }

		// Never allow empty space then opposite player capture item
		if(psSquare.state == EMPTY_STATE && square.state != this.activePlayerState) { /*then*/ this.saveSelectionReset(); /*and*/ return false; }

		// Never allow capture item in different heading
		if(this.selectionDirectionWrong(id)) { /*then*/ this.saveSelectionReset(); /*and*/ return false; }

		// save the selection square id only
		this.selectionIds[this.selectionIndex++] = square.id;

		if(!this.isDevice()/*its not*/)
			square.activate();

		this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "save selection " + this.selectionIndex + " id->"+id);
		return true; // selection square id saved.
	}

	/**
	 * Clears or resets the active players selection path
	 */
	public void saveSelectionReset() {
		this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "save selection reset. Path with " + this.selectionIndex + " ids gone.");
		for(int index=0; index<this.selectionIndex; index++) {
			BoardSquareInfo square = this.getData(this.selectionIds[index]);
			square.deactivate();
		}

		this.selectionIndex = -1;
		this.selectionIds = new int[10];
	}

	/**
	 * Determines if the active player selection square is part of a good path.
	 *
	 * @param id a square unique engine board identifier or number.
	 * @return the success flag of path.
	 */
	public boolean selectionDirectionWrong(int id) {
		BoardSquareInfo square = this.getData(id);
		if(square == null || square.id != id || square.state == LOCKED_STATE) {
		   this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "selection direction id->"+ id + " wrong.");
		   return true;
		}

		// Previous select square
		BoardSquareInfo psSquare = this.getData(selectionIds[selectionIndex-1]);

		// Determine the valid available
		if(this.activePlayerState == PLAYER1_STATE || this.activePlayerIsKing) { 
			if(psSquare.backwardSiblings.leftId == square.id || 
					psSquare.backwardSiblings.rightId == square.id) return false; // Not wrong
		}

		// Determine the valid available
		if(this.activePlayerState == PLAYER2_STATE || this.activePlayerIsKing) { 
			if(psSquare.forwardSiblings.leftId == square.id || 
					psSquare.forwardSiblings.rightId == square.id) return false; // Not wrong.
		}
	   	
		this.log.it(LOG_TYPE_DEBUG, LOG_TAG, "selection direction id->"+ id + " wrong.");
		return true; // Wrong.
	}

	/**
	 * Gives the square a different visible skin color and type.
	 *
	 * @param id square unique badge identifier.
	 * @param newState color or skin type seen on the board.
	 *
	 * @return success of square updates or chagnes.
	 */
	public boolean updateSquareState(int id, int newState) {
		BoardSquareInfo square = this.getData(id);
		if(square == null || /*or*/ square.state == LOCKED_STATE) return false;

		return this.updateSquareState(square.row,square.column,newState);
	}

	/**
	 * Gives the square a different visible skin color and type.
	 *
	 * @param row identifier of square on this engine board.
	 * @param col column identifier of square on this engine board.
	 * @param newState color or skin type seen on the board.
	 *
	 * @return success of square updates or changes.
	 */
	public boolean updateSquareState(int row, int col, int newState) {
		if(row < 0 || row >= CHECKERS_ENGINE_ROWS) return false;
		if(col < 0 || col >= CHECKERS_ENGINE_COLUMNS) return false;

		BoardSquareInfo square = this.getData(row,col);
		switch(square.state) { // verify the value of state is
			case PLAYER1_STATE:  // good integer
			case PLAYER2_STATE:  // good integer
			case EMPTY_STATE:    // good integer
				break; // and continue next line with new state value

			case LOCKED_STATE:
			default:
				this.log.it(LOG_TYPE_ERROR, LOG_TAG, "square.id->" + square.id + " state[" + square.state + "] not valid with updateSquareState.");
				return false;
		}

		// and continue next line with new state value
		square.chip = (newState == EMPTY_STATE)? EMPTY_CHIP: PAWN_CHIP;
		square.state = newState;

		square.isKing = (/*if*/ this.activePlayerState == newState /*and*/ && /* pawn found the */ (/*top*/ square.row == 0 /*or*/ || /*bottom*/ square.row == 7));
		square.isKing = (square.isKing == false && this.activePlayerState == newState && this.activePlayerIsKing == true);
		square.deactivate();

		return true;
	}

	private int[] deviceSelectionIds;
	private int deviceSelectionIndex;
   	
	/**
	 * Gets the path size of the active player selections.
	 *
	 * @returns the total number of squares found on selection path.
	 */
	public int getSelectionSize() {
		return this.selectionIndex; // selection index will always hold the best path of this.activePlayerState
	}

	/**
	 * Locate best possible device movable path on the board. This
	 * function uses 'recursion', a process where the method calls
	 * the same method within.
	 *
	 * @return success of best possible device selection path.
	 */
	public boolean locateDeviceBestPossiblePath(BoardSquareInfo start, boolean forward) {
		// Keep it simple today, and look at forward sibling squares only.

		BoardSquareInfo lSquare = (forward)? 
			this.getData(start.forwardSiblings.leftId) : /*forward is true*/
			this.getData(start.backwardSiblings.leftId); /*forward is false*/

		if(this.saveSelection(start.id) && this.locateDeviceBestPossiblePath(lSquare,forward)) {
			return true;
		}

		BoardSquareInfo rSquare = (forward)? 
			this.getData(start.forwardSiblings.rightId) : /*forward is true*/
			this.getData(start.backwardSiblings.rightId); /*forward is false*/

		if(this.saveSelection(start.id) && this.locateDeviceBestPossiblePath(rSquare,forward)) {
			return true;
		}

		return false;
	} // end locate best possible path

	public void deviceMove() {
		if(!this.isDevice()/*false*/) return;

		this.locateDeviceMovableSquareIds();
		this.updateGameBoard();
	}

	/**
	 * Locate and create a list of squares the device
	 * should use with initial play.
	 *
	 * @return success of finding a device selection path.
	 */
	public boolean locateDeviceMovableSquareIds() {
		if(/*not*/ !this.vsDevice) return false;

		this.deviceSelectionIds = new int[10];
		this.deviceSelectionIndex = -1;

		// Start loop backwards from square 0 top-left side of board.
		for(int id=0; id<this.getSize()-1; id++) {
			BoardSquareInfo square = this.getData(id);

			if(square.state != /*not*/ this.activePlayerState) continue; // for loop at id++. (id=id+1)
	
			this.selectionIndex = -1; // prepare for save selection
			this.selectionIds = new int[10];
			boolean forward = true;
			
			do { // First locate best possible path where forward is true.
				if(this.locateDeviceBestPossiblePath(square,  forward)) {
					if(this.selectionIndex >= this.deviceSelectionIndex) {
						// save the selection index size
						this.deviceSelectionIndex = this.selectionIndex;

						// save the selection ids to device selections
						this.deviceSelectionIds = 
							java.util.Arrays.copyOf(this.selectionIds, this.selectionIndex);
					}
				} // end if locate best possible path

				forward = !forward; // toggle forward between true and false
			} /*do*/ while /*loop*/(/*if*/ square.isKing /*true*/ && /*and*/ !forward /* is false now. Loop once again backwards.*/);
		} // end for id++

		return (this.deviceSelectionIndex > 0); // true else false.
	} // end locationDeviceMovableSquareIds
} // end CheckersEngine