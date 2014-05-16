package karega.scott.checkers;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/*
 * Adapter used to provide data to the BoardActivity.boardGame
 */
public class BoardAdapter extends BaseAdapter {
	private static final String LOG_TAG = "BoardAdapter";
	
	private BoardGameEngine engine; 
	private Context context;
	
	public BoardAdapter(Context context, BoardGameEngine engine) {
		this.context = context;
		this.engine = engine;
	}
	
	@Override
	public int getCount() {
		return this.engine.getSize();
	}

	@Override
	public Object getItem(int position) {
		return this.engine.getData(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		Log.d(LOG_TAG, String.format("Get view at position %s",position));
		
		BoardSquare view = getViewFromConvertView(position, convertView, parent);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(LOG_TAG,"On touch");
				
				// TODO: How do I prevent the on item click from firing too?
				BoardSquare square = (BoardSquare)v;
				engine.moveSquare(square.getInformation());
				return false;
			}
			
		});
		
		return (View)view;
	}

	public BoardSquare getViewFromConvertView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof BoardSquare)) {
			BoardSquareInfo info = (BoardSquareInfo)this.engine.getData(position);
			
			BoardSquare view = BoardSquare.instance(this.context, this.engine.getId(), info);
			view.setLayoutParams( new GridView.LayoutParams(BoardGameEngine.SQUARE_WIDTH, BoardGameEngine.SQUARE_HEIGHT));
			return view;
		}
		
		return (BoardSquare) convertView;	
	}	
} //end GameBoardAdapter
