package karega.scott.checkers.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

public class Player2PawnTest extends CheckersBaseTest {
	// TODO: What is the equivalent of FixtureSetup
	
	@Override
	public void setUp() {
		super.setUp();		
		super.clearGameBoard();
	}
	
	@Override
	public void tearDown() {
	}
	
	@Test public void move_from_3_4_to_4_3() {
	    engine.switchPlayer();
		
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

	@Test public void move_from_3_4_to_4_5() {
		engine.switchPlayer();
		
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

	@Test public void no_move_from_3_4_to_2_3() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void no_move_from_3_4_to_2_5() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}
	
	@Test public void move_from_6_3_to_7_2_makes_king() {
		engine.switchPlayer();
		
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

	@Test public void move_from_6_3_to_7_4_makes_king() {
		engine.switchPlayer();
		
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
	
	@Test public void no_move_from_3_6_to_7_4() {
		engine.switchPlayer();
		
		// Prepare
		BoardSquareInfo opponent = engine.getData(4,5);
		opponent.state = BoardGameEngine.PLAYER1_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
		
		opponent = engine.getData(6,3);
		opponent.state = BoardGameEngine.PLAYER1_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
				
		BoardSquareInfo start = engine.getData(3,6);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,6);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,5);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(6,3);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(7,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());				
	}

	@Test public void no_move_from_3_2_to_5_2() {
		// Prepare
		BoardSquareInfo player2 = engine.getData(4,3);
		player2.state = BoardGameEngine.PLAYER2_STATE;
		player2.chip = BoardGameEngine.PAWN_CHIP;
		
		BoardSquareInfo opponent = engine.getData(4,1);
		opponent.state = BoardGameEngine.PLAYER1_STATE;
		opponent.chip = BoardGameEngine.PAWN_CHIP;
		
		BoardSquareInfo start = engine.getData(3,2);
		start.state = BoardGameEngine.PLAYER2_STATE;
		start.chip = BoardGameEngine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		target.state = BoardGameEngine.EMPTY_STATE;
		target.chip = BoardGameEngine.EMPTY_CHIP;
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,2);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,1);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
	}
} // end Player2PawnTest
