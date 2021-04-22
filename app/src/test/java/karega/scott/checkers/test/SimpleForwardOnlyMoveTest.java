package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class SimpleForwardOnlyMoveTest {
	CheckersEngine engine;
	ArrayList<BoardSquareInfo> squares;

	@Before public void before() {
		engine = new CheckersEngine(/*vsDevice*/false);
		engine.newGame();
		engine.setBoardSquaresEmpty();

		squares = new ArrayList<BoardSquareInfo>();
	}

	@After public void after() {
	}

	@Test public void invalidMovePlayer2() {
		engine.switchPlayer();
		Assert.assertTrue(engine.isPlayer2());

		Assert.assertTrue(engine.updateSquareState(0,1,CheckersEngine.PLAYER2_STATE));
		squares.add(/*source*/ engine.getData(0,1));
		squares.add(/*target*/ engine.getData(1,1));

				
	}
}

