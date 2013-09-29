package karega.scott.checkers;

import karega.scott.checkers.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
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
	// TODO: Do I really need these instance variables
	private Button exitGame;
	private Button newGame;
	private GridView boardGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board);
		this.setTitle(R.string.app_name);
		
		boardGame = (GridView) this.findViewById(R.id.boardGame);
		boardGame.setOnItemClickListener(new BoardActivity.InternalItemClickListener());
		boardGame.setAdapter(new BoardAdapter(boardGame.getContext(), 
				BoardAdapter.createNewGameData()));
		
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

	/*
	 * 
	 */
	public class InternalItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.v("BoardActivity.InternalItemClickListener.onItemClick",((BoardSquare)view).getStateType().toString());
			
			BoardSquare square = (BoardSquare)view;	
			switch(square.getStateType()) {
				case PLAYER1:
					square.setIsDirty(true);
					square.setBorderColor(Color.GREEN);
					break;
				case PLAYER2:
					square.setIsDirty(true);
					square.setBorderColor(Color.GREEN);
					break;				
					
			}
		}
	
	} // end InternalItemClickListener
	
	/**
	 * 
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
					boardGame.invalidate();
					break;
					
				case R.id.exitGame:
					activity.finish();
					break;
			} //end switch
		} // end onClick		
	} // end InternalClickListener
} // end BoardActivity
