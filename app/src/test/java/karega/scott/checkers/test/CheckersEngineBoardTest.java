package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class CheckersEngineBoardTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine( /*vsDevice*/ false);
		engine.newGame();
	}

	@After public void after() {
	}
	
	@Test public void getDataAtRow0() {
		BoardSquareInfo bsi = engine.getData(0,0);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(0,1);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(0,2);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(0,3);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(0,4);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(0,5);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(0,6);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(0,7);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);
	}
	
	@Test public void getDataAtRow1() {
		BoardSquareInfo bsi = engine.getData(1,0);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);
		
		bsi = engine.getData(1,1);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(1,2);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);
		
		bsi = engine.getData(1,3);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(1,4);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(1,5);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(1,6);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(1,7);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
	}

	@Test public void getDataAtRow2() {
		BoardSquareInfo bsi = engine.getData(2,0);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(2,1);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(2,2);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(2,3);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(2,4);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(2,5);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);

		bsi = engine.getData(2,6);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(2,7);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, bsi.state);
	}

	@Test public void getDataAtRow3() {
		BoardSquareInfo bsi = engine.getData(3,0);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);
		
		bsi = engine.getData(3,1);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(3,2);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);
		
		bsi = engine.getData(3,3);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(3,4);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);

		bsi = engine.getData(3,5);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(3,6);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);

		bsi = engine.getData(3,7);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
	}

	@Test public void getDataAtRow4() {
		BoardSquareInfo bsi = engine.getData(4,0);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(4,1);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);

		bsi = engine.getData(4,2);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(4,3);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);

		bsi = engine.getData(4,4);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(4,5);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);

		bsi = engine.getData(4,6);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(4,7);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, bsi.state);
	}

	@Test public void getDataAtRow5() {
		BoardSquareInfo bsi = engine.getData(5,0);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);
		
		bsi = engine.getData(5,1);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(5,2);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);
		
		bsi = engine.getData(5,3);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(5,4);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(5,5);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(5,6);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(5,7);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
	}

	@Test public void getDataAtRow6() {
		BoardSquareInfo bsi = engine.getData(6,0);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(6,1);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(6,2);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
		
		bsi = engine.getData(6,3);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(6,4);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(6,5);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(6,6);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(6,7);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);
	}
	
	@Test public void getDataAtRow7() {
		BoardSquareInfo bsi = engine.getData(7,0);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);
		
		bsi = engine.getData(7,1);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(7,2);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);
		
		bsi = engine.getData(7,3);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(7,4);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(7,5);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);

		bsi = engine.getData(7,6);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, bsi.state);

		bsi = engine.getData(7,7);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, bsi.state);
	}
}
