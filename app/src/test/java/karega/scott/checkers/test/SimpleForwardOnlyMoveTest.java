package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class SimpleForwardOnlyMoveTest {
	CheckersEngine engine;
	ArrayList<BoardSquareInfo> squares;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
		engine.setBoardSquaresEmpty();

		squares = new ArrayList<BoardSquareInfo>();
	}

	@After public void after() {
	}

	@Test public void updateSquareStatePlayer1() {
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

	@Test public void Player2Middle() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());
	}
}

