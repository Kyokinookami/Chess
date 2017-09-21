package Server;

public class TextArea {
	private boolean alive = false;
	private boolean write = false;
	private int x = 0;
	private int y = 0;
	private int farX = 0;
	private int farY = 0;
	private int charLimit = 0;
	private String text = "";
	
	public boolean isAlive(){return alive;}
	public boolean canWrite(){return write;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getFarX(){return farX;}
	public int getFarY(){return farY;}
	public int getCharLimit(){return charLimit;}
	public String getText(){return text;}
	
	public void setWrite(boolean write){this.write = write;}
	public void setAlive(boolean alive){this.alive = alive;}
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setFarX(int farX){this.farX = farX;}
	public void setFarY(int farY){this.farY = farY;}
	public void setCharLimit(int charLimit){this.charLimit = charLimit;}
	public void setText(String text){this.text = text;};
	
	public int findLength(){
		return farX - x;
	}
	
	public int findHeight(){
		return farY - y;
	}
	TextArea(int x, int y, int x1, int y1, int z){
		setX(x);
		setY(y);
		setFarX(x1);
		setFarY(y1);
		setCharLimit(z);
	}
}
