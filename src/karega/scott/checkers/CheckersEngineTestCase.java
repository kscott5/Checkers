package karega.scott.checkers;


import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Checkers Engine Test Case
 *
 * Configuration of JUnit 
 * http://stackoverflow.com/questions/2172152/cant-run-junit-4-test-case-in-eclipse-android-project
 * 
 * @author admin
 *
 */
public class CheckersEngineTestCase extends AndroidTestCase {
	
	private Context context;// = super.getContext(); 
	private CheckersEngine engine;// = new CheckersEngine(context, /*vsDevice*/ false);
	
	public class CheckerBoardSquareMock extends CheckerBoardSquare {
		public CheckerBoardSquareMock(Context context, BoardSquareInfo info) {
			super(context, info);
		}
		
		@Override
		public void invalidate() {}
	}

	/**
	 * Resets the board for simulated game play.
	 * This method will set activeSquare to null and
	 * activeState to EMPTY_STATE (no player) 
	 */
	protected void resetGameBoard() {
		engine.activeSquare = null;
		engine.activeState = BoardGameEngine.EMPTY_STATE;
		
		for(int row=0;row<8;row++) {
			for(int col=0;col<8;col++) {
				BoardSquareInfo square=engine.getData(row,col);
				if(square == null) continue;
				square.makeEmpty();
			}
		}
	} // end resetGameBoard
	
	@Override
	public void setUp() {
		context = super.getContext(); 
		engine = new CheckersEngine(context, /*vsDevice*/ false);
		
		engine.newGame();
	}
	
	@Override
	public void tearDown() {
		engine.exitGame();
	}
	
	public void test_getId() {		
		Assert.assertEquals(BoardGameEngine.CHECKERS_ENGINE, engine.getId());
	}
	
	public void test_isPlayer1(){
		Assert.assertTrue(engine.isPlayer1());
	}
	
	public void test_isPlayer2(){
		Assert.assertFalse(engine.isPlayer2());
	}
	
	public void test_isDevice(){
		Assert.assertFalse(engine.isDevice());
	}
	
