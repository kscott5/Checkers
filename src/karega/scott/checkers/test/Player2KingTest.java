package karega.scott.checkers.test;

import junit.framework.Assert;
import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

public class Player2KingTest extends CheckersBaseTest {
	// TODO: What is the equivalent of FixtureSetup
	
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
	
	public void test_move_from_7_0_to_0_7() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(7,0);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,7);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(7,0);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,7);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_move_from_0_7_to_7_0() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(0,7);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,0);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(0,7);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,0);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}
	
	public void test_move_from_0_1_to_6_7() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(0,1);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(6,7);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(0,1);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(6,7);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_move_from_6_7_to_0_1() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(6,7);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = true;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,1);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,7);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,1);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}
} // end Player2KingTest
