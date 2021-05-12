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

}

