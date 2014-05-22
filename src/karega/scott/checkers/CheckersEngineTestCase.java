package karega.scott.checkers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.test.mock.MockContext;

/**
 * Checkers Engine Test Case
 *
 * Configuration of JUnit 
 * http://stackoverflow.com/questions/2172152/cant-run-junit-4-test-case-in-eclipse-android-project
 * 
 * @author admin
 *
 */
@RunWith(JUnit4.class)
public class CheckersEngineTestCase  {
	
	private MockContext context = new MockContext();
	private CheckersEngine engine;
	
	/**
	 * Mock for Checker Board Square (android.view.View)
	 * @author admin
	 *
	 */
	public class CheckerBoardSquareMock extends CheckerBoardSquare {
		public CheckerBoardSquareMock(MockContext context, BoardSquareInfo info) {
			super(context, info);
		}
		
		public void invalidate() {}
	} // end Mock CheckerBoardSquare

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
	
	@Before
	public void setUp() {
		engine = new CheckersEngine(context, /*vsDevice*/ false);
		engine.newGame();
	}
	
	@Test
	public void getId() {		
		assertEquals(BoardGameEngine.CHECKERS_ENGINE, engine.getId());
	}
	
	@Test
	public void isPlayer1(){
		assertEquals(true, engine.isPlayer1());
	}
	
	@Test
	public void isPlayer2(){
		assertEquals(false, engine.isPlayer2());
	}
	
	@Test
	public void isDevice(){
		assertEquals(false, engine.isDevice());
	}
	
	@Test
	public void switchPlayers() {
		assertEquals(true, engine.isPlayer1());
		assertEquals(false, engine.isDevice());
		assertEquals(false, engine.isPlayer2());
		
		engine.switchPlayer();
		
		assertEquals(false, engine.isPlayer1());
		assertEquals(false, engine.isDevice());
		assertEquals(true, engine.isPlayer2());
		
		engine.switchPlayer();
		assertEquals(true, engine.isPlayer1());
		assertEquals(false, engine.isDevice());
		assertEquals(false, engine.isPlayer2());
	} // end testSwitchPlayers
	
	@Test
	public void getSize() {
		assertEquals(64,engine.getSize());
	}
	
	@Test
	public void newGame() {	
		int player1=0,player2=0,empty=0,locked=0;
	
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == BoardGameEngine.LOCKED_STATE) locked++;
			if(square.state == BoardGameEngine.PLAYER2_STATE) player2++;
			if(square.state == BoardGameEngine.EMPTY_STATE) empty++;
			if(square.state == BoardGameEngine.PLAYER1_STATE) player1++;
			
