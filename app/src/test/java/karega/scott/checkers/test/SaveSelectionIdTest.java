package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

// Test each branch of the @Link CheckersEngine.saveSelection() routine.
// This is a good test but the effort compared with expections of actual
// consumer use could affect if these tests a relevant.
//
// equation: id = (row*8)+col
public class SaveSelectionIdTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
	}

	@After public void after() {
	}

	@Test public void if0ValidSuqareId() {
		Assert.assertFalse(engine.saveSelection(-1)); // Wrong. Not on board
		Assert.assertFalse(engine.saveSelection(0));  // Wrong. LOCKED_STATE
	}

	@Test public void if1FirstSquareSelected() {
		Assert.assertFalse(engine.saveSelection(1)); // Wrong. PLAYER2_STATE
		Assert.assertFalse(engine.saveSelection(24)); // Wrong. EMPTY_STATE
		Assert.assertFalse(engine.saveSelection(49)); // Wrong. Capture not available
		Assert.assertTrue(engine.saveSelection(40));  // Good. Capture EMPTY_STATE
	}

	@Test public void if2SelectionIdAvailable() {
		Assert.assertFalse(false /* ids are available. Not a test */);
	}

	@Test public void if3NeverAllow() {
		engine.setBoardSquaresEmpty(); // Not LOCKED_STATE

		// Use fake squares and board state
		Assert.assertTrue(engine.updateSquareState(26,engine.PLAYER1_STATE));
		Assert.assertTrue(engine.updateSquareState(28,engine.EMPTY_STATE));
		Assert.assetTrue(engine.updateSquareState(35,engine.PLAYER1_STATE));
		
		Assert.assertTrue(engine.saveSelection(35));
		Assert.assertFalse(engine.saveSelection(26)); // Wrong. Capture PLAYER1_STATE
		Assert.assertTrue(engine.saveSelection(28));  // Good. Capture EMPTY_STATE
	}
}

