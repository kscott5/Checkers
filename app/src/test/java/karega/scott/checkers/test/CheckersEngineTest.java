package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class CheckersEngineTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/ false);
	}

	@After public void after() {
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
	
	@Test public void emptyBoardPlayer2() {
		engine.setBoardSquaresEmpty();

		BoardSquareInfo square = engine.getData(0,1);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(0,3);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(0,5);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(0,7);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(1,0);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(1,2);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(1,4);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(1,6);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(2,1);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(2,3);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(2,5);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(2,7);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
	}

	@Test public void emptyBoardPlayer1() {
		engine.setBoardSquaresEmpty();

		BoardSquareInfo square = engine.getData(5,0);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(5,2);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(5,4);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(5,6);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(6,1);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(6,3);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(6,5);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(6,7);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(7,0);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(7,2);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
		
		square = engine.getData(7,4);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);

		square = engine.getData(7,6);
		Assert.assertEquals(engine.EMPTY_STATE, square.state);
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
		// TODO: newGame asserts what?
		engine.newGame();
	}
			
	@Test public void generateSquareId() {
		int id = engine.generateSquareId(12323,33412341);
		Assert.assertEquals(id,-1);

		id = engine.generateSquareId(0,0);
		Assert.assertEquals(id,0);

	 	id = engine.generateSquareId(4,6);
        Assert.assertEquals(id,38);

		id = engine.generateSquareId(6,4);
        Assert.assertEquals(id,52);

		id = engine.generateSquareId(7,7);
		Assert.assertEquals(id,63);
	}

	@Test public void exitGame() {
		// TODO: exitGame asserts what?
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
		Assert.assertEquals(0, square.id);
		Assert.assertEquals(CheckersEngine.LOCKED_STATE, square.state);
		Assert.assertEquals(CheckersEngine.EMPTY_CHIP, square.chip);
		Assert.assertFalse(square.isKing);
		Assert.assertEquals(0, square.row);
		Assert.assertEquals(0, square.column);
		
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
}
