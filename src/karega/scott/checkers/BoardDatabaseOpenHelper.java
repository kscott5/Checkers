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
		sql.append("INSERT INTO GameEngine (Id, Name) VALUES (1, 'Checkers');\n");
		
		// Checkers Engine Row 1
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,0,0,0,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,1,0,1,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,2,0,2,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,3,0,3,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,4,0,4,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,5,0,5,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,6,0,6,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,7,0,7,2,2);\n");
		
		// Checkers Engine Row 2
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,8,1,0,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,9,1,1,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,10,1,2,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,11,1,3,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,12,1,4,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,13,1,5,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,14,1,6,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,15,1,7,4,1);\n");
		
		// Checkers Engine Row 3
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,16,2,0,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,17,2,1,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,18,2,2,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,19,2,3,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,20,2,4,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,21,2,5,2,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,22,2,6,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,23,2,7,2,2);\n");
		
		// Checkers Engine Row 4
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,24,3,0,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,25,3,1,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,26,3,2,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,27,3,3,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,28,3,4,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,29,3,5,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,30,3,6,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,31,3,7,4,1);\n");
	
		// Checkers Engine Row 5 
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,32,4,0,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,33,4,1,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,34,4,2,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,35,4,3,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,36,4,4,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,37,4,5,3,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,38,4,6,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,39,4,7,3,1);\n");
		
		// Checkers Engine Row 6  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,40,5,0,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,41,5,1,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,42,5,2,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,43,5,3,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,44,5,4,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,45,5,5,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,46,5,6,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,47,5,7,4,1);\n");
		
		// Checkers Engine Row 7  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,48,6,0,4,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,49,6,1,1,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,50,6,2,4,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,51,6,3,1,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,52,6,4,4,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,53,6,5,1,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,54,6,6,4,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,55,6,7,1,1);\n");
		
		// Checkers Engine Row 8  
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,56,7,0,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,57,7,1,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,58,7,2,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,59,7,3,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,60,7,4,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,61,7,5,4,1);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,62,7,6,1,2);\n");
		sql.append("INSERT INTO GameEngineState (Engine,Id,Row,Column,State,Chip) VALUES (1,63,7,7,4,1);\n");
		
		String[] queries = sql.toString().split("\n");
		for(int i=0; i<queries.length; i++) {
			db.execSQL(queries[i]);
		}
	}
} // end BoardGameSQLHelper
