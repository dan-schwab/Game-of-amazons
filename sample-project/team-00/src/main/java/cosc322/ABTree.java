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
		//System.out.println("dpeth level > 0, children: " + node.children.size());
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

            GameStateNode optimal = null;
		if(asBlack) {
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



       public void expandFrontier() {
        ArrayList<GameStateNode> newFrontier = new ArrayList<>();
        // See if we're at the very first root node
        if (depth != 0) {
            if (depth % 2 == 0) {
                for (GameStateNode S : searchSpace)
                    newFrontier.addAll(S.createChildren());
            } else {
                for (GameStateNode S : searchSpace)
                    newFrontier.addAll(S.createChildren());
            }
        } else {
            newFrontier.addAll(rootNode.createChildren());
        }

        // Clear the old frontier, and add in the new SearchTreeNodes to use
        searchSpace.clear();
        for (GameStateNode S : newFrontier) {
            // deepCopy in order to keep Object relationships
            GameStateNode newNode = S.deepClone();
            searchSpace.add(newNode);
        }
        depth++;
    }


    public GameStateNode makeMove() {
        /* "Thresholding" based off the number of moves we have
            in order to increase our likelyhood of winning
         */
        if(numOfMoves <= 20) {
            this.expandFrontier();
            this.trimFrontier();
        }

        else if(numOfMoves > 20 && numOfMoves <= 30) {
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();

        }
        else if(numOfMoves > 30 && numOfMoves <= 45) {
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();
            this.trimFrontier();

        }
        else if(numOfMoves > 45 && numOfMoves <= 60) {
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();
            this.trimFrontier();
        }
        else if(numOfMoves > 60) {
            this.expandFrontier();
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();
            this.trimFrontier();
            this.expandFrontier();
            this.trimFrontier();

        }

        this.ABSearch(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        GameStateNode bestMove = this.getMoveAfterAlphaBeta();
        makeMoveOnRoot(bestMove);
        return bestMove;
    } // end of makeMove

    private GameStateNode getMoveAfterAlphaBeta() {
        int max = Integer.MIN_VALUE;
        GameStateNode best = null;
        ArrayList<GameStateNode> currentBest = new ArrayList<>(); // just to initialize currentBest
        for (GameStateNode S : rootNode.createChildren()) {
            if (max < h_value(S)) {
                max = h_value(S);
                currentBest.add(S);
            }
        }
        if (currentBest.size() > 1) {
            best = currentBest.get((currentBest.size() - 1));
        } else {
            try {
                best = currentBest.get(0);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Goal state reached!");
            }
        }

        return best;
    }

    public void makeMoveOnRoot(GameStateNode bestMove) {

    }

}
