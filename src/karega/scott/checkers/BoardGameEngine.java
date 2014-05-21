package karega.scott.checkers;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.LockSupport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

/**
 * Base game engine
 * @author Karega Scott
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
	
	private Context context;
	private Timer deviceTimer;
	
	private static SparseArray<BoardSquareInfo[][]> engineSquares = 
			new SparseArray<BoardSquareInfo[][]>(2);

	protected BoardSquareInfo activeSquare;
	protected int activeState;
	
	protected BoardGameEngine(Context ctx, int id, boolean vsDevice) {		
		this.context = ctx;
		this.vsDevice = vsDevice;
		this.engineId = id;
		
		if(this.vsDevice) {
			initializeHandler();

			DeviceTask task = new DeviceTask(this);
			
			this.deviceTimer = new Timer();
			this.deviceTimer.schedule(task, 100, 1000);
		}
		
		this.newGame();
	} // end constructor

	@Override
	public void finalize() {
		if(this.deviceTimer != null) {
			deviceTimer.cancel();
			deviceTimer = null;
		}
		
		context = null;	
	} // end finalize
	
	/**
	 * Move the square for current player
	 * @param square
	 * @return
	 */
	public abstract void moveSquare(BoardSquareInfo square);		

	/**
	 * Move the square for the device
	 */
	protected abstract void moveSquareForDevice();		

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
		
		int player1=0, player2=player1;
		for(int id=0; id<ROWS*COLUMNS; id++) {
			BoardSquareInfo square = getData(id);
			
			if(square.state == PLAYER1_STATE)
				player1++;
			
			if(square.state == PLAYER2_STATE)
				player2++;
		}
		
		if(player1 > player2)
			Log.d(LOG_TAG, "Player 1 wins");
		else if(player2 > player1)
			Log.d(LOG_TAG, "Player 2 wins");
		else
			Log.d(LOG_TAG, "Draw");
	} // end determineWinner
	
	/**
	 * Loads the current player for the saved game
	 * @return
	 */
	protected void loadCurrentState(boolean forNewGame) {
		Log.d(LOG_TAG, "Load current state for " + ((forNewGame)? "a new game": "an existing game"));
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		this.activeState = BoardGameEngine.PLAYER1_STATE;
		
		if(forNewGame)
			return;
		
		try {
			db = BoardDatabaseOpenHelper.getReadableDB(this.context);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT Player ");
			query.append("FROM SavedGame WHERE Engine = ");			
			query.append(this.getId());
			
			cursor = db.rawQuery(query.toString(), null);
			if(cursor.moveToFirst())
				this.activeState = cursor.getInt(0 /*Player*/);
			
		} catch(SQLiteException e) {
			
		} finally {
			if(cursor != null)
				cursor.close();
			cursor = null;
			
			if(db != null) 
				db.close();
			db = null;
		} // end try-catch-finally		
	} // end loadCurrentPlayer
	
	/**
	 * Loads the game data from database
	 */
	protected void loadSquares(boolean forNewGame){
		Log.d(LOG_TAG, "Load squares for " + ((forNewGame)? "a new game": "an existing game"));
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		BoardSquareInfo[][] squares = engineSquares.get(this.engineId);
		if(squares != null) {
			for (int i = 0; i < getSize(); i++) {
				getData(i).reset();
			} // end for
			
			return;
		}
		
		squares = new BoardSquareInfo[BoardGameEngine.ROWS][BoardGameEngine.COLUMNS];
		
		try {
			
			db = BoardDatabaseOpenHelper.getReadableDB(this.context);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT Id, Row, Column, State, Chip ");
			
			if(forNewGame)
				query.append("FROM GameEngineState WHERE Engine = ");
			else
				query.append("FROM SavedGameState WHERE Engine = ");
			
			query.append(this.getId());
			query.append(" ORDER BY Id");
			
			cursor = db.rawQuery(query.toString(), null);
			while(cursor.moveToNext()) {
				BoardSquareInfo square = new BoardSquareInfo(
						cursor.getInt(0 /*Id*/),
						cursor.getInt(1 /*Row*/),
						cursor.getInt(2 /*Column*/),
						cursor.getInt(3 /*State*/),
						cursor.getInt(4 /*Chip*/));
				
				squares[square.row][square.column] = square;				
			}
			
			engineSquares.put(engineId, squares);
			
		} catch(SQLiteException e) {
			Log.v("BoardGameEngine.loadSquares", e.getMessage());
		} finally {
			if(cursor != null)
				cursor.close();
			cursor = null;
			
			if(db != null) 
				db.close();
			db = null;
		} // end try-catch-finally
	} // end loadSquares
	
	/**
	 * Creates the specific game engine for play
	 * @param engine Integer value
	 * @return @link BoardGameEngine engine for play
	 */
	public static BoardGameEngine instance(Context context, int id, boolean vsDevice) {
		BoardGameEngine engine = null;
		
		switch(id) {
			case BoardGameEngine.CHECKERS_ENGINE:
				Log.d(LOG_TAG, "Creating an instance for checkers engine");
				engine = new CheckersEngine(context, vsDevice);
				break;
				
			case BoardGameEngine.CHESS_ENGINE:
			default:
				Log.d(LOG_TAG, "This instance not support");
				engine = null;
		}
		
		return engine;
	} // end instance

	/*
	 * Gets the Game Engine Identifier
	 */
	public final int getId() { return this.engineId; }
	
	/*
	 * Loads the previous saved game
	 */
	public void loadGame() {
		Log.d(LOG_TAG, "Loading an existing game");

		activeSquare = null;
		loadCurrentState(/* for new game */false);
		loadSquares(/* for new game */false);
	} // end loadGame

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
		Log.d(LOG_TAG, "Loading a new game");

		activeSquare = null;
		loadCurrentState(/* for new game */true);
		loadSquares(/* for new game */true);
	} // end newGame

	/*
	 * Saves the current state of the game to database
	 */
	public void saveGame() {
		Log.d(LOG_TAG, "Saving game");

		throw new Error("Save Game Not implemented");
	} // end saveGame

	/**
	 * Is player 1 moving square
	 * @return
	 */
	public boolean isPlayer1() {
		return (activeState == PLAYER1_STATE);
	} // end ActiveState
	
	/**
	 * Is player 2 moving square
	 * @return
	 */
	private boolean isPlayer2() {
		return (activeState == PLAYER2_STATE);
	} // end ActiveState
	
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
	protected void switchPlayer() {
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
	 * Is the square empty at this coordinates on the board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	protected boolean isEmpty(int row, int col) {
		try {
			BoardSquareInfo[][] squares = engineSquares.get(this.engineId);
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
	protected BoardSquareInfo getData(int row, int col) {
		Log.d(LOG_TAG, String.format("Get data row[%s] col[%s]", row, col));

		try {
			BoardSquareInfo[][] squares = engineSquares.get(this.engineId);
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

	/**
	 * Gets the data for the identifier
	 * @param id a numeric identifier for game board square
	 * @return @link BoardSquareInfo represented by the id
	 */
	protected BoardSquareInfo getData(int id) {
		Log.v(LOG_TAG, String.format("Get data for id[%s]", id));
		try {
			int row = id / 8;
			int col = id % 8;

			BoardSquareInfo[][] squares = engineSquares.get(this.engineId);
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

	/**
	 * Returns the number of key/value pairs in the board game @link java.util.hashtable
	 * @return
	 */
	protected int getSize() {
		return ROWS * COLUMNS;
	} // end getSize
	
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
