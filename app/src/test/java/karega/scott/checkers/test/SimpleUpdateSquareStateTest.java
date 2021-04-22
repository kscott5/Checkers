package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class SimpleUpdateSquareStateTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
		engine.setBoardSquaresEmpty();
	}

	@After public void after() {
	}

	@Test public void updateBoardMiddleWithPlayer2() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.updateSquareState(3,0,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(4,1,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(3,2,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(4,3,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(3,4,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(4,5,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(3,6,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(4,7,CheckersEngine.PLAYER2_STATE));
	}

	@Test public void updateBoardMiddleWithPlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertTrue(engine.updateSquareState(3,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(4,1,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(3,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(4,3,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(3,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(4,5,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(3,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(4,7,CheckersEngine.PLAYER1_STATE));
	}

	@Test public void updateBoardTopWithPlayer2() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.updateSquareState(0,0,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(2,0,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(1,1,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(0,2,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(2,2,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(1,3,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(0,4,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(2,4,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(1,5,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(0,6,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(2,6,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(1,7,CheckersEngine.PLAYER2_STATE));
	}

	@Test public void updateBoardTopWithPlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertTrue(engine.updateSquareState(0,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(2,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(1,1,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(0,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(2,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(1,3,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(0,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(2,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(1,5,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(0,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(2,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(1,7,CheckersEngine.PLAYER1_STATE));
	}

	@Test public void updateBoardBottomWithPlayer2() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.updateSquareState(5,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,1,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,3,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,5,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,7,CheckersEngine.PLAYER1_STATE));
	}

	@Test public void updateBoardBottomWithPlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertTrue(engine.updateSquareState(5,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,0,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,1,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,2,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,3,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,4,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,5,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(5,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(7,6,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(6,7,CheckersEngine.PLAYER1_STATE));
	}
}