			square.makeEmpty();
		}
		
		assertEquals(32, locked);
		assertEquals(12, player2);
		assertEquals(8, empty);
		assertEquals(12, player1);
		
		engine.newGame();		
		
		player1=player2=empty=locked=0;
		
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == BoardGameEngine.LOCKED_STATE) locked++;
			if(square.state == BoardGameEngine.PLAYER2_STATE) player2++;
			if(square.state == BoardGameEngine.EMPTY_STATE) empty++;
			if(square.state == BoardGameEngine.PLAYER1_STATE) player1++;			
		}
		
		assertEquals(32, locked);
		assertEquals(12, player2);
		assertEquals(8, empty);
		assertEquals(12, player1);
	}
	
	@Test
	public void saveGame() throws Exception {
		engine.saveGame();
	}

	@Test
	public void exitGame() {
		// TODO: What are we testing
		engine.exitGame();
	}
	
	@Test
	public void getDataById() {
		BoardSquareInfo square = engine.getData(0);
		assertEquals(0, square.id);
		assertEquals(BoardGameEngine.LOCKED_STATE, square.state);
		assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(0, square.row);
		assertEquals(0, square.column);
		
		square = engine.getData(24);
		assertEquals(24, square.id);
		assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(2, square.row);
		assertEquals(7, square.column);
		
		square = engine.getData(34);
		assertEquals(0, square.id);
		assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(4, square.row);
		assertEquals(1, square.column);
		
		square = engine.getData(63);
		assertEquals(63, square.id);
		assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(7, square.row);
		assertEquals(7, square.column);
		
		square = engine.getData(-1);
		assertNull(square);
		
		square = engine.getData(64);
		assertNull(square);
	}
	
	@Test
	public void getDataByRowColumn() {
		BoardSquareInfo square = engine.getData(0,0);
		assertNull(square);
		
		square = engine.getData(2,7);
		assertEquals(24, square.id);
		assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(2, square.row);
		assertEquals(7, square.column);
		
		square = engine.getData(4,1);
		assertEquals(34, square.id);
		assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(4, square.row);
		assertEquals(1, square.column);
		
		engine.getData(7,7);
		assertEquals(63, square.id);
		assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(7, square.row);
		assertEquals(7, square.column);
		
		square = engine.getData(8,8);
		assertNull(square);
		
		square = engine.getData(-1,-1);
		assertNull(square);
	}
	
	@Test
	public void isEmpty() {
		BoardSquareInfo square = engine.getData(0,0);
		assertEquals(0, square.id);
		assertEquals(BoardGameEngine.LOCKED_STATE, square.state);
		assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(0, square.row);
		assertEquals(0, square.column);

		assertEquals(false, engine.isEmpty(0, 0));
		
		square = engine.getData(2,7);
		assertEquals(24, square.id);
		assertEquals(BoardGameEngine.PLAYER2_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(2, square.row);
		assertEquals(7, square.column);
		
		assertEquals(false, engine.isEmpty(2, 7));
		
		square = engine.getData(4,1);
		assertEquals(34, square.id);
		assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		assertEquals(BoardGameEngine.EMPTY_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(4, square.row);
		assertEquals(1, square.column);
		
		assertEquals(true, engine.isEmpty(4, 1));
		
		engine.getData(7,7);
		assertEquals(63, square.id);
		assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);
		assertEquals(BoardGameEngine.PAWN_CHIP, square.chip);
		assertEquals(false, square.isKing);
		assertEquals(7, square.row);
		assertEquals(7, square.column);
		
		assertEquals(false, engine.isEmpty(7, 7));
	}
	
	@Test
	public void activeSquare() {
		assertNull(engine.activeSquare);
		
		// Locked
		CheckerBoardSquareMock square = new CheckerBoardSquareMock(context, engine.getData(0,0));
		engine.handleOnTouch(square);
		
		assertNull(engine.activeSquare);
		
		// Player2
		square = new CheckerBoardSquareMock(context, engine.getData(2,7));
		engine.handleOnTouch(square);
				
		assertNull(engine.activeSquare);
				
		// Empty
		square = new CheckerBoardSquareMock(context, engine.getData(2,7));
		engine.handleOnTouch(square);
				
		assertNull(engine.activeSquare);		

		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(7,7));
		engine.handleOnTouch(square);
				
		assertNull(engine.activeSquare);		

		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(5,0));
		engine.handleOnTouch(square);
				
		assertNotNull(engine.activeSquare);
	}
	
	@Test
	public void movePlayer1SingleSquare() {
		assertEquals(true, engine.isPlayer1());
		assertNull(engine.activeSquare);		

		// Player1
		CheckerBoardSquareMock start = new CheckerBoardSquareMock(context, engine.getData(5,0));
		engine.handleOnTouch(start);
				
		assertNotNull(engine.activeSquare);
		assertEquals(start.info, engine.activeSquare);
		
		// Target for Player1
		CheckerBoardSquareMock target = new CheckerBoardSquareMock(context, engine.getData(4,1));
		engine.handleOnTouch(target);

		BoardSquareInfo square = engine.getData(start.info.id);
		assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		
		square = engine.getData(target.info.id);
		assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);

		assertNull(engine.activeSquare);
		assertEquals(BoardGameEngine.PLAYER2_STATE, engine.isPlayer2());
	}

	@Test
	public void movePlayer2SingleSquare() {
		movePlayer1SingleSquare();
		
		assertEquals(true, engine.isPlayer2());
		assertNull(engine.activeSquare);		

		// Player2
		CheckerBoardSquareMock start = new CheckerBoardSquareMock(context, engine.getData(2,7));
		engine.handleOnTouch(start);
				
		assertNotNull(engine.activeSquare);
		assertEquals(start.info, engine.activeSquare);
		
		// Target for Player2
		CheckerBoardSquareMock target = new CheckerBoardSquareMock(context, engine.getData(3,6));
		engine.handleOnTouch(target);

		BoardSquareInfo square = engine.getData(start.info.id);
		assertEquals(BoardGameEngine.EMPTY_STATE, square.state);
		
		square = engine.getData(target.info.id);
		assertEquals(BoardGameEngine.PLAYER1_STATE, square.state);

		assertNull(engine.activeSquare);
		assertEquals(BoardGameEngine.PLAYER2_STATE, engine.isPlayer1());
	}
	
	@Test
	public void movePlayer1WithJumps() {
		
	}
}
