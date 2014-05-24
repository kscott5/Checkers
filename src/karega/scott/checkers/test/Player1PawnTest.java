package karega.scott.checkers.test;

import junit.framework.Assert;
import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

public class Player1PawnTest extends CheckersBaseTest {
	@Override
	public void setUp() {
		engine = new CheckersEngineWrapper(super.getContext(), /*vsDevice*/ false);
		super.clearGameBoard();
	}
	
	public void test_move_from_4_3_to_3_4() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	public void test_move_from_4_3_to_3_2() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(3,2);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	public void test_move_from_4_3_to_5_4() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(5,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_move_from_4_3_to_5_2() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}
	
	public void test_move_from_1_2_to_0_3_makes_king() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(1,2);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,2);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,3);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());		
	}

	public void test_move_from_1_4_to_0_3_makes_king() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER1_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(1,4);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,3);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());		
	}
} // end Player1PawnTest