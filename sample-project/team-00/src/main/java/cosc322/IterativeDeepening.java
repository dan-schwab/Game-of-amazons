/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.Stack;

/**
 *
 * @author mattd
 */
public class IterativeDeepening {

    private int numberOfNodes;
    private int depth;
    private int maxDepth;
    private boolean goalFound = false;
    private Stack<Integer> stack;

    public IterativeDeepening() {
        stack = new Stack<>();
    }

    public void iterativeDeeping(int[][] adjMatrix, int destination) {
        numberOfNodes = adjMatrix[1].length - 1;
        while (!goalFound) {
            depthSearch(adjMatrix, 1, destination);
            maxDepth++;
        }
        System.out.println("\nGoal Found at depth " + depth);
    }

    private void depthSearch(int adjacencyMatrix[][], int source, int goal) {
        int el;
        int dest = 1;
        int[] visited = new int[numberOfNodes + 1];
        stack.push(source);
        depth = 0;
        System.out.println("\nAt Depth " + maxDepth);
        System.out.print(source + "\t");

        while (!stack.isEmpty()) {
            el = stack.peek();
            while (dest <= numberOfNodes) {
                if (depth < maxDepth) {
                    if (adjacencyMatrix[el][dest] == 1 && visited[dest] != 1) {
                        stack.push(dest);
                        visited[dest] = 1;
                        System.out.print(dest + "\t");
                        depth++;
                        if (goal == dest) {
                            goalFound = true;
                            return;
                        }
                        el = dest;
                        dest = 1;
                        continue;
                    }
                } else {
                    break;
                }
                dest++;
            }
            dest = stack.pop() + 1;
            depth--;
        }
    }
}
