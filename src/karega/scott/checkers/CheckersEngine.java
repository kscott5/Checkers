package karega.scott.checkers;

import java.io.Console;

import junit.framework.Assert;
import android.content.Context;
import android.util.Log;

/**
 * Game engine for checkers
 * @author Administrator
 *
 */
public class CheckersEngine extends BoardGameEngine {
	private BoardSquareInfo[][] squares;
	private BoardSquare activeSquare;
	private int currentState;
	
	public CheckersEngine(Context context) {
		super(context, BoardGameEngine.CHECKERS_ENGINE);
	}
	
	@Override
	public boolean setPlayerSquare(BoardSquare square) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveChip(BoardSquare square) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadGame() {
		this.currentState = this.loadCurrentState(/*for new game*/ false);
		this.activeSquare = null;
		
		this.squares = this.loadSquares(/*for new game */false);
	}
	
	@Override
	public void newGame() {
		this.currentState = this.loadCurrentState(/*for new game*/ true);
		this.activeSquare = null;		

		this.squares = this.loadSquares(/*for new game */true);
	} // end newGame

	@Override
	public void saveGame() {
		
	}
	
	@Override
	public int getCurrentState() {
		return this.currentState;
	}

	@Override
	public void switchState() {
		this.activeSquare = null;
		
		this.currentState = (this.currentState == BoardGameEngine.PLAYER2_STATE) ?
				BoardGameEngine.PLAYER1_STATE : BoardGameEngine.PLAYER2_STATE;
		
		// TODO: Ensure no squares are highlighted
	} //end switchState

	@Override
	public BoardSquareInfo getData(int id) {
		int row = id/8;
		int col = id%8;
		
		BoardSquareInfo info = squares[row][col];
		
		if(id != info.getId()) 
			throw new Error("Checkers get data id, "+ id + ", not found");
		
		return info;
	}

	@Override
	public int getSize() {
		return BoardGameEngine.ROWS*BoardGameEngine.COLUMNS;
	}

	@Override
	public BoardSquareInfo getActiveSquare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActiveSquare(BoardSquareInfo value) {
		// TODO Auto-generated method stub
		
	}
} // end CheckersEngine
