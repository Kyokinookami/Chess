package Client;

public class Piece extends Img{
	public Piece(int x, int y, int farX, int farY, String image, String type){
		super(x,y,farX,farY,image);
		this.type = type;
		if(type == "rpawn" || type == "lpawn")
			goTwice = true;
	}

	public Piece(int x, int y, String image, String type){
		super(x,y,image);
		this.type = type;
		if(type == "rpawn" || type == "lpawn")
			goTwice = true;
	}
	
	public Piece(String image, String type){
		super(image);
		this.type = type;
		if(type == "rpawn" || type == "lpawn")
			goTwice = true;
	}
	
	private boolean moving = false;
	public void setMoving(boolean moving){this.moving = moving;}
	public boolean isMoving(){return moving;}
	
	private boolean attacked = false;
	public void setAttacked(boolean attacked){this.attacked = attacked;}
	public boolean hasAttacked(){return attacked;}
	
	private boolean goTwice = false;
	public void setGoTwice(boolean goTwice){this.goTwice = goTwice;}
	public boolean canGoTwice(){return goTwice;}
	
	private String type = "";
	public String getType(){return type;}
	
	private boolean moved = false;
	public boolean hasMoved(){return moved;}
	public void  setMoved(boolean moved){this.moved = moved;}
	
}
