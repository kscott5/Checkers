package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import org.junit.Assert;

public class DeviceMovableSquaresTest {
	CheckersEngine engine;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/ true);
		engine.newGame();
		engine.switchPlayer();
	}

	@After public void after() {
	}

	@Test public void isPlayer2() {
		Assert.assertTrue(engine.isPlayer2());
	}

	@Test public void isDevice() {
		Assert.assertTrue(engine.isDevice());
	}

	@Test public void getDeviceSelectionSize() {
		Assert.assertEquals(engine.getDeviceSelectionSize(), -1);
	}

	@Test public void locateDeviceMoveSquareIds() {
	}

	@Test public void simpleDummyBoard() {
		engine.setBoardSquaresEmpty();

		Assert.assertTrue(engine.updateSquareState(12,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(19,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(26,CheckersEngine.PLAYER2_STATE));
		Assert.assertTrue(engine.updateSquareState(28,CheckersEngine.PLAYER2_STATE));
	}
}
