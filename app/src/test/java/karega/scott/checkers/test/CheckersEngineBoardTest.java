package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import org.junit.Test;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;

public class CheckersEngineBoardTest extends EngineBaseTest {
    @Test public void validateSelectionRow0() {
        engine.newGame(); // Player1 active
        Assert.assertEquals(CheckersEngine.PLAYER1_STATE, engine.isPlayer1());

		Assert.assertFalse(engine.validateSelection(0,0));
		Assert.assertTrue(engine.validateSelection(0,1));
		Assert.assertFalse(engine.validateSelection(0,2));
        Assert.assertTrue(engine.validateSelection(0,3));
		Assert.assertFalse(engine.validateSelection(0,4));
        Assert.assertTrue(engine.validateSelection(0,5));
		Assert.assertFalse(engine.validateSelection(0,6));
        Assert.assertTrue(engine.validateSelection(0,7));
    }

	@Test public void validateSelectionRow1() {
		engine.newGame(); // Player1 active		
        Assert.assertEquals(CheckersEngine.PLAYER1_STATE, engine.isPlayer1());

        Assert.assertTrue(engine.validateSelection(1,0));
        Assert.assertFalse(engine.validateSelection(1,1));
        Assert.assertTrue(engine.validateSelection(1,2));
        Assert.assertFalse(engine.validateSelection(1,3));
        Assert.assertTrue(engine.validateSelection(1,4));
        Assert.assertFalse(engine.validateSelection(1,5));
        Assert.assertTrue(engine.validateSelection(1,6));
        Assert.assertFalse(engine.validateSelection(1,7));
	}

	@Test public void validateSelectionRow2() {
		engine.newGame(); // Player1 active
        Assert.assertEquals(CheckersEngine.PLAYER1_STATE, engine.isPlayer1());

        Assert.assertFalse(engine.validateSelection(2,0));
        Assert.assertTrue(engine.validateSelection(2,1));
        Assert.assertFalse(engine.validateSelection(2,2));
        Assert.assertTrue(engine.validateSelection(2,3));
        Assert.assertFalse(engine.validateSelection(2,4));
        Assert.assertTrue(engine.validateSelection(2,5));
        Assert.assertFalse(engine.validateSelection(2,6));
        Assert.assertTrue(engine.validateSelection(2,7));
	}

	@Test public void validateSelectionRow3() {
		engine.newGame(); // Player1 active     
        Assert.assertEquals(CheckersEngine.PLAYER1_STATE, engine.isPlayer1());

        Assert.assertTrue(engine.validateSelection(3,0));
        Assert.assertFalse(engine.validateSelection(3,1));
        Assert.assertTrue(engine.validateSelection(3,2));
        Assert.assertFalse(engine.validateSelection(3,3));
        Assert.assertTrue(engine.validateSelection(3,4));
        Assert.assertFalse(engine.validateSelection(3,5));
        Assert.assertTrue(engine.validateSelection(3,6));
        Assert.assertFalse(engine.validateSelection(3,7));
	}

	@Test public void validateSelectionRow4() {
		engine.newGame(); // Player1 active		
        Assert.assertEquals(CheckersEngine.PLAYER1_STATE, engine.isPlayer1());

        Assert.assertFalse(engine.validateSelection(4,0));
        Assert.assertTrue(engine.validateSelection(4,1));
        Assert.assertFalse(engine.validateSelection(4,2));
        Assert.assertTrue(engine.validateSelection(4,3));
        Assert.assertFalse(engine.validateSelection(4,4));
        Assert.assertTrue(engine.validateSelection(4,5));
        Assert.assertFalse(engine.validateSelection(4,6));
        Assert.assertTrue(engine.validateSelection(4,7));
	}

	@Test public void validateSelectionRow5() {
        engine.newGame(); // Player1 active
		engine.switchPlayer(); // from Player1 to Player2
        Assert.assertEquals(CheckersEngine.PLAYER2_STATE, engine.isPlayer2());

        Assert.assertTrue(engine.validateSelection(5,0));
        Assert.assertFalse(engine.validateSelection(5,1));
        Assert.assertTrue(engine.validateSelection(5,2));
        Assert.assertFalse(engine.validateSelection(5,3));
        Assert.assertTrue(engine.validateSelection(5,4));
        Assert.assertFalse(engine.validateSelection(5,5));
        Assert.assertTrue(engine.validateSelection(5,6));
        Assert.assertFalse(engine.validateSelection(5,7));
    }

    @Test public void validateSelectionRow6() {
        engine.newGame(); // Player1 active
		engine.switchPlayer(); // from Player1 to Player2
        Assert.assertEquals(CheckersEngine.PLAYER2_STATE, engine.isPlayer2());

        Assert.assertFalse(engine.validateSelection(6,0));
        Assert.assertTrue(engine.validateSelection(6,1));
        Assert.assertFalse(engine.validateSelection(6,2));
        Assert.assertTrue(engine.validateSelection(6,3));
        Assert.assertFalse(engine.validateSelection(6,4));
        Assert.assertTrue(engine.validateSelection(6,5));
        Assert.assertFalse(engine.validateSelection(6,6));
        Assert.assertTrue(engine.validateSelection(6,7));
    }

    @Test public void validateSelectionRow7() {
        engine.newGame(); // Player1 active
		engine.switchPlayer(); // from Player1 to Player2
        Assert.assertEquals(CheckersEngine.PLAYER2_STATE, engine.isPlayer2());

        Assert.assertTrue(engine.validateSelection(7,0));
        Assert.assertFalse(engine.validateSelection(7,1));
        Assert.assertTrue(engine.validateSelection(7,2));
        Assert.assertFalse(engine.validateSelection(7,3));
        Assert.assertTrue(engine.validateSelection(7,4));
        Assert.assertFalse(engine.validateSelection(7,5));
        Assert.assertTrue(engine.validateSelection(7,6));
        Assert.assertFalse(engine.validateSelection(7,7));
    }

/*
    public void validateSelection() {
        engine.newGame(); // Player1 active
        Assert.assertEquals(CheckersEngine.PLAYER2_STATE, engine.activeState);

        final int multiplier = 8;
        for(int row=0; row<CheckersEngine.CHECKERS_ROWS; row++) {
            for(int col=0; col<CheckersEngine.CHECKERS_COLUMNS; col++) {
                boolean lockedState = [((multiplier*row)+col) % 2]; // CheckersEngine.LOCK_STATE true

                if(row%2 == 0 && col%2)
                    Assert.assertFalse(engine.validateSelection(row,col));
                else if(
                Assert.assertEquals(CheckersEngine.PLAYER2_STATE, engine.activeState);
                Assert.assertEquals(TRUE, engine.validateSelection(row,col));
            }
        }
    }
	*/


}
