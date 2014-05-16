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
	private static final String LOG_TAG = "BoardActivity";
	
	private BoardGameEngine boardEngine;
	
	// TODO: Do I really need these instance variables
	private Button exitGame;
	private Button newGame;
	private GridView boardGame;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "On create called");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		this.setTitle(R.string.app_name);
		
		
		boardEngine = BoardGameEngine.instance(this.getBaseContext(), BoardGameEngine.CHECKERS_ENGINE);
		
		boardGame = (GridView) this.findViewById(R.id.boardGame);		
		
		// TODO: Do I need this if BoardAdapter BoardSquare listening to on touch
		//boardGame.setOnItemClickListener(new BoardActivity.InternalItemClickListener(boardEngine));
		boardGame.setAdapter(new BoardAdapter(boardGame.getContext(), 
				boardEngine));		
		
		exitGame = (Button) this.findViewById(R.id.exitGame);
		exitGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
		
		newGame = (Button) this.findViewById(R.id.newGame);
		newGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
	}	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(LOG_TAG, "On create options menu called");
		
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
		private static final String LOG_TAG = "InternalItemClickListener";
		
		private BoardGameEngine engine;
		
		public InternalItemClickListener(BoardGameEngine engine) {
			this.engine = engine;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d(LOG_TAG,"On item clicked");
			
			// TODO: Determine why this method fires after onTouch		
			
			//BoardSquare square = (BoardSquare)view;
			//engine.moveSquare(square.getInformation());			
		} // end onItemClick
	} // end InternalItemClickListener
	
	/**
	 * Internal listener used when this activity child view is clicked
	 * @author Administrator
	 *
	 */
	public class InternalClickListener implements OnClickListener {
		private static final String LOG_TAG = "InternalClickListener";
		
		private BoardActivity activity;
		
		public InternalClickListener(BoardActivity activity) {
			this.activity = activity;
		}
		
		@Override
		public void onClick(View view) {
			Log.d(LOG_TAG, "On click");
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
