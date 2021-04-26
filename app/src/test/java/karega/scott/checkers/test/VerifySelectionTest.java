package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

// 
// NOTE: This engine expects consecutive selections of squares in a row. 
// Its not a touch rather a swipe through a serious of individual squares.
//
// equation: id = (row*8)+col
public class VerifySelectionTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
	}

	@After public void after() {
	}

	@Test public void simplePlayer1Valid() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertTrue(engine.saveSelection(46));
		Assert.assertTrue(engine.saveSelection(39));
		Assert.assertTrue(engine.updateGameBoard());

		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void simplePlayer1NotValid() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.saveSelection(57));
		Assert.assertTrue(engine.saveSelection(58)); // Why? second selection of a square not available yet.
		Assert.assertFalse(engine.saveSelection(59));
	}

	@Test public void simplePlayer2Valid() {
		engine.switchPlayer();

		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.saveSelection(17));
		Assert.assertTrue(engine.saveSelection(26));
		Assert.assertTrue(engine.updateGameBoard());

		Assert.assertTrue(engine.isPlayer1());
	}

	@Test public void simplePlayer2NotValid() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.saveSelection(1));
		Assert.assertFalse(engine.saveSelection(8));
	}
}

