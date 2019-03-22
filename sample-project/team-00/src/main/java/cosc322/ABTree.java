package cosc322;

import java.util.ArrayList;

public class ABTree {

	public GameStateNode rootNode;
	int depth = 0;
	ArrayList<GameStateNode> searchSpace = new ArrayList<GameStateNode>();


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

			if(node.asBlack) {
			alpha = Math.max(alpha, currentScore);
			if(alpha >= beta) break;
			}
			else {
				beta = Math.min(beta, currentScore);
				if(beta <= alpha) break;
			}

		}

		if(node.asBlack) {
			node.value = alpha;
		}
		else {
			node.value = beta;
		}
		return node.value;
	}

	public int h_value(GameStateNode node) {
		return 0;

	}


	public void createFrontier() {

		rootNode.createChildren();


	}

	public int h_value(GameStateNode node) {


            return 0;
	}

        public int miniMax(GameStateNode node, boolean isMaxPlayer, int alpha, int beta){
            //if node is leaf: return value of the node
            int bestVal = 0;
            return bestVal;
        }

}
