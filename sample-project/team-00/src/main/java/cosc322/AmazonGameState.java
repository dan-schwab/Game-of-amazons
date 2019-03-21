package cosc322;

import java.util.ArrayList;

public class AmazonGameState {

	short[][] board = new short[10][10];
	int turnNumber = 0;
	ArrayList<short[]> blackQueens;
	ArrayList<short[]> whiteQueens;
	public boolean asBlack;

	// 0 = empty, 1 = black queen, 2 = white queen, 3 = arrow;

	public AmazonGameState(short[][] board, int turnNumber, boolean asBlack) {
		this.board = board;
		this.turnNumber = turnNumber;
		this.asBlack = asBlack;
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
	
	public boolean applyMove(short[] QCurr, short[]QNew, short[] ANew) {
		short queenType = board[QCurr[0]][QCurr[1]];
		board[QCurr[0]][QCurr[1]] = 0;
		board[QNew[0]][QNew[1]] = queenType;
		board[ANew[0]][ANew[1]] = 3;
		
		int i = blackQueens.indexOf({QCurr[0],QCurr[1]});
		if(i>=0){
			blackQueens.set(i, {QNew[0],QNew[1]});
		}
		else{
			i = whiteQueens.indexOf({QCurr[0],QCurr[1]});
			whiteQueens.set(i, {QNew[0],QNew[1]});
		}
		//check if legal move, and return false if not?
		return true;
	}

	public ArrayList<short[]> movesFromSpace(short[] startingSpace) {

		ArrayList<short[]> moves = new ArrayList<short[]>();
		short row = startingSpace[0];
		short col = startingSpace[1];

		// left moves
		for (int i = 1; (col - i) >= 0; i++) {
			if (board[row][col - i] == 0) {
				moves.add(new short[] { row, (short) (col - i) });
			}

		}

		// right moves
		for (int i = 1; (col + i) <= 9; i++) {
			if (board[row][col + i] == 0) {
				moves.add(new short[] { row, (short) (col + i) });
			}

		}

		// up moves
		for (int i = 1; (row - i) >= 0; i++) {
			if (board[row - i][col] == 0) {
				moves.add(new short[] { (short) (row - i), col });
			}

		}

		// down moves
		for (int i = 1; (row + i) <= 9; i++) {
			if (board[row + i][col] == 0) {
				moves.add(new short[] { (short) (row + i), col });
			}

		}

		// up/left diag
		for (int i = 1; (row - i) >= 0 && (col - i) >= 0; i++) {
			if (board[row - i][col - i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col - i) });
			}

		}
		// down/left diag
		for (int i = 1; (row + i) <= 9 && (col - i) >= 0; i++) {
			if (board[row + i][col - i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col - i) });
			}

		}
		// up/right diag
		for (int i = 1; (row - i) >= 0 && (col + i) <= 9; i++) {
			if (board[row - i][col + i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col + i) });
			}

		}
		// down/right diag
		for (int i = 1; (row + i) <= 9 && (col + i) <= 9; i++) {
			if (board[row + i][col + i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col + i) });
			}

		}
		
		System.out.println("Number of moves for this node is: " + moves.size());

		return moves;
	}
	
	public static short[][] deepCloneBoard(short[][] oldBoard) {
		
		short[][] result = new short[oldBoard.length][];
		
		for(int i = 0; i < oldBoard.length; i ++) {
			result[i] = oldBoard[i].clone();
		}
		
		
		return result;
	}

}
