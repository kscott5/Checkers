package karega.scott.checkers.util;

import karega.scott.checkers.BoardSquareInfo;

import java.lang.Throwable;
import java.lang.RuntimeException;

import java.util.Arrays;
import java.util.Comparator;

public class BoardSquareComparor implements Comparator<BoardSquareInfo> {
	public final static int COMPAROR_MATRIX_TYPE = 0;
	public final static int COMPAROR_STEP_TYPE = 1;

	public final int comparorType;

	public BoardSquareComparor() {
		this.comparorType = BoardSquareComparor.COMPAROR_MATRIX_TYPE;
	}

	public BoardSquareComparor(int comparorType) {
		switch(comparorType) {
			case BoardSquareComparor.COMPAROR_STEP_TYPE:
				this.comparorType = BoardSquareComparor.COMPAROR_STEP_TYPE;
				break;

			case BoardSquareComparor.COMPAROR_MATRIX_TYPE:
			default:
				this.comparorType = BoardSquareComparor.COMPAROR_MATRIX_TYPE;
		}
	}
	
	public int compare(BoardSquareInfo obj1, BoardSquareInfo obj2) {
		throw new RuntimeException("Not implemented");
	}

	public boolean equals(Object obj) {
		throw new RuntimeException("Not implemented");
	}
}
