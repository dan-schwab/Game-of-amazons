/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosc322;

import java.util.Stack;
import java.util.ArrayList;

/**
 *
 * @author mattd
 */
public class IterativeDeepening {
    GameStateNode target;
    boolean any_remaining;
    boolean remaining;
    
    public GameStateNode iterativeDeepening(GameStateNode root, int goal){
        any_remaining = true;
        for(int depth = 0 ; depth < Integer.MAX_VALUE ; depth++){
            target = depthLimitedSearch(root, depth, goal);
            if(target != null){
                return target;
            }
            if(!any_remaining){
                return null;
            }
        }
        return null;
    }
    
    public GameStateNode depthLimitedSearch(GameStateNode node, int depth, int goal){
        if(depth == 0){
            remaining = true;
            if(node.value == goal){
                return node;
            }
            else{
                return null;
            }
        }
        else{
            any_remaining = false;
            for(GameStateNode child : node.children){
                target = depthLimitedSearch(child, depth - 1, goal);
                if(target != null){
                    remaining = true;
                    return target;
                }
                if(remaining){
                    any_remaining = true;
                }
            }
            remaining = any_remaining;
            return null;
        }
    }
        
    }

//    private int numberOfNodes;
//    private int depth;
//    private int maxDepth;
//    private boolean goalFound = false;
//    private Stack<Integer> stack;
//
//    public IterativeDeepening() {
//        stack = new Stack<>();
//    }
//
//    public void iterativeDeeping(int[][] adjMatrix, int destination) {
//        numberOfNodes = adjMatrix[1].length - 1;
//        while (!goalFound) {
//            depthSearch(adjMatrix, 1, destination);
//            maxDepth++;
//        }
//        System.out.println("\nGoal Found at depth " + depth);
//    }
//
//    private void depthSearch(int adjacencyMatrix[][], int source, int goal) {
//        int el;
//        int dest = 1;
//        int[] visited = new int[numberOfNodes + 1];
//        stack.push(source);
//        depth = 0;
//        System.out.println("\nAt Depth " + maxDepth);
//        System.out.print(source + "\t");
//
//        while (!stack.isEmpty()) {
//            el = stack.peek();
//            while (dest <= numberOfNodes) {
//                if (depth < maxDepth) {
//                    if (adjacencyMatrix[el][dest] == 1 && visited[dest] != 1) {
//                        stack.push(dest);
//                        visited[dest] = 1;
//                        System.out.print(dest + "\t");
//                        depth++;
//                        if (goal == dest) {
//                            goalFound = true;
//                            return;
//                        }
//                        el = dest;
//                        dest = 1;
//                        continue;
//                    }
//                } else {
//                    break;
//                }
//                dest++;
//            }
//            dest = stack.pop() + 1;
//            depth--;
//        }
//    }