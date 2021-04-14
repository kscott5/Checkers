package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class Player1KingTest extends CheckersEngineTest {
	CheckersEngine engine;
	
	@Before public void beforeTest() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
	}
	
	@After public void afterTest() {
		engine.exitGame();
	}

	@Test public void move_from_7_0_to_0_7() {
		// Perform
		BoardSquareInfo start = engine.getData(7,0);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,7);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(7,0);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,7);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_0_7_to_7_0() {
		// Perform
		BoardSquareInfo start = engine.getData(0,7);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,0);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(0,7);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,0);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_7_6_to_1_0() {
		// Perform
		BoardSquareInfo start = engine.getData(7,6);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(1,0);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(7,6);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(1,0);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_1_0_to_7_6() {
		// Perform
		BoardSquareInfo start = engine.getData(1,0);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,6);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,0);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,6);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}
} // end Player1KingTest
