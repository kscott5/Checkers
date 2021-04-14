package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;;

import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before
import org.junit.After;

public class CheckersEngineTest {
	CheckersEngine engine;

	@Before public void beforeTest() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
	}
	@After public void afterTest() {
		engine.exitGame();
	}
	
	@Test public void getId() {		
		Assert.assertEquals(CheckersEngine.CHECKERS_ENGINE, engine.getId());
	}
	
	@Test public void isPlayer1(){
		Assert.assertTrue(engine.isPlayer1());
	}
	
	@Test public void isPlayer2(){
		Assert.assertFalse(engine.isPlayer2());
	}
	
	@Test public void isDevice(){
		Assert.assertFalse(engine.isDevice());
	}
	
	@Test public void switchPlayers() {
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
	
	@Test public void getSize() {
		Assert.assertEquals(64,engine.getSize());
	}
	
	@Test public void newGame() {		
		Assert.assertEquals(32, countSquares(CheckersEngine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(CheckersEngine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(CheckersEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(CheckersEngine.PLAYER1_STATE));
		
		clearGameBoard();
		
		Assert.assertEquals(32, countSquares(CheckersEngine.LOCKED_STATE));
		Assert.assertEquals(0, countSquares(CheckersEngine.PLAYER2_STATE));
		Assert.assertEquals(32, countSquares(CheckersEngine.EMPTY_STATE));
		Assert.assertEquals(0, countSquares(CheckersEngine.PLAYER1_STATE));
		
		engine.newGame();		
		
		Assert.assertEquals(32, countSquares(CheckersEngine.LOCKED_STATE));
		Assert.assertEquals(12, countSquares(CheckersEngine.PLAYER2_STATE));
		Assert.assertEquals(8, countSquares(CheckersEngine.EMPTY_STATE));
		Assert.assertEquals(12, countSquares(CheckersEngine.PLAYER1_STATE));
	}
			
	@Test public void exitGame() {
		// TODO: What are we testing
		engine.exitGame();
	}
		
	@Test public void getDataById() {
		BoardSquareInfo square = engine.getData(0);
		Assert.assertEquals(0, square.id);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(0, square.row);
		Assert.assertEquals(0, square.column);
		
		square = engine.getData(17);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(33);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(62);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		square = engine.getData(-1);
		Assert.assertNull(square);
		
		square = engine.getData(64);
		Assert.assertNull(square);
	}
		
	@Test public void getDataByRowColumn() {
		BoardSquareInfo square = engine.getData(0,0);
		Assert.assertNull(square);
		
		square = engine.getData(2,1);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(4,1);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		square = engine.getData(7,6);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		square = engine.getData(8,8);
		Assert.assertNull(square);
		
		square = engine.getData(-1,-1);
		Assert.assertNull(square);
	}
	
	@Test public void isEmpty() {
		BoardSquareInfo square = engine.getData(0);
		Assert.assertEquals(0, square.id);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(0, square.row);
		Assert.assertEquals(0, square.column);

		Assert.assertFalse(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(17);
		Assert.assertEquals(17, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(2, square.row);
		Assert.assertEquals(1, square.column);
		
		Assert.assertFalse(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(33);
		Assert.assertEquals(33, square.id);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(4, square.row);
		Assert.assertEquals(1, square.column);
		
		Assert.assertTrue(engine.isEmpty(square.row, square.column));
		
		square = engine.getData(62);
		Assert.assertEquals(62, square.id);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.state);
		Assert.assertEquals(CheckersEngine.PAWN_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(7, square.row);
		Assert.assertEquals(6, square.column);
		
		Assert.assertFalse(engine.isEmpty(square.row, square.column));
	}
	
	@Test public void handleOnTouch() {	
		// Locked
		CheckerBoardSquareMock square = new CheckerBoardSquareMock(context, engine.getData(0));
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, square.getInformation().state);
		
		boolean handled = engine.handleOnTouch(square);
		Assert.assertTrue(handled);
		
		BoardSquareInfo actual = engine.getData(0);
		Assert.assertFalse(actual.isActive);
		
		// Player2
		square = new CheckerBoardSquareMock(context, engine.getData(23));
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, square.getInformation().state);
		
		handled = engine.handleOnTouch(square);
		Assert.assertTrue(handled);

		actual = engine.getData(23);
		Assert.assertFalse(actual.isActive);
				
		// Empty
		square = new CheckerBoardSquareMock(context, engine.getData(24));
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, square.getInformation().state);
		
		handled = engine.handleOnTouch(square);
		Assert.assertTrue(handled);

		actual = engine.getData(24);
		Assert.assertFalse(actual.isActive);

		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(62));
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.getInformation().state);
		
		handled = engine.handleOnTouch(square);
		Assert.assertTrue(handled);

		actual = engine.getData(62);
		Assert.assertFalse(actual.isActive);
		
		// Player1
		square = new CheckerBoardSquareMock(context, engine.getData(40));
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.getInformation().state);
		
		handled = engine.handleOnTouch(square);
		Assert.assertTrue(handled);

		actual = engine.getData(40);
		Assert.assertTrue(actual.isActive);
	}
	
	@Test public void moveSquare() {		
		// Locked
		BoardSquareInfo square = engine.getData(0);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, square.state);
		engine.moveSquare(square);
		
		BoardSquareInfo actual = engine.getData(0);
		Assert.assertFalse(actual.isActive);
		
		// Player2
		square = engine.getData(23);
		Assert.assertEquals(CheckersEngine.PLAYER2_STATE, square.state);
		engine.moveSquare(square);
				
		actual = engine.getData(23);
		Assert.assertFalse(actual.isActive);
				
		// Empty
		square = engine.getData(24);
		Assert.assertEquals(CheckersEngine.EMPTY_STATE, square.state);
		engine.moveSquare(square);
				
		actual = engine.getData(24);
		Assert.assertFalse(actual.isActive);		

		// Player1
		square = engine.getData(62);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.state);
		engine.moveSquare(square);
			
		actual = engine.getData(62);
		Assert.assertFalse(actual.isActive);		
		
		// Player1
		square = engine.getData(40);
		Assert.assertEquals(CheckersEngine.PLAYER1_STATE, square.state);
		engine.moveSquare(square);
				
		actual = engine.getData(40);
		Assert.assertTrue(actual.isActive);
	}	

} // end CheckersEngineTestCase
