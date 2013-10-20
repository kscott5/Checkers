package karega.scott.checkers;

import java.util.Hashtable;

import android.graphics.Point;
import android.util.Log;

/**
 * Game engine for checkers
 * @author Administrator
 *
 */
public class CheckersEngine extends BoardGameEngine {

	/**
	 * Constructor
	 */
	public CheckersEngine() {
		super(BoardGameEngineType.CHECKERS);
	}

	/* (non-Javadoc)
	 * @see karega.scott.checkers.BoardGameEngine#initializeBoardGameData()
	 */
	@Override
	protected Hashtable<Point, BoardSquareInfo> initializeBoardGameData() {
		Hashtable<Point, BoardSquareInfo> initialSquares = new Hashtable<Point, BoardSquareInfo>();
		
		int key = 0;
		for(int row=0; row<ROWS; row++)  {
			for(int col=0; col<COLUMNS; col++) {
				BoardSquareInfo square = null;
				Point point = new Point(col, row);				
				
				// Configure Locked square
				if((row+col)%2 != 0 && row < 3) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.PLAYER2, BoardSquarePieceType.PAWN);
				} else if((row+col)%2 != 0 && (row == 3 || row == 4)) {					
					square = new BoardSquareInfo(key, point, BoardSquareStateType.EMPTY, BoardSquarePieceType.NONE);
				} else if((row+col)%2 != 0 && row > 4) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.PLAYER1, BoardSquarePieceType.PAWN);
				} else { 
					//if((row+col)%2 == 0) {
					square = new BoardSquareInfo(key, point, BoardSquareStateType.LOCKED, BoardSquarePieceType.NONE);					
				}
				
				square.reset();
				initialSquares.put(point, square);
				key++;
			} // end for
		} // end for
		
		return initialSquares;
	} //end initializeBoardGameData

	/* (non-Javadoc)
	 * @see karega.scott.checkers.BoardGameEngine#setPlayerSquare(karega.scott.checkers.BoardSquare)
	 */
	@Override
	public boolean setPlayerSquare(BoardSquare square) {
		BoardSquareInfo info = square.getInformation();
		
		// Must be current player
		if(this.getCurrentPlayer() == info.getStateType()) 
		{		
			// Is the information movable
			if(player1Movable(info) || player2Movable(info) || playerKingMovable(info)) 
			{
				if(this.getActiveSquare() != null) {
					// Reset previous selected chip
					this.getActiveSquare().deactivatePlayer();
				} //end if
				
				info.activatePlayer();			
				this.setActiveSquare(info);
				
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
		if(this.getCurrentPlayer() == BoardSquareStateType.PLAYER1 || info.getIsKing()) {
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
		if(this.getCurrentPlayer() == BoardSquareStateType.PLAYER2 || info.getIsKing()) {
			boolean movableForwards = isPlayerMovable(info,true,false,true);
			boolean movableBackwards = isPlayerMovable(info,false,false,true);
		
			return movableForwards || movableBackwards;
		}
		
		return false;
	} // end player2Movable
	
	/**
	 * Determine if the current player's king has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean playerKingMovable(BoardSquareInfo info) {
		return (player1Movable(info) || player2Movable(info));
	} // end playerKingMovable
	
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
		if(this.containsKey(point)) {
			BoardSquareInfo pointInfo = this.getData(point);
			
			if(pointInfo.getStateType() == BoardSquareStateType.EMPTY) { 		
				return true;
			} else if(pointInfo.getStateType() != this.getCurrentPlayer() &&
					pointInfo.getStateType() != BoardSquareStateType.LOCKED && levelUp) {
				if(isPlayerMovable(pointInfo, colForward, rowForward, false))
					return true;
			}
		} // end if
		
		return false;
	} // end isPlayerMovable
	
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
		if(this.containsKey(point)) {
			BoardSquareInfo pointInfo = this.getData(point);
			
			if(this.getActiveSquare().equals(pointInfo)) 
			{
				this.getActiveSquare().swap(target);
				return true;
			} else if((pointInfo.getStateType() != this.getCurrentPlayer() &&
				pointInfo.getStateType() == BoardSquareStateType.EMPTY &&
				movePlayerToTarget(pointInfo, colBackward, rowBackward)) 
				|| 
				(pointInfo.getStateType() != this.getCurrentPlayer() &&
				pointInfo.getStateType() == BoardSquareStateType.EMPTY &&
				movePlayerToTarget(pointInfo, !colBackward, !rowBackward)))
			{
				pointInfo.makeEmpty();
				return true;
			} else if((pointInfo.getStateType() != this.getCurrentPlayer() &&
				pointInfo.getStateType() != BoardSquareStateType.EMPTY && 
				movePlayerToTarget(pointInfo, colBackward, rowBackward)) 
				||
				(pointInfo.getStateType() != this.getCurrentPlayer() &&
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
		if(this.getCurrentPlayer() != BoardSquareStateType.PLAYER1)
			return false;
		
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,true);
		boolean moveForwards = movePlayerToTarget(target,false,true);
		
		if(moveBackwards || moveForwards) {
			this.switchCurrentPlayer();
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
		if(this.getCurrentPlayer() != BoardSquareStateType.PLAYER2)
			return false;
		
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,false);
		boolean moveForwards = movePlayerToTarget(target,false,false);
		
		if(moveBackwards || moveForwards) {
			this.switchCurrentPlayer();
			return true;
		}
		
		return false;
	} // end movePlayer2ToTarget

	/* (non-Javadoc)
	 * @see karega.scott.checkers.BoardGameEngine#movePlayer(karega.scott.checkers.BoardSquare)
	 */
	@Override
	public boolean movePlayer(BoardSquare square) {
		BoardSquareInfo info = square.getInformation();

		return movePlayer1ToTarget(info) || movePlayer2ToTarget(info);
	} // end movePlayer
} // end CheckersEngine
