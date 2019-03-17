package cosc322;

import java.util.ArrayList;

public class GameStateNode {
	
	private AmazonGameState nodeBoard;
	public int value;
	public int turnNumber;
	public int player;
	public ArrayList<GameStateNode> children;
	
	
	public GameStateNode(AmazonGameState board, int turnNumber) {
		
	}

}
