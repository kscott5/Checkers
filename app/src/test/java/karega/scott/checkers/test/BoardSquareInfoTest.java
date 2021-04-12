package karega.scott.checkers.test;

import karega.scott.checkers.BoardGameEngine;
import karega.scott.checkers.BoardSquareInfo;

import java.lang.Integer;
import org.junit.Test;
import org.junit.Assert;

public class BoardSquareInfoTest {    
    @Test public void deactivate() {
        // setup
		BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
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
		BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
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
	
	@Test public void swap() {
	}

	@Test public void makeEmpty() {
		BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,BoardGameEngine.EMPTY_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
		bsi.chip = Integer.MAX_VALUE;
		bsi.state = Integer.MAX_VALUE;
		bsi.fillColor = Integer.MAX_VALUE;
		bsi.borderColor = Integer.MAX_VALUE;
		bsi.inactiveColor = Integer.MAX_VALUE;
		bsi.activeColor = Integer.MAX_VALUE;

		bsi.isKing = true;
		bsi.isActive = true;
				
		bsi.makeEmpty();

		Assert.assertNotEquals(bsi.initialState,BoardGameEngine.LOCKED_STATE);
		
		Assert.assertEquals(bsi.chip, BoardGameEngine.EMPTY_CHIP);
		Assert.assertEquals(bsi.state, BoardGameEngine.EMPTY_STATE);
		Assert.assertEquals(bsi.fillColor, 0xff444444);
		Assert.assertEquals(bsi.borderColor, 0xff000000);
		Assert.assertEquals(bsi.inactiveColor, 0x00000000);
		Assert.assertEquals(bsi.activeColor, 0x00000000);
		Assert.assertFalse(bsi.isKing);
		Assert.assertFalse(bsi.isActive);
	}

	@Test public void makeEmptyIgnored() {
		BoardSquareInfo bsi = new BoardSquareInfo(0,0,0,BoardGameEngine.LOCKED_STATE,BoardGameEngine.SQUARE_CHIP_START_ANGLE);
		bsi.chip = Integer.MAX_VALUE;
		bsi.state = Integer.MAX_VALUE;
		bsi.fillColor = Integer.MAX_VALUE;
		bsi.borderColor = Integer.MAX_VALUE;
		bsi.inactiveColor = Integer.MAX_VALUE;
		bsi.activeColor = Integer.MAX_VALUE;

		bsi.isKing = true;
		bsi.isActive = true;
				
		bsi.makeEmpty();

		Assert.assertEquals(bsi.initialState,BoardGameEngine.LOCKED_STATE);
		
		Assert.assertEquals(bsi.chip, Integer.MAX_VALUE);
		Assert.assertEquals(bsi.state, Integer.MAX_VALUE);
		Assert.assertEquals(bsi.fillColor, Integer.MAX_VALUE);
		Assert.assertEquals(bsi.borderColor, Integer.MAX_VALUE);
		Assert.assertEquals(bsi.inactiveColor, Integer.MAX_VALUE);
		Assert.assertEquals(bsi.activeColor, Integer.MAX_VALUE);
		Assert.assertTrue(bsi.isKing);
		Assert.assertTrue(bsi.isActive);
	}
}