package karega.scott.checkers;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * Adapter used to provide data to the BoardActivity.boardGame
 */
public class BoardAdapter extends BaseAdapter {
	private static final String LOG_TAG = "BoardAdapter";
	
	private BoardGameEngine engine; 
	private Context context;
	private ArrayList squares;

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
		BoardSquare view = getViewFromConvertView(position, convertView, parent);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				Log.d(LOG_TAG,"On touch");
			
				switch(action) {
            		case MotionEvent.ACTION_DOWN:
                		break;
            		case MotionEvent.ACTION_MOVE:
                		break;
            		case MotionEvent.ACTION_UP:
            		case MotionEvent.ACTION_CANCEL:
                		break;
        		}

				BoardSquare square = (BoardSquare)view;
				return engine.handleOnTouch(square);
			}
			
		});
		
		return (View)view;
	}

	public BoardSquare getViewFromConvertView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof BoardSquare)) {
			BoardSquareInfo info = (BoardSquareInfo)this.engine.getData(position);

			return BoardSquare.instance(this.context, this.engine.getId(), parent, info);
		}
		
		return (BoardSquare) convertView;	
	}	
} //end GameBoardAdapter
