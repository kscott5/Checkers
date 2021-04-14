package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

public class Player1PawnTest extends CheckersEngineTest {
	@Test public void move_from_4_3_to_3_4() {
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(3,4);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void move_from_4_3_to_3_2() {
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(3,2);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void no_move_from_4_3_to_5_4() {
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	@Test public void no_move_from_4_3_to_5_2() {	
		// Perform
		BoardSquareInfo start = engine.getData(4,3);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}
	
	@Test public void move_from_1_2_to_0_3_makes_king() {
		// Perform
		BoardSquareInfo start = engine.getData(1,2);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,2);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,3);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());		
	}

	@Test public void move_from_1_4_to_0_3_makes_king() {
		// Perform
		BoardSquareInfo start = engine.getData(1,4);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(0,3);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());		
	}
	
	@Test public void no_move_from_4_1_to_0_3() {
		// Prepare
		BoardSquareInfo opponent = engine.getData(3,2);
		opponent.state = engine.PLAYER2_STATE;
		opponent.chip = engine.PAWN_CHIP;
		
		opponent = engine.getData(1,4);
		opponent.state = engine.PLAYER2_STATE;
		opponent.chip = engine.PAWN_CHIP;
				
		BoardSquareInfo start = engine.getData(4,1);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(0,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(4,1);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(3,2);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(1,4);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);

		Assert.assertEquals(1, countSquares(engine.PLAYER1_STATE));
		Assert.assertEquals(2, this.countSquares(engine.PLAYER2_STATE));
		
		Assert.assertTrue(engine.isPlayer1());				
	}
	
	@Test public void no_move_from_5_2_to_3_2() {
		// Prepare
		BoardSquareInfo player1 = engine.getData(4,1);
		player1.state = engine.PLAYER1_STATE;
		player1.chip = engine.PAWN_CHIP;
		
		BoardSquareInfo opponent = engine.getData(4,3);
		opponent.state = engine.PLAYER2_STATE;
		opponent.chip = engine.PAWN_CHIP;
		
		BoardSquareInfo start = engine.getData(5,2);
		start.state = engine.PLAYER1_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,2);
		target.state = engine.EMPTY_STATE;
		target.chip = engine.EMPTY_CHIP;
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,2);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,1);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
	}
} // end Player1PawnTest