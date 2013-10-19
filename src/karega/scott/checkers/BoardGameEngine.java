package karega.scott.checkers;
import java.util.Hashtable;

import karega.scott.checkers.BoardSquareStateType;
import android.graphics.Point;
import android.util.Log;

public class BoardGameEngine {
	private static final int ROWS = 8;
	private static final int COLUMNS = 8;
	
	private BoardSquareStateType currentPlayerStateType;
	private BoardSquareInfo activeSquareInfo;
	
	private Hashtable<Point, BoardSquareInfo> squares;
	
	public BoardGameEngine() {
		this.currentPlayerStateType = BoardSquareStateType.PLAYER1;
		this.activeSquareInfo = null;		
		this.squares = initializeBoardGameData();
	}
	
	/*
	 * Create the data used on the checker board
	 */
	private Hashtable<Point, BoardSquareInfo> initializeBoardGameData() {
		Hashtable<Point, BoardSquareInfo> initialSquares = new Hashtable<Point, BoardSquareInfo>();
		
		int key = 0;
		for(int row=0; row<ROWS; row++)  {
			for(int col=0; col<COLUMNS; col++) {
				Log.v("GameBoardAdapter.createNewData", String.format("position: %1s, row: %2s, col: %3s, (row+col)%%2 = %4s",key, row, col, (row+col)%2));
				
				BoardSquareInfo square = null;
				Point point = new Point(col, row);				
				
				// Configure Locked square
				if((row+col)%2 != 0 && row < 3) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.PLAYER1);
				} else if((row+col)%2 != 0 && (row == 3 || row == 4)) {					
					square = new BoardSquareInfo(key, point, BoardSquareStateType.EMPTY);
				} else if((row+col)%2 != 0 && row > 4) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.PLAYER2);
				} else { 
					//if((row+col)%2 == 0) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.LOCKED);					
				}
				
				square.reset();
				initialSquares.put(point, square);
				key++;
			} // end for
		} // end for
		
		return initialSquares;
	} //end initializeBoardGameData

	/**
	 * Starts a new game
	 */
	public void newGame() {
		this.currentPlayerStateType = BoardSquareStateType.PLAYER1;
		this.activeSquareInfo = null;		

		for(BoardSquareInfo square : this.squares.values()) {
			square.reset();
		} // end for
	} //end newGame
	
	// TODO: Implement custom collection and include these methods
	public BoardSquareInfo getData(int id) {
		for(BoardSquareInfo square : this.squares.values()) {
			if(square.getId() == id)
				return square;
		}
		return null;
	}
	
	public int getSize() {
		return this.squares.size();
	}
	// end TODO: Implement custom collection and include above methods
	
	public boolean isPlayerSquareActive() {
		return activeSquareInfo != null;
	}
	
	/**
	 * Determines if square is for the current player
	 * @param square
	 * @return true or false
	 */
	public boolean setPlayerSquare(BoardSquare square) {
		BoardSquareInfo info = square.getSquareInformation();
		
		// Must be current player
		if(currentPlayerStateType == info.getStateType()) 
		{		
			// Is the information movable
			if(player1Movable(info) || player2Movable(info) || playerKingMovable(info)) 
			{
				if(activeSquareInfo != null) {
					// Reset previous selected chip
					activeSquareInfo.deactivatePlayer();
				} //end if
				
				info.activatePlayer();					
				activeSquareInfo = info;
				return true;
			} // end if (player movable)
		} // end if
		
		return false;
	} // end setPlayerSquare
	
	/**
	 * Determine if the current player1 has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean player1Movable(BoardSquareInfo info) {
		if(currentPlayerStateType == BoardSquareStateType.PLAYER1 || info.getIsKing()) {
			boolean movableForwards = isPlayerMovable(info,true,true,true);
			boolean movableBackwards = isPlayerMovable(info,false,true,true);
			
			return movableForwards || movableBackwards;
		}
		
		return false;
	}
	
	/**
	 * Determine if the current player2 has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean player2Movable(BoardSquareInfo info) {
		if(currentPlayerStateType == BoardSquareStateType.PLAYER2 || info.getIsKing()) {
			boolean movableForwards = isPlayerMovable(info,true,false,true);
			boolean movableBackwards = isPlayerMovable(info,false,false,true);
		
			return movableForwards || movableBackwards;
		}
		
		return false;
	}
	
	/**
	 * Determine if the current player's king has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean playerKingMovable(BoardSquareInfo info) {
		return (player1Movable(info) || player2Movable(info));
	}
	
	/**
	 * Determine if the current player has an available square to move to
	 * @param info
	 * @param colForward
	 * @param rowForward
	 * @param levelUp
	 * @return
	 */
	protected boolean isPlayerMovable(BoardSquareInfo info, boolean colForward, boolean rowForward, boolean levelUp) {
		int colIncrement = (colForward)? 1: -1;
		int rowIncrement = (rowForward)? 1: -1;
		
		Point point = new Point(info.getPoint().x+colIncrement, info.getPoint().y+rowIncrement);
		if(squares.containsKey(point)) {
			BoardSquareInfo pointInfo = this.squares.get(point);
			
			if(pointInfo.getStateType() == BoardSquareStateType.EMPTY) { 		
				return true;
			} else if(pointInfo.getStateType() != this.currentPlayerStateType &&
					pointInfo.getStateType() != BoardSquareStateType.LOCKED && levelUp) {
				if(isPlayerMovable(pointInfo, colForward, rowForward, false))
					return true;
			}
		} // end if
		
		return false;
	} // end isPlayerMovable
	
	/**
	 * 
	 * @param square
	 */
	public boolean movePlayer(BoardSquare square) {
		BoardSquareInfo info = square.getSquareInformation();

		return movePlayer1ToTarget(info) || movePlayer2ToTarget(info);
	} // end movePlayer	
	
	/**
	 * Using the target location, work backwards to update the game board
	 * @param target
	 * @param colBackward
	 * @param rowBackward
	 * @return
	 */
	protected boolean movePlayerToTarget(BoardSquareInfo target, boolean colBackward, boolean rowBackward) {
		int colIncrement = (colBackward)? -1: 1;
		int rowIncrement = (rowBackward)? -1: 1;
		
		Point point = new Point(target.getPoint().x+colIncrement, target.getPoint().y+rowIncrement);
		if(squares.containsKey(point)) {
			BoardSquareInfo pointInfo = this.squares.get(point);
			
			if(this.activeSquareInfo.equals(pointInfo)) 
			{
				this.activeSquareInfo.swap(target);
				return true;
			} else if((pointInfo.getStateType() != this.currentPlayerStateType &&
				pointInfo.getStateType() == BoardSquareStateType.EMPTY &&
				movePlayerToTarget(pointInfo, colBackward, rowBackward)) 
				|| 
				(pointInfo.getStateType() != this.currentPlayerStateType &&
				pointInfo.getStateType() == BoardSquareStateType.EMPTY &&
				movePlayerToTarget(pointInfo, !colBackward, !rowBackward)))
			{
				pointInfo.makeEmpty();
				return true;
			} else if((pointInfo.getStateType() != this.currentPlayerStateType &&
				pointInfo.getStateType() != BoardSquareStateType.EMPTY && 
				movePlayerToTarget(pointInfo, colBackward, rowBackward)) 
				||
				(pointInfo.getStateType() != this.currentPlayerStateType &&
				pointInfo.getStateType() != BoardSquareStateType.EMPTY && 
				movePlayerToTarget(pointInfo, !colBackward, !rowBackward))) 
			{
				pointInfo.makeEmpty();
				return true;
			} // end if-else
		} // end if
		
		return false;
	} // end movePlayerToTarget	
	
	/**
	 * Move player1 to target location
	 * @param target
	 * @return
	 */
	protected boolean movePlayer1ToTarget(BoardSquareInfo target) {
		if(this.currentPlayerStateType != BoardSquareStateType.PLAYER1)
			return false;
		
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,true);
		boolean moveForwards = movePlayerToTarget(target,false,true);
		
		if(moveBackwards || moveForwards) {
			this.activeSquareInfo = null;
			this.currentPlayerStateType = BoardSquareStateType.PLAYER2;
			return true;
		}
		
		return false;
	} // end movePlayer1ToTarget

	/**
	 * Move player2 to target location
	 * @param target
	 * @return
	 */
	protected boolean movePlayer2ToTarget(BoardSquareInfo target) {
		if(this.currentPlayerStateType != BoardSquareStateType.PLAYER2)
			return false;
		
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,false);
		boolean moveForwards = movePlayerToTarget(target,false,false);
		
		if(moveBackwards || moveForwards) {
			this.activeSquareInfo = null;
			this.currentPlayerStateType = BoardSquareStateType.PLAYER1;
			return true;
		}
		
		return false;
	} // end movePlayer2ToTarget
} // end BoardGameEngine
