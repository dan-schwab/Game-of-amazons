package cosc322;

import java.util.ArrayList;

public class GameStateNode {
	
	private AmazonGameState nodeBoard;
	public int value;
	public int turnNumber;
	public int player;
	public ArrayList<GameStateNode> children;
	public short[][] territory;
	
	
	public GameStateNode(AmazonGameState board, int turnNumber) {
		this.nodeBoard = board;
		this.turnNumber = turnNumber;
		this.territory = new short[][];
	}
	
	private void getTerritory() {
		
	}
	
	private void getMinMoves

}
