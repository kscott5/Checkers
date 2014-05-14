package karega.scott.checkers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper for creating/opening a database
 * @author admin
 *
 */
public class BoardDatabaseOpenHelper  extends SQLiteOpenHelper {
	private static final String LOG_TAG = "BoardDatabaseOpenHelper";
	private static final String DATABASE_NAME = "boardgame.db";
	private static final int DATABASE_VERSION = 1;
	
	private BoardDatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static SQLiteDatabase getReadableDB(Context ctx) {
		BoardDatabaseOpenHelper helper = new BoardDatabaseOpenHelper(ctx);
		return helper.getReadableDatabase();
	}
	
	public static SQLiteDatabase getWritableDB(Context ctx) {
		BoardDatabaseOpenHelper helper = new BoardDatabaseOpenHelper(ctx);
		return helper.getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			createGameEngineTable(db);
			createGameEngineStateTable(db);
			createSavedGameTable(db);
			createSavedGameStateTable(db);
			createSeedData(db);
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	} 

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.beginTransaction();
		try {
			
			// NOTHING TO DO...
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * Creates the game engine table. This table used to store the types of game engines
	 * @param db
	 */
	private static void createGameEngineTable(SQLiteDatabase db) {
		Log.d(LOG_TAG, "Creating game engine table");
		
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE GameEngine(");
		sql.append("Id smallint PRIMARY KEY,");
		sql.append("Name nvarchar(30)");
		sql.append(");");
		
		db.execSQL(sql.toString());
	}

	/**
	 * Creates the game engine state table. This table used to store the initial state of game engine
	 * @param db
	 */
	private static void createGameEngineStateTable(SQLiteDatabase db) {
		Log.d(LOG_TAG, "Creating game engine state table");
		StringBuilder sql = new StringBuilder();
	
		sql.append("CREATE TABLE GameEngineState (");
		sql.append("Engine smallint,");
		sql.append("Id smallinit,");
		sql.append("Row smallint,");
		sql.append("Column smallint,");
		sql.append("Chip smallint,");
		sql.append("State smallint,");
		sql.append("PRIMARY KEY (Engine, Id, Row, Column),");
		sql.append("FOREIGN KEY (Engine) REFERENCES GameEngine(Id)");
		sql.append(");");
	
		db.execSQL(sql.toString());
	}
	
	/**
	 * Creates the saved game table. This table used to determine which player is goes first for specific game engine
	 * @param db
	 */
	private static void createSavedGameTable(SQLiteDatabase db) {
		Log.d(LOG_TAG, "Creating saved game table");
		
		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE SavedGame (");
		sql.append("Engine smallint,");
		sql.append("Player smallint,");
		sql.append("PRIMARY KEY (Engine, Player),");
		sql.append("FOREIGN KEY (Engine) REFERENCES GameEngine(Id)");
		sql.append(");");
		
		db.execSQL(sql.toString());
	} 
	
	/**
	 * Creates the saved game state table. This table used to store the stated for a specific game engine
	 * @param db
	 */
	private static void createSavedGameStateTable(SQLiteDatabase db) {
		Log.d(LOG_TAG, "Creating saved game state table");
		StringBuilder sql = new StringBuilder();
		
		sql.append("CREATE TABLE SavedGameState (");
		sql.append("Engine smallint,");
		sql.append("Id smallint,");
		sql.append("Row smallint,");
		sql.append("Column smallint,");
		sql.append("Chip smallint,  State smallint,");
		sql.append("PRIMARY KEY (Engine, Id, Row, Column),");
		sql.append("FOREIGN KEY (Engine) REFERENCES GameEngine(Id)");
		sql.append(");");
		
		db.execSQL(sql.toString());
	}		
	
	/**
	 * Creates the initial data for the app
	 * @param db
	 */
	private static void createSeedData(SQLiteDatabase db) {
		Log.d(LOG_TAG, "Inserting seed data");
				
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO GameEngine (Id, Name) VALUES (1001, 'Checkers');\n");
		
		// Checkers Engine Row 1
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,0,0,0,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,1,0,1,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,2,0,2,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,3,0,3,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,4,0,4,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,5,0,5,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,6,0,6,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,7,0,7,2002,3002);\n");
		
		// Checkers Engine Row 2
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,8,1,0,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,9,1,1,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,10,1,2,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,11,1,3,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,12,1,4,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,13,1,5,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,14,1,6,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,15,1,7,2004,3001);\n");
		
		// Checkers Engine Row 3
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,16,2,0,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,17,2,1,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,18,2,2,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,19,2,3,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,20,2,4,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,21,2,5,2002,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,22,2,6,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,23,2,7,2002,3002);\n");
		
		// Checkers Engine Row 4
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,24,3,0,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,25,3,1,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,26,3,2,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,27,3,3,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,28,3,4,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,29,3,5,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,30,3,6,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,31,3,7,2004,3001);\n");
	
		// Checkers Engine Row 5 
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,32,4,0,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,33,4,1,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,34,4,2,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,35,4,3,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,36,4,4,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,37,4,5,2003,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,38,4,6,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,39,4,7,2003,3001);\n");
		
		// Checkers Engine Row 6  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,40,5,0,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,41,5,1,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,42,5,2,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,43,5,3,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,44,5,4,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,45,5,5,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,46,5,6,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,47,5,7,2004,3001);\n");
		
		// Checkers Engine Row 7  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,48,6,0,2004,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,49,6,1,2001,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,50,6,2,2004,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,51,6,3,2001,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,52,6,4,2004,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,53,6,5,2001,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,54,6,6,2004,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,55,6,7,2001,3001);\n");
		
		// Checkers Engine Row 8  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,56,7,0,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,57,7,1,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,58,7,2,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,59,7,3,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,60,7,4,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,61,7,5,2004,3001);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,62,7,6,2001,3002);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1001,63,7,7,2004,3001);\n");
		
		String[] queries = sql.toString().split("\n");
		for(int i=0; i<queries.length; i++) {
			db.execSQL(queries[i]);
		}
	}
} // end BoardGameSQLHelper
