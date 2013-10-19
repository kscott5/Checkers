package karega.scott.checkers;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/*
 * Adapter used to provide data to the BoardActivity.boardGame
 */
public class BoardAdapter extends BaseAdapter {
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
		Log.v("GameBoardAdapter.getView", String.format("view position %s",position));
		
		BoardSquare view = getViewFromConvertView(convertView, parent);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				engine.setPlayerSquare((BoardSquare)v);
				return false;
			}
			
		});
		
		BoardSquareInfo info = (BoardSquareInfo)this.engine.getData(position);
		view.setSquareInformation(info);
		view.setLayoutParams( new GridView.LayoutParams(BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT));
		
		return (View)view;
	}

	public BoardSquare getViewFromConvertView(View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof BoardSquare)) {
			return new BoardSquare(this.context);
		}
		
		return (BoardSquare) convertView;	
	}	
} //end GameBoardAdapter
