package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class EngineBaseTest {
	CheckersEngine engine;

	@Before public void beforeTest() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
	}
	@After public void afterTest() {
		engine.exitGame();
	}

	/**
	 * Prepares the board for simulated game play.
	 * by set all squares to EMPTY_STATE and ensures
	 * player1 is ready for play.
	 * 
	 * ASSUMES IT WAS ALREADY INITIALIZE BY CALLING
	 * engine.newGame()
	 */
	public void clearGameBoard() {		
		if(!engine.isPlayer1())
			engine.switchPlayer();
		
		for(int id=0;id<63;id++) {
			BoardSquareInfo square=engine.getData(id);
			if(square.state == CheckersEngine.LOCKED_STATE 
					|| square.state == CheckersEngine.EMPTY_STATE) continue;
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
} // end EngineBaseTest
