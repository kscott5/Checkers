package karega.scott.checkers;

import junit.framework.Assert;

public class CheckersEngineJumpTest extends CheckersEngineBaseTest {
	
	@Override
	public void setUp() {
		super.setUp();		
	}

	@Override
	public void tearDown() {
		super.tearDown();
	}
	
	public void test_moveSquare1JumpForPlayer1() {
		// Preparing board for jump
		CheckersEngineTest prep = new CheckersEngineTest();
		prep.test_movePlayerFinal();
		
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
		BoardSquareInfo test = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE,test.state);
		
		test = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, test.state);
		
		Assert.assertTrue(engine.isPlayer2());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(9, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(BoardGameEngine.PLAYER1_STATE));
	}

	public void test_moveSquare1JumpForPlayer2() {
		// Preparing board for jump
		test_moveSquare1JumpForPlayer1();

		// Performing jump
		BoardSquareInfo start = engine.getData(1,6);
		engine.moveSquare(start);
		
		BoardSquareInfo target = engine.getData(3,4);
		engine.moveSquare(target);
		
		// Test
		BoardSquareInfo test = engine.getData(3,4);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE,test.state);
		
		test = engine.getData(2,5);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, test.state);
		
		Assert.assertTrue(engine.isPlayer1());
		
		Assert.assertEquals(32, countSquares(BoardGameEngine.LOCKED_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER2_STATE));
		Assert.assertEquals(10, countSquares(BoardGameEngine.EMPTY_STATE));
		Assert.assertEquals(11, countSquares(BoardGameEngine.PLAYER1_STATE));
	}

} // end CheckersEngineJumpTestCase
