package karega.scott.checkers.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

public class Player1KingTest extends CheckersBaseTest {
	@Override
	public void setUp() {
		if(engine == null) {
			super.setUp();
		}
		
		super.clearGameBoard();
	}

	@Override
	public void tearDown() {
		
	}
	
	@Test public void move_from_7_0_to_0_7() {
		// Perform
		BoardSquareInfo start = engine.getData(7,0);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,7);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(7,0);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,7);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_0_7_to_7_0() {
		// Perform
		BoardSquareInfo start = engine.getData(0,7);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,0);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(0,7);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,0);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_7_6_to_1_0() {
		// Perform
		BoardSquareInfo start = engine.getData(7,6);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(1,0);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(7,6);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(1,0);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_1_0_to_7_6() {
		// Perform
		BoardSquareInfo start = engine.getData(1,0);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,6);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,0);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,6);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);
		
		Assert.assertTrue(engine.isPlayer2());
	}
} // end Player1KingTest
