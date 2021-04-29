package karega.scott.checkers.test;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;
import karega.scott.checkers.BoardSquareSiblings;

import java.lang.Integer;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;

public class BoardSquareInfoTest {    
	@Before public void before() {
	}
	
    @Test public void deactivate() {
        // setup
		final BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,CheckersEngine.EMPTY_STATE,CheckersEngine.SQUARE_CHIP_START_ANGLE);
        bsi.isActive = true;
        bsi.activeColor = bsi.activeColor;

        bsi.setOnChangeListener(new BoardSquareInfo.OnChangeListener() {
            @Override
            public void OnSquareInformationChange() {
                // assert
                Assert.assertFalse(bsi.isActive);        
		        Assert.assertEquals(bsi.activeColor, bsi.inactiveColor);
            }
        });

        bsi.deactivate();

		// assert
		Assert.assertFalse(bsi.isActive);        
		Assert.assertEquals(bsi.activeColor, bsi.inactiveColor);
	}
	
    @Test public void activate() {
		// setup
		final BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,CheckersEngine.EMPTY_STATE,CheckersEngine.SQUARE_CHIP_START_ANGLE);
        bsi.isActive = false;
        bsi.activeColor = 0xff000000;

        bsi.setOnChangeListener(new BoardSquareInfo.OnChangeListener() {
            @Override
            public void OnSquareInformationChange() {
                // assert
                Assert.assertTrue(bsi.isActive);        
		        Assert.assertEquals(bsi.activeColor, 0xffffffff);
            }
        });

        bsi.activate();

		// assert
		Assert.assertTrue(bsi.isActive);        
		Assert.assertEquals(bsi.activeColor, 0xffffffff);
	}

	@Test public void reset() {
	} 
	
}
