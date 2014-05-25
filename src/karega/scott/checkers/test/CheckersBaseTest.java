package karega.scott.checkers.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquare;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.CheckerBoardSquare;
import karega.scott.checkers.CheckersEngine;
import android.content.Context;
import android.test.AndroidTestCase;

public abstract class CheckersBaseTest extends AndroidTestCase {
	// Class variables allow extended subclasses to build complex
	// test case using the same engine and in some case existing
	// test methods
	protected Context context;
	protected CheckersEngineWrapper engine;
		
	public class CheckersEngineWrapper extends CheckersEngine {		
		public CheckersEngineWrapper(Context context, boolean vsDevice) {
			super(context, vsDevice);
		}
		
		@Override
		public BoardSquareInfo getData(int row, int col) {
			return super.getData(row, col);
		}
		
		@Override
		public BoardSquareInfo getData(int id) {
			return super.getData(id);
		}
		
		@Override
		protected void switchPlayer() {
			super.switchPlayer();
		}

		@Override
		public boolean handleOnTouch(BoardSquare square) {
			return super.handleOnTouch(square);
		}
		
		@Override
		public void moveSquare(BoardSquareInfo target) {
			super.moveSquare(target);
		}

		@Override
		protected void moveSquareForDevice() {
			super.moveSquareForDevice();
		}
		
		@Override
		protected int getSize() {
			return super.getSize();
		}
		
		@Override
		protected boolean isEmpty(int row, int col) {
			return super.isEmpty(row, col);
		}		
	}
	
	public class CheckerBoardSquareMock extends CheckerBoardSquare {
		protected BoardSquareInfo info;
		
		public CheckerBoardSquareMock(Context context, BoardSquareInfo info) {
			super(context, info);
		}
		
		@Override
		public void invalidate() {}
	}

	/**
	 * Prepares the board for simulated game play.
	 * by set all squares to EMPTY_STATE and ensures
	 * player1 is ready for play.
	 * 
	 * ASSUMES THE WAS ALREADY INITIALIZE BY CALLING
	 * engine.newGame()
	 */
	public void clearGameBoard() {		
		if(!engine.isPlayer1())
			engine.switchPlayer();
		
		for(int id=0;id<63;id++) {
			BoardSquareInfo square=engine.getData(id);
			if(square.state == BoardGameEngine.LOCKED_STATE 
					|| square.state == BoardGameEngine.EMPTY_STATE) continue;
			square.makeEmpty();
		}
	} // end clearGameBoard
	
	/**
	 * Asserts the total square count for each Board Game Square State
	 * @param forState
	 */
	public int countSquares(int forState) {
		int count=0;
		
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == forState) count++;
		}
		
		return count;
	} // end countSquares
	

	/*
	 * Always starts a new game
	 * @see android.test.AndroidTestCase#setUp()
	 */
	@Override
	public void setUp() {
		context = super.getContext();
		engine = new CheckersEngineWrapper(context, /*vsDevice*/ false);
		
		engine.newGame();
	}
	
	@Override
	public void tearDown() {
		engine.exitGame();
	}
} // end CheckersEngineBaseTestCase
