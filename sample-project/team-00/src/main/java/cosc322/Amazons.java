package cosc322;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TimerTask;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ygraphs.ai.smart_fox.GameMessage;
import ygraphs.ai.smart_fox.games.AmazonsGameMessage;
import ygraphs.ai.smart_fox.games.GameClient;
import ygraphs.ai.smart_fox.games.GameModel;
import ygraphs.ai.smart_fox.games.GamePlayer;

/**
 * For testing and demo purposes only. An GUI Amazon client for human players 
 * @author yong.gao@ubc.ca
 */
public class Amazons extends GamePlayer{

    private GameClient gameClient;   
    private JFrame guiFrame = null;    
    private GameBoard board = null; 
    private boolean gameStarted = false;   
    public String usrName = null;
    
    private ABTree gameStateTree = null;
    int turnNumber = 0;
    private AmazonGameState currentState = null;
    boolean playingAsBlack;
    
    public long startTime;
            
    
    /**
     * Constructor
     * @param name
     * @param passwd
     */
    public Amazons(String name, String passwd){  
	
       this.usrName = name;		       	   
       setupGUI();       
                     
       connectToServer(name, passwd);        
    }
	
    private void connectToServer(String name, String passwd){
    	// create a GameClient and use "this" class (a GamePlayer) as the delegate.
    	// the client will take care of the communication with the server.
    	gameClient = new GameClient(name, passwd, this);	
    }
    