	public void test_switchPlayers() {
		Assert.assertTrue(engine.isPlayer1());
		Assert.assertFalse(engine.isDevice());
		Assert.assertFalse(engine.isPlayer2());
		
		engine.switchPlayer();
		
		Assert.assertFalse(engine.isPlayer1());
		Assert.assertFalse(engine.isDevice());
		Assert.assertTrue(engine.isPlayer2());
		
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer1());
		Assert.assertFalse(engine.isDevice());
		Assert.assertFalse(engine.isPlayer2());
	} // end testSwitchPlayers
	
	public void test_getSize() {
		Assert.assertEquals(64,engine.getSize());
	}
	
	public void test_newGame() {	
		int player1=0,player2=0,empty=0,locked=0;
	
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == BoardGameEngine.LOCKED_STATE) locked++;
			if(square.state == BoardGameEngine.PLAYER2_STATE) player2++;
			if(square.state == BoardGameEngine.EMPTY_STATE) empty++;
			if(square.state == BoardGameEngine.PLAYER1_STATE) player1++;
			
			square.makeEmpty();
		}
		
		Assert.assertEquals(32, locked);
		Assert.assertEquals(12, player2);
		Assert.assertEquals(8, empty);
		Assert.assertEquals(12, player1);
		
		engine.newGame();		
		
		player1=player2=empty=locked=0;
		
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == BoardGameEngine.LOCKED_STATE) locked++;
			if(square.state == BoardGameEngine.PLAYER2_STATE) player2++;
			if(square.state == BoardGameEngine.EMPTY_STATE) empty++;
			if(square.state == BoardGameEngine.PLAYER1_STATE) player1++;			
		}
		
		Assert.assertEquals(32, locked);
		Assert.assertEquals(12, player2);
		Assert.assertEquals(8, empty);
		Assert.assertEquals(12, player1);
	}
		
	public void test_saveGame()  {
		// TODO: What are we testing
		try {
			engine.saveGame();
			Assert.fail();
		} catch(Error e) {
		}
	}
	
	public void test_exitGame() {
		// TODO: What are we testing
		engine.exitGame();
	}
		
	public void test_getDataById() {
		BoardSquareInfo square = engine.getData(0);
		Assert.assertEquals(0, square.id);
		Assert.assertEquals(BoardGameEngine.LOCKED_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(0, square.row);
		Assert.assertEquals(0, square.column);
		
		square = engine.getData(17);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(33);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(62);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		square = engine.getData(-1);
		Assert.assertNull(square);
		
		square = engine.getData(64);
		Assert.assertNull(square);
	}
	
	
	public void test_getDataByRowColumn() {
		BoardSquareInfo square = engine.getData(0,0);
		Assert.assertNull(square);
		
		square = engine.getData(2,1);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(4,1);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(7,6);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		square = engine.getData(8,8);
		Assert.assertNull(square);
		
		square = engine.getData(-1,-1);
		Assert.assertNull(square);
	}
	
	
	public void test_isEmpty() {
		BoardSquareInfo square = engine.getData(0);
		Assert.assertEquals(0, square.id);
		Assert.assertEquals(BoardGameEngine.LOCKED_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(0, square.row);
		Assert.assertEquals(0, square.column);

		Assert.assertFalse(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(17);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		Assert.assertFalse(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(33);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		Assert.assertTrue(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(62);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		Assert.assertFalse(engine.isEmpty(square.row, square.column));
	}
	
	public void test_activeSquare() {
		Assert.assertNull(engine.activeSquare);
		
		// Locked
		CheckerBoardSquareMock square = new CheckerBoardSquareMock(context, engine.getData(0));
		Assert.assertEquals(BoardGameEngine.LOCKED_STATE, square.getInformation().state);
		engine.handleOnTouch(square);
		
		Assert.assertNull(engine.activeSquare);
		
		// Player2
		square = new CheckerBoardSquareMock(context, engine.getData(23));
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, square.getInformation().state);
		engine.handleOnTouch(square);
				
		Assert.assertNull(engine.activeSquare);
				
		// Empty
		square = new CheckerBoardSquareMock(context, engine.getData(24));
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.getInformation().state);
		engine.handleOnTouch(square);
				
		Assert.assertNull(engine.activeSquare);		

		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(62));
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.getInformation().state);
		engine.handleOnTouch(square);
				
		Assert.assertNull(engine.activeSquare);		
		
		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(40));
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.getInformation().state);
		engine.handleOnTouch(square);
				
		Assert.assertNotNull(engine.activeSquare);
		Assert.assertTrue(engine.activeSquare.equals(square.getInformation()));
	}
		
	public void test_movePlayer1SingleSquare() {
		Assert.assertTrue(engine.isPlayer1());
		Assert.assertNull(engine.activeSquare);		

		// Player1
		CheckerBoardSquareMock start = new CheckerBoardSquareMock(context, engine.getData(5,0));
		engine.handleOnTouch(start);
				
		Assert.assertNotNull(engine.activeSquare);
		Assert.assertTrue(engine.activeSquare.equals(start.info));
		
		// Target for Player1
		CheckerBoardSquareMock target = new CheckerBoardSquareMock(context, engine.getData(4,1));
		engine.handleOnTouch(target);

		BoardSquareInfo square = engine.getData(start.info.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		
		square = engine.getData(target.info.id);
		Assert.assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);

		Assert.assertNull(engine.activeSquare);
		Assert.assertTrue(engine.isPlayer2());
	}

	public void test_movePlayer2SingleSquare() {
		test_movePlayer1SingleSquare();
		
		Assert.assertTrue(engine.isPlayer2());
		Assert.assertNull(engine.activeSquare);		

		// Player2
		CheckerBoardSquareMock start = new CheckerBoardSquareMock(context, engine.getData(2,7));
		engine.handleOnTouch(start);
				
		Assert.assertNotNull(engine.activeSquare);
		Assert.assertTrue(engine.activeSquare.equals(start.info));
		
		// Target for Player2
		CheckerBoardSquareMock target = new CheckerBoardSquareMock(context, engine.getData(3,6));
		engine.handleOnTouch(target);

		BoardSquareInfo square = engine.getData(start.info.id);
		Assert.assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		
		square = engine.getData(target.info.id);
		Assert.assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);

		Assert.assertNull(engine.activeSquare);
		Assert.assertTrue(engine.isPlayer1());
	}
	
	public void test_movePlayer1WithJumps() {		
	}
}
