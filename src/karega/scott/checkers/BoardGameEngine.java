package karega.scott.checkers;
import java.util.Hashtable;

import karega.scott.checkers.BoardSquareStateType;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class BoardGameEngine {
	private static final int ROWS = 8;
	private static final int COLUMNS = 8;
	
	private BoardSquareStateType currentPlayerStateType;
	private BoardSquareInfo activeSquareInfo;
	
	private Hashtable<Point, BoardSquareInfo> data;
	
	public BoardGameEngine() {
		this.currentPlayerStateType = BoardSquareStateType.PLAYER1;
		this.activeSquareInfo = null;		
		this.data = initializeBoardGameData();
	}
	
	/*
	 * Create the data used on the checker board
	 */
	private Hashtable<Point, BoardSquareInfo> initializeBoardGameData() {
		Hashtable<Point, BoardSquareInfo> list = new Hashtable<Point, BoardSquareInfo>();
		
		int key = 0;
		for(int row=0; row<ROWS; row++)  {
			for(int col=0; col<COLUMNS; col++) {
				Log.v("GameBoardAdapter.createNewData", String.format("position: %1s, row: %2s, col: %3s, (row+col)%%2 = %4s",key, row, col, (row+col)%2));
				
				BoardSquareInfo value = new BoardSquareInfo();

				Point point = new Point(col, row);
				
				value.setId(key);
				value.setPoint(point);
				value.setIsKing(false);
				
				value.setBorderColor(Color.BLACK);
				
				// Configure Locked square
				if((row+col)%2 == 0) {
					value.setFillColor(Color.GRAY);					
					value.setStateType(BoardSquareStateType.LOCKED);
				} 
				
				// Configure Player 1 square
				else if((row+col)%2 != 0 && row < 3) {
					value.setFillColor(Color.DKGRAY);
					value.setStateType(BoardSquareStateType.PLAYER1);
					value.setPlayerColor(Color.RED);
					value.resetActivePlayerColor();
				}

				// Configure Empty square
				else if((row+col)%2 != 0 && (row == 3 || row == 4)) {					
					value.setFillColor(Color.DKGRAY);					
					value.setStateType(BoardSquareStateType.EMPTY);
				}					

				// Configure Player 2 square
				else if((row+col)%2 != 0 && row > 4) {
					value.setFillColor(Color.DKGRAY);
					value.setStateType(BoardSquareStateType.PLAYER2);
					value.setPlayerColor(Color.BLUE);
					value.resetActivePlayerColor();
				}
				
				list.put(point, value);
				key++;
			} // end for
		} // end for
		
		return list;
	} //end initializeBoardGameData

	// TODO: Implement custom collection and include these methods
	public BoardSquareInfo getData(int id) {
		for(BoardSquareInfo info : this.data.values()) {
			if(info.getId() == id)
				return info;
		}
		return null;
	}
	
	public int getSize() {
		return this.data.size();
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
					activeSquareInfo.resetActivePlayerColor();
				} //end if
				
				info.setActivePlayerColor();					
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
		if(data.containsKey(point)) {
			BoardSquareInfo pointInfo = this.data.get(point);
			
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
	public void movePlayer(BoardSquare square) {
		BoardSquareInfo info = square.getSquareInformation();
				
		if(info.getStateType() == BoardSquareStateType.EMPTY) {

			if(this.currentPlayerStateType == BoardSquareStateType.PLAYER1) {
				
			} 
		}
	} // end movePlayer	
	
	/**
	 * Using the target location, work backwards to update the game board
	 * @param target
	 * @param colBackward
	 * @param rowBackward
	 * @param levelUp
	 * @return
	 */
	protected boolean movePlayerToTarget(BoardSquareInfo target, boolean colBackward, boolean rowBackward, boolean levelUp) {
		int colIncrement = (colBackward)? -1: 1;
		int rowIncrement = (rowBackward)? -1: 1;
		
		Point point = new Point(target.getPoint().x+colIncrement, target.getPoint().y+rowIncrement);
		if(data.containsKey(point)) {
			BoardSquareInfo pointInfo = this.data.get(point);
			
			if(this.activeSquareInfo.equals(pointInfo) && !levelUp) 
			{
				this.activeSquareInfo.swapInformation(target);
				return true;
			} else if(pointInfo.getStateType() != this.currentPlayerStateType &&
				pointInfo.getStateType() != BoardSquareStateType.EMPTY && 
				pointInfo.getStateType() != BoardSquareStateType.LOCKED &&
				movePlayerToTarget(pointInfo, colBackward, rowBackward, true)) 
			{
				pointInfo.makeInformationEmpty();
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
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,true,false);
		boolean moveForwards = movePlayerToTarget(target,false,true,false);
		
		return moveBackwards || moveForwards;
	}

	/**
	 * Move player2 to target location
	 * @param target
	 * @return
	 */
	protected boolean movePlayer2ToTarget(BoardSquareInfo target) {
		if(target.getStateType() != BoardSquareStateType.EMPTY) 
			return false;
		
		boolean moveBackwards = movePlayerToTarget(target,true,false,false);
		boolean moveForwards = movePlayerToTarget(target,false,false,false);
		
		return moveBackwards || moveForwards;
	}
} // end BoardGameEngine
