package cosc322;

import java.util.ArrayList;

public class AmazonGameState {

	short[][] board = new short[10][10];
	int turnNumber = 0;
	ArrayList<short[]> blackQueens;
	ArrayList<short[]> whiteQueens;
	public boolean asBlack;

	// 0 = empty, 1 = black queen, 2 = white queen, 3 = arrow;

	public AmazonGameState(short[][] board, int turnNumber, boolean asBlack, ArrayList<short[]> black, ArrayList<short[]> white) {
		this.board = board;
		this.turnNumber = turnNumber;
		this.asBlack = asBlack;
                this.blackQueens = black;
                this.whiteQueens = white;
	}

	public AmazonGameState(int turnNumber, boolean asBlack) {
		this.board = new short[][]{{0,0,0,1,0,1,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0,2},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,2,0,2,0,0,0,0}};
		this.turnNumber = turnNumber;
                this.asBlack = asBlack;
                this.blackQueens = new ArrayList<>();
                this.whiteQueens = new ArrayList<>();
		blackQueens.add(new short[]{7,0});
		blackQueens.add(new short[]{9,3});
		blackQueens.add(new short[]{9,6});
		blackQueens.add(new short[]{7,9});
		whiteQueens.add(new short[]{2,0});
		whiteQueens.add(new short[]{0,3});
		whiteQueens.add(new short[]{0,6});
		whiteQueens.add(new short[]{2,9});
	}
        

	public boolean applyMove(ArrayList<Integer> QCurr, ArrayList<Integer> QNew, ArrayList<Integer> ANew) {
		short queenType = board[QCurr.get(0)][QCurr.get(1)];
		board[QCurr.get(0)][QCurr.get(1)] = 0;
		board[QNew.get(0)][QNew.get(1)] = queenType;
		board[ANew.get(0)][ANew.get(1)] = 3;
		short[] qnew = new short[]{QNew.get(0).shortValue(),QNew.get(1).shortValue()};
                
		int i = blackQueens.indexOf(new int[]{QCurr.get(0),QCurr.get(1)});
		if(i>=0){
			blackQueens.set(i, qnew);
		}
		else{
			i = whiteQueens.indexOf(new short[]{QCurr.get(0).shortValue(),QCurr.get(1).shortValue()});
			whiteQueens.set(i, qnew);
		}
		//check if legal move, and return false if not?
		return true;
	}
	
	public boolean applyMove(short[] QCurr, short[]QNew, short[] ANew) {
		short queenType = board[QCurr[0]][QCurr[1]];
                
		board[QCurr[0]][QCurr[1]] = 0;
		board[QNew[0]][QNew[1]] = queenType;
		board[ANew[0]][ANew[1]] = 3;
		
                //System.out.println("Queen index: " + QCurr[0] + ", " + QCurr[1]);
		int i = -1;
                for(int j = 0; j < blackQueens.size(); j++) {
                    //System.out.println("Compared to Queen index: " + blackQueens.get(j)[0] + ", " + blackQueens.get(j)[1]);
                    if (blackQueens.get(j)[0] == QCurr[0] && blackQueens.get(j)[1] == QCurr[1]) {
                        i = j;
                    }
                }
		if(i>=0){
			blackQueens.set(i, new short[]{QNew[0],QNew[1]});
		}
		else{
			i = whiteQueens.indexOf(new short[]{QCurr[0],QCurr[1]});
			whiteQueens.set(i, new short[]{QNew[0],QNew[1]});
                }
		//check if legal move, and return false if not?
		return true;
	}



	public ArrayList<short[]> movesFromSpaceQueen(short[] startingSpace) {

		ArrayList<short[]> moves = new ArrayList<short[]>();
		short row = startingSpace[0];
		short col = startingSpace[1];

		// left moves
		for (int i = 1; (col - i) >= 0; i++) {
			if (board[row][col - i] == 0) {
				moves.add(new short[] { row, (short) (col - i) });
			}
                        else{
                            break;
                        }

		}

		// right moves
		for (int i = 1; (col + i) <= 9; i++) {
			if (board[row][col + i] == 0) {
				moves.add(new short[] { row, (short) (col + i) });
			}
                        else{
                            break;
                        }

		}

		// up moves
		for (int i = 1; (row - i) >= 0; i++) {
			if (board[row - i][col] == 0) {
				moves.add(new short[] { (short) (row - i), col });
			}
                        else{
                            break;
                        }

		}

		// down moves
		for (int i = 1; (row + i) <= 9; i++) {
			if (board[row + i][col] == 0) {
				moves.add(new short[] { (short) (row + i), col });
			}
                        else{
                            break;
                        }

		}

		// up/left diag
		for (int i = 1; (row - i) >= 0 && (col - i) >= 0; i++) {
			if (board[row - i][col - i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col - i) });
			}
                        else{
                            break;
                        }

		}
		// down/left diag
		for (int i = 1; (row + i) <= 9 && (col - i) >= 0; i++) {
			if (board[row + i][col - i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col - i) });
			}
                        else{
                            break;
                        }

		}
		// up/right diag
		for (int i = 1; (row - i) >= 0 && (col + i) <= 9; i++) {
			if (board[row - i][col + i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col + i) });
			}
                        else{
                            break;
                        }

		}
		// down/right diag
		for (int i = 1; (row + i) <= 9 && (col + i) <= 9; i++) {
			if (board[row + i][col + i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col + i) });
			}
                        else{
                            break;
                        }

		}
		
		//System.out.println("Number of moves for this node is: " + moves.size());

		return moves;
	}
        
        public ArrayList<short[]> movesFromSpaceArrow(short[] startingSpace,  short[] queenSpace) {

		ArrayList<short[]> moves = new ArrayList<short[]>();
		short row = startingSpace[0];
		short col = startingSpace[1];
                
                short oldQueen = board[queenSpace[0]][queenSpace[1]];
                
                board[queenSpace[0]][queenSpace[1]] = 0;

		// left moves
		for (int i = 1; (col - i) >= 0; i++) {
			if (board[row][col - i] == 0) {
				moves.add(new short[] { row, (short) (col - i) });
			}
                        else{
                            break;
                        }

		}

		// right moves
		for (int i = 1; (col + i) <= 9; i++) {
			if (board[row][col + i] == 0) {
				moves.add(new short[] { row, (short) (col + i) });
			}
                        else{
                            break;
                        }

		}

		// up moves
		for (int i = 1; (row - i) >= 0; i++) {
			if (board[row - i][col] == 0) {
				moves.add(new short[] { (short) (row - i), col });
			}
                        else{
                            break;
                        }

		}

		// down moves
		for (int i = 1; (row + i) <= 9; i++) {
			if (board[row + i][col] == 0) {
				moves.add(new short[] { (short) (row + i), col });
			}
                        else{
                            break;
                        }

		}

		// up/left diag
		for (int i = 1; (row - i) >= 0 && (col - i) >= 0; i++) {
			if (board[row - i][col - i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col - i) });
			}
                        else{
                            break;
                        }

		}
		// down/left diag
		for (int i = 1; (row + i) <= 9 && (col - i) >= 0; i++) {
			if (board[row + i][col - i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col - i) });
			}
                        else{
                            break;
                        }

		}
		// up/right diag
		for (int i = 1; (row - i) >= 0 && (col + i) <= 9; i++) {
			if (board[row - i][col + i] == 0) {
				moves.add(new short[] { (short) (row - i), (short) (col + i) });
			}
                        else{
                            break;
                        }

		}
		// down/right diag
		for (int i = 1; (row + i) <= 9 && (col + i) <= 9; i++) {
			if (board[row + i][col + i] == 0) {
				moves.add(new short[] { (short) (row + i), (short) (col + i) });
			}
                        else{
                            break;
                        }

		}
		
		//System.out.println("Number of moves for this node is: " + moves.size());

                board[queenSpace[0]][queenSpace[1]] = oldQueen;
		return moves;
	}
	
	public static short[][] deepCloneBoard(short[][] oldBoard) {
		
		short[][] result = new short[oldBoard.length][];
		
		for(int i = 0; i < oldBoard.length; i ++) {
			result[i] = oldBoard[i].clone();
		}
               
			
		return result;
	}
        
        public static ArrayList<short[]> deepCloneWhite(ArrayList<short[]> white) {
		
		ArrayList<short[]> result = new ArrayList<short[]>();
		
		for(int i = 0; i < white.size(); i ++) {
			result.add(white.get(i));
		}
               
			
		return result;
	}
        
        public static ArrayList<short[]> deepCloneBlack(ArrayList<short[]> black) {
		
		ArrayList<short[]> result = new ArrayList<short[]>();
		
		for(int i = 0; i < black.size(); i ++) {
			result.add(black.get(i));
		}
               
			
		return result;
	}

}
