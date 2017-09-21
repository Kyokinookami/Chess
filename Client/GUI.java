package Client;

import java.awt.event.MouseEvent;

public class GUI {
	String style = "classic";
	String prevStyle = style;
	
	Board[][] board = new Board[8][8];{
		for(int x = 0;x < board.length;x++){
			for(int y = 0;y < board[x].length;y++){
				board[x][y] = new Board(x*50,y*50 + 50,x*50 + 50,y*50 + 100,"Chess/Classic/Board/right.png");
				if(y % 2 == 0){
					if(x % 2 == 1)
						board[x][y].setImg("Chess/Classic/Board/left.png");
				}
				else if(y % 2 == 1)
					if(x % 2 == 0)
						board[x][y].setImg("Chess/Classic/Board/left.png");
			}
		}
	}
	
	Img leave = new Img(0, 0, 400, 50, "Chess/Classic/GUI/leave.png");
	Img main = new Img("Chess/Classic/GUI/main.png");
	Img ip = new Img(86, 56, 315, 80, "Chess/Classic/GUI/ip.png");
	Img serverIP = new Img(86, 16, 315, 40, "Chess/Classic/GUI/ip.png");
	Img play = new Img(168, 96, 232, 120, "Chess/Classic/GUI/play.png");
	Img classic = new Img(146, 136, 255, 160, "Chess/Classic/GUI/classic.png");
	Img rvb = new Img(108, 176, 292, 200, "Chess/Classic/GUI/rvb.png");
	Img regal = new Img(160, 216, 239, 240, "Chess/Classic/GUI/regal.png");
	Img move = new Img(0, 0, 0, 0, "Chess/Classic/Misc/move.png");
	Img attack = new Img(0, 0, 0, 0, "Chess/Classic/Misc/attack.png");
	Img overlay = new Img(0,0,"Chess/Regal/overlay.png");
	Img promotion = new Img(98,198,"Chess/Classic/GUI/promotion.png");
	
	Img prKnight = new Img(100,200,150,250,"Chess/Classic/Pieces/Right/knight.png");
	Img prRook = new Img(150,200,200,250,"Chess/Classic/Pieces/Right/rook.png");
	Img prBishop = new Img(200,200,250,250,"Chess/Classic/Pieces/Right/bishop.png");
	Img prQueen = new Img(250,200,300,250,"Chess/Classic/Pieces/Right/queen.png");
	
	Img plKnight = new Img(100,200,150,250,"Chess/Classic/Pieces/Left/knight.png");
	Img plRook = new Img(150,200,200,250,"Chess/Classic/Pieces/Left/rook.png");
	Img plBishop = new Img(200,200,250,250,"Chess/Classic/Pieces/Left/bishop.png");
	Img plQueen = new Img(250,200,300,250,"Chess/Classic/Pieces/Left/queen.png");

	Piece[] rBishop = new Piece[10];
	{
		for (int x = 0; x < rBishop.length; x++)
			rBishop[x] = new Piece("Chess/Classic/Pieces/Right/bishop.png","bishop");
	}
	Piece rKing = new Piece("Chess/Classic/Pieces/Right/king.png","king");
	Piece[] rKnight = new Piece[10];
	{
		for (int x = 0; x < rKnight.length; x++)
			rKnight[x] = new Piece("Chess/Classic/Pieces/Right/knight.png","knight");
	}
	Piece[] rPawn = new Piece[8];
	{
		for (int x = 0; x < rPawn.length; x++)
			rPawn[x] = new Piece("Chess/Classic/Pieces/Right/pawn.png","rpawn");
	}
	Piece[] rQueen = new Piece[9];
	{
		for (int x = 0; x < rQueen.length; x++)
			rQueen[x] = new Piece("Chess/Classic/Pieces/Right/queen.png","queen");
	}
	Piece[] rRook = new Piece[10];
	{
		for (int x = 0; x < rRook.length; x++)
			rRook[x] = new Piece("Chess/Classic/Pieces/Right/rook.png","rook");
	}

