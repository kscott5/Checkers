package karega.scott.checkers;

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
				CheckersEngine engine = square.engine;

				switch(event.getActionMasked()) {
            		case MotionEvent.ACTION_DOWN:
						return engine.verifyInitialSelection(square.info);

            		case MotionEvent.ACTION_MOVE:
						return engine.verifySelection(square.info);

            		case MotionEvent.ACTION_UP:
            		case MotionEvent.ACTION_CANCEL:
						return engine.verifyFinalSelection(square.info);
        		}

				return engine.handleOnTouch(square.info);
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
