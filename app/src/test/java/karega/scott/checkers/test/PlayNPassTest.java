package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class PlayNPassTest extends CheckersEngineTest {
	CheckersEngine engine;
	
	@Before public void beforeTest() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
	}
	
	@After public void afterTest() {
		engine.exitGame();
	}
	
	/**
	 * Moves player 1 a single square from (5,0) to (4,1)
	 */
	@Test public void moveSquareStep1_SingleMovePlayer1() {		
		// Player1
		BoardSquareInfo start = engine.getData(5,0);
		engine.moveSquare(start);
						
		// Target for Player1
		BoardSquareInfo target = engine.getData(4,1);
		engine.moveSquare(target);

		BoardSquareInfo actual = engine.getData(start.id);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(target.id);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);

		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(engine.PLAYER1_STATE));
	}

	/*
	 * Moves player 2 a single square from (2,7) to (3,6)
	 */
	@Test public void moveSquareStep2_SingleMovePlayer2() {
		moveSquareStep1_SingleMovePlayer1();
		
		// Player2
		BoardSquareInfo start = engine.getData(2,7);
		engine.moveSquare(start);
						
		// Target for Player2
		BoardSquareInfo target = engine.getData(3,6);
		engine.moveSquare(target);

		BoardSquareInfo actual = engine.getData(start.id);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(target.id);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);

		Assert.assertTrue(engine.isPlayer1());

		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(engine.PLAYER1_STATE));
	}	

	/*
	 * Player 1 move from (5,2) to (4,3)
	 * Player 2 move from (2,5) to (3,4)
	 * Player 1 jumps from (4,3) to (2,5)
	 * Player 2 removed at (3,4)
	 */
	@Test public void moveSquareStep3_Player1Jumping() {
		moveSquareStep2_SingleMovePlayer2();
		
		BoardSquareInfo start = engine.getData(5,2);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(4,3);
		engine.moveSquare(target);
				
		start = engine.getData(2,5);
		engine.moveSquare(start);
		
		target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Performing jump
		start = engine.getData(4, 3);
		engine.moveSquare(start);
		
		target = engine.getData(2,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(2,5);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(3,4);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(9, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(engine.PLAYER1_STATE));
	}

	/*
	 * Player 2 jumps from (1,6) to (3,4) 
	 * Player 1 removed at ((2,5)
	 */
	@Test public void moveSquareStep4_Player2Jumping() {
		moveSquareStep3_Player1Jumping();
		// Performing jump
		BoardSquareInfo start = engine.getData(1,6);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer1());
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER1_STATE));
	}

	/*
	 * Player 1 moves from (6,1) to (5,2)
	 * Player 2 moves from (3,4) to (4,3)
	 */
	@Test public void moveSquareStep5_SingleMoveEach() {
		moveSquareStep4_Player2Jumping();
		// Perform
		BoardSquareInfo start = engine.getData(6,1);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(5,2);
		engine.moveSquare(target);
		
		start = engine.getData(3,4);
		engine.moveSquare(start);
		
		target = engine.getData(4,3);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(5,2);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER1_STATE));

		Assert.assertTrue(engine.isPlayer1());
	}
	
	/*
	 * Player 1 moves from (7,0) to (6,1)
	 * Player 2 moves from (1,4) to (2,5)
	 */
	@Test public void moveSquareStep6_SingleMoveEach() {
		moveSquareStep5_SingleMoveEach();
		
		// Perform
		BoardSquareInfo start = engine.getData(7,0);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(6,1);
		engine.moveSquare(target);
		
		start = engine.getData(1,4);
		engine.moveSquare(start);
		
		target = engine.getData(2,5);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(6,1);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(engine.PLAYER2_STATE, actual.state);
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER1_STATE));

		Assert.assertTrue(engine.isPlayer1());
	}
	
	/**
	 * Player 1 jump from (5,2) to (1,6)
	 * Player 2 removed at (4,3) and (2,5)
	 */
	@Test public void moveSquareStep7_Player1Jumping() {
		moveSquareStep6_SingleMoveEach();
		
		// Perform
		BoardSquareInfo start = engine.getData(5,2);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(1,6);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,6);
		Assert.assertEquals(engine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(engine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(engine.LOCKED_STATE));
		Assert.assertEquals(9, countSquares(engine.PLAYER2_STATE));
		Assert.assertEquals(12, countSquares(engine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(engine.PLAYER1_STATE));
	}
} // end PlayNPassTest
