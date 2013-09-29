package karega.scott.checkers;

import karega.scott.checkers.BoardSquare.StateType;
import android.graphics.Color;

public class BoardSquareInfo {
	public static final int HEIGHT = 40;
	public static final int WIDTH = 40;	

	public int id = 0;
	public int xAxis = 0;
	public int yAxis = 0;
	public boolean isDirty = false;
	public StateType stateType = StateType.EMPTY;
	public int fillColor = Color.GRAY;
	public int borderColor = Color.BLACK;
	public int playerColor = Color.TRANSPARENT;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardSquareInfo{");
		builder.append(String.format("id=%s, ", id));
		builder.append(String.format("xAxis=%s, ", xAxis));
		builder.append(String.format("yAxis=%s, ", yAxis));
		builder.append(String.format("isDirty=%s, ", isDirty));
		builder.append(String.format("stateType=%s, ", stateType));
		builder.append(String.format("fillColor=%s, ", fillColor));
		builder.append(String.format("borderColor=%s", borderColor));
		builder.append(String.format("playerColor=%s", playerColor));
		builder.append("}");
		return "";
	}
	
	@Override
	public boolean equals(Object value) {
		if(!(value instanceof BoardSquareInfo)) 
			return false;
		
		BoardSquareInfo info = (BoardSquareInfo)value;		
		return (this.id == info.id && 
				this.xAxis == info.xAxis &&
				this.yAxis == info.yAxis &&
				this.isDirty == info.isDirty &&
				this.stateType == info.stateType &&
				this.playerColor == info.playerColor &&
				this.fillColor == info.fillColor &&
				this.borderColor == info.borderColor);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 22 + this.id;
		hash = hash * 34 + this.xAxis;
		hash = hash * 34 + this.yAxis;
		hash = hash * 42 + this.stateType.hashCode();
		hash = hash * 43 + this.fillColor + this.borderColor + this.playerColor;
	
		return hash;
	}
}

