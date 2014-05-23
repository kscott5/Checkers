package karega.scott.checkers;

import android.content.Context;
import android.test.AndroidTestCase;

public abstract class CheckersEngineBaseTest extends AndroidTestCase {
	// Class variables allow extended subclasses to build complex
	// test case using the same engine and in some case existing
	// test methods
	protected static Context context;
	protected static CheckersEngine engine;
		
	public class CheckerBoardSquareMock extends CheckerBoardSquare {
		public CheckerBoardSquareMock(Context context, BoardSquareInfo info) {
			super(context, info);
		}
		
		// Prevents drawing of view
		@Override
		public void invalidate() {}
	}

	/**
	 * Prepares the board for simulated game play.
	 * This method will set activeSquare to null,
	 * activeState to EMPTY_STATE (no player) and
	 * set all squares to EMPTY_STATE.
	 */
	public void clearGameBoard() {
		engine.activeSquare = null;
		engine.activeState = BoardGameEngine.EMPTY_STATE;
		
		for(int row=0;row<8;row++) {
			for(int col=0;col<8;col++) {
				BoardSquareInfo square=engine.getData(row,col);
				if(square == null) continue;
				square.makeEmpty();
			}
		}
	} // end resetGameBoard
	
	/**
	 * Asserts the total square count for each Board Game Square State
	 * @param forState
	 */
	public int countSquares(int forState) {
		int count=0;
		
		for(int id=0; id<64; id++) {
			BoardSquareInfo square = engine.getData(id);
			if(square.state == forState) count++;
		}
		
		return count;
	} // end countSquares
	
	@Override
	public void setUp() {
		context = super.getContext();
		engine = new CheckersEngine(context, /*vsDevice*/ false);
		
		engine.newGame();
	}
	
	@Override
	public void tearDown() {
		engine.exitGame();
	}
} // end CheckersEngineBaseTestCase
