package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class VerifySelectionTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
	}

	@After public void after() {
	}

	@Test public void initialStartSquarePlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(7,1)));
		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(7,2)));
		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(7,3)));

		Assert.assertTrue(engine.verifyInitialSelection(engine.getData(5,4)));
	}

	@Test public void initialStartSquarePlayer2() {
		engine.switchPlayer();

		Assert.assertTrue(engine.isPlayer2());

		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(0,0)));
		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(0,1)));
		Assert.assertFalse(engine.verifyInitialSelection(engine.getData(0,3)));

		Assert.assertTrue(engine.verifyInitialSelection(engine.getData(2,3)));
	}
}

