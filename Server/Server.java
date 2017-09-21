package Server;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

@SuppressWarnings("serial")
public class Server extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener, ActionListener{

	Random rand = new Random();

	Thread gameloop;
	BufferedImage backbuffer;
	static Graphics2D g2d;
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	static int sWidth = 500;
	static int sHeight = 500;
	
	AffineTransform identity = new AffineTransform();
	
	int gameCount = 5;
//	Img bg = new Img("bg.png");
	//Font font = new Font();
	
	ClientManager cm = new ClientManager();
	
	public void init(){
		setSize(sWidth,sHeight);
		backbuffer = new BufferedImage (sWidth, sHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
	//	bg.setAlive(true);
		
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}	
	
	public void drawImg(Img img){
		g2d.setTransform(identity);
		if(img.isAlive())
			g2d.drawImage(img.getImg(),img.getX(),img.getY(),this);
	}
	
	public void paint(Graphics g){
		g.drawImage(backbuffer, 0, 0,this);	
	}
	
	public void update(Graphics g){		
		paint(g);
		//drawImg(bg);
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
	
	public void updateGame(){}
	
	public void mouseClicked(MouseEvent arg0){}
	public void mouseMoved(MouseEvent arg0){}
	
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mouseDragged(MouseEvent arg0){}
	
	
	public void keyPressed(KeyEvent k){
		int KeyCode = k.getKeyCode();
		if(KeyCode == KeyEvent.VK_A){
			cm.startRunning();
		}
	}
	
	public void keyReleased(KeyEvent k){}
	public void keyTyped(KeyEvent k){}

	public void actionPerformed(ActionEvent e){}
	
	/*Font Drawing Functions*/
	
}
