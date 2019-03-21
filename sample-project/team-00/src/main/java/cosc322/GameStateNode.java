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
        
    private void getMinMoves(){
		for(short i=0; i<4; i++){
			expandLeft((short) 0, nodeBoard.blackQueens.get(i), true);
			expandRight((short) 0, nodeBoard.blackQueens.get(i), true);
			expandUp((short) 0, nodeBoard.blackQueens.get(i), true);
			expandDown((short) 0, nodeBoard.blackQueens.get(i), true);
			expandDiagRightUp((short) 0, nodeBoard.blackQueens.get(i), true);
			expandDiagRightDown((short) 0, nodeBoard.blackQueens.get(i), true);
			expandDiagLeftUp((short) 0, nodeBoard.blackQueens.get(i), true);
			expandDiagLeftDown((short) 0, nodeBoard.blackQueens.get(i), true);
			
			expandLeft((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandRight((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandUp((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandDown((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandDiagRightUp((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandDiagRightDown((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandDiagLeftUp((short) 0, nodeBoard.whiteQueens.get(i), false);
			expandDiagLeftDown((short) 0, nodeBoard.whiteQueens.get(i), false);
		}
		
		
		//for the current min amount of moves, check if there are any empty spaces adjacent to the current move space
		//if there are empty spaces, expand again in the direction of the empty space, which represents a new move
		//go until there are no more expansion (no more reachable spaces)
		boolean expand = false;
		short level = 1;
		
		do{
		for(short i = 0; i <= 9; i++){
			for(short j = 0; j <= 9; j++){
				if(minBlack[i][j]==level){
					if(nodeBoard.board[i][j-1]==0){
						expandLeft(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i][j+1]==0){
						expandRight(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i-1][j]==0){
						expandDown(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i+1][j]==0){
						expandUp(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i+1][j+1]==0){
						expandDiagRightUp(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i-1][j+1]==0){
						expandDiagRightDown(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i+1][j-1]==0){
						expandDiagLeftUp(level, new short[]{i,j}, true);
						expand = true;
					}
					if(nodeBoard.board[i-1][j-1]==0){
						expandDiagLeftDown(level, new short[]{i,j}, true);
						expand = true;
					}
				}
				
				if(minWhite[i][j]==level){
					if(nodeBoard.board[i][j-1]==0){
						expandLeft(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i][j+1]==0){
						expandRight(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i-1][j]==0){
						expandDown(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i+1][j]==0){
						expandUp(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i+1][j+1]==0){
						expandDiagRightUp(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i-1][j+1]==0){
						expandDiagRightDown(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i+1][j-1]==0){
						expandDiagLeftUp(level, new short[]{i,j}, false);
						expand = true;
					}
					if(nodeBoard.board[i-1][j-1]==0){
						expandDiagLeftDown(level, new short[]{i,j}, false);
						expand = true;
					}
				}
			}
		}
		level++;
		}while(expand);
	}
	
	private void expandLeft(short value, short[] position, boolean black){
		for(int i=position[1]-1; i>=0; i--){
			if(black){
				if(nodeBoard.board[position[0]][i]==0 && minBlack[position[0]][i]==0){
					minBlack[position[0]][i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]][i]==0 && minWhite[position[0]][i]==0){
					minWhite[position[0]][i] = (short)(value+(short)1);
				}
			}
		}
	}
	
	private void expandRight(short value, short[] position, boolean black){
		for(int i=position[1]+1; i<=9; i++){
			if(black){
				if(nodeBoard.board[position[0]][i]==0 && minBlack[position[0]][i]==0){
					minBlack[position[0]][i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]][i]==0 && minWhite[position[0]][i]==0){
					minWhite[position[0]][i] = (short)(value+(short)1);
				}
			}
		}
	}
	
	private void expandUp(short value, short[] position, boolean black){
		for(int i=position[0]+1; i<=9; i++){
			if(black){
				if(nodeBoard.board[i][position[1]]==0 && minBlack[i][position[1]]==0){
					minBlack[i][position[1]] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[i][position[1]]==0 && minWhite[i][position[1]]==0){
					minWhite[i][position[1]] = (short) (value + (short)1);
				}
			}
		}
	}
	
	private void expandDown(short value, short[] position, boolean black){
		for(int i=position[0]-1; i>=0; i--){
			if(black){
				if(nodeBoard.board[i][position[1]]==0 && minBlack[i][position[1]]==0){
					minBlack[i][position[1]] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[i][position[1]]==0 && minWhite[i][position[1]]==0){
					minWhite[i][position[1]] = (short) (value + (short)1);
				}
			}
		}
	}
	
	private void expandDiagRightUp(short value, short[] position, boolean black){
		short i=1;
		while(position[0]+i<=9 && position[1]+i<=9){
			if(black){
				if(nodeBoard.board[position[0]+i][position[1]+i]==0 && minBlack[position[0]+i][position[1]+i]==0){
					minBlack[position[0]+i][position[1]+i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]+i][position[1]+i]==0 && minWhite[position[0]+i][position[1]+i]==0){
					minWhite[position[0]+i][position[1]+i] = (short) (value + (short)1);
				}
			}
			i++;
		}
	}
	
	private void expandDiagRightDown(short value, short[] position, boolean black){
		short i=1;
		while(position[0]-i>=0 && position[1]+i<=9){
			if(black){
				if(nodeBoard.board[position[0]-i][position[1]+i]==0 && minBlack[position[0]+i][position[1]+i]==0){
					minBlack[position[0]-i][position[1]+i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]-i][position[1]+i]==0 && minWhite[position[0]-i][position[1]+i]==0){
					minWhite[position[0]-i][position[1]+i] = (short) (value + (short)1);
				}
			}
			i++;
		}
	}
	
	private void expandDiagLeftUp(short value, short[] position, boolean black){
		short i=1;
		while(position[0]+i<=9 && position[1]-i>=0){
			if(black){
				if(nodeBoard.board[position[0]+i][position[1]-i]==0 && minBlack[position[0]+i][position[1]-i]==0){
					minBlack[position[0]+i][position[1]-i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]+i][position[1]-i]==0 && minWhite[position[0]+i][position[1]-i]==0){
					minWhite[position[0]+i][position[1]-i] = (short) (value + (short)1);
				}
			}
			i++;
		}
	}
	
	private void expandDiagLeftDown(short value, short[] position, boolean black){
		short i=1;
		while(position[0]-i>=0 && position[1]-i>=0){
			if(black){
				if(nodeBoard.board[position[0]-i][position[1]-i]==0 && minBlack[position[0]-i][position[1]-i]==0){
					minBlack[position[0]-i][position[1]-i] = (short) (value + (short)1);
				}
			}
			else{
				if(nodeBoard.board[position[0]-i][position[1]-i]==0 && minWhite[position[0]-i][position[1]-i]==0){
					minWhite[position[0]-i][position[1]-i] = (short) (value + (short)1);
				}
			}
			i++;
		}
	}

	public ArrayList<GameStateNode>	createChildren() {
		
		ArrayList<GameStateNode> generated = new ArrayList<GameStateNode>();
		
		if(asBlack) {
                    System.out.println("Number of queens for this root node is: " + nodeBoard.blackQueens.size());
			for(int i = 0; i < nodeBoard.blackQueens.size(); i ++) {
				
				ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpace(nodeBoard.blackQueens.get(i));
                                
                                System.out.println("Number of space moves for queen " + i + " at " + nodeBoard.blackQueens.get(i)[0] + ", " + nodeBoard.blackQueens.get(i)[1] + " is: " + legalQueenMoves.size());
				
				for(int j = 0; j < legalQueenMoves.size(); j++) {
					ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpace(legalQueenMoves.get(j));	
					
                                        for(int k = 0; k < legalArrowShots.size(); k++) {
                                            
                                        
					AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack);
                                        newState.applyMove(nodeBoard.blackQueens.get(i), legalQueenMoves.get(j), legalArrowShots.get(k));
					
					GameStateNode newNode = new GameStateNode(newState, turnNumber, asBlack);
					generated.add(newNode);
					
                                }
				}
				
				
				
				
			}
		}
		else {
			
for(int i = 0; i < nodeBoard.whiteQueens.size(); i ++) {
				
				ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpace(nodeBoard.blackQueens.get(i));
				
				for(int j = 0; j < legalQueenMoves.size(); j++) {
					ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpace(legalQueenMoves.get(j));	
					
                                        for(int k = 0; k < legalArrowShots.size(); k++) {
                                            
                                        
					AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack);
                                        newState.applyMove(nodeBoard.blackQueens.get(i), legalQueenMoves.get(j), legalArrowShots.get(k));
					
					GameStateNode newNode = new GameStateNode(newState, turnNumber, asBlack);
					generated.add(newNode);
					
                                }
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
