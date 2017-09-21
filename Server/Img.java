package Server;
import java.awt.Image;
import java.awt.Toolkit;


public class Img {
	//For use in all my two dimensional coding endeavors
	private int x = 0;
	private int y = 0;
	private int farX = 0;
	private int farY = 0;
	private Image image;
	private boolean alive = false;
	private Toolkit tk = Toolkit.getDefaultToolkit();
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getFarX(){return farX;}
	public int getFarY(){return farY;}
	public Image getImg(){return image;}
	public boolean isAlive(){return alive;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setFarX(int farX){this.farX = farX;}
	public void setFarY(int farY){this.farY = farY;}
	public void setImg(String a){image = tk.getImage(getClass().getResource(a));}
	public void setAlive(boolean alive){this.alive = alive;}
	
	public Img(int x, int y, int farX, int farY, String image){
		setX(x);
		setY(y);
		setFarX(farX);
		setFarY(farY);
		setImg(image);
	}
	
	public Img(int x, int y, String image){
		setX(x);
		setY(y);
		setImg(image);
	}
	
	public Img(String image){
		setImg(image);
	}
}
