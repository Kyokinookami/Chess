package Client;

public class Board extends Img{
	
	public Board(int x, int y, int farX, int farY,String image){
		super(x,y,farX,farY,image);
	}
	
	public Board(int x, int y, String image){
		super(x,y,image);
	}
	
	public Board(String image){
		super(image);
	}
	
	public int chess2CoordX(char a, boolean isFar){
		int temp = 0;
		if(isFar)
			temp = 50;
		
		switch(a){
			case 'A': return 0 + temp;
			case 'B': return 50 + temp;
			case 'C': return 100 + temp;
			case 'D': return 150 + temp;
			case 'E': return 200 + temp;
			case 'F': return 250 + temp;
			case 'G': return 300 + temp;
			case 'H': return 350 + temp;
			default: return 0;
		}
	}
	public int chess2CoordY(int a, boolean isFar){
		int temp = 0;
		if(isFar)
			temp = 50;
		
		switch(a){
			case 8: return 50 + temp;
			case 7: return 100 + temp;
			case 6: return 150 + temp;
			case 5: return 200 + temp;
			case 4: return 250 + temp; 
			case 3: return 300 + temp;
			case 2: return 350 + temp;
			case 1: return 400 + temp;
			default: return 0;
		}
	
	}
	
	public char coord2ChessX(int a, boolean isFar){
		if(isFar)
			a = a - 50;
		switch(a){
			case 0: return 'A';
			case 50: return 'B';
			case 100: return 'C';
			case 150: return 'D';
			case 200: return 'E';
			case 250: return 'F';
			case 300: return 'G';
 			case 350: return 'H';
 			default: return 'z';
		}
	}
	
	public int coord2ChessY(int a, boolean isFar){
		if(isFar)
			a = a - 50;
		switch(a){
			case 50: return 8;
			case 100: return 7;
			case 150: return 6;
			case 200: return 5;
			case 250: return 4;
			case 300: return 3;
 			case 350: return 2;
 			case 400: return 1;
 			default: return 0;
		}
	}
}
