package cosc322;

import java.util.ArrayList;

public class ABTree {
	
	public GameStateNode rootNode;
	int depth;
    public long startTime;
	ArrayList<GameStateNode> searchSpace = new ArrayList<GameStateNode>();
	
	
	public ABTree(GameStateNode root, long startTime) {
		this.rootNode = root;
        this.startTime = startTime;
        this.depth = 0;
	}

	
	public void AlphaBetaHelper() {
		//change depth here
		//depth = 1;
		ABSearch(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	
	public int ABSearch(GameStateNode node, int depth, int alpha, int beta) {
		
		if(depth == 0 || node.children.isEmpty()) {
			node.value = h_value(node);
			//System.out.println("At depth 0, value: " + node.value);
			return node.value;
			//return h_value(node);
		}
		System.out.println("dpeth level > 0, children: " + node.children.size());
		for(GameStateNode child : node.children) {
			
			int currentScore = ABSearch(child, depth-1, alpha, beta);
			
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
		//System.out.println("Returning Value " + node.value);
		return node.value;
		
	}
	
	public GameStateNode getOptimalMove() {
		int max = Integer.MIN_VALUE;
		GameStateNode optimal = null;
		ArrayList<GameStateNode> bestList = new ArrayList<>();
		for(int i = 0; i < rootNode.children.size(); i ++) {
			if(max < rootNode.children.get(i).value) {
				max = rootNode.children.get(i).value;
				bestList.add( rootNode.children.get(i));
				
			}
			
			
		}
		if(bestList.size() > 0) {
			if(bestList.size() > 1) {
				optimal = bestList.get(bestList.size() - 1);
			}
			else optimal = bestList.get(0);
		}
		else {
			System.out.println("Game Over");
			return null;
		}
		
		return optimal;
		
	}
	
	
	public void createFrontier() {
		
		ArrayList<GameStateNode> nextLayer = rootNode.createChildren();
		
		searchSpace.addAll(nextLayer);
		System.out.println("Nodes in frontier before trim: " + searchSpace.size());
		trimFrontier();
		System.out.println("Nodes in frontier after trim: " + searchSpace.size());
		this.depth++;
		
		
	}
	
	public void trimFrontier() {                // trim down the search space
        int avg = 0;                            // average heuristic value
        //avg = searchSpace.stream().map((S) -> h_value(S)).reduce(avg, Integer::sum); // add all heuristic values to average
        for(int i = 0; i < searchSpace.size(); i++) {
            avg+= searchSpace.get(i).value;
            
        }
        if (!searchSpace.isEmpty()) {            // if the searchSpace isn't empty get mean
            avg = avg / searchSpace.size();
        }
        for (int i = 0; i < searchSpace.size(); i++) {     // remove all below the average
            if (h_value(searchSpace.get(i)) < avg) {
                searchSpace.remove(searchSpace.get(i));
            }
        }
        
		System.out.println("Avg heuristic value: " + avg);

    }
	
	public int h_value(GameStateNode node) {
		        
            return node.value;
	}
	
}
