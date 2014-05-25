package karega.scott.checkers.test;

import junit.framework.Assert;
import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

public class PlayNPassTest extends CheckersBaseTest {
	// TODO: What is the equivalent of FixtureSetup
	
	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Override
	public void tearDown() {		
	}
	
	/**
	 * Moves player 1 a single square from (5,0) to (4,1)
	 */
	public void test_moveSquareStep1_SingleMovePlayer1() {		
		// Player1
		BoardSquareInfo start = engine.getData(5,0);
		engine.moveSquare(start);
						
		// Target for Player1
		BoardSquareInfo target = engine.getData(4,1);
		engine.moveSquare(target);

		BoardSquareInfo actual = engine.getData(start.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(target.id);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);

		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER1_STATE));
	}

	/*
	 * Moves player 2 a single square from (2,7) to (3,6)
	 */
	public void test_moveSquareStep2_SingleMovePlayer2() {
		test_moveSquareStep1_SingleMovePlayer1();
		
		// Player2
		BoardSquareInfo start = engine.getData(2,7);
		engine.moveSquare(start);
						
		// Target for Player2
		BoardSquareInfo target = engine.getData(3,6);
		engine.moveSquare(target);

		BoardSquareInfo actual = engine.getData(start.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(target.id);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);

		Assert.assertTrue(engine.isPlayer1());

		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER1_STATE));
	}	

	/*
	 * Player 1 move from (5,2) to (4,3)
	 * Player 2 move from (2,5) to (3,4)
	 * Player 1 jumps from (4,3) to (2,5)
	 * Player 2 removed at (3,4)
	 */
	public void test_moveSquareStep3_Player1Jumping() {
		test_moveSquareStep2_SingleMovePlayer2();
		
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
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(9, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER1_STATE));
	}

	/*
	 * Player 2 jumps from (1,6) to (3,4) 
	 * Player 1 removed at ((2,5)
	 */
	public void test_moveSquareStep4_Player2Jumping() {
		test_moveSquareStep3_Player1Jumping();
		// Performing jump
		BoardSquareInfo start = engine.getData(1,6);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer1());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER1_STATE));
	}

	/*
	 * Player 1 moves from (6,1) to (5,2)
	 * Player 2 moves from (3,4) to (4,3)
	 */
	public void test_moveSquareStep5_SingleMoveEach() {
		test_moveSquareStep4_Player2Jumping();
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
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER1_STATE));

		Assert.assertTrue(engine.isPlayer1());
	}
	
	/*
	 * Player 1 moves from (7,0) to (6,1)
	 * Player 2 moves from (1,4) to (2,5)
	 */
	public void test_moveSquareStep6_SingleMoveEach() {
		test_moveSquareStep5_SingleMoveEach();
		
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
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, actual.state);
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER1_STATE));

		Assert.assertTrue(engine.isPlayer1());
	}
	
	/**
	 * Player 1 jump from (5,2) to (1,6)
	 * Player 2 removed at (4,3) and (2,5)
	 */
	public void test_moveSquareStep7_Player1Jumping() {
		test_moveSquareStep6_SingleMoveEach();
		
		// Perform
		BoardSquareInfo start = engine.getData(5,2);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(1,6);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo actual = engine.getData(1,6);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, actual.state);
		
		actual = engine.getData(4,3);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		actual = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, actual.state);
		
		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(9, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER1_STATE));
	}
} // end PlayNPassTest
