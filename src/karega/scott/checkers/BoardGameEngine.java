package karega.scott.checkers;
import java.util.Hashtable;

import karega.scott.checkers.BoardSquareStateType;
import android.graphics.Point;
import android.util.Log;

/**
 * Base game engine
 * @author Karega Scott
 *
 */
public abstract class BoardGameEngine {
	protected static final int ROWS = 8;
	protected static final int COLUMNS = 8;
	
	private BoardSquareStateType currentPlayer;
	private BoardSquareInfo activeSquare;
	private BoardGameEngineType engineType;
	
	private Hashtable<Point, BoardSquareInfo> squares;
	
	/**
	 * Creates the specific game engine for play
	 * @param type @BoardGameEngineType
	 * @return @link BoardGameEngine engine for play
	 */
	public static BoardGameEngine instance(BoardGameEngineType type) {
		BoardGameEngine engine = null;
		
		switch(type) {
			case CHECKERS:
				engine = new CheckersEngine();
				break;
			case CHESS:
				break;
		}
		
		return engine;
	} // end instance
	
	/**
	 * Constructor for {@link BoardGameEngine}
	 */
	protected BoardGameEngine(BoardGameEngineType engineType) {
		this.currentPlayer = BoardSquareStateType.PLAYER1;
		this.engineType = engineType;
		this.activeSquare = null;		
		this.squares = initializeBoardGameData();
	}
	
	/**
	 *  Create the data used on the checker board
	 * @return @link java.util.hashtable representing the data used for game play  
	 */
	protected abstract Hashtable<Point, BoardSquareInfo> initializeBoardGameData();

	/**
	 * Initializes the player's square for game play
	 * @param square the {@link BoardSquareInfo} to initialize for game play
	 * @return True when initialize else False 
	 */
	public abstract boolean setPlayerSquare(BoardSquare square);
	
	/**
	 * 
	 * @param square
	 * @return
	 */
	public abstract boolean movePlayer(BoardSquare square);		

	/**
	 * Gets the current game engine used for play
	 * @return @link BoardGameEngineType
	 */
	public final BoardGameEngineType getType() { return this.engineType; }
	
	/**
	 * Starts a new game for play
	 */
	public final void newGame() {
		this.currentPlayer = BoardSquareStateType.PLAYER1;
		this.activeSquare = null;		

		for(BoardSquareInfo square : this.squares.values()) {
			square.reset();
		} // end for
	} //end newGame
	
	/**
	 * Gets the current player for the game engine
	 * @return @BoardSquareStateType
	 */
	public final BoardSquareStateType getCurrentPlayer() { return this.currentPlayer; }
	
	/**
	 * Switch the current player for the game engine
	 */
	public final void switchCurrentPlayer() {
		this.currentPlayer = (this.currentPlayer == BoardSquareStateType.PLAYER1)?
				BoardSquareStateType.PLAYER2: BoardSquareStateType.PLAYER1;
		
		this.activeSquare = null;
	} //end switchCurrentPlayer
	
	/**
	 * Gets the data for the identifier
	 * @param id a numeric identifier for game board square
	 * @return @link BoardSquareInfo represented by the id
	 */
	public final BoardSquareInfo getData(int id) {
		for(BoardSquareInfo square : this.squares.values()) {
			if(square.getId() == id)
				return square;
		}
		return null;
	} // end getData
	
	/**
	 * Gets the data for the key
	 * @param key Identifier 
	 * @return The @link BoardSquareInfo if key found else null
	 */
	public final BoardSquareInfo getData(Point key) {
		if(!this.containsKey(key)) return null;
		
		return this.squares.get(key);
	} // end getData
	
	/**
	 * Returns the number of key/value pairs in the board game @link java.util.hashtable
	 * @return
	 */
	public final int getSize() {
		return this.squares.size();
	} // end getSize
	
	/**
	 * Determines if the game contains the key
	 * @param key Identifier to lookup in the game data
	 * @return True if key found else False
	 */
	public final boolean containsKey(Point key) {
		if(key.x < 0 || key.x >= COLUMNS) return false;
		if(key.y < 0 || key.y >= ROWS) return false;
		
		return this.squares.containsKey(key);
	} // end containsKey
	
	/**
	 * Determine if the player has selected a square for play
	 * @return True/False
	 */
	public final boolean isPlayerSquareActive() {
		return activeSquare != null;
	} // end isPlayerSquareActive
	
	/**
	 * Gets the current player's ready for moving
	 * @return the current players selected @BoardSquareInfo
	 */
	public final BoardSquareInfo getActiveSquare() { return this.activeSquare; }
	
	/**
	 * Sets the current player's square for moving
	 * @param value
	 */
	public final void setActiveSquare(BoardSquareInfo value) {
		this.activeSquare = value;
	}
} // end BoardGameEngine
