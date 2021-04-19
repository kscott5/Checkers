package karega.scott.checkers.util.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.util.BoardSquareComparor;

import java.lang.Integer;
import org.junit.Test;
import org.junit.Assert;

public class BoardSquareComparorTest {    
	@Test public void ctrDefault() {
		BoardSquareComparor comparor = new BoardSquareComparor();

		Assert.assertEquals(BoardSquareComparor.COMPAROR_MATRIX_TYPE, comparor.compororType);
	}

	@Test public void ctrComparorMatrixType() {
	}

	@Test public void ctrComparorStepType() {
	}

	@Test public void ctrComparorRandomType() {
	}

    @Test public void compare() {
	}
	
    @Test public void equalsTrue() {
		final BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);

		BoardSquareComparor compare = new BoardSquareComparor(bsi);
		
		Assert.assertTrue(compare.equals(bsi));
	}

	@Test public void equalsFalse() {
		final BoardSquareComparor bsi0 = new BoardSquareInfo(0,0,BoardGameEngine.PLAYER1_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
		final BoardSquareComparor bsi1 = new BoardSquareInfo(1,0,BoardGameEngine.PLAYER2_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);

		BoardSquareComparor compare = new BoardSquareComparor(bsi1);

		Assert.assertFalse(compare.equals(bsi0));
	}
}
