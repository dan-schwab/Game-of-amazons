package cosc322;

import java.util.ArrayList;

public class GameStateNode {

	private AmazonGameState nodeBoard;
	public int value;
	public int turnNumber;
	public boolean asBlack;
	public ArrayList<GameStateNode> children;
	public short[][] territory;
	public short[][] minBlack;
	public short[][] minWhite;

	public GameStateNode(AmazonGameState board, int turnNumber, boolean asBlack) {
		this.nodeBoard = board;
		this.turnNumber = turnNumber;
		this.asBlack = asBlack;
		this.territory = new short[10][10];
		this.minBlack = new short[10][10];
		this.minWhite = new short[10][10];
	}

	private void getTerritory() {

	}
/*
	private void getMinMoves() {
		for (short i = 0; i < 4; i++) {
			expandLeft(0, nodeBoard.blackQueens.get(i), true);
			expandRight(0, nodeBoard.blackQueens.get(i), true);
			expandUp(0, nodeBoard.blackQueens.get(i), true);
			expandDown(0, nodeBoard.blackQueens.get(i), true);
			expandDiagRightUp(0, nodeBoard.blackQueens.get(i), true);
			expandDiagRightDown(0, nodeBoard.blackQueens.get(i), true);
			expandDiagLeftUp(0, nodeBoard.blackQueens.get(i), true);
			expandDiagLeftDown(0, nodeBoard.blackQueens.get(i), true);

			expandLeft(0, nodeBoard.whiteQueens.get(i), false);
			expandRight(0, nodeBoard.whiteQueens.get(i), false);
			expandUp(0, nodeBoard.whiteQueens.get(i), false);
			expandDown(0, nodeBoard.whiteQueens.get(i), false);
			expandDiagRightUp(0, nodeBoard.whiteQueens.get(i), false);
			expandDiagRightDown(0, nodeBoard.whiteQueens.get(i), false);
			expandDiagLeftUp(0, nodeBoard.whiteQueens.get(i), false);
			expandDiagLeftDown(0, nodeBoard.whiteQueens.get(i), false);
		}
	}

	private void expandLeft(short value, short[] position, boolean black) {
		for (short i = position[1] - 1; i >= 0; i--) {
			if (black) {
				if (nodeBoard.board[position[0]][i] == 0 && minBlack[position[0]][i] == 0) {
					minBlack[position[0]][i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0]][i] == 0 && minWhite[position[0]][i] == 0) {
					minWhite[position[0]][i] = value + 1;
				}
			}
		}
	}

	private void epxandRight(short value, short[] position, boolean black) {
		for (short i = position[1] + 1; i <= 9; i++) {
			if (black) {
				if (nodeBoard.board[position[0]][i] == 0 && minBlack[position[0]][i] == 0) {
					minBlack[position[0]][i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0]][i] == 0 && minWhite[position[0]][i] == 0) {
					minWhite[position[0]][i] = value + 1;
				}
			}
		}
	}

	private void expandUp(short value, short[] position, boolean black) {
		for (short i = position[0] + 1; i <= 9; i++) {
			if (black) {
				if (nodeBoard.board[i][position[1]] == 0 && minBlack[i][position[1]] == 0) {
					minBlack[i][position[1]] = value + 1;
				}
			} else {
				if (nodeBoard.board[i][position[1]] == 0 && minWhite[i][position[1]] == 0) {
					minWhite[i][position[1]] = value + 1;
				}
			}
		}
	}

	private void expandDown(short value, short[] position, boolean black) {
		for (short i = position[0] - 1; i >= 0; i--) {
			if (black) {
				if (nodeBoard.board[i][position[1]] == 0 && minBlack[i][position[1]] == 0) {
					minBlack[i][position[1]] = value + 1;
				}
			} else {
				if (nodeBoard.board[i][position[1]] == 0 && minWhite[i][position[1]] == 0) {
					minWhite[i][position[1]] = value + 1;
				}
			}
		}
	}

	private void expandDiagRightUp(short value, short[] position, boolean black) {
		short i = 1;
		while (position[0] + i <= 9 && position[1] + i <= 9) {
			if (black) {
				if (nodeBoard.board[position[0] + i][position[1] + i] == 0
						&& minBlack[position[0] + i][position[1] + i] == 0) {
					minBlack[position[0] + i][position[1] + i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0] + i][position[1] + i] == 0
						&& minWhite[position[0] + i][position[1] + i] == 0) {
					minWhite[position[0] + i][position[1] + i] = value + 1;
				}
			}
			i++;
		}
	}

	private void expandDiagRightDown(short value, short[] position, boolean black) {
		short i = 1;
		while (position[0] - i >= 0 && position[1] + i <= 9) {
			if (black) {
				if (nodeBoard.board[position[0] - i][position[1] + i] == 0
						&& minBlack[position[0] + i][position[1] + i] == 0) {
					minBlack[position[0] - i][position[1] + i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0] - i][position[1] + i] == 0
						&& minWhite[position[0] - i][position[1] + i] == 0) {
					minWhite[position[0] - i][position[1] + i] = value + 1;
				}
			}
			i++;
		}
	}

	private void expandDiagLeftUp(short value, short[] position, boolean black) {
		short i = 1;
		while (position[0] + i <= 9 && position[1] - i >= 0) {
			if (black) {
				if (nodeBoard.board[position[0] + i][position[1] - i] == 0
						&& minBlack[position[0] + i][position[1] - i] == 0) {
					minBlack[position[0] + i][position[1] - i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0] + i][position[1] - i] == 0
						&& minWhite[position[0] + i][position[1] - i] == 0) {
					minWhite[position[0] + i][position[1] - i] = value + 1;
				}
			}
			i++;
		}
	}

	private void expandDiagLeftDown(short value, short[] position, boolean black) {
		short i = 1;
		while (position[0] - i >= 0 && position[1] - i >= 0) {
			if (black) {
				if (nodeBoard.board[position[0] - i][position[1] - i] == 0
						&& minBlack[position[0] - i][position[1] - i] == 0) {
					minBlack[position[0] - i][position[1] - i] = value + 1;
				}
			} else {
				if (nodeBoard.board[position[0] - i][position[1] - i] == 0
						&& minWhite[position[0] - i][position[1] - i] == 0) {
					minWhite[position[0] - i][position[1] - i] = value + 1;
				}
			}
			i++;
		}
	}
*/

