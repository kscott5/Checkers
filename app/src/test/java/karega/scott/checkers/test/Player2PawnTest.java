package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class Player2PawnTest {
	CheckersEngine engine;
	
	@Before public void beforeTest() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
	}
	
	@After public void afterTest() {
		engine.exitGame();
	}
	
	@Test public void move_from_3_4_to_4_3() {
	    engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(4,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	@Test public void move_from_3_4_to_4_5() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(4,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(4,5);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());
	}

	@Test public void no_move_from_3_4_to_2_3() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void no_move_from_3_4_to_2_5() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(3,4);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(2,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		Assert.assertFalse(actual.isKing);

		Assert.assertTrue(engine.isPlayer2());
	}
	
	@Test public void move_from_6_3_to_7_2_makes_king() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(6,3);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,2);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,2);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());		
	}

	@Test public void move_from_6_3_to_7_4_makes_king() {
		engine.switchPlayer();
		
		// Perform
		BoardSquareInfo start = engine.getData(6,3);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(7,4);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		Assert.assertTrue(actual.isKing);

		Assert.assertTrue(engine.isPlayer1());		
	}
	
	@Test public void no_move_from_3_6_to_7_4() {
		engine.switchPlayer();
		
		// Prepare
		BoardSquareInfo opponent = engine.getData(4,5);
		opponent.state = engine.PLAYER1_STATE;
		opponent.chip = engine.PAWN_CHIP;
		
		opponent = engine.getData(6,3);
		opponent.state = engine.PLAYER1_STATE;
		opponent.chip = engine.PAWN_CHIP;
				
		BoardSquareInfo start = engine.getData(3,6);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(7,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,6);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,5);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(6,3);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(7,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());				
	}

	@Test public void no_move_from_3_2_to_5_2() {
		// Prepare
		BoardSquareInfo player2 = engine.getData(4,3);
		player2.state = engine.PLAYER2_STATE;
		player2.chip = engine.PAWN_CHIP;
		
		BoardSquareInfo opponent = engine.getData(4,1);
		opponent.state = engine.PLAYER1_STATE;
		opponent.chip = engine.PAWN_CHIP;
		
		BoardSquareInfo start = engine.getData(3,2);
		start.state = engine.PLAYER2_STATE;
		start.chip = engine.PAWN_CHIP;
		start.isKing = false;
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		target.state = engine.EMPTY_STATE;
		target.chip = engine.EMPTY_CHIP;
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,2);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(4,1);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(5,2);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
	}
} // end Player2PawnTest
