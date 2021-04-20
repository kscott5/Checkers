package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class CheckersEngineBoardTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine( /*vsDevice*/ false);
		engine.newGame();
	}

	@After public void after() {
	}
	
	@Test public void validateSelectionPlayer1Row0() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.validateSelection(0,0));
		Assert.assertFalse(engine.validateSelection(0,1));
		Assert.assertFalse(engine.validateSelection(0,2));
		Assert.assertFalse(engine.validateSelection(0,3));
		Assert.assertFalse(engine.validateSelection(0,4));
		Assert.assertFalse(engine.validateSelection(0,5));
		Assert.assertFalse(engine.validateSelection(0,6));
		Assert.assertFalse(engine.validateSelection(0,7));
	}

	@Test public void validateSelectionPlayer2Row0() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertFalse(engine.validateSelection(0,0));
		Assert.assertTrue(engine.validateSelection(0,1));
		Assert.assertFalse(engine.validateSelection(0,2));
		Assert.assertTrue(engine.validateSelection(0,3));
		Assert.assertFalse(engine.validateSelection(0,4));
		Assert.assertTrue(engine.validateSelection(0,5));
		Assert.assertFalse(engine.validateSelection(0,6));
		Assert.assertTrue(engine.validateSelection(0,7));
	}

	@Test public void validateSelectionPlayer1Row1() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.validateSelection(1,0));
		Assert.assertFalse(engine.validateSelection(1,1));
		Assert.assertFalse(engine.validateSelection(1,2));
		Assert.assertFalse(engine.validateSelection(1,3));
		Assert.assertFalse(engine.validateSelection(1,4));
		Assert.assertFalse(engine.validateSelection(1,5));
		Assert.assertFalse(engine.validateSelection(1,6));
		Assert.assertFalse(engine.validateSelection(1,7));
	}

	@Test public void validateSelectionPlayer2Row1() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.validateSelection(1,0));
		Assert.assertFalse(engine.validateSelection(1,1));
		Assert.assertTrue(engine.validateSelection(1,2));
		Assert.assertFalse(engine.validateSelection(1,3));
		Assert.assertTrue(engine.validateSelection(1,4));
		Assert.assertFalse(engine.validateSelection(1,5));
		Assert.assertTrue(engine.validateSelection(1,6));
		Assert.assertFalse(engine.validateSelection(1,7));
	}

	@Test public void validateSelectionPlayer1Row2() {
		Assert.assertTrue(engine.isPlayer1());

		Assert.assertFalse(engine.validateSelection(2,0));
		Assert.assertFalse(engine.validateSelection(2,1));
		Assert.assertFalse(engine.validateSelection(2,2));
		Assert.assertFalse(engine.validateSelection(2,3));
		Assert.assertFalse(engine.validateSelection(2,4));
		Assert.assertFalse(engine.validateSelection(2,5));
		Assert.assertFalse(engine.validateSelection(2,6));
		Assert.assertFalse(engine.validateSelection(2,7));
	}

	@Test public void validateSelectionPlayer2Row2() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertFalse(engine.validateSelection(2,0));
		Assert.assertTrue(engine.validateSelection(2,1));
		Assert.assertFalse(engine.validateSelection(2,2));
		Assert.assertTrue(engine.validateSelection(2,3));
		Assert.assertFalse(engine.validateSelection(2,4));
		Assert.assertTrue(engine.validateSelection(2,5));
		Assert.assertFalse(engine.validateSelection(2,6));
		Assert.assertTrue(engine.validateSelection(2,7));
	}

}
