package cosc322;

import java.util.ArrayList;

public class GameStateNode {

    private AmazonGameState nodeBoard;
    public int value;
    public int turnNumber;
    public boolean asBlack;
    public ArrayList<GameStateNode> children;
    public short blackTerritory;
    public short whiteTerritory;
    public short[][] minBlack;
    public short[][] minWhite;

    public GameStateNode(AmazonGameState board, int turnNumber, boolean asBlack) {
        this.nodeBoard = board;
        this.turnNumber = turnNumber;
        this.asBlack = asBlack;
        this.minBlack = new short[10][10];
        this.minWhite = new short[10][10];
		//getMinMoves();
        //getTerritory();
    }

    private void getTerritory() {
        for (short i = 0; i <= 9; i++) {
            for (short j = 0; j <= 9; j++) {
                if (minBlack[i][j] == 0) {
                    if (minWhite[i][j] != 0) {
                        whiteTerritory++;
                    }
                } else if (minWhite[i][j] == 0) {
                    blackTerritory++;
                } else if (minBlack[i][j] < minWhite[i][j]) {
                    blackTerritory++;
                } else if (minWhite[i][j] < minBlack[i][j]) {
                    whiteTerritory++;
                }
            }
        }
    }

    private void getMinMoves() {
        for (short i = 0; i < 4; i++) {
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

		//for the current min amount of moves, check if there are any empty spaces adjacent to the current move space
        //if there are empty spaces, expand again in the direction of the empty space, which represents a new move
        //go until there are no more expansion (no more reachable spaces)
        boolean expand = false;
        short level = 1;

        do {
            for (short i = 0; i <= 9; i++) {
                for (short j = 0; j <= 9; j++) {
                    if (minBlack[i][j] == level) {
                        if (j > 0) {
                            if (nodeBoard.board[i][j - 1] == 0) {
                                expandLeft(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (j < 9) {
                            if (nodeBoard.board[i][j + 1] == 0) {
                                expandRight(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i > 0) {
                            if (nodeBoard.board[i - 1][j] == 0) {
                                expandDown(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i < 9) {
                            if (nodeBoard.board[i + 1][j] == 0) {
                                expandUp(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i < 9 && j < 9) {
                            if (nodeBoard.board[i + 1][j + 1] == 0) {
                                expandDiagRightUp(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i > 0 && j < 9) {
                            if (nodeBoard.board[i - 1][j + 1] == 0) {
                                expandDiagRightDown(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i < 9 && j > 0) {
                            if (nodeBoard.board[i + 1][j - 1] == 0) {
                                expandDiagLeftUp(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i > 0 && j > 0) {
                            if (nodeBoard.board[i - 1][j - 1] == 0) {
                                expandDiagLeftDown(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                    }

                    if (minWhite[i][j] == level) {
                        if (j > 0) {
                            if (nodeBoard.board[i][j - 1] == 0) {
                                expandLeft(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (j < 9) {
                            if (nodeBoard.board[i][j + 1] == 0) {
                                expandRight(level, new short[]{i, j}, true);
                                expand = true;
                            }
                        }
                        if (i > 0) {
                            if (nodeBoard.board[i - 1][j] == 0) {
                                expandDown(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                        if (i < 9) {
                            if (nodeBoard.board[i + 1][j] == 0) {
                                expandUp(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                        if (i < 9 && j < 9) {
                            if (nodeBoard.board[i + 1][j + 1] == 0) {
                                expandDiagRightUp(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                        if (i > 0 && j < 9) {
                            if (nodeBoard.board[i - 1][j + 1] == 0) {
                                expandDiagRightDown(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                        if (i < 9 && j > 0) {
                            if (nodeBoard.board[i + 1][j - 1] == 0) {
                                expandDiagLeftUp(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                        if (i > 0 && j > 0) {
                            if (nodeBoard.board[i - 1][j - 1] == 0) {
                                expandDiagLeftDown(level, new short[]{i, j}, false);
                                expand = true;
                            }
                        }
                    }
                }
            }
            level++;
        } while (expand);
    }

    private void expandLeft(short value, short[] position, boolean black) {
        for (int i = position[1] - 1; i >= 0; i--) {
            if (black) {
                if (nodeBoard.board[position[0]][i] == 0 && minBlack[position[0]][i] == 0) {
                    minBlack[position[0]][i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0]][i] == 0 && minWhite[position[0]][i] == 0) {
                    minWhite[position[0]][i] = (short) (value + (short) 1);
                }
            }
        }
    }

    private void expandRight(short value, short[] position, boolean black) {
        for (int i = position[1] + 1; i <= 9; i++) {
            if (black) {
                if (nodeBoard.board[position[0]][i] == 0 && minBlack[position[0]][i] == 0) {
                    minBlack[position[0]][i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0]][i] == 0 && minWhite[position[0]][i] == 0) {
                    minWhite[position[0]][i] = (short) (value + (short) 1);
                }
            }
        }
    }

    private void expandUp(short value, short[] position, boolean black) {
        for (int i = position[0] + 1; i <= 9; i++) {
            if (black) {
                if (nodeBoard.board[i][position[1]] == 0 && minBlack[i][position[1]] == 0) {
                    minBlack[i][position[1]] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[i][position[1]] == 0 && minWhite[i][position[1]] == 0) {
                    minWhite[i][position[1]] = (short) (value + (short) 1);
                }
            }
        }
    }

    private void expandDown(short value, short[] position, boolean black) {
        for (int i = position[0] - 1; i >= 0; i--) {
            if (black) {
                if (nodeBoard.board[i][position[1]] == 0 && minBlack[i][position[1]] == 0) {
                    minBlack[i][position[1]] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[i][position[1]] == 0 && minWhite[i][position[1]] == 0) {
                    minWhite[i][position[1]] = (short) (value + (short) 1);
                }
            }
        }
    }

    private void expandDiagRightUp(short value, short[] position, boolean black) {
        short i = 1;
        while (position[0] + i <= 9 && position[1] + i <= 9) {
            if (black) {
                if (nodeBoard.board[position[0] + i][position[1] + i] == 0 && minBlack[position[0] + i][position[1] + i] == 0) {
                    minBlack[position[0] + i][position[1] + i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0] + i][position[1] + i] == 0 && minWhite[position[0] + i][position[1] + i] == 0) {
                    minWhite[position[0] + i][position[1] + i] = (short) (value + (short) 1);
                }
            }
            i++;
        }
    }

    private void expandDiagRightDown(short value, short[] position, boolean black) {
        short i = 1;
        while (position[0] - i >= 0 && position[1] + i <= 9) {
            if (black) {
                if (nodeBoard.board[position[0] - i][position[1] + i] == 0 && minBlack[position[0] - i][position[1] + i] == 0) {
                    minBlack[position[0] - i][position[1] + i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0] - i][position[1] + i] == 0 && minWhite[position[0] - i][position[1] + i] == 0) {
                    minWhite[position[0] - i][position[1] + i] = (short) (value + (short) 1);
                }
            }
            i++;
        }
    }

    private void expandDiagLeftUp(short value, short[] position, boolean black) {
        short i = 1;
        while (position[0] + i <= 9 && position[1] - i >= 0) {
            if (black) {
                if (nodeBoard.board[position[0] + i][position[1] - i] == 0 && minBlack[position[0] + i][position[1] - i] == 0) {
                    minBlack[position[0] + i][position[1] - i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0] + i][position[1] - i] == 0 && minWhite[position[0] + i][position[1] - i] == 0) {
                    minWhite[position[0] + i][position[1] - i] = (short) (value + (short) 1);
                }
            }
            i++;
        }
    }

    private void expandDiagLeftDown(short value, short[] position, boolean black) {
        short i = 1;
        while (position[0] - i >= 0 && position[1] - i >= 0) {
            if (black) {
                if (nodeBoard.board[position[0] - i][position[1] - i] == 0 && minBlack[position[0] - i][position[1] - i] == 0) {
                    minBlack[position[0] - i][position[1] - i] = (short) (value + (short) 1);
                }
            } else {
                if (nodeBoard.board[position[0] - i][position[1] - i] == 0 && minWhite[position[0] - i][position[1] - i] == 0) {
                    minWhite[position[0] - i][position[1] - i] = (short) (value + (short) 1);
                }
            }
            i++;
        }
    }

    public ArrayList<GameStateNode> createChildren() {

        ArrayList<GameStateNode> generated = new ArrayList<>();

        if (asBlack) {
            System.out.println("Number of queens for this root node is: " + nodeBoard.blackQueens.size());
            for (int i = 0; i < nodeBoard.blackQueens.size(); i++) {

                ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpaceQueen(nodeBoard.blackQueens.get(i));

                System.out.println("Number of space moves for queen " + i + " at " + nodeBoard.blackQueens.get(i)[0] + ", " + nodeBoard.blackQueens.get(i)[1] + " is: " + legalQueenMoves.size());

                for (int j = 0; j < legalQueenMoves.size(); j++) {
                    ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpaceArrow(legalQueenMoves.get(j), nodeBoard.blackQueens.get(i));

                    for (int k = 0; k < legalArrowShots.size(); k++) {

                        AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack, deepCloneBlack(nodeBoard.blackQueens), deepCloneWhite(nodeBoard.whiteQueens));

                        for (short[] queen : nodeBoard.blackQueens) {
                            //System.out.println("Queen index: " + queen + " at " + queen[0] + ", " + queen[1]);

                        }

                                        //System.out.println("Queen index: " + queen + " at " + queen[0] + ", " + queen[1]);
                        newState.applyMove(nodeBoard.blackQueens.get(i), legalQueenMoves.get(j), legalArrowShots.get(k));

                        GameStateNode newNode = new GameStateNode(newState, turnNumber++, !asBlack);
                        generated.add(newNode);

                    }
                }

            }
        } else {

            nodeBoard.whiteQueens.stream().forEach((whiteQueen) -> {
                ArrayList<short[]> legalQueenMoves = nodeBoard.movesFromSpaceQueen(whiteQueen);
                legalQueenMoves.stream().forEach((legalQueenMove) -> {
                    ArrayList<short[]> legalArrowShots = nodeBoard.movesFromSpaceArrow(legalQueenMove, whiteQueen);
                    legalArrowShots.stream().map((legalArrowShot) -> {
                        AmazonGameState newState = new AmazonGameState(deepCloneBoard(nodeBoard.board), turnNumber, asBlack, deepCloneBlack(nodeBoard.blackQueens), deepCloneWhite(nodeBoard.whiteQueens));
                        //System.out.println("Queen is at: " + nodeBoard.whiteQueens.get(i));
                        newState.applyMove(whiteQueen, legalQueenMove, legalArrowShot);
                        return newState;
                    }).map((newState) -> new GameStateNode(newState, turnNumber, asBlack)).forEach((newNode) -> {
                        generated.add(newNode);
                    });
                });
            });

        }

        System.out.println("Number of moves for this root node is: " + generated.size());
        return generated;

    }

    public static short[][] deepCloneBoard(short[][] oldBoard) {

        short[][] result = new short[oldBoard.length][];

        for (int i = 0; i < oldBoard.length; i++) {
            result[i] = oldBoard[i].clone();
        }

        return result;
    }

    public static ArrayList<short[]> deepCloneWhite(ArrayList<short[]> white) {

        ArrayList<short[]> result = new ArrayList<>();

        white.stream().forEach((white1) -> {
            result.add(white1);
        });

        return result;
    }

    public static ArrayList<short[]> deepCloneBlack(ArrayList<short[]> black) {

        ArrayList<short[]> result = new ArrayList<>();

        black.stream().forEach((black1) -> {
            result.add(black1);
        });

        return result;
    }

}
