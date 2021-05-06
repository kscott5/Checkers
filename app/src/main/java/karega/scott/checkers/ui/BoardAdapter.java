package karega.scott.checkers.ui;

import karega.scott.checkers.CheckersEngine;
import karega.scott.checkers.BoardSquareInfo;

import android.util.Log;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * Adapter used to provide data to the BoardActivity.boardGame
 */
public class BoardAdapter extends BaseAdapter {
	public static final String LOG_TAG = "BoardAdapter";

	private CheckersEngine engine; 
	private Context context;

	public BoardAdapter(Context context, CheckersEngine engine) {
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
		CheckerBoardSquare view = getViewFromConvertView(position, convertView, parent);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				CheckerBoardSquare square = (CheckerBoardSquare)view;

		int id = event.getPointerId(0);
		int action = event.getActionMasked();
		int index = event.getActionIndex();
		int size = event.getHistorySize();
		float x = event.getX(id), y = event.getY(id);

		Log.d(LOG_TAG, "on Touch:          Id->" + id);
		Log.d(LOG_TAG, "on Touch:       Index->" + index);
		Log.d(LOG_TAG, "on Touch: HistorySize->" + size);
		Log.d(LOG_TAG, "on Touch:           X->" + x);
		Log.d(LOG_TAG, "on Touch:           Y->" + y);
	
		
				switch(event.getActionMasked()) {
            		case MotionEvent.ACTION_DOWN:
            		case MotionEvent.ACTION_MOVE:
						Log.d(LOG_TAG, "Down/Move Square.Id->" + square.info.id);
						 BoardAdapter.this.engine.updateGameBoard(square.info.id, /*hasMore continue*/ true);
						 break;
					
            		case MotionEvent.ACTION_UP:
            		case MotionEvent.ACTION_CANCEL:
					default:						
//						Log.d(LOG_TAG, "Up/Cancel Square.Id->" + square.info.id);
//						BoardAdapter.this.engine.updateGameBoard(square.info.id,/*hasMore continue*/ false);
						break;
        		}

				return true;
			}
		});
		
		return (View)view;
	}

	public CheckerBoardSquare getViewFromConvertView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof CheckerBoardSquare)) {
			BoardSquareInfo info = (BoardSquareInfo)this.engine.getData(position);

			return CheckerBoardSquare.instance(this.context, this.engine, parent, info);
		}
		
		return (CheckerBoardSquare) convertView;	
	}
} //end GameBoardAdapter
