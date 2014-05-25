package karega.scott.checkers.test;

import junit.framework.Assert;
import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

public class Player1PawnTest extends CheckersBaseTest {
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
	
	public void test_move_from_4_3_to_3_4() {
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

	public void test_no_move_from_4_3_to_5_4() {
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	public void test_no_move_from_4_3_to_5_2() {	
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}
	
	public void test_move_from_1_2_to_0_3_makes_king() {
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
	
	public void test_no_move_from_4_1_to_0_3() {
		// Prepare
		BoardSquareInfo opponent = engine.getData(3,2);
		opponent.state = BoardGameEngine.PLAYER2_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
		
		opponent = engine.getData(1,4);
		opponent.state = BoardGameEngine.PLAYER2_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
				
		BoardSquareInfo start = engine.getData(4,1);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,1);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(3,2);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(1,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);

		Assert.assertEquals(1, countSquares(BoardGameEngine.PLAYER1_STATE));
		Assert.assertEquals(2, this.countSquares(BoardGameEngine.PLAYER2_STATE));
		
		Assert.assertTrue(engine.isPlayer1());				
	}
	
	public void test_no_move_from_5_2_to_3_2() {
		// Prepare
		BoardSquareInfo player1 = engine.getData(4,1);
		player1.state = BoardGameEngine.PLAYER1_STATE;
		player1.chip = BoardGameEngine.PAWN_CHIP;
		
		BoardSquareInfo opponent = engine.getData(4,3);
		opponent.state = BoardGameEngine.PLAYER2_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
		
		BoardSquareInfo start = engine.getData(5,2);
		start.state = BoardGameEngine.PLAYER1_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,2);
		target.state = BoardGameEngine.EMPTY_STATE;
		target.chip = BoardGameEngine.EMPTY_CHIP;
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,2);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,1);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
	}
} // end Player1PawnTest