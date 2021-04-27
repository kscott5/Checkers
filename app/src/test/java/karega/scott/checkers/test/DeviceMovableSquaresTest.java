package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import org.junit.Assert;

public class DeviceMovableSquaresTest {
	CheckersEngine engine;

	@Before public void before() {
		CheckersEngine engine = new CheckersEngine(/*vsDevice*/ true);
		engine.newGame();
		engine.switchPlayer();
	}

	@After public void after() {
	}

	@Test public void locateDeviceMoveSquareIds() {
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertEquals(engine.getDeviceSelectionSize(),-1);
	}
}
