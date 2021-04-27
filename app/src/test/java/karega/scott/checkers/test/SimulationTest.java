package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

public class SimulationTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/ false);
		engine.newGame();
		engine.setBoardSquaresEmpty();
	}

	@After public void after() {
	}

	@Test public void smallPlayer1() {
		Assert.assertTrue(engine.updateSquareState(19,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(28,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(35,CheckersEngine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(44,CheckersEngine.PLAYER1_STATE));

		Assert.assertTrue(engine.isPlayer1());

		Assert.assertTrue(engine.saveSelection(28));
		Assert.assertTrue(engine.saveSelection(19));
		Assert.assertTrue(engine.saveSelection(10));
		Assert.assertTrue(engine.updateGameBoard());

		Assert.assertTrue(engine.isPlayer2());

		Assert.assertEquals(engine.getData(28).state,CheckersEngine.EMPTY_STATE);
		Assert.assertEquals(engine.getData(19).state,CheckersEngine.EMPTY_STATE);
		Assert.assertEquals(engine.getData(10).state,CheckersEngine.PLAYER1_STATE);
		Assert.assertFalse(engine.getData(10).isKing);
	}

	@Test public void mediumPlayer1() {
	}

	@Test public void largePlayer1() {
	}
}
