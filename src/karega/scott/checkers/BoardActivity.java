package karega.scott.checkers;

import karega.scott.checkers.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
		
		Intent intent = this.getIntent();
		boolean vsComputer = intent.getBooleanExtra(BoardGameEngine.VS_COMPUTER, true);
		
		boardEngine = BoardGameEngine.instance(this.getBaseContext(), BoardGameEngine.CHECKERS_ENGINE, vsComputer);
		
		boardGame = (GridView) this.findViewById(R.id.boardGame);		
		boardGame.setAdapter(new BoardAdapter(boardGame.getContext(), boardEngine));		

		// TODO: Get width of parent to resize buttons or configure .xml file
		int width = boardGame.getWidth();
		
		exitGame = (Button) this.findViewById(R.id.exitGame);
		exitGame.setWidth(width/2);
		exitGame.setOnClickListener(new BoardActivity.InternalClickListener(this));
		
		newGame = (Button) this.findViewById(R.id.newGame);
		newGame.setWidth(width/2);
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
