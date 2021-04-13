package karega.scott.checkers.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquare;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.CheckerBoardSquare;
import karega.scott.checkers.CheckersEngine;

import org.junit.Test;
import org.junit.Assert;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import android.content.Context;

import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public abstract class CheckersBaseTest {
	// Class variables allow extended subclasses to build complex
	// test case using the same engine and in some case existing
	// test methods
	@Mock protected Context context;
	protected static CheckersEngineWrapper engine;
		
	public class CheckersEngineWrapper extends CheckersEngine {
		public static CheckersEngine getEngine(boolean vsDevice) {
			return new CheckersEngine(vsDevice);
		}

		public CheckersEngineWrapper(boolean vsDevice) {
			super(vsDevice);
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
	 * @see org.junit4.BeforeClass
	 */
	@BeforeClass
	public static void setUp() {
		engine = CheckersEngineWrapper.getEngine(/*vsDevice*/ false);
		
		engine.newGame();
	}
	
	/*
	 * Always end the game
	 * @see org.junit4.AfterClass
	 */
	@AfterClass
	public static void tearDown() {
		engine.exitGame();
	}
} // end CheckersEngineBaseTestCase
