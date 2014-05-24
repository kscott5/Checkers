package karega.scott.checkers.test;

import junit.framework.Assert;
import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

public class Player2PawnTest extends CheckersBaseTest {
	@Override
	public void setUp() {
		engine = new CheckersEngineWrapper(super.getContext(), /*vsDevice*/ false);
		super.clearGameBoard();
	}
	
	public void test_move_from_3_4_to_4_3() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(4,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_move_from_3_4_to_4_5() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(4,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,5);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_move_from_3_4_to_2_3() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(2,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	public void test_move_from_3_4_to_2_5() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}
	
	public void test_move_from_6_3_to_7_2_makes_king() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(6,3);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,2);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());		
	}

	public void test_move_from_6_3_to_7_4_makes_king() {
	    // Prepare
		engine.activeState = BoardGameEngine.PLAYER2_STATE;
		
		// Perform
		BoardSquareInfo start = engine.getData(6,3);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());		
	}
} // end Player2PawnTest
