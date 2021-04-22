package karega.scott.checkers;

import java.util.Random;
import java.util.Timer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Base game engine
 * @author Karega Scott
 *
 * 
 * Player 2 is top-left = {0,0}
 * Player 1 is bottom-right = {7,7}
 */
public abstract class BoardGameEngine {
	private static final String LOG_TAG = "BoardGameEngine";

	public static final String VS_DEVICE = "you vs computer";
	
	protected static final Random random = new Random();
	
	protected static final int NUM_OF_TRIES = 6;
	
	public static final int INVALIDATE_VIEW_MESSAGE_HANDLER = 1;
	
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
	
	private static Handler handler;	
	
	private Timer deviceTimer;
	
	protected BoardSquareInfo[][] engineSquares;
	protected BoardSquareInfo activeSquare;
	protected int activeState;
	
	protected BoardGameEngine(int id, boolean vsDevice) {		
		this.vsDevice = vsDevice;
		this.engineId = id;
		
		this.activeSquare = null;
		this.activeState = PLAYER1_STATE;
		
		if(this.vsDevice) {
			initializeHandler();

			DeviceTask task = new DeviceTask(this);
			
			this.deviceTimer = new Timer();
			this.deviceTimer.schedule(task, 100, 1000);
		}
	} // end constructor

	@Override
	public void finalize() {
		if(this.deviceTimer != null) {
			deviceTimer.cancel();
			deviceTimer = null;
		}
	} // end finalize
	
	/**
	 * Move the square for current player
	 * @param square
	 * @return
	 */
	public abstract void moveSquare(BoardSquareInfo square);		
	public abstract void moveSquare(BoardSquareInfo[] squares);
	/**
	 * Move the square for the device
	 */
	protected abstract void moveSquareForDevice();		

	/**
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public abstract boolean isEmpty(int row, int col);

	/**
     * Gets the square at the screen position {0..63}
     *
     * @param position
     * @return Square information or null for LOCKED_STATE
     */
    public abstract BoardSquareInfo getData(int position); 

	/**
	 * Gets the square at these coordinates.
	 * 
	 * @param row
	 * @param col
	 * @return Square information or null for LOCKED_STATE
	 */
	public abstract BoardSquareInfo getData(int row, int col);

	/**
	 * Returns the size of the game engine board. Must call newGame() to load board first.
	 * @return
	 */
	public abstract int getSize();
	
	/**
	 * Allows the device to take a turn
	 */
	public void deviceMove() {
		if(isDevice()) {
			moveSquareForDevice();
		}
	} // end deviceMoveSquare
	
	private static void initializeHandler() {
		if(handler == null) {
			handler = new Handler(new BoardGameCallback());
		}
	} // end initializeHandler
	
	/**
	 * Handler for executing messages on the main looper (Thread)
	 * @return
	 */
	private static void handleMessage(int what, Object object) {
		if(object == null)
			return;
		
		initializeHandler();
		
		Message msg = Message.obtain(handler, what, object);
		msg.sendToTarget();
	} // end handler
	
	/**
	 * Handles the square changes by calling invalidating its view.
	 * @param square
	 */
	public static void handleSquareChanged(BoardSquare square) {
		// When there is no looper, changes are on 
		// secondary thread used to active and move 
		// square for this device play
		if(Looper.myLooper() == null) {
			handleMessage(BoardGameEngine.INVALIDATE_VIEW_MESSAGE_HANDLER, square);
		} else {
			square.invalidate();
		}
	} // end handleSquareChanged
	
	/**
	 * Handles the player on touch by selecting and/or moving the active square 
	 * @param square
	 */
	public boolean handleOnTouch(BoardSquare square) {
		Log.d(LOG_TAG, "Handling on touch event");
		
		if(square == null)
			return true;
		
		if(!isDevice()) { 
			moveSquare(square.getInformation());
		}
		
		return true;
	} // end handleOnTouch
	
	protected void determineWinner() {
		Log.d(LOG_TAG, "Determine winner now");
		
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
		
		if(player1 > player2)
			Log.d(LOG_TAG, "Player 1 wins");
		else if(player2 > player1)
			Log.d(LOG_TAG, "Player 2 wins");
		else
			Log.d(LOG_TAG, "Draw");
	} // end determineWinner
	
	/*
	 * Gets the Game Engine Identifier
	 */
	public final int getId() { return this.engineId; }
	
	/**
	 * Exit the game
	 */
	public void exitGame() {
		if(deviceTimer != null) {
			deviceTimer.cancel();
		}
	} // end exitGame
	
	/*
	 * Loads a new game for play
	 */
	public void newGame() {
		activeSquare = null;
		activeState = PLAYER1_STATE;
	} // end newGame
	
	public boolean verifySelectionHead(BoardSquareInfo info) {
		if(this.isPlayer1() && info.state == PLAYER1_STATE) {
			return true;
		}

		if((this.isPlayer2() || this.isDevice()) && 
				info.state == PLAYER2_STATE) {
			return true;
		}

		return false;
	}

	public boolean verifySelectionTail(BoardSquareInfo info) {
		if(this.isPlayer1() && info.state == EMPTY_STATE) {
		   	return true;
		}

		if((this.isPlayer2() || this.isDevice()) && 
				info.state == EMPTY_STATE) {
			return true;
		}

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
		Log.d(LOG_TAG, "Switching players");

		if (activeSquare != null) {
			activeSquare.deactivate();
		}

		if(isDevice()) {
			pause(1);
		}
		
		activeSquare = null;
		activeState = (activeState == PLAYER2_STATE) ? PLAYER1_STATE : PLAYER2_STATE;		
	} // end switchPlayer

	/**
	 * Pauses the current thread
	 * @param seconds
	 */
	protected void pause(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch(Exception e) {
			Log.e(LOG_TAG, String.format("Move square for device sleep error: %s", e.getMessage()));
		}
	} // end pause
} // end BoardGameEngine
