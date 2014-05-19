package karega.scott.checkers;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
	protected static final Date date = new Date();
	
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
	protected final Context context;
	protected final boolean vsDevice;
	
	private static SparseArray<BoardSquareInfo[][]> engineSquares = 
			new SparseArray<BoardSquareInfo[][]>(2);

	protected BoardSquareInfo activeSquare;
	protected int activeState;

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
	
	protected BoardGameEngine(Context ctx, int id, boolean vsDevice) {		
		this.context = ctx;
		this.vsDevice = vsDevice;
		this.engineId = id;
		
		this.newGame();
	}
			
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
	}

	/*
	 * Allows the other player to take turn
	 */
	protected void switchPlayer() {
		Log.d(LOG_TAG, "Switching players");

		if (activeSquare != null) {
			activeSquare.deactivate();
		}

		activeSquare = null;
		activeState = (activeState == PLAYER2_STATE) ? PLAYER1_STATE : PLAYER2_STATE;

		if(vsDevice && activeState == PLAYER2_STATE) {
			// TODO: Need a delay to allow view draw to catch-up
			moveSquareForComputer();
		}
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
} // end BoardGameEngine
