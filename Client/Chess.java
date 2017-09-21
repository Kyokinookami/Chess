package Client;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

@SuppressWarnings("serial")
public class Chess extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener, ActionListener{

	Random rand = new Random();

	Thread gameloop;
	BufferedImage backbuffer;
	static Graphics2D g2d;
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	static int sWidth = 400;
	static int sHeight = 450;
	
	private boolean inGame = false;
	public boolean isInGame(){return inGame;}
	public void setInGame(boolean inGame){this.inGame = inGame;}
	GUI gui = new GUI();
	Font font = new Font();
	
	TextArea ip = new TextArea(41,58,86,78,3);
	TextArea sip = new TextArea(26,18,86,38,4);
	TextArea ipaddress = new TextArea(gui.ip.getX()+2,gui.ip.getY()+2,gui.ip.getFarX()-2,gui.ip.getFarY()-2,15);
	TextArea serverIP = new TextArea(gui.serverIP.getX()+2,gui.serverIP.getY()+2,gui.serverIP.getFarX()-2,gui.serverIP.getFarY()-2,15);
	TextArea play = new TextArea(gui.play.getX()+2,gui.play.getY()+2,gui.play.getFarX()-2,gui.play.getFarY()-2,4);
	TextArea classic = new TextArea(gui.classic.getX()+2,gui.classic.getY()+2,gui.classic.getFarX()-2,gui.classic.getFarY()-2,7);
	TextArea rvb = new TextArea(gui.rvb.getX()+2,gui.rvb.getY()+2,gui.rvb.getFarX()-2,gui.rvb.getFarY()-2,11);
	TextArea regal = new TextArea(gui.regal.getX()+2,gui.regal.getY()+2,gui.regal.getFarX()-2,gui.regal.getFarY()-2,6);
	TextArea leave = new TextArea(163,16,237,35,5);
	
	MoveAnalyzer ma = new MoveAnalyzer();
	Game gg = new Game();
	
	AffineTransform identity = new AffineTransform();
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static String move = "";
	private Socket connection;
	private int port = 12121;
	
	private static boolean yourTurn = false;
	private boolean send = false;
	private static boolean putPiece = false;
	private static boolean white = false;
	private boolean canConnect = false;
	private boolean isConnected = false;
	private boolean isFirst = true;
	private static boolean promoting = false;
	
	public boolean isYourTurn(){return yourTurn;}
	public boolean canSend(){return send;}
	public boolean hasPutPiece(){return putPiece;}
	public static boolean isWhite(){return white;}
	public boolean isPromoting(){return promoting;}
	public String getMove(){return move;}
	
	public static void setMove(String move1){move = move1;}
	public static void setHasPutPiece(boolean putPiece1){putPiece = putPiece1;}
	public static void setYourTurn(boolean yourTurn1){yourTurn = yourTurn1;}
	public static void setPromoting(boolean promoting1){promoting = promoting1;}
	
