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

		Assert.assertEquals(BoardSquareComparor.COMPAROR_MATRIX_TYPE, comparor.comparorType);
	}

	@Test public void ctrComparorMatrixType() {
		BoardSquareComparor comparor = new BoardSquareComparor(BoardSquareComparor.COMPAROR_MATRIX_TYPE);

		Assert.assertEquals(BoardSquareComparor.COMPAROR_MATRIX_TYPE, comparor.comparorType);
	}

	@Test public void ctrComparorStepType() {
		BoardSquareComparor comparor = new BoardSquareComparor(BoardSquareComparor.COMPAROR_STEP_TYPE);

		Assert.assertEquals(BoardSquareComparor.COMPAROR_STEP_TYPE, comparor.comparorType);
	}

	@Test public void ctrComparorRandomType() {
		BoardSquareComparor comparor = new BoardSquareComparor(543210);

		Assert.assertEquals(BoardSquareComparor.COMPAROR_MATRIX_TYPE, comparor.comparorType);
	}

    @Test public void compare() {
	}
	
    @Test public void equalsTrue() {
		BoardSquareInfo bsi0 = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
        BoardSquareInfo bsi1 = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);

		BoardSquareComparor comparor = new BoardSquareComparor();
		
		Assert.assertTrue(comparor.equals(bsi0,bsi1));
	}

	@Test public void equalsFalse() {
		BoardSquareComparor bsi0 = new BoardSquareInfo(0,0,BoardGameEngine.PLAYER1_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
		BoardSquareComparor bsi1 = new BoardSquareInfo(1,0,BoardGameEngine.PLAYER2_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);

		BoardSquareComparor comparor = new BoardSquareComparor();

		Assert.assertFalse(comparor.equals(bsi0,bsi1));
	}
}
