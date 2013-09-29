package karega.scott.checkers;

import java.util.Hashtable;

import karega.scott.checkers.BoardSquare.StateType;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/*
 * Adapter used to provide data to the BoardActivity.boardGame
 */
public class BoardAdapter extends BaseAdapter {
	private static final int ROWS = 8;
	private static final int COLUMNS = 8;
	
	private Hashtable<Integer, BoardSquareInfo> data;
	
	private Context context;
	
	public BoardAdapter(Context context, Hashtable<Integer, BoardSquareInfo> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		return this.data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		Log.v("GameBoardAdapter.getView", String.format("view position %s",position));
		
		BoardSquare view = getViewFromConvertView(convertView, parent);
		
		BoardSquareInfo info = (BoardSquareInfo)this.data.get(position);
		view.setLayoutParams( new GridView.LayoutParams(BoardSquareInfo.WIDTH, BoardSquareInfo.HEIGHT));
		view.setId(info.id);
		view.setFillColor(info.fillColor);
		view.setBorderColor(info.borderColor);
		view.setXAxis(info.xAxis);
		view.setYAxis(info.yAxis);
		view.setPlayerColor(info.playerColor);
		view.setIsDirty(info.isDirty);
		view.setStateType(info.stateType);

		return (View)view;
	}

	public BoardSquare getViewFromConvertView(View convertView, ViewGroup parent) {
		if(convertView == null || !(convertView instanceof BoardSquare)) {
			return new BoardSquare(this.context);
		}
		
		return (BoardSquare) convertView;	
	}


	/*
	 * Create the data used on the checker board
	 */
	public static Hashtable<Integer, BoardSquareInfo> createNewGameData() {
		Hashtable<Integer, BoardSquareInfo> list = new Hashtable<Integer, BoardSquareInfo>();
		
		int key = 0;
		for(int row=0; row<BoardAdapter.ROWS; row++)  {
			for(int col=0; col<BoardAdapter.COLUMNS; col++) {
				Log.v("GameBoardAdapter.createNewData", String.format("position: %1s, row: %2s, col: %3s, (row+col)%%2 = %4s",key, row, col, (row+col)%2));
				
				BoardSquareInfo value = new BoardSquareInfo();

				value.id = key;
				value.xAxis = col;
				value.yAxis = row;
				value.isDirty = true;
				
				value.borderColor = Color.BLACK;
				
				// Configure Locked square
				if((row+col)%2 == 0) {
					value.fillColor = Color.GRAY;					
					value.stateType = StateType.LOCKED;
				} 
				
				// Configure Player 1 square
				else if((row+col)%2 != 0 && row < 3) {
					value.fillColor = Color.DKGRAY;
					value.stateType = StateType.PLAYER1;
					value.playerColor = Color.RED;
				}

				// Configure Empty square
				else if((row+col)%2 != 0 && (row == 3 || row == 4)) {					
					value.fillColor = Color.DKGRAY;
					value.stateType = StateType.EMPTY;
				}					

				// Configure Player 2 square
				else if((row+col)%2 != 0 && row > 4) {
					value.fillColor = Color.DKGRAY;
					value.stateType = StateType.PLAYER2;
					value.playerColor = Color.BLUE;
				}
				
				list.put(key, value);
				key++;
			} // end for
		} // end for
		
		return list;
	} //end createNewGameBoardData
	
} //end GameBoardAdapter
