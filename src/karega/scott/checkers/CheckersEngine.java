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
		Hashtable<Point, BoardSquareInfo> initialSquares = new Hashtable<Point, BoardSquareInfo>(64);
		
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
		if(this.getCurrentPlayer() == BoardSquareStateType.LOCKED ||
		   this.getCurrentPlayer() == BoardSquareStateType.EMPTY)
			return false;
			
		// Must be current player
		if(this.getCurrentPlayer() == info.getCurrentPlayer()) 
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
	 * Determine if the current player2 has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean player2Movable(BoardSquareInfo info) {
		if(this.getCurrentPlayer() == BoardSquareStateType.PLAYER2 || info.getIsKing()) {
			boolean movableForwards = isPlayerMovable(info,true,true,true);
			boolean movableBackwards = isPlayerMovable(info,false,true,true);
			
			return movableForwards || movableBackwards;
		}
		
		return false;
	}
	
	/**
	 * Determine if the current player1 has an available square to move to
	 * @param info
	 * @return
	 */
	protected boolean player1Movable(BoardSquareInfo info) {
		if(this.getCurrentPlayer() == BoardSquareStateType.PLAYER1 || info.getIsKing()) {
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
			
			if(pointInfo.getCurrentPlayer() == BoardSquareStateType.EMPTY) { 		
				return true;
			} else if(pointInfo.getCurrentPlayer() != this.getCurrentPlayer() &&
					pointInfo.getCurrentPlayer() != BoardSquareStateType.LOCKED && levelUp) {
				if(isPlayerMovable(pointInfo, colForward, rowForward, false))
					return true;
			}
		} // end if
		
		return false;
	} // end isPlayerMovable
	
	/**
	 * Using the target point, work backwards and forwards to update the game board
	 * until you reach the active square 
	 * @param point
	 * @return
	 */
	protected boolean updateBoardForMove(Point point) {		
		Point movePtFwdRight = new Point(point.x+1, point.y+1);		
		if(this.containsKey(movePtFwdRight)) {
			BoardSquareInfo info = this.getData(movePtFwdRight);			
			if(this.getActiveSquare().equals(info)) 
				return true;
			
			if(this.getCurrentPlayer() != info.getCurrentPlayer() && 
					updateBoardForMove(movePtFwdRight)) {
				info.makeEmpty();
				return true;
			}
		} // end if
		
		Point movePtFwdLeft = new Point(point.x-1, point.y+1);
		if(this.containsKey(movePtFwdLeft)) {
			BoardSquareInfo info = this.getData(movePtFwdLeft);			
			if(this.getActiveSquare().equals(info)) 
				return true;
			
			if(this.getCurrentPlayer() != info.getCurrentPlayer() && 
					updateBoardForMove(movePtFwdLeft)) {
				info.makeEmpty();
				return true;
			}
		} // end if
		
		Point movePtBkwdRight = new Point(point.x+1, point.y-1);		
		if(this.containsKey(movePtBkwdRight)) {
			BoardSquareInfo info = this.getData(movePtBkwdRight);			
			if(this.getActiveSquare().equals(info)) 
				return true;
			
			if(this.getCurrentPlayer() != info.getCurrentPlayer() && 
					updateBoardForMove(movePtBkwdRight)) {
				info.makeEmpty();
				return true;
			}
		} // end if
		
		Point movePtBkwdLeft = new Point(point.x-1, point.y-1);
		if(this.containsKey(movePtBkwdLeft)) {
			BoardSquareInfo info = this.getData(movePtBkwdLeft);			
			if(this.getActiveSquare().equals(info)) 
				return true;
			
			if(this.getCurrentPlayer() != info.getCurrentPlayer() && 
					updateBoardForMove(movePtBkwdLeft)) {
				info.makeEmpty();
				return true;
			}
		} // end if

		return false;
	} // end movePlayerToTarget	

	/* (non-Javadoc)
	 * @see karega.scott.checkers.BoardGameEngine#movePlayer(karega.scott.checkers.BoardSquare)
	 */
	@Override
	public boolean movePlayer(BoardSquare square) {
		BoardSquareInfo target = square.getInformation();
		if(target.getCurrentPlayer() != BoardSquareStateType.EMPTY) 
			return false;
		
		if(updateBoardForMove(target.getPoint())) {
			this.getActiveSquare().swap(target);
			this.switchCurrentPlayer();
			return true;
		}
		
		return false;
	} // end movePlayer
} // end CheckersEngine
