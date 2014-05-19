package karega.scott.checkers;


import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Base game engine
 * @author Karega Scott
 *
 * Player 2 is top-left = {0,0}
 * Player 1 is bottom-right = {7,7}
 */
public abstract class BoardGameEngine {
	private static final String LOG_TAG = "BoardGameEngine";

	public static final String VS_COMPUTER = "you vs computer";
	
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

	public static final int CHECKERS_ENGINE = 1001;
	public static final int CHESS_ENGINE = 1002;
	
	public static final int PLAYER1 = 1101;
	public static final int PLAYER2 = 1102;
	
	public static final int PLAYER1_STATE = 2001;
	public static final int PLAYER2_STATE = 2002;
	public static final int EMPTY_STATE = 2003;
	public static final int LOCKED_STATE = 2004;

	public static final int EMPTY_CHIP = 3001;
	public static final int PAWN_CHIP = 3002;
		
	protected final int id;
	protected final Context context;
	protected final boolean vsComputer;
	
	protected BoardGameEngine(Context ctx, int id, boolean vsComputer) {
		this.context = ctx;
		this.vsComputer = vsComputer;
		this.id = id;
		this.newGame();
	}
			
	/**
	 * Loads the current player for the saved game
	 * @return
	 */
	protected int loadCurrentState(boolean forNewGame) {
		Log.d(LOG_TAG, "Load current state for " + ((forNewGame)? "a new game": "an existing game"));
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		int player = BoardGameEngine.PLAYER1_STATE;
		
		if(forNewGame)
			return player;
		
		try {
			db = BoardDatabaseOpenHelper.getReadableDB(this.context);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT Player ");
			query.append("FROM SavedGame WHERE Engine = ");			
			query.append(this.getId());
			
			cursor = db.rawQuery(query.toString(), null);
			if(cursor.moveToFirst())
				player = cursor.getInt(0 /*Player*/);
			
		} catch(SQLiteException e) {
			
		} finally {
			if(cursor != null)
				cursor.close();
			cursor = null;
			
			if(db != null) 
				db.close();
			db = null;
		} // end try-catch-finally
		
		return player;
	} // end loadCurrentPlayer
	
	/**
	 * Loads the game data from database
	 */
	protected BoardSquareInfo[][] loadSquares(boolean forNewGame){
		Log.d(LOG_TAG, "Load squares for " + ((forNewGame)? "a new game": "an existing game"));
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		
		BoardSquareInfo[][] squares = new BoardSquareInfo[BoardGameEngine.ROWS][BoardGameEngine.COLUMNS];
		
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
			
		} catch(SQLiteException e) {
			Log.v("BoardGameEngine.loadSquares", e.getMessage());
		} finally {
			if(cursor != null)
				cursor.close();
			cursor = null;
			
			if(db != null) 
				db.close();
			db = null;
		}	
		
		return squares;
	}
	
	/**
	 * Creates the specific game engine for play
	 * @param engine Integer value
	 * @return @link BoardGameEngine engine for play
	 */
	public static BoardGameEngine instance(Context context, int id, boolean vsComputer) {
		BoardGameEngine engine = null;
		
		switch(id) {
			case BoardGameEngine.CHECKERS_ENGINE:
				Log.d(LOG_TAG, "Creating an instance for checkers engine");
				engine = new CheckersEngine(context, vsComputer);
				break;
				
			case BoardGameEngine.CHESS_ENGINE:
			default:
				Log.d(LOG_TAG, "This instance not support");
				engine = null;
		}
		
		return engine;
	} // end instance
	
	public final int getId() { return this.id; }
	
	/**
	 * Move the square for current player
	 * @param square
	 * @return
	 */
	public abstract void moveSquare(BoardSquareInfo square);		

	/**
	 * Move the square for the computer
	 */
	protected abstract void moveSquareForComputer();
	
	/**
	 * Loads a previously saved game
	 */
	public abstract void loadGame();

	/**
	 * Starts a new game for play
	 */
	public abstract void newGame();

	/**
	 * Save the current game
	 */
	public abstract void saveGame();

	/**
	 * Switch the current state (player) for the game engine
	 */
	public abstract void switchPlayer();
	
	/**
	 * Gets the data for the identifier
	 * @param id a numeric identifier for game board square
	 * @return @link BoardSquareInfo represented by the id
	 */
	public abstract BoardSquareInfo getData(int id);
	
	/**
	 * Returns the number of key/value pairs in the board game @link java.util.hashtable
	 * @return
	 */
	public abstract int getSize();	
} // end BoardGameEngine
