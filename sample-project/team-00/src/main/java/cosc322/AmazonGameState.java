package cosc322;

import java.util.ArrayList;

public class AmazonGameState {
	
	
	short[][] board = new short[10][10];
	int turnNumber = 0;
	ArrayList<short[]> blackQueens;
	ArrayList<short[]> whiteQueens;
	
	
	//0 = empty, 1 = black queen, 2 = white queen, 3 = arrow;
	
	public AmazonGameState(short[][] board, int turnNumber) {
		this.board = board;
		this.turnNumber = turnNumber;
	}
	
	public AmazonGameState(int turnNumber) {
		this.board = new short[][]{{0,0,0,1,0,1,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0,2},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,2,0,2,0,0,0,0}};
		this.turnNumber = turnNumber;
		blackQueens.add({6,0});
		blackQueens.add({9,3});
		blackQueens.add({9,6});
		blackQueens.add({6,9});
		whiteQueens.add({3,0});
		whiteQueens.add({0,3});
		whiteQueens.add({0,6});
		whiteQueens.add({3,9});
	}
	
	
	public boolean applyMove(ArrayList<Integer> QCurr, ArrayList<Integer> QNew, ArrayList<Integer> ANew) {
		short queenType = board[QCurr.get(0)][QCurr.get(1)];
		board[QCurr.get(0)][QCurr.get(1)] = 0;
		board[QNew.get(0)][QNew.get(1)] = queenType;
		board[ANew.get(0)][ANew.get(1)] = 3;
		
		int i = blackQueens.indexOf({QCurr.get(0),QCurr.get(1)});
		if(i>=0){
			blackQueens.set(i, {QNew.get(0),QNew.get(1)});
		}
		else{
			i = whiteQueens.indexOf({QCurr.get(0),QCurr.get(1)});
			whiteQueens.set(i, {QNew.get(0),QNew.get(1)});
		}
		//check if legal move, and return false if not?
		return true;
	}
	

}