	Piece[] lBishop = new Piece[10];
	{
		for (int x = 0; x < lBishop.length; x++)
			lBishop[x] = new Piece("Chess/Classic/Pieces/Left/bishop.png","bishop");
	}
	Piece lKing = new Piece("Chess/Classic/Pieces/Left/king.png","king");
	Piece[] lKnight = new Piece[10];
	{
		for (int x = 0; x < lKnight.length; x++)
			lKnight[x] = new Piece("Chess/Classic/Pieces/Left/knight.png","knight");
	}
	Piece[] lPawn = new Piece[8];
	{
		for (int x = 0; x < lPawn.length; x++)
			lPawn[x] = new Piece("Chess/Classic/Pieces/Left/pawn.png","lpawn");
	}
	Piece[] lQueen = new Piece[9];
	{
		for (int x = 0; x < lQueen.length; x++)
			lQueen[x] = new Piece("Chess/Classic/Pieces/Left/queen.png","queen");
	}
	Piece[] lRook = new Piece[10];
	{
		for (int x = 0; x < lRook.length; x++)
			lRook[x] = new Piece("Chess/Classic/Pieces/Left/rook.png","rook");
	}

	public void update() {
		if (style != prevStyle) {
			if (style == "classic") {
				for (int x = 0; x < board.length; x++) {
					for (int y = 0; y < board[x].length; y++) {
						if(y % 2 == 0){
							if(x % 2 == 1)
								board[x][y].setImg("Chess/Classic/Board/left.png");
							else
								board[x][y].setImg("Chess/Classic/Board/right.png");
						}
						else if(y % 2 == 1)
							if(x % 2 == 0)
								board[x][y].setImg("Chess/Classic/Board/left.png");
							else
								board[x][y].setImg("Chess/Classic/Board/right.png");
					}
				}
				leave.setImg("Chess/Classic/GUI/leave.png");
				main.setImg("Chess/Classic/GUI/main.png");
				ip.setImg("Chess/Classic/GUI/ip.png");
				serverIP.setImg("Chess/Classic/GUI/ip.png");
				play.setImg("Chess/Classic/GUI/play.png");
				classic.setImg("Chess/Classic/GUI/classic.png");
				rvb.setImg("Chess/Classic/GUI/rvb.png");
				regal.setImg("Chess/Classic/GUI/regal.png");
				move.setImg("Chess/Classic/Misc/move.png");
				attack.setImg("Chess/Classic/Misc/attack.png");
				promotion.setImg("Chess/Classic/GUI/promotion.png");

				prKnight.setImg("Chess/Classic/Pieces/Right/knight.png");
				prRook.setImg("Chess/Classic/Pieces/Right/rook.png");
				prBishop.setImg("Chess/Classic/Pieces/Right/bishop.png");
				prQueen.setImg("Chess/Classic/Pieces/Right/queen.png");
				
				plKnight.setImg("Chess/Classic/Pieces/Left/knight.png");
				plRook.setImg("Chess/Classic/Pieces/Left/rook.png");
				plBishop.setImg("Chess/Classic/Pieces/Left/bishop.png");
				plQueen.setImg("Chess/Classic/Pieces/Left/queen.png");
				
				for (int x = 0; x < rBishop.length; x++)
					rBishop[x].setImg("Chess/Classic/Pieces/Right/bishop.png");
				rKing.setImg("Chess/Classic/Pieces/Right/king.png");
				for (int x = 0; x < rKnight.length; x++)
					rKnight[x].setImg("Chess/Classic/Pieces/Right/knight.png");
				for (int x = 0; x < rPawn.length; x++)
					rPawn[x].setImg("Chess/Classic/Pieces/Right/pawn.png");
				for (int x = 0; x < rQueen.length; x++)
					rQueen[x].setImg("Chess/Classic/Pieces/Right/queen.png");
				for (int x = 0; x < rRook.length; x++)
					rRook[x].setImg("Chess/Classic/Pieces/Right/rook.png");

				
				for (int x = 0; x < lBishop.length; x++)
					lBishop[x].setImg("Chess/Classic/Pieces/Left/bishop.png");
				lKing.setImg("Chess/Classic/Pieces/Left/king.png");
				for (int x = 0; x < lKnight.length; x++)
					lKnight[x].setImg("Chess/Classic/Pieces/Left/knight.png");
				for (int x = 0; x < lPawn.length; x++)
					lPawn[x].setImg("Chess/Classic/Pieces/Left/pawn.png");
				for (int x = 0; x < lQueen.length; x++)
					lQueen[x].setImg("Chess/Classic/Pieces/Left/queen.png");
				for (int x = 0; x < lRook.length; x++)
					lRook[x].setImg("Chess/Classic/Pieces/Left/rook.png");
				
			} else if (style == "rvb") {
				for (int x = 0; x < board.length; x++) {
					for (int y = 0; y < board[x].length; y++) {
						if(y % 2 == 0){
							if(x % 2 == 1)
								board[x][y].setImg("Chess/RVB/Board/left.png");
							else
								board[x][y].setImg("Chess/RVB/Board/right.png");
						}
						else if(y % 2 == 1)
							if(x % 2 == 0)
								board[x][y].setImg("Chess/RVB/Board/left.png");
							else
								board[x][y].setImg("Chess/RVB/Board/right.png");
					}
				}
				leave.setImg("Chess/RVB/GUI/leave.png");
				main.setImg("Chess/RVB/GUI/main.png");
				ip.setImg("Chess/RVB/GUI/ip.png");
				serverIP.setImg("Chess/RVB/GUI/ip.png");
				play.setImg("Chess/RVB/GUI/play.png");
				classic.setImg("Chess/RVB/GUI/classic.png");
				rvb.setImg("Chess/RVB/GUI/rvb.png");
				regal.setImg("Chess/RVB/GUI/regal.png");
				move.setImg("Chess/RVB/Misc/move.png");
				attack.setImg("Chess/RVB/Misc/attack.png");
				promotion.setImg("Chess/RVB/GUI/promotion.png");

				prKnight.setImg("Chess/RVB/Pieces/Right/knight.png");
				prRook.setImg("Chess/RVB/Pieces/Right/rook.png");
				prBishop.setImg("Chess/RVB/Pieces/Right/bishop.png");
				prQueen.setImg("Chess/RVB/Pieces/Right/queen.png");
				
				plKnight.setImg("Chess/RVB/Pieces/Left/knight.png");
				plRook.setImg("Chess/RVB/Pieces/Left/rook.png");
				plBishop.setImg("Chess/RVB/Pieces/Left/bishop.png");
				plQueen.setImg("Chess/RVB/Pieces/Left/queen.png");
				
				for (int x = 0; x < rBishop.length; x++)
					rBishop[x].setImg("Chess/RVB/Pieces/Right/bishop.png");
				rKing.setImg("Chess/RVB/Pieces/Right/king.png");
				for (int x = 0; x < rKnight.length; x++)
					rKnight[x].setImg("Chess/RVB/Pieces/Right/knight.png");
				for (int x = 0; x < rPawn.length; x++)
					rPawn[x].setImg("Chess/RVB/Pieces/Right/pawn.png");
				for (int x = 0; x < rQueen.length; x++)
					rQueen[x].setImg("Chess/RVB/Pieces/Right/queen.png");
				for (int x = 0; x < rRook.length; x++)
					rRook[x].setImg("Chess/RVB/Pieces/Right/rook.png");

				
				for (int x = 0; x < lBishop.length; x++)
					lBishop[x].setImg("Chess/RVB/Pieces/Left/bishop.png");
				lKing.setImg("Chess/RVB/Pieces/Left/king.png");
				for (int x = 0; x < lKnight.length; x++)
					lKnight[x].setImg("Chess/RVB/Pieces/Left/knight.png");
				for (int x = 0; x < lPawn.length; x++)
					lPawn[x].setImg("Chess/RVB/Pieces/Left/pawn.png");
				for (int x = 0; x < lQueen.length; x++)
					lQueen[x].setImg("Chess/RVB/Pieces/Left/queen.png");
				for (int x = 0; x < lRook.length; x++)
					lRook[x].setImg("Chess/RVB/Pieces/Left/rook.png");
			} else if (style == "regal") {
				for (int x = 0; x < board.length; x++) {
					for (int y = 0; y < board[x].length; y++) {
						if(y % 2 == 0){
							if(x % 2 == 1)
								board[x][y].setImg("Chess/Regal/Board/left.png");
							else
								board[x][y].setImg("Chess/Regal/Board/right.png");
						}
						else if(y % 2 == 1)
							if(x % 2 == 0)
								board[x][y].setImg("Chess/Regal/Board/left.png");
							else
								board[x][y].setImg("Chess/Regal/Board/right.png");
					}
				}
				leave.setImg("Chess/Regal/GUI/leave.png");
				main.setImg("Chess/Regal/GUI/main.png");
				ip.setImg("Chess/Regal/GUI/ip.png");
				serverIP.setImg("Chess/Regal/GUI/ip.png");
				play.setImg("Chess/Regal/GUI/play.png");
				classic.setImg("Chess/Regal/GUI/classic.png");
				rvb.setImg("Chess/Regal/GUI/rvb.png");
				regal.setImg("Chess/Regal/GUI/regal.png");
				move.setImg("Chess/Regal/Misc/move.png");
				attack.setImg("Chess/Regal/Misc/attack.png");
				promotion.setImg("Chess/Regal/GUI/promotion.png");

				prKnight.setImg("Chess/Regal/Pieces/Right/knight.png");
				prRook.setImg("Chess/Regal/Pieces/Right/rook.png");
				prBishop.setImg("Chess/Regal/Pieces/Right/bishop.png");
				prQueen.setImg("Chess/Regal/Pieces/Right/queen.png");
				
				plKnight.setImg("Chess/Regal/Pieces/Left/knight.png");
				plRook.setImg("Chess/Regal/Pieces/Left/rook.png");
				plBishop.setImg("Chess/Regal/Pieces/Left/bishop.png");
				plQueen.setImg("Chess/Regal/Pieces/Left/queen.png");
				
				for (int x = 0; x < rBishop.length; x++)
					rBishop[x].setImg("Chess/Regal/Pieces/Right/bishop.png");
				rKing.setImg("Chess/Regal/Pieces/Right/king.png");
				for (int x = 0; x < rKnight.length; x++)
					rKnight[x].setImg("Chess/Regal/Pieces/Right/knight.png");
				for (int x = 0; x < rPawn.length; x++)
					rPawn[x].setImg("Chess/Regal/Pieces/Right/pawn.png");
				for (int x = 0; x < rQueen.length; x++)
					rQueen[x].setImg("Chess/Regal/Pieces/Right/queen.png");
				for (int x = 0; x < rRook.length; x++)
					rRook[x].setImg("Chess/Regal/Pieces/Right/rook.png");

				
				for (int x = 0; x < lBishop.length; x++)
					lBishop[x].setImg("Chess/Regal/Pieces/Left/bishop.png");
				lKing.setImg("Chess/Regal/Pieces/Left/king.png");
				for (int x = 0; x < lKnight.length; x++)
					lKnight[x].setImg("Chess/Regal/Pieces/Left/knight.png");
				for (int x = 0; x < lPawn.length; x++)
					lPawn[x].setImg("Chess/Regal/Pieces/Left/pawn.png");
				for (int x = 0; x < lQueen.length; x++)
					lQueen[x].setImg("Chess/Regal/Pieces/Left/queen.png");
				for (int x = 0; x < lRook.length; x++)
					lRook[x].setImg("Chess/Regal/Pieces/Left/rook.png");
			} else
				style = "classic";
			prevStyle = style;
		}
	}
	
	public void mouseReleased(MouseEvent arg0){
		if(arg0.getX() < classic.getFarX() && arg0.getX() > classic.getX() && 
			arg0.getY() < classic.getFarY() && arg0.getY() > classic.getY() && classic.isAlive())
			style = "classic";
		
		else if(arg0.getX() < rvb.getFarX() && arg0.getX() > rvb.getX() && 
				arg0.getY() < rvb.getFarY() && arg0.getY() > rvb.getY() && rvb.isAlive())
				style = "rvb";
		
		else if(arg0.getX() < regal.getFarX() && arg0.getX() > regal.getX() && 
				arg0.getY() < regal.getFarY() && arg0.getY() > regal.getY() && regal.isAlive())
				style = "regal";
		else if(arg0.getX() < leave.getFarX() && arg0.getX() > leave.getX() && 
				arg0.getY() < leave.getFarY() && arg0.getY() > leave.getY() && leave.isAlive())
				System.exit(0);
	}
}
