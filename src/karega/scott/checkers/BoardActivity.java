package karega.scott.checkers;

import karega.scott.checkers.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

/*
 * The activity used to display the checker board.
 */
public class BoardActivity extends Activity {
	private BoardGameEngine boardEngine;
	
	// TODO: Do I really need these instance variables
	private Button exitGame;
	private Button newGame;
	private GridView boardGame;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		this.setTitle(R.string.app_name);
		
		boardEngine = BoardGameEngine.instance(BoardGameEngineType.CHECKERS);
		
		boardGame = (GridView) this.findViewById(R.id.boardGame);
		boardGame.setOnItemClickListener(new BoardActivity.InternalItemClickListener(boardEngine));
		boardGame.setAdapter(new BoardAdapter(boardGame.getContext(), 
				boardEngine));		
		
		exitGame = (Button) this.findViewById(R.id.exitGame);
		exitGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
		
		newGame = (Button) this.findViewById(R.id.newGame);
		newGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
	}	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_board, menu);
		return true;
	}
	
	/**
	 * Internal listener used when an item is clicked in the GridView
	 * @author Administrator
	 *
	 */
	public class InternalItemClickListener implements OnItemClickListener {
		private BoardGameEngine engine;
		
		public InternalItemClickListener(BoardGameEngine engine) {
			this.engine = engine;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.v("BoardActivity.InternalItemClickListener.onItemClick",
					((BoardSquare)view).getInformation().getCurrentPlayer().toString());
						
			BoardSquare square = (BoardSquare)view;
			if(engine.isPlayerSquareActive()) {
				engine.movePlayer(square);
			} // end if								
		} // end onItemClick
	} // end InternalItemClickListener
	
	/**
	 * Internal listener used when this activity child view is clicked
	 * @author Administrator
	 *
	 */
	public class InternalClickListener implements OnClickListener {
		private BoardActivity activity;
		
		public InternalClickListener(BoardActivity activity) {
			this.activity = activity;
		}
		
		@Override
		public void onClick(View view) {
			switch(view.getId())
			{
				case R.id.newGame:					
					boardEngine.newGame();
					break;
					
				case R.id.exitGame:
					activity.finish();
					break;
			} //end switch
		} // end onClick		
	} // end InternalClickListener
} // end BoardActivity
