package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

// equation: id = (row*8)
public class VerifySelectionTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
	}

	@After public void after() {
	}

	@Test public void verifySelectionPlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.saveSelection(57));
		Assert.assertFalse(engine.saveSelection(58));
		Assert.assertFalse(engine.saveSelection(59));

		Assert.assertTrue(engine.saveSelection(44));
	}

	@Test public void verifySelectionPlayer2() {
		engine.switchPlayer();

		Assert.assertTrue(engine.isPlayer2());

		Assert.assertFalse(engine.saveSelection(0));
		Assert.assertFalse(engine.saveSelection(1));
		Assert.assertFalse(engine.saveSelection(2));

		Assert.assertTrue(engine.saveSelection(19));
	}
}

