package cosc322;

import java.util.ArrayList;

public class AmazonGameState {
	
	
	short[][] board = new short[10][10];
	int turnNumber = 0;
	
	
	//0 = empty, 1 = black queen, 2 = white queen, 3 = arrow;
	
	public AmazonGameState(short[][] board, int turnNumber) {
		this.board = board;
		this.turnNumber = turnNumber;
	}
	
	public AmazonGameState(int turnNumber) {
		this.board = new short[][]{{0,0,0,1,0,1,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0,2},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,2,0,2,0,0,0,0}};
		this.turnNumber = turnNumber;
	}
	
	
	public boolean applyMove(ArrayList<Integer> QCurr, ArrayList<Integer> QNew, ArrayList<Integer> ANew) {
		short queenType = board[QCurr.get(0)][QCurr.get(1)];
		board[QCurr.get(0)][QCurr.get(1)] = 0;
		board[QNew.get(0)][QNew.get(1)] = queenType;
		board[ANew.get(0)][ANew.get(1)] = 3;
		//check if legal move, and return false if not?
		return true;
	}
	

}
