package cosc322;

import java.util.ArrayList;

public class ABTree {

	public GameStateNode rootNode;
	int depth;
  public long startTime;
	ArrayList<GameStateNode> searchSpace = new ArrayList<>();
	public GameStateNode currentBest;
	boolean asBlack;
  int numOfMoves;


	public ABTree(GameStateNode root, long startTime, boolean asBlack) {
		this.rootNode = root;
        this.startTime = startTime;
        this.depth = 0;
        this.asBlack = asBlack;
	}


	public void AlphaBetaHelper() {

               for(int level = 1; level < depth && (System.currentTimeMillis() - startTime < 20000); level++) {
                 System.out.println("calling ab search at level " + level + " with time " + (System.currentTimeMillis() - startTime));
		ABSearch(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, rootNode.asBlack);
                }
	}


	public int ABSearch(GameStateNode node, int depth, int alpha, int beta, boolean maximizing) {

		if(depth == 0 || node.children.isEmpty()) {
			node.value = node.value;
			//System.out.println("At depth 0, value: " + node.value);
			return node.value;
		}
		//System.out.println("dpeth level > 0, children: " + node.children.size());
		
		if(maximizing) {
			
			int currentScore = Integer.MIN_VALUE;
			
			for(GameStateNode child : node.children) {

			currentScore = Math.max(currentScore, ABSearch(child, depth-1, alpha, beta, false));
			
			alpha = Math.max(alpha, currentScore);
			if(alpha >= beta) break;

			}
			
			return node.value;
			
		}
		else {
			
			int currentScore = Integer.MAX_VALUE;
			
			for(GameStateNode child : node.children) {

			currentScore = Math.min(currentScore, ABSearch(child, depth-1, alpha, beta, true));
			
			beta = Math.min(beta, currentScore);
			if(alpha >= beta) break;

			}
			
			return node.value;
			
		}

	}

	public GameStateNode getOptimalMove(boolean black) {

            GameStateNode optimal = null;
		if(black) {
                    return getOptimalMoveBlack();
                }
                else {
                    return getOptimalMoveWhite();
                }

	}

        public GameStateNode getOptimalMoveBlack() {
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

	public GameStateNode getOptimalMoveWhite() {
		int min = Integer.MAX_VALUE;
		GameStateNode optimal = null;
		ArrayList<GameStateNode> bestList = new ArrayList<>();
		for(int i = 0; i < rootNode.children.size(); i ++) {
			if(min > rootNode.children.get(i).value) {
				min = rootNode.children.get(i).value;
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
		//System.out.println("Nodes in frontier before trim: " + searchSpace.size());
		removeLowValue();
		//System.out.println("Nodes in frontier after trim: " + searchSpace.size());
		this.depth++;
	}


	public void removeLowValue() {                // remove low heuristic value nodes
        int avg = 0;
        avg = searchSpace.stream().map((S) -> S.value).reduce(avg, Integer::sum); // add all heuristic values to average
//        for(int i = 0; i < searchSpace.size(); i++) {
//            avg+= searchSpace.get(i).value;
//
//        }
        if (!searchSpace.isEmpty()) {
            avg = avg / searchSpace.size();
        }
        
        for (int i = 0; i < searchSpace.size(); i++) {
            if(searchSpace.get(i).asBlack){ 
                if (searchSpace.get(i).value < avg) {
                    searchSpace.get(i).parent.children.remove(searchSpace.get(i));
                    searchSpace.remove(searchSpace.get(i));
                }   
            }
            else{
                if (searchSpace.get(i).value > avg) {
                    searchSpace.get(i).parent.children.remove(searchSpace.get(i));
                    searchSpace.remove(searchSpace.get(i));
                }
            }

		//System.out.println("Avg heuristic value: " + avg);

        }
    }


       public void expandDeeper() {
        ArrayList<GameStateNode> nextLayer = new ArrayList<>();

                for (int i = 0; i < searchSpace.size(); i++) {
                    //temp holding next layer
                    nextLayer.addAll(searchSpace.get(i).createChildren()); }

        //clear current layer
        searchSpace.clear();
        //reinit serach space with next layer
        for (int i = 0; i < nextLayer.size(); i++) {
            // deepCopy in order to keep Object relationships
            GameStateNode newNode = nextLayer.get(i).cloneGameStateNode(nextLayer.get(i));
            searchSpace.add(newNode);
        }
        depth++;
    }


}