	public void init(){
		setSize(sWidth,sHeight);
		backbuffer = new BufferedImage (sWidth, sHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
		ip.setText("IP:");
		sip.setText("Serv");
		play.setText("Play");
		classic.setText("Classic");
		rvb.setText("Red VS. Blue");
		regal.setText("Regal");
		leave.setText("Leave");
		
		ipaddress.setText("localhost");
		serverIP.setText("localhost");
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}	
	
	public void drawImg(Img img){
		g2d.setTransform(identity);
		if(img.isAlive())
			g2d.drawImage(img.getImg(),img.getX(),img.getY(),this);
	}
	
	public void drawImg(Img img, int x, int y){
		g2d.setTransform(identity);
		//if(img.isAlive())
			g2d.drawImage(img.getImg(), x, y, this);
	}
	
	public void paint(Graphics g){
		g.drawImage(backbuffer, 0, 0,this);	
	}
	
	public void update(Graphics g){
		paint(g);
		if(gg.hasLost(white, gui) && gui.board[7][7].isAlive()){
			leave.setText("You lose");
			leave.setX(140);
			leave.setY(15);
			leave.setFarX(260);
			leave.setFarY(35);
			leave.setCharLimit(8);
		}
		if(gg.hasLost(!white, gui) && gui.board[7][7].isAlive()){
			leave.setText("You win");
			leave.setX(148);
			leave.setY(15);
			leave.setFarX(253);
			leave.setFarY(35);
			leave.setCharLimit(7);
		}
		
		
		for(int x = 0;x < gui.board.length;x++){
			for(int y = 0;y < gui.board[x].length;y++){
				drawImg(gui.board[x][y]);
			}
		}
		
		drawMoveAndAttack();
		
		drawImg(gui.main);
		drawImg(gui.ip);
		drawImg(gui.serverIP);
		drawImg(gui.play);
		drawImg(gui.classic);
		drawImg(gui.rvb);
		drawImg(gui.regal);
		drawImg(gui.leave);
		
		for(int x = 0;x < gui.rPawn.length;x++)
			drawImg(gui.rPawn[x]);
		for(int x = 0;x < gui.lPawn.length;x++)
			drawImg(gui.lPawn[x]);
		for(int x = 0;x < gui.rRook.length;x++)
			drawImg(gui.rRook[x]);
		for(int x = 0;x < gui.lRook.length;x++)
			drawImg(gui.lRook[x]);
		for(int x = 0;x < gui.rBishop.length;x++)
			drawImg(gui.rBishop[x]);
		for(int x = 0;x < gui.lBishop.length;x++)
			drawImg(gui.lBishop[x]);
		for(int x = 0;x < gui.rKnight.length;x++)
			drawImg(gui.rKnight[x]);
		for(int x = 0;x < gui.lKnight.length;x++)
			drawImg(gui.lKnight[x]);
		for(int x = 0;x < gui.rQueen.length;x++)
			drawImg(gui.rQueen[x]);
		for(int x = 0;x < gui.lQueen.length;x++)
			drawImg(gui.lQueen[x]);
		drawImg(gui.rKing);
		drawImg(gui.lKing);
		
		drawImg(gui.promotion);
		drawImg(gui.prKnight);
		drawImg(gui.prRook);
		drawImg(gui.prBishop);
		drawImg(gui.prQueen);
		
		drawImg(gui.plKnight);
		drawImg(gui.plRook);
		drawImg(gui.plBishop);
		drawImg(gui.plQueen);
		
		drawString(ip.getText(),ip,font);
		drawString(sip.getText(),sip,font);
		drawString(ipaddress.getText(),ipaddress,font);
		drawString(serverIP.getText(),serverIP,font);
		drawString(play.getText(),play,font);
		drawString(classic.getText(),classic,font);
		drawString(rvb.getText(),rvb,font);
		drawString(regal.getText(),regal,font);
		drawString(leave.getText(),leave,font);
		
		drawImg(gui.overlay);
	}
	
	public void start(){
		gameloop = new Thread(this);
		gameloop.start();
	}
	public void run(){
		Thread t = Thread.currentThread();
		while(t == gameloop){
			try{
				updateGame();
				Thread.sleep(20);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void stop(){
		gameloop = null;
	}
	
	public void updateGame(){
		gui.update();
		if(inGame){
			gui.main.setAlive(false);
			gui.ip.setAlive(false);
			gui.serverIP.setAlive(false);
			gui.play.setAlive(false);
			gui.classic.setAlive(false);
			gui.rvb.setAlive(false);
			gui.regal.setAlive(false);
			gui.leave.setAlive(true);
			
			for(int x = 0;x < gui.board.length;x++){
				for(int y = 0;y < gui.board[x].length;y++){
					gui.board[x][y].setAlive(true);
				}
			}
		}
		else{
			gui.main.setAlive(true);
			gui.ip.setAlive(true);
			gui.serverIP.setAlive(true);
			gui.play.setAlive(true);
			gui.classic.setAlive(true);
			gui.rvb.setAlive(true);
			gui.regal.setAlive(true);
			gui.leave.setAlive(false);
			
			for(int x = 0;x < gui.board.length;x++){
				for(int y = 0;y < gui.board[x].length;y++){
					gui.board[x][y].setAlive(false);
				}
			}
		}
		ip.setAlive(gui.ip.isAlive());
		sip.setAlive(gui.serverIP.isAlive());
		ipaddress.setAlive(gui.ip.isAlive());
		serverIP.setAlive(gui.serverIP.isAlive());
		play.setAlive(gui.play.isAlive());
		classic.setAlive(gui.classic.isAlive());
		rvb.setAlive(gui.rvb.isAlive());
		regal.setAlive(gui.regal.isAlive());
		leave.setAlive(gui.leave.isAlive());
		if(gui.style == "regal")
			gui.overlay.setAlive(true);
		else
			gui.overlay.setAlive(false);
		
		if(canConnect){
			try {
				connection = new Socket(InetAddress.getByName(serverIP.getText()), port);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());}
			catch (IOException e){e.printStackTrace();}
			canConnect = false;
			isConnected = true;
		}
		
		if(isConnected){
			sendMove(ipaddress.getText());
			setMove("");
			while(getMove() == "")
				setMove(readMove());
			if(getMove().equals("BLACK")){
				yourTurn = false;
				white = false;
				update(g2d);
			}
			else if(getMove().equals("WHITE")){
				yourTurn = true;
				white = true;
			}
			gg.setBoard(gui);
			isConnected = false;
			inGame = true;
		}
		
		if(inGame){
			if(move != "STOP"){
				if(yourTurn && putPiece || yourTurn && gg.hasPromoted()){
					if(!isPromoting() && !gg.hasPromoted()){
						setMove(gg.getMove());
						sendMove(getMove());
						//sends  the move to the server to get to the other client
						putPiece = false;
					}
					
					if(!gui.promotion.isAlive()){
						yourTurn = (yourTurn) ? false : true;
						setPromoting(false);
						gg.setPromoted(false);
					}
					else{
						setPromoting(true);
						if(gg.hasPromoted()){
							setMove(gg.getMove());
							sendMove(getMove());
							gg.turnPromotionOff(gui);
						}							
					}
					//sets the yourTurn to be false
					isFirst = true;
					//helps it update the graphics properly
					if(leave.getText() == "You win" || leave.getText() == "You lose"){
						setMove("STOP");
						sendMove(getMove());
					}
				}
				else if(!yourTurn){
					setMove("");
					if(!isFirst)
					setMove(readMove());
					//gets the move from the server from the other player
					if(getMove() != ""){
				//		System.out.println(getMove() + " Received");
						gg.movePiece(getMove(),gui,ma);
						yourTurn = (yourTurn) ? false : true;
						if(getMove().length() >= 1){
							if(getMove().toCharArray()[getMove().length()-1] == '-'){
								yourTurn = (yourTurn) ? false : true;
							}
						}
						//sets the yourTurn to be true
					}
					//sets the piece from the move it just got
					
					isFirst = false;
					if(getMove().length() >= 1){
						if(getMove().toCharArray()[getMove().length()-1] == '-'){
							isFirst = true;
						}
					}
					//this boolean helps the client that is waiting for a move to update its graphics
					if(leave.getText() == "You win" || leave.getText() == "You lose"){
						setMove("STOP");
						sendMove(getMove());
					}
				}
			}
			else{
				try{
					input.close();
					output.close();
					connection.close();
				}catch(Exception e){e.printStackTrace();}
			}
		}
	}
	
	public void mouseClicked(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}
	
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){
		if(arg0.getX() < play.getFarX() && arg0.getX() > play.getX() &&
			arg0.getY() < play.getFarY() && arg0.getY() > play.getY() && play.isAlive()){
			canConnect = true;
		}
		if(arg0.getX() < ipaddress.getFarX() && arg0.getX() > ipaddress.getX() && 
			arg0.getY() < ipaddress.getFarY() && arg0.getY() > ipaddress.getY() && ipaddress.isAlive())
			ipaddress.setWrite(true);
		else
			ipaddress.setWrite(false);
		if(arg0.getX() < serverIP.getFarX() && arg0.getX() > serverIP.getX() && 
				arg0.getY() < serverIP.getFarY() && arg0.getY() > serverIP.getY() && serverIP.isAlive())
				serverIP.setWrite(true);
		else
			serverIP.setWrite(false);
		if(yourTurn && leave.getText() != "You win" && leave.getText() != "You lose")
			gg.mouseReleased(arg0, gui, white, ma);
		gui.mouseReleased(arg0);
		
	}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseDragged(MouseEvent arg0){}
	
	
	public void keyPressed(KeyEvent k){
		/*if(KeyCode == KeyEvent.VK_S){
			try{
				sm.writeToFile("/Test.map",hex);
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		if(KeyCode == KeyEvent.VK_L){
			lm.load("/Test.map",hex);
		}*/
	}
	
	public void keyReleased(KeyEvent k){
		inputString(k,ipaddress,ipaddress.getCharLimit(),false);
		inputString(k,serverIP,serverIP.getCharLimit(),false);
	}
	public void keyTyped(KeyEvent k){}

	public void actionPerformed(ActionEvent e){}
	
	private void sendMove(String move){
		try{
			output.writeObject(move);
			output.flush();
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	private String readMove(){
		String temp = "";
		try{
			temp = input.readObject().toString();
		}catch(ClassNotFoundException e){e.printStackTrace();}
		catch(IOException e){e.printStackTrace();}
		
		return temp;
	}
	
	public void drawMoveAndAttack(){
		for(int a = 0;a < gui.board.length;a++){
			for(int b = 0;b < gui.board[a].length;b++){
				for(int x = 0; x < gui.rPawn.length;x++){
					if(gui.rPawn[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.rPawn[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canAttack(gui, gui.board[a][b], gui.rPawn[x])){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.rRook.length;x++){
					if(gui.rRook[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.rRook[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.rRook[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.rRook[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rRook[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.rRook[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.rRook[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rRook[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.rBishop.length;x++){
					if(gui.rBishop[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.rBishop[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.rBishop[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.rBishop[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rBishop[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.rBishop[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.rBishop[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rBishop[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.rKnight.length;x++){
					if(gui.rKnight[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.rKnight[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.rKnight[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.rKnight[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rKnight[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.rKnight[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.rKnight[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rKnight[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.rQueen.length;x++){
					if(gui.rQueen[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.rQueen[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.rQueen[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.rQueen[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rQueen[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.rQueen[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.rQueen[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.rQueen[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				if(gui.rKing.isMoving()){
					if(gg.canMove(gui, gui.board[a][b], gui.rKing) && gg.spaceEmpty(gui, gui.board[a][b]) ||
						gg.canCastle(gui,gui.board[a][b],gui.rKing)){
						drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
					}
					else if(gg.canMove(gui,gui.board[a][b],gui.rKing) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							gg.isPieceWhite(gui,gui.rKing) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.rKing) ||
							
							gg.canMove(gui,gui.board[a][b],gui.rKing) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							!gg.isPieceWhite(gui,gui.rKing) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.rKing) ){
						drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
					}
				}
				for(int x = 0; x < gui.lPawn.length;x++){
					if(gui.lPawn[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.lPawn[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canAttack(gui, gui.board[a][b], gui.lPawn[x])){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.lRook.length;x++){
					if(gui.lRook[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.lRook[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.lRook[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.lRook[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lRook[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.lRook[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.lRook[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lRook[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.lBishop.length;x++){
					if(gui.lBishop[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.lBishop[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.lBishop[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							gg.isPieceWhite(gui,gui.lBishop[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.lBishop[x]) ||
							
							gg.canMove(gui,gui.board[a][b],gui.lBishop[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							!gg.isPieceWhite(gui,gui.lBishop[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.lBishop[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.lKnight.length;x++){
					if(gui.lKnight[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.lKnight[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.lKnight[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.lKnight[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lKnight[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.lKnight[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.lKnight[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lKnight[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				for(int x = 0; x < gui.lQueen.length;x++){
					if(gui.lQueen[x].isMoving()){
						if(gg.canMove(gui, gui.board[a][b], gui.lQueen[x]) && gg.spaceEmpty(gui, gui.board[a][b])){
							drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
						else if(gg.canMove(gui,gui.board[a][b],gui.lQueen[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								gg.isPieceWhite(gui,gui.lQueen[x]) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lQueen[x]) ||
								
								gg.canMove(gui,gui.board[a][b],gui.lQueen[x]) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
								!gg.isPieceWhite(gui,gui.lQueen[x]) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
								&& !gg.isPawn(gui.lQueen[x]) ){
							drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
						}
					}
				}
				if(gui.lKing.isMoving()){
					if(gg.canMove(gui, gui.board[a][b], gui.lKing) && gg.spaceEmpty(gui, gui.board[a][b]) ||
						gg.canCastle(gui,gui.board[a][b],gui.lKing)){
						drawImg(gui.move,gui.board[a][b].getX(),gui.board[a][b].getY());
					}
					else if(gg.canMove(gui,gui.board[a][b],gui.lKing) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							gg.isPieceWhite(gui,gui.lKing) && !gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.lKing) ||
							
							gg.canMove(gui,gui.board[a][b],gui.lKing) && !gg.spaceEmpty(gui,gui.board[a][b]) &&
							!gg.isPieceWhite(gui,gui.lKing) && gg.isPieceWhite(gui,gg.whichPiece(gui,gui.board[a][b]))
							&& !gg.isPawn(gui.lKing) ){
						drawImg(gui.attack,gui.board[a][b].getX(),gui.board[a][b].getY());
					}
				}
			}
		}
	}
	
	/*Font Drawing Functions*/
	
	public void inputString(KeyEvent k, TextArea ta, int charLimit, boolean fileRelated){
		char KeyChar = k.getKeyChar();
		int KeyCode = k.getKeyCode();
		if(ta.canWrite()){
			if(KeyCode == KeyEvent.VK_BACK_SPACE || KeyCode == KeyEvent.VK_DELETE){
				ta.setText(ta.getText().substring(0, ta.getText().length()-1));
			}
			else if(ta.getText().length() + 1 <= charLimit){
				if(KeyCode != KeyEvent.VK_SHIFT && KeyCode != KeyEvent.VK_ALT && KeyCode != KeyEvent.VK_CONTROL){
					if(fileRelated){
						if(KeyChar != '\\' && KeyChar != '/' && KeyChar != ':' && KeyChar != '?' &&
						   KeyChar != '<' &&KeyChar != '>' && KeyChar != '*' && KeyChar != '"' && KeyChar != '|'){
							ta.setText(ta.getText() + KeyChar);
						}
					}
					else{
						ta.setText(ta.getText() + KeyChar);
					}
				}
			}
		}
		
	}
	
	public void drawAPIImg(FontImg img, int x, int y){
		g2d.setTransform(identity);
		g2d.drawImage(img.getAPIImg(),x,y,this);
	}
	
	public int charLeftCounter(int recentSpace, String s){
		int z = 0;
		char[] y = s.toCharArray();
		for(int x = recentSpace + 1;x < s.length();x++){
			if(y[x] == ' ' || y[x] == y[s.length()-1]){
				z = x;
				x = s.length();
			}
		}
		return z - recentSpace;
	}
	
	public void drawString(String x, TextArea ta, Font font){
		if(ta.isAlive()){
			int charCounter = -1;
			int height = 0;
			int recentSpace = 0;
			char[] y = x.toCharArray();
			for(int i = 0;i < x.length();i++){
				charCounter++;
				if(y[i] == 'a' || y[i] == 'A'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.a,ta.getX() + font.a.getLength()*charCounter,ta.getY() + font.a.getHeight()*height);
				}
				else if(y[i] == 'b' || y[i] == 'B'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.b,ta.getX() + font.b.getLength()*charCounter,ta.getY() + font.b.getHeight()*height);
				}
				else if(y[i] == 'c' || y[i] == 'C'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.c,ta.getX() + font.c.getLength()*charCounter,ta.getY() + font.c.getHeight()*height);
				}
				else if(y[i] == 'd' || y[i] == 'D'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.d,ta.getX() + font.d.getLength()*charCounter,ta.getY() + font.d.getHeight()*height);
				}
				else if(y[i] == 'e' || y[i] == 'E'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.e,ta.getX() + font.e.getLength()*charCounter,ta.getY() + font.e.getHeight()*height);
				}
				else if(y[i] == 'f' || y[i] == 'F'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.f,ta.getX() + font.f.getLength()*charCounter,ta.getY() + font.f.getHeight()*height);
				}
				else if(y[i] == 'g' || y[i] == 'G'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.g,ta.getX() + font.g.getLength()*charCounter,ta.getY() + font.g.getHeight()*height);
				}
				else if(y[i] == 'h' || y[i] == 'H'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.h,ta.getX() + font.h.getLength()*charCounter,ta.getY() + font.h.getHeight()*height);
				}
				else if(y[i] == 'i' || y[i] == 'I'){
					drawAPIImg(font.i,ta.getX() + font.i.getLength()*charCounter,ta.getY() + font.i.getHeight()*height);
				}
				else if(y[i] == 'j' || y[i] == 'J'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.j,ta.getX() + font.j.getLength()*charCounter,ta.getY() + font.j.getHeight()*height);
				}
				else if(y[i] == 'k' || y[i] == 'K'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.k,ta.getX() + font.k.getLength()*charCounter,ta.getY() + font.k.getHeight()*height);
				}
				else if(y[i] == 'l' || y[i] == 'L'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.l,ta.getX() + font.l.getLength()*charCounter,ta.getY() + font.l.getHeight()*height);
				}
				else if(y[i] == 'm' || y[i] == 'M'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.m,ta.getX() + font.m.getLength()*charCounter,ta.getY() + font.m.getHeight()*height);
				}
				else if(y[i] == 'n' || y[i] == 'N'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.n,ta.getX() + font.n.getLength()*charCounter,ta.getY() + font.n.getHeight()*height);
				}
				else if(y[i] == 'o' || y[i] == 'O'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.o,ta.getX() + font.o.getLength()*charCounter,ta.getY() + font.o.getHeight()*height);
				}
				else if(y[i] == 'p' || y[i] == 'P'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.p,ta.getX() + font.p.getLength()*charCounter,ta.getY() + font.p.getHeight()*height);
				}
				else if(y[i] == 'q' || y[i] == 'Q'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.q,ta.getX() + font.q.getLength()*charCounter,ta.getY() + font.q.getHeight()*height);
				}
				else if(y[i] == 'r' || y[i] == 'R'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.r,ta.getX() + font.r.getLength()*charCounter,ta.getY() + font.r.getHeight()*height);
				}
				else if(y[i] == 's' || y[i] == 'S'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.s,ta.getX() + font.s.getLength()*charCounter,ta.getY() + font.s.getHeight()*height);
				}
				else if(y[i] == 't' || y[i] == 'T'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.t,ta.getX() + font.t.getLength()*charCounter,ta.getY() + font.t.getHeight()*height);
				}
				else if(y[i] == 'u' || y[i] == 'U'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.u,ta.getX() + font.u.getLength()*charCounter,ta.getY() + font.u.getHeight()*height);
				}
				else if(y[i] == 'v' || y[i] == 'V'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.v,ta.getX() + font.v.getLength()*charCounter,ta.getY() + font.v.getHeight()*height);
				}
				else if(y[i] == 'w' || y[i] == 'W'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.w,ta.getX() + font.w.getLength()*charCounter,ta.getY() + font.w.getHeight()*height);
				}
				else if(y[i] == 'x' || y[i] == 'X'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.x,ta.getX() + font.x.getLength()*charCounter,ta.getY() + font.x.getHeight()*height);
				}
				else if(y[i] == 'y' || y[i] == 'Y'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.y,ta.getX() + font.y.getLength()*charCounter,ta.getY() + font.y.getHeight()*height);
				}
				else if(y[i] == 'z' || y[i] == 'Z'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.z,ta.getX() + font.z.getLength()*charCounter,ta.getY() + font.z.getHeight()*height);
				}
				else if(y[i] == '0'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.zero,ta.getX() + font.zero.getLength()*charCounter,ta.getY() + font.zero.getHeight()*height);
				}
				else if(y[i] == '1'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.one,ta.getX() + font.one.getLength()*charCounter,ta.getY() + font.one.getHeight()*height);
				}
				else if(y[i] == '2'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.two,ta.getX() + font.two.getLength()*charCounter,ta.getY() + font.two.getHeight()*height);
				}
				else if(y[i] == '3'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.three,ta.getX() + font.three.getLength()*charCounter,ta.getY() + font.three.getHeight()*height);
				}
				else if(y[i] == '4'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.four,ta.getX() + font.four.getLength()*charCounter,ta.getY() + font.four.getHeight()*height);
				}
				else if(y[i] == '5'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.five,ta.getX() + font.five.getLength()*charCounter,ta.getY() + font.five.getHeight()*height);
				}
				else if(y[i] == '6'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.six,ta.getX() + font.six.getLength()*charCounter,ta.getY() + font.six.getHeight()*height);
				}
				else if(y[i] == '7'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.seven,ta.getX() + font.seven.getLength()*charCounter,ta.getY() + font.seven.getHeight()*height);
				}
				else if(y[i] == '8'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.eight,ta.getX() + font.eight.getLength()*charCounter,ta.getY() + font.eight.getHeight()*height);
				}
				else if(y[i] == '9'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.nine,ta.getX() + font.nine.getLength()*charCounter,ta.getY() + font.nine.getHeight()*height);
				}
				else if(y[i] == '~'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.tilde,ta.getX() + font.tilde.getLength()*charCounter,ta.getY() + font.tilde.getHeight()*height);
				}
				else if(y[i] == '!'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.exclamationPoint,ta.getX() + font.exclamationPoint.getLength()*charCounter,ta.getY() + font.exclamationPoint.getHeight()*height);
				}
				else if(y[i] == '@'){
					drawAPIImg(font.at,ta.getX() + font.at.getLength()*charCounter,ta.getY() + font.at.getHeight()*height);
				}
				else if(y[i] == '#'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.hashtag,ta.getX() + font.hashtag.getLength()*charCounter,ta.getY() + font.hashtag.getHeight()*height);
				}
				else if(y[i] == '$'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.dollarSign,ta.getX() + font.dollarSign.getLength()*charCounter,ta.getY() + font.dollarSign.getHeight()*height);
				}
				else if(y[i] == '%'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.percent,ta.getX() + font.percent.getLength()*charCounter,ta.getY() + font.percent.getHeight()*height);
				}
				else if(y[i] == '^'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.carot,ta.getX() + font.carot.getLength()*charCounter,ta.getY() + font.carot.getHeight()*height);
				}
				else if(y[i] == '&'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.ampersand,ta.getX() + font.ampersand.getLength()*charCounter,ta.getY() + font.ampersand.getHeight()*height);
				}
				else if(y[i] == '*'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.asterisk,ta.getX() + font.asterisk.getLength()*charCounter,ta.getY() + font.asterisk.getHeight()*height);
				}
				else if(y[i] == '('){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.openingParen,ta.getX() + font.openingParen.getLength()*charCounter,ta.getY() + font.openingParen.getHeight()*height);
				}
				else if(y[i] == ')'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.closingParen,ta.getX() + font.closingParen.getLength()*charCounter,ta.getY() + font.closingParen.getHeight()*height);
				}
				else if(y[i] == '_'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.underScore,ta.getX() + font.underScore.getLength()*charCounter,ta.getY() + font.underScore.getHeight()*height);
				}
				else if(y[i] == '+'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.plus,ta.getX() + font.plus.getLength()*charCounter,ta.getY() + font.plus.getHeight()*height);
				}
				else if(y[i] == '-'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.minus,ta.getX() + font.minus.getLength()*charCounter,ta.getY() + font.minus.getHeight()*height);
				}
				else if(y[i] == '='){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.equal,ta.getX() + font.equal.getLength()*charCounter,ta.getY() + font.equal.getHeight()*height);
				}
				else if(y[i] == '['){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.openingBracket,ta.getX() + font.openingBracket.getLength()*charCounter,ta.getY() + font.openingBracket.getHeight()*height);
				}
				else if(y[i] == ']'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.closingBracket,ta.getX() + font.closingBracket.getLength()*charCounter,ta.getY() + font.closingBracket.getHeight()*height);
				}
				else if(y[i] == '{'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.openingCurly,ta.getX() + font.openingCurly.getLength()*charCounter,ta.getY() + font.openingCurly.getHeight()*height);
				}
				else if(y[i] == '}'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.closingCurly,ta.getX() + font.closingCurly.getLength()*charCounter,ta.getY() + font.closingCurly.getHeight()*height);
				}
				else if(y[i] == '\\'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.backSlash,ta.getX() + font.backSlash.getLength()*charCounter,ta.getY() + font.backSlash.getHeight()*height);
				}
				else if(y[i] == '|'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.verticalBar,ta.getX() + font.verticalBar.getLength()*charCounter,ta.getY() + font.verticalBar.getHeight()*height);
				}
				else if(y[i] == ';'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.semiColon,ta.getX() + font.semiColon.getLength()*charCounter,ta.getY() + font.semiColon.getHeight()*height);
				}
				else if(y[i] == ':'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.colon,ta.getX() + font.colon.getLength()*charCounter,ta.getY() + font.colon.getHeight()*height);
				}
				else if(y[i] == '\''){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.apostrophe,ta.getX() + font.apostrophe.getLength()*charCounter,ta.getY() + font.apostrophe.getHeight()*height);
				}
				else if(y[i] == '"'){
					drawAPIImg(font.quotationMark,ta.getX() + font.quotationMark.getLength()*charCounter,ta.getY() + font.quotationMark.getHeight()*height);
				}
				else if(y[i] == '/'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.forwardSlash,ta.getX() + font.forwardSlash.getLength()*charCounter,ta.getY() + font.forwardSlash.getHeight()*height);
				}
				else if(y[i] == ','){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.comma,ta.getX() + font.comma.getLength()*charCounter,ta.getY() + font.comma.getHeight()*height);
				}
				else if(y[i] == '.'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.period,ta.getX() + font.period.getLength()*charCounter,ta.getY() + font.period.getHeight()*height);
				}
				else if(y[i] == '<'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.openingChevron,ta.getX() + font.openingChevron.getLength()*charCounter,ta.getY() + font.openingChevron.getHeight()*height);
				}
				else if(y[i] == '>'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.closingChevron,ta.getX() + font.closingChevron.getLength()*charCounter,ta.getY() + font.closingChevron.getHeight()*height);
				}
				else if(y[i] == '?'){
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.questionMark,ta.getX() + font.questionMark.getLength()*charCounter,ta.getY() + font.questionMark.getHeight()*height);
				}
				else if(y[i] == ' '){
					recentSpace = i;
				}
				else{
					if(recentSpace == i-1 && (charLeftCounter(recentSpace, x) + charCounter)*15 > ta.findLength()){
						height++;
						charCounter = 0;
					}
					drawAPIImg(font.lolwat,ta.getX() + font.lolwat.getLength()*charCounter,ta.getY() + font.lolwat.getHeight()*height);
				}
			}
		}
	}
	
}
