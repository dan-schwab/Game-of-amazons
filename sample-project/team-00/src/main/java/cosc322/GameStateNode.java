package cosc322;

import java.util.ArrayList;

public class GameStateNode {
	
	private AmazonGameState nodeBoard;
	public int value;
	public int turnNumber;
	public int player;
	public ArrayList<GameStateNode> children;
	public short[][] territory;
	public short[][] minBlack;
	public short[][] minWhite;
	
	public GameStateNode(AmazonGameState board, int turnNumber) {
		this.nodeBoard = board;
		this.turnNumber = turnNumber;
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

}
