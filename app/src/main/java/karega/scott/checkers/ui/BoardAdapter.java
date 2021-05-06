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

	private Context context;

	public BoardAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return BoardActivity.gameEngine.getSize();
	}

	@Override
	public Object getItem(int position) {
		return BoardActivity.gameEngine.getData(position);
	}

	@Override
	public long getItemId(int position) {
		Log.d(LOG_TAG, "get item id with position-> "+ position);
		return BoardActivity.gameEngine.getData(position).id;
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
				Log.d(LOG_TAG, "On Touch:      height->" + view.getWidth());	
				Log.d(LOG_TAG, "On Touch:       width->" + view.getWidth());	
				Log.d(LOG_TAG, "On Touch:       action->" + event.actionToString(action));	

				return true;
			}
		});
		
		return (View)view;
	}

	public CheckerBoardSquare getViewFromConvertView(int position, View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof CheckerBoardSquare)) {
			BoardSquareInfo info = (BoardSquareInfo)BoardActivity.gameEngine.getData(position);

			return CheckerBoardSquare.instance(this.context, parent, info);
		}
		
		return (CheckerBoardSquare) convertView;	
	}
} //end GameBoardAdapter