	public ArrayList<GameStateNode>	createChildren() {
		
		ArrayList<GameStateNode> generated = new ArrayList<GameStateNode>();
		
		if(asBlack) {
			for(int i = 0; i < nodeBoard.blackQueens.size(); i ++) {
				
				ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpace(nodeBoard.blackQueens.get(i));
				
				for(int j = 0; j < legalQueenMoves.size(); j++) {
					ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpace(legalQueenMoves.get(j));	
					
					AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack);
					
					GameStateNode newNode = new GameStateNode(newState, turnNumber, asBlack);
					generated.add(newNode);
					
					
				}
				
				
				
				
			}
		}
		else {
			
for(int i = 0; i < nodeBoard.whiteQueens.size(); i ++) {
				
				ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpace(nodeBoard.blackQueens.get(i));
				
				for(int j = 0; j < legalQueenMoves.size(); j++) {
					ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpace(legalQueenMoves.get(j));	
					
					AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack);
					
					GameStateNode newNode = new GameStateNode(newState, turnNumber, asBlack);
					generated.add(newNode);
					
					
				}
				
				
				
				
			}
			
		}
		System.out.println("Number of moves for this root node is: " + generated.size());
		return generated;
		
	}
        
        public static short[][] deepCloneBoard(short[][] oldBoard) {
		
		short[][] result = new short[oldBoard.length][];
		
		for(int i = 0; i < oldBoard.length; i ++) {
			result[i] = oldBoard[i].clone();
		}
		
		
		return result;
	}

}