    @Override
    /**
     * Implements the abstract method defined in GamePlayer. Will be invoked by the GameClient
     * when the server says the login is successful
     */
    public void onLogin() {

	//once logged in, the gameClient will have  the names of available game rooms  
	ArrayList<String> rooms = gameClient.getRoomList();
	this.gameClient.joinRoom(rooms.get(8));	 		
    }
    
    
    /**
     * Implements the abstract method defined in GamePlayer. Once the user joins a room, 
     * all the game-related messages will be forwarded to this method by the GameClient.
     * 
     * See GameMessage.java 
     * 
     * @param messageType - the type of the message
     * @param msgDetails - A HashMap info and data about a game action     
     */
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails){
		
	if(messageType.equals(GameMessage.GAME_ACTION_START)){	 
	    if(((String) msgDetails.get("player-black")).equals(this.userName())){
		System.out.println("Game State: " +  msgDetails.get("player-black" ) + " Playing as white");
		playingAsBlack = true;
		turnNumber = 1;
        startTime = System.currentTimeMillis();
		currentState = new AmazonGameState(turnNumber, playingAsBlack);
                
                
		GameStateNode currentNode = new GameStateNode(currentState, turnNumber, true, null, null, null);
		gameStateTree = new ABTree(currentNode, startTime, currentState.asBlack);
		
		gameStateTree.createFrontier();                
		gameStateTree.AlphaBetaHelper();
		GameStateNode move = gameStateTree.getOptimalMove();
		
		System.out.println("Found optimal move");
		System.out.println("Move queen at " +  + (int) move.QOld[0] + ", "   + (int) move.QOld[1]);
		System.out.println("To "  + (int)move.QNew[0] + ", "   + (int) move.QNew[1]);
		System.out.println("Then shoot arrow to " + (int) move.ANew[0] + ", "   + (int) move.ANew[1]);
		System.out.println("White territory is: " + move.whiteTerritory);
        System.out.println("Black territory is: " + move.blackTerritory);
        System.out.println("neutral territory is: " + move.neutral);
        
        for(int i = 0; i <= 9; i++) {
            System.out.println();
            for(int j = 0; j <= 9; j++) {
                System.out.print(move.nodeBoard.board[i][j] + " ");
                
            }
       }
		
		currentState = move.nodeBoard;
               // try {
    //if(System.currentTimeMillis() - startTime < 20000)
    //    Thread.sleep(2000);
    //} catch(Exception e){};
    board.markPosition(10-move.QNew[0], 1+move.QNew[1], 10-move.ANew[0], 1+move.ANew[1], 
			10-move.QOld[0], 1+move.QOld[1], true);
    
		playerMove((int) move.QNew[0], (int) move.QNew[1], (int) move.ANew[0], (int) move.ANew[1], (int) move.QOld[0], (int) move.QOld[1]);
		
		
	    }
	    else if(((String) msgDetails.get("player-white")).equals(this.userName())) {
	    	System.out.println("Game State: " +  msgDetails.get("player-white") + " Playing as white");
	    	playingAsBlack = false;
			//turnNumber = 1;
			currentState = new AmazonGameState(turnNumber, playingAsBlack);
			GameStateNode currentNode = new GameStateNode(currentState, turnNumber, playingAsBlack, null, null, null);
            //startTime = System.currentTimeMillis();
			gameStateTree = new ABTree(currentNode, startTime, playingAsBlack);
	    	
	    }
	}
	else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
	    handleOpponentMove(msgDetails);
	}
	
	return true;
    }
    
    //handle the event that the opponent makes a move. 
    private void handleOpponentMove(Map<String, Object> msgDetails){
    System.out.println(this.userName() + " recieving a move, playing as " + currentState.asBlack + " on turn: " + turnNumber);
	System.out.println("OpponentMove(): " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
	ArrayList<Integer> qcurr = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
	ArrayList<Integer> qnew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
	ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
	System.out.println("QCurr: " + qcurr);
	System.out.println("QNew: " + qnew);
	System.out.println("Arrow: " + arrow);

	board.markPosition(qnew.get(0), qnew.get(1), arrow.get(0), arrow.get(1), 
			qcurr.get(0), qcurr.get(1), true);	
    
	short[] QC = new short[] {(short) (10-qcurr.get(0).intValue()), (short)  (qcurr.get(1).intValue()-1)};
	short[] QN = new short[] {(short) (10-qnew.get(0).intValue()), (short)  (qnew.get(1).intValue()-1)};
	short[] AN = new short[] {(short)  (10-arrow.get(0).intValue()), (short) (arrow.get(1).intValue()-1)};

	
	turnNumber++;
    startTime = System.currentTimeMillis();
    
    currentState.applyMove(QC, QN, AN);
    
    GameStateNode currentNode = new GameStateNode(currentState, turnNumber, currentState.asBlack, null, null, null);
	gameStateTree = new ABTree(currentNode, startTime, currentState.asBlack);
    
	turnNumber++;
    
    gameStateTree.createFrontier();
    if(turnNumber > 40) {
        System.out.println("Expanding before: " + gameStateTree.searchSpace.size());
                gameStateTree.expandDeeper();
                System.out.println("Expanding afer: " + gameStateTree.searchSpace.size());
                }
    if(turnNumber > 60) {
        System.out.println("Expanding before: " + gameStateTree.searchSpace.size());
                gameStateTree.expandDeeper();
                System.out.println("Expanding afer: " + gameStateTree.searchSpace.size());
                }
	gameStateTree.AlphaBetaHelper();
        GameStateNode move = gameStateTree.getOptimalMove();

	
	if(move == null) {
		System.out.println("Game over");
	} else {
	System.out.println("Found optimal move");
	System.out.println("Move queen at " +  + (int) move.QOld[0] + ", "   + (int) move.QOld[1]);
	System.out.println("To "  + (int)move.QNew[0] + ", "   + (int) move.QNew[1]);
	System.out.println("Then shoot arrow to " + (int) move.ANew[0] + ", "   + (int) move.ANew[1]);
	System.out.println("White territory is: " + move.whiteTerritory);
    System.out.println("Black territory is: " + move.blackTerritory);
    System.out.println("neutral territory is: " + move.neutral);
    
    for(int i = 0; i <= 9; i++) {
        System.out.println();
        for(int j = 0; j <= 9; j++) {
            System.out.print(move.nodeBoard.board[i][j] + " ");
            
        }
   }
	
    currentState = move.nodeBoard;
    //try {
    //if(System.currentTimeMillis() - startTime < 20000)
    //    Thread.sleep(2000);
    //} catch(Exception e){};
	playerMove((int) move.QNew[0], (int) move.QNew[1], (int) move.ANew[0], (int) move.ANew[1], (int) move.QOld[0], (int) move.QOld[1]);
	
		//gameStateTree.ABSearch();
        }			
		//gameClient.sendMoveMessage();
    }
	
	
    /**
     * handle a move made by this player --- send the info to the server.
     * @param x queen row index 
     * @param y queen col index
     * @param arow arrow row index
     * @param acol arrow col index
     * @param qfr queen original row
     * @param qfc queen original col
     */
    public void playerMove(int x, int y, int arow, int acol, int qfr, int qfc){		
		
    	/*y++;
    	acol++;
    	qfc++;
    	
    	switch(x) {
    	case 0:
    		x = 10;
    		break;
    	case 1:
    		x = 9;
    		break;
    	case 2:
    		x = 8;
    		break;
    	case 3:
    		x = 7;
    		break;
    	case 4:
    		x = 6;
    		break;
    	case 5:
    		x = 5;
    		break;
    	case 6:
    		x = 4;
    		break;
    	case 7:
    		x = 3;
    		break;
    	case 8:
    		x = 2;
    		break;
    	case 9:
    		x = 1;
    		break;
    	}
    	switch(qfc) {
    	case 0:
    		qfc = 10;
    		break;
    	case 1:
    		qfc = 9;
    		break;
    	case 2:
    		qfc = 8;
    		break;
    	case 3:
    		qfc = 7;
    		break;
    	case 4:
    		qfc = 6;
    		break;
    	case 5:
    		qfc = 5;
    		break;
    	case 6:
    		qfc = 4;
    		break;
    	case 7:
    		qfc = 3;
    		break;
    	case 8:
    		qfc = 2;
    		break;
    	case 9:
    		qfc = 1;
    		break;
    	}
    	switch(acol) {
    	case 0:
    		acol = 10;
    		break;
    	case 1:
    		acol = 9;
    		break;
    	case 2:
    		acol = 8;
    		break;
    	case 3:
    		acol = 7;
    		break;
    	case 4:
    		acol = 6;
    		break;
    	case 5:
    		acol = 5;
    		break;
    	case 6:
    		acol = 4;
    		break;
    	case 7:
    		acol = 3;
    		break;
    	case 8:
    		acol = 2;
    		break;
    	case 9:
    		acol = 1;
    		break;
    	}*/
    	
    	x = 10-x;
    	y = y+1;
    	qfr = 10 - qfr;
    	qfc = qfc+1;
    	arow = 10-arow;
    	acol=acol+1;
    	
    	
	int[] qf = new int[2];
	qf[0] = qfr;
	qf[1] = qfc;

	int[] qn = new int[2];
	qn[0] = x;
	qn[1] = y;

	int[] ar = new int[2];
	ar[0] = arow;
	ar[1] = acol;
	
	
	
	

	//To send a move message, call this method with the required data  
	this.gameClient.sendMoveMessage(qf, qn, ar);
	
	//Task: Replace the above with a timed task to wait for 10 seconds befor sending the move
	
    }
	  
    //set up the game board
    private void setupGUI(){
	    guiFrame = new JFrame();
		   
		guiFrame.setSize(800, 600);
		guiFrame.setTitle("Game of the Amazons (COSC 322, )" + this.userName());	
		
		guiFrame.setLocation(200, 200);
		guiFrame.setVisible(true);
	    guiFrame.repaint();		
		guiFrame.setLayout(null);
		
		Container contentPane = guiFrame.getContentPane();
		contentPane.setLayout(new  BorderLayout());
		 
		contentPane.add(Box.createVerticalGlue()); 
		
		board = createGameBoard();		
		contentPane.add(board,  BorderLayout.CENTER);
    }
    
    private GameBoard createGameBoard(){
	return new GameBoard(this);
    }	
		
    public boolean handleMessage(String msg) {
	System.out.println("Time Out ------ " + msg); 
	return true;
    }

    @Override
    public String userName() { 
	return usrName;
    }
	

    /**
     * The game board
     * 
     * @author yongg
     *
     */
    public class GameBoard extends JPanel{

	    private static final long serialVersionUID = 1L;
	    private  int rows = 10;
	    private  int cols = 10; 

	    int width = 500;
	    int height = 500;
	    int cellDim = width / 10; 
	    int offset = width / 20;

	    int posX = -1;
	    int posY = -1;

	    int r = 0;
	    int c = 0;


	    Amazons game = null; 
	private BoardGameModel gameModel = null;

	    boolean playerAMove;

	    public GameBoard(Amazons game){
	    this.game = game;	       
	    gameModel = new BoardGameModel(this.rows + 1, this.cols + 1);

	    //if(!game.isGamebot){
		    addMouseListener(new  GameEventHandler());
	    //}
	    init(true);	
	    }


	    public void init(boolean isPlayerA){
	    String tagB = null;
	    String tagW = null;

	    tagB = BoardGameModel.POS_MARKED_BLACK;
	    tagW = BoardGameModel.POS_MARKED_WHITE;

	    gameModel.gameBoard[1][4] = tagW;
	    gameModel.gameBoard[1][7] = tagW;
	    gameModel.gameBoard[3][1] = tagW;
	    gameModel.gameBoard[3][10] = tagW;

	    gameModel.gameBoard[8][1] = tagB;
	    gameModel.gameBoard[8][10] = tagB;
	    gameModel.gameBoard[10][4] = tagB;
	    gameModel.gameBoard[10][7] = tagB;		
	    }


	    /**
	     * repaint the part of the board
	     * @param qrow queen row index
	     * @param qcol queen col index 
	     * @param arow arrow row index
     * @param acol arrow col index
     * @param qfr queen original row
     * @param qfc queen original col
	     */
	    public boolean markPosition(int qrow, int qcol, int arow, int acol, 
			      int qfr, int qfc, boolean  opponentMove){						

		    System.out.println(qrow + ", " + qcol + ", " + arow + ", " + acol 
				    + ", " + qfr + ", " + qfc);

		    boolean valid = gameModel.positionMarked(qrow, qcol, arow, acol, qfr, qfc, opponentMove);
		    repaint();						
		    return valid;
	    }

	    // JCmoponent method
	    protected void paintComponent(Graphics gg){
		    Graphics g = (Graphics2D) gg;

		    for(int i = 0; i < rows + 1; i++){
			    g.drawLine(i * cellDim + offset, offset, i * cellDim + offset, rows * cellDim + offset);
			    g.drawLine(offset, i*cellDim + offset, cols * cellDim + offset, i*cellDim + offset);					 
		    }

		    for(int r = 0; r < rows; r++){
		      for(int c = 0; c < cols; c++){

				    posX = c * cellDim + offset;
				    posY = r * cellDim + offset;

				    posY = (9 - r) * cellDim + offset;

			    if(gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_AVAILABLE)){
				    g.clearRect(posX + 1, posY + 1, 49, 49);					
			    }

			    if(gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(
					      BoardGameModel.POS_MARKED_BLACK)){
				    g.fillOval(posX, posY, 50, 50);
			    } 
			    else if(gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(
				      BoardGameModel.POS_MARKED_ARROW)) {
				    g.clearRect(posX + 1, posY + 1, 49, 49);
				    g.drawLine(posX, posY, posX + 50, posY + 50);
				    g.drawLine(posX, posY + 50, posX + 50, posY);
			    }
			    else if(gameModel.gameBoard[r + 1][c + 1].equalsIgnoreCase(BoardGameModel.POS_MARKED_WHITE)){
				    g.drawOval(posX, posY, 50, 50);
			    }
		      }
	    }

	    }//method

	    //JComponent method
	    public Dimension getPreferredSize() {
		    return new Dimension(500,500);
	     }

	    /**
	     * Handle mouse events
	     * 
	     * @author yongg
	     */
	    public class GameEventHandler extends MouseAdapter{

			int counter = 0;

			int qrow = 0;
			int qcol = 0;

			int qfr = 0;
			int qfc = 0;

			int arow = 0;
			int acol = 0; 

		public void mousePressed(MouseEvent e) {

		    if(!gameStarted){
			    //return; 
		    }

		int x = e.getX();
		int y = e.getY();


		if(((x - offset) < -5) || ((y - offset) < -5)){
		    return;
		}

		int row = (y - offset) / cellDim + 1;                        
		int col = (x - offset) / cellDim + 1;

		if(counter == 0){
		    qfr = row;
		    qfc = col;

		    qfr = 11 - qfr;
		    counter++;
		}
		else if(counter ==1){
		    qrow = row;
		    qcol = col;

		    qrow = 11 - qrow;
		    counter++;
		}
		else if (counter == 2){
		    arow = row;
		    acol = col;

		    arow = 11 - arow;
		    counter++;
		}

		if(counter == 3){
		  counter = 0; 	
		  boolean validMove = markPosition(qrow, qcol, arow, acol, qfr, qfc, false); // update itself

		  if(validMove){
		    //game.playerMove(qrow, qcol, arow, acol, qfr, qfc); //to server
		  }

		  qrow = 0;
		  qcol = 0;
		  arow = 0;
		  acol = 0;

		}
		}			 
	     }//end of GameEventHandler		

    }//end of GameBoard  

    class BoardGameModel extends GameModel {

	public static final String POS_MARKED_BLACK = "black";
	public static final String POS_MARKED_WHITE = "white";
	public static final String POS_MARKED_ARROW = "arrow";
	public static final String POS_AVAILABLE = "available";
	
	String[][] gameBoard = null; 
	//int[][] posScores = null;
	
	
	/**
	 * @param rows
	 * @param columns
	 */
	public BoardGameModel(int rows, int columns){
		
		gameBoard = new String[rows + 1][columns + 1];
		//posScores = new int[rows][columns];
		for(int i = 1; i < rows + 1; i++){
			for(int j = 1; j < columns + 1; j++){
				gameBoard[i][j] = BoardGameModel.POS_AVAILABLE;
				//posScores[i][j] = 0;
			}
		}
	}
	
	
	public boolean positionMarked(int row, int column, int arow, int acol,
			 int qfr, int qfc, boolean opponentMove){
		boolean valid = true;
		
 
		
		if(row >= gameBoard.length | column >= gameBoard.length 
				 || row <= 0 || column <= 0){
			valid = false;
		}
		else if (!gameBoard[row][column].equalsIgnoreCase(BoardGameModel.POS_AVAILABLE)){
			valid = false;
		}
        
		if(valid){
			gameBoard[row][column] = gameBoard[qfr][qfc];		
			gameBoard[qfr][qfc] = BoardGameModel.POS_AVAILABLE;		
			gameBoard[arow][acol] = BoardGameModel.POS_MARKED_ARROW;
		}
		
		//System.out.println(this.toString());
		
		return valid;
	}	
	
	public String toString(){
      String b = null;

      for(int i = 1; i < 11; i++){
	      for(int j = 1; j< 11; j++){
		b = b + gameBoard[i][j] + " ";
	      }
	      b = b + "\n";
      }  	  
      return b;
    }	
    }
	
    class MyTimer extends TimerTask{
	GameClient gameClient = null;
	int[] qf;
	int[] qn;
	int[] ar;
	
	public MyTimer(GameClient gameClient, int[] qf, int[] qn, int[] ar){	
	    this.gameClient = gameClient;
	    this.qf = qf;
	    this.qn = qn;
	    this.ar = ar;
	}
		
	/**
	 * send the move 
	 */
	public void run() {
		gameClient.sendMoveMessage(qf, qn, ar);
	}
    }
    
    /**
     * Constructor 
     * @param args
     */
    public static void main(String[] args) { 
	Amazons game01 = new Amazons("Hippolyta", "01");
	Amazons game02 = new Amazons("player-02", "02");
	//Amazons game = new Amazons(args[0], args[1]);		
    }
}//end of Amazon
