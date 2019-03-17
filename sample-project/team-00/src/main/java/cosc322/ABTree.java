package cosc322;

public class ABTree {
	
	public GameStateNode rootNode;
	
	
	public ABTree(GameStateNode root) {
		this.rootNode = root;
	}

	
	public int ABSearch(GameStateNode node, int depth, int alpha, int beta) {
		
		if(depth == 0) {
			node.value = h_value(node);
			return node.value;
		}
		
		for(GameStateNode child : node.children) {
			
			int currentScore = ABSearch(child, depth -1, alpha, beta);
			
			if(node.player == 1) {
			alpha = Math.max(alpha, currentScore);
			if(alpha >= beta) break;
			}
			else {
				beta = Math.min(beta, currentScore);
				if(beta <= alpha) break;
			}
			
		}
		
		if(node.player == 1) {
			node.value = alpha;
		}
		else {
			node.value = beta;
		}
		
	}
	
	public int h_value(GameStateNode node) {
		
	}
	
}
