package karega.scott.checkers.test;

import karega.scott.checkers.CheckerEngine;

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

	@Test public void smallPlayer1Winner() {
	}

	@Test public void mediumPlayer1Winner() {
	}

	@Test public void largePlayer1Winner() {
	}
}
