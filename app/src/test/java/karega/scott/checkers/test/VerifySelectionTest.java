package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

// equation: id = (row*8)+col
public class VerifySelectionTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
	}

	@After public void after() {
	}

	@Test public void simplePlayer1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.saveSelection(57));
		Assert.assertFalse(engine.saveSelection(58));
		Assert.assertFalse(engine.saveSelection(59));

		BoardSquareInfo square = engine.getData(44);
		Assert.assertNotNull(square);
		Assert.assertTrue(square.state == CheckersEngine.PLAYER1_STATE);
		Assert.assertTrue(engine.saveSelection(44));

		square = engine.getData(35);
		Assert.assertNotNull(square);
		Assert.assertTrue(square.state == CheckersEngine.EMPTY_STATE);
		Assert.assertTrue(engine.saveSelection(35));

		Assert.assertTrue(engine.verifySelectionList());
	}

	@Test public void simplePlayer2() {
		engine.switchPlayer();

		Assert.assertTrue(engine.isPlayer2());

		Assert.assertFalse(engine.saveSelection(0));
		Assert.assertFalse(engine.saveSelection(1));
		Assert.assertFalse(engine.saveSelection(2));

		BoardSquareInfo square = engine.getData(19);
		Assert.assertNotNull(square);
		Assert.assertTrue(square.state, CheckersEngine.PLAYER2_STATE);
		Assert.assertTrue(engine.saveSelection(19));

		square = engine.getData(28);
		Assert.assertNotNull(square);
		Assert.assertTrue(square.state, CheckersEngine.EMPTY_STATE);
		Assert.assertTrue(engine.saveSelection(28));

		Assert.assertTrue(engine.verifySelectionList());
	}
}

