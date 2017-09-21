package Client;

import java.awt.event.MouseEvent;

public class Game {
	
	private String move = "";
	public String getMove(){return move;}
	public void setMove(String move){this.move = move;}
	
	private boolean promoted = false;
	public void setPromoted(boolean promoted){this.promoted = promoted;}
	public boolean hasPromoted(){return promoted;}
	
	private int origX = 0;
	private int origY = 0;
	
	public void setBoard(GUI gui){
		gui.rKing.setAlive(true);
		gui.lKing.setAlive(true);
		
		gui.rQueen[0].setAlive(true);
		gui.lQueen[0].setAlive(true);
		
		gui.rKnight[0].setAlive(true);
		gui.rKnight[1].setAlive(true);
		gui.lKnight[0].setAlive(true);
		gui.lKnight[1].setAlive(true);
		
		gui.rBishop[0].setAlive(true);
		gui.rBishop[1].setAlive(true);
		gui.lBishop[0].setAlive(true);
		gui.lBishop[1].setAlive(true);
		
		gui.rRook[0].setAlive(true);
		gui.rRook[1].setAlive(true);
		gui.lRook[0].setAlive(true);
		gui.lRook[1].setAlive(true);
		
		for(int x = 0; x < gui.rPawn.length;x++){
			board2Piece(gui.rPawn[x],gui.board[x][6]);
			board2Piece(gui.lPawn[x],gui.board[x][1]);
			
			gui.rPawn[x].setAlive(true);
			gui.lPawn[x].setAlive(true);
		}
		board2Piece(gui.rKing,gui.board[4][7]);
		board2Piece(gui.lKing,gui.board[4][0]);
		
		board2Piece(gui.rQueen[0],gui.board[3][7]);
		board2Piece(gui.lQueen[0],gui.board[3][0]);
		
		board2Piece(gui.rKnight[0],gui.board[1][7]);
		board2Piece(gui.rKnight[1],gui.board[6][7]);
		board2Piece(gui.lKnight[0],gui.board[1][0]);
		board2Piece(gui.lKnight[1],gui.board[6][0]);
		
		board2Piece(gui.rBishop[0],gui.board[2][7]);
		board2Piece(gui.rBishop[1],gui.board[5][7]);
		board2Piece(gui.lBishop[0],gui.board[2][0]);
		board2Piece(gui.lBishop[1],gui.board[5][0]);
		
		board2Piece(gui.rRook[0],gui.board[0][7]);
		board2Piece(gui.rRook[1],gui.board[7][7]);
		board2Piece(gui.lRook[0],gui.board[0][0]);
		board2Piece(gui.lRook[1],gui.board[7][0]);
	}
	
	public void board2Piece(Piece piece,Board board){
		piece.setX(board.getX());
		piece.setFarX(board.getFarX());
		piece.setY(board.getY());
		piece.setFarY(board.getFarY());
	}
	
	public boolean hasLost(boolean isWhite, GUI gui){
		if(isWhite && !gui.rKing.isAlive())
			return true;
		else if(!isWhite && !gui.lKing.isAlive())
			return true;
		else
			return false;
	}
	
	private void killPieces(GUI gui){
		for(int x = 0;x < gui.board.length;x++){
			for(int y = 0; y < gui.board[x].length;y++){
				if(whichPiece(gui,gui.board[x][y]) != null && whichPiece1(gui,gui.board[x][y]) != null){
					Piece piece = new Piece("", "");
					piece = whichPiece(gui,gui.board[x][y]);
					Piece piece1 = new Piece("","");
					piece1 = whichPiece1(gui,gui.board[x][y]);
					if(piece.hasAttacked() && !piece1.hasAttacked()){
						piece1.setAlive(false);
						piece1.setX(0);
						piece1.setY(0);
						piece1.setFarX(0);
						piece1.setFarY(0);
						piece.setAttacked(false);
					}
					else if(!piece.hasAttacked() && piece1.hasAttacked()){
						piece.setAlive(false);
						piece.setX(0);
						piece.setY(0);
						piece.setFarX(0);
						piece.setFarY(0);
						piece1.setAttacked(false);
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent arg0, GUI gui, boolean isWhite, MoveAnalyzer ma){
		Piece piece = new Piece("","");
		if(arg0.getButton() == 3){
			for(int x = 0; x < gui.rPawn.length;x++)
				gui.rPawn[x].setMoving(false);
			for(int x = 0; x < gui.rRook.length;x++)
				gui.rRook[x].setMoving(false);
			for(int x = 0; x < gui.rQueen.length;x++)
				gui.rQueen[x].setMoving(false);
			for(int x = 0; x < gui.rBishop.length;x++)
				gui.rBishop[x].setMoving(false);
			for(int x = 0; x < gui.rKnight.length;x++)
				gui.rKnight[x].setMoving(false);
			gui.rKing.setMoving(false);
			
			for(int x = 0; x < gui.lPawn.length;x++)
				gui.lPawn[x].setMoving(false);
			for(int x = 0; x < gui.lRook.length;x++)
				gui.lRook[x].setMoving(false);
			for(int x = 0; x < gui.lQueen.length;x++)
				gui.lQueen[x].setMoving(false);
			for(int x = 0; x < gui.lBishop.length;x++)
				gui.lBishop[x].setMoving(false);
			for(int x = 0; x < gui.lKnight.length;x++)
				gui.lKnight[x].setMoving(false);
			gui.lKing.setMoving(false);
		}
		else if(gui.promotion.isAlive()){
			if(arg0.getX() < gui.prKnight.getFarX() && arg0.getX() > gui.prKnight.getX() && 
				arg0.getY() < gui.prKnight.getFarY() && arg0.getY() > gui.prKnight.getY() && gui.prKnight.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][0]) != null){
							piece = whichPiece(gui,gui.board[x][0]);
							if(piece.getType() == "rpawn"){
								for(int y = 0; y < gui.rKnight.length;y++){
									if(!gui.rKnight[y].isAlive()){
										piece.setAlive(false);
										gui.rKnight[y].setAlive(true);
										gui.rKnight[y].setX(piece.getX());
										gui.rKnight[y].setY(piece.getY());
										gui.rKnight[y].setFarX(piece.getFarX());
										gui.rKnight[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.rKnight.length;
										setMove("P2K");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.prRook.getFarX() && arg0.getX() > gui.prRook.getX() && 
					arg0.getY() < gui.prRook.getFarY() && arg0.getY() > gui.prRook.getY() && gui.prRook.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][0]) != null){
							piece = whichPiece(gui,gui.board[x][0]);
							if(piece.getType() == "rpawn"){
								for(int y = 0; y < gui.rRook.length;y++){
									if(!gui.rRook[y].isAlive()){
										piece.setAlive(false);
										gui.rRook[y].setAlive(true);
										gui.rRook[y].setX(piece.getX());
										gui.rRook[y].setY(piece.getY());
										gui.rRook[y].setFarX(piece.getFarX());
										gui.rRook[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.rRook.length;
										setMove("P2R");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.prBishop.getFarX() && arg0.getX() > gui.prBishop.getX() && 
					arg0.getY() < gui.prBishop.getFarY() && arg0.getY() > gui.prBishop.getY() && gui.prBishop.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][0]) != null){
							piece = whichPiece(gui,gui.board[x][0]);
							if(piece.getType() == "rpawn"){
								for(int y = 0; y < gui.rBishop.length;y++){
									if(!gui.rBishop[y].isAlive()){
										piece.setAlive(false);
										gui.rBishop[y].setAlive(true);
										gui.rBishop[y].setX(piece.getX());
										gui.rBishop[y].setY(piece.getY());
										gui.rBishop[y].setFarX(piece.getFarX());
										gui.rBishop[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.rBishop.length;
										setMove("P2B");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.prQueen.getFarX() && arg0.getX() > gui.prQueen.getX() && 
					arg0.getY() < gui.prQueen.getFarY() && arg0.getY() > gui.prQueen.getY() && gui.prQueen.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][0]) != null){
							piece = whichPiece(gui,gui.board[x][0]);
							if(piece.getType() == "rpawn"){
								for(int y = 0; y < gui.rQueen.length;y++){
									if(!gui.rQueen[y].isAlive()){
										piece.setAlive(false);
										gui.rQueen[y].setAlive(true);
										gui.rQueen[y].setX(piece.getX());
										gui.rQueen[y].setY(piece.getY());
										gui.rQueen[y].setFarX(piece.getFarX());
										gui.rQueen[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.rQueen.length;
										setMove("P2Q");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.plKnight.getFarX() && arg0.getX() > gui.plKnight.getX() && 
					arg0.getY() < gui.plKnight.getFarY() && arg0.getY() > gui.plKnight.getY() && gui.plKnight.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][7]) != null){
							piece = whichPiece(gui,gui.board[x][7]);
							if(piece.getType() == "lpawn"){
								for(int y = 0; y < gui.lKnight.length;y++){
									if(!gui.lKnight[y].isAlive()){
										piece.setAlive(false);
										gui.lKnight[y].setAlive(true);
										gui.lKnight[y].setX(piece.getX());
										gui.lKnight[y].setY(piece.getY());
										gui.lKnight[y].setFarX(piece.getFarX());
										gui.lKnight[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.lKnight.length;
										setMove("P2K");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.plRook.getFarX() && arg0.getX() > gui.plRook.getX() && 
					arg0.getY() < gui.plRook.getFarY() && arg0.getY() > gui.plRook.getY() && gui.plRook.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][7]) != null){
							piece = whichPiece(gui,gui.board[x][7]);
							if(piece.getType() == "lpawn"){
								for(int y = 0; y < gui.lRook.length;y++){
									if(!gui.lRook[y].isAlive()){
										piece.setAlive(false);
										gui.lRook[y].setAlive(true);
										gui.lRook[y].setX(piece.getX());
										gui.lRook[y].setY(piece.getY());
										gui.lRook[y].setFarX(piece.getFarX());
										gui.lRook[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.lRook.length;
										setMove("P2R");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.plBishop.getFarX() && arg0.getX() > gui.plBishop.getX() && 
					arg0.getY() < gui.plBishop.getFarY() && arg0.getY() > gui.plBishop.getY() && gui.plBishop.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][7]) != null){
							piece = whichPiece(gui,gui.board[x][7]);
							if(piece.getType() == "lpawn"){
								for(int y = 0; y < gui.lBishop.length;y++){
									if(!gui.lBishop[y].isAlive()){
										piece.setAlive(false);
										gui.lBishop[y].setAlive(true);
										gui.lBishop[y].setX(piece.getX());
										gui.lBishop[y].setY(piece.getY());
										gui.lBishop[y].setFarX(piece.getFarX());
										gui.lBishop[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.lBishop.length;
										setMove("P2B");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
			else if(arg0.getX() < gui.plQueen.getFarX() && arg0.getX() > gui.plQueen.getX() && 
					arg0.getY() < gui.plQueen.getFarY() && arg0.getY() > gui.plQueen.getY() && gui.plQueen.isAlive()){
			//		turnPromotionOff(gui);
					for(int x = 0; x < gui.board.length;x++){
						if(whichPiece(gui,gui.board[x][7]) != null){
							piece = whichPiece(gui,gui.board[x][7]);
							if(piece.getType() == "lpawn"){
								for(int y = 0; y < gui.lQueen.length;y++){
									if(!gui.lQueen[y].isAlive()){
										piece.setAlive(false);
										gui.lQueen[y].setAlive(true);
										gui.lQueen[y].setX(piece.getX());
										gui.lQueen[y].setY(piece.getY());
										gui.lQueen[y].setFarX(piece.getFarX());
										gui.lQueen[y].setFarY(piece.getFarY());
										
										piece.setX(0);
										piece.setY(0);
										piece.setFarX(0);
										piece.setFarY(0);
										y = gui.lQueen.length;
										setMove("P2Q");
										setPromoted(true);
									}
								}
								x = gui.board.length;
							}
						}
					}
			}
		}
		else{
			
		for(int a = 0 ;a < gui.rPawn.length;a++){
			if(gui.rPawn[a].isMoving()){
				putPiece(arg0,gui,gui.rPawn[a], ma);
				a = gui.rPawn.length+1;
			}
			else if(a+1 == gui.rPawn.length){
				for(int b = 0 ;b < gui.lPawn.length;b++){
					if(gui.lPawn[b].isMoving()){
						putPiece(arg0,gui,gui.lPawn[b], ma);
						b = gui.lPawn.length+1;
					}
					else if(b+1 == gui.lPawn.length){
						for(int c = 0 ;c < gui.rRook.length;c++){
							if(gui.rRook[c].isMoving()){
								putPiece(arg0,gui,gui.rRook[c], ma);
								c = gui.rRook.length+1;
							}
							else if(c+1 == gui.rRook.length){
								for(int d = 0 ;d < gui.lRook.length;d++){
									if(gui.lRook[d].isMoving()){
										putPiece(arg0,gui,gui.lRook[d], ma);
										d = gui.lRook.length+1;
									}
									else if(d+1 == gui.lRook.length){
										for(int e = 0 ;e < gui.rBishop.length;e++){
											if(gui.rBishop[e].isMoving()){
												putPiece(arg0,gui,gui.rBishop[e], ma);
												e = gui.rBishop.length+1;
											}
											else if(e+1 == gui.rBishop.length){
												for(int f = 0 ;f < gui.lBishop.length;f++){
													if(gui.lBishop[f].isMoving()){
														putPiece(arg0,gui,gui.lBishop[f], ma);
														f = gui.lBishop.length+1;
													}
													else if(f+1 == gui.lBishop.length){
														for(int g = 0 ;g < gui.rKnight.length;g++){
															if(gui.rKnight[g].isMoving()){
																putPiece(arg0,gui,gui.rKnight[g], ma);
																g = gui.rKnight.length+1;
															}
															else if(g+1 == gui.rKnight.length){
																for(int h = 0 ;h < gui.lKnight.length;h++){
																	if(gui.lKnight[h].isMoving()){
																		putPiece(arg0,gui,gui.lKnight[h], ma);
																		h = gui.lKnight.length+1;
																	}
																	else if(h+1 == gui.lKnight.length){
																		for(int i = 0 ;i < gui.rQueen.length;i++){
																			if(gui.rQueen[i].isMoving()){
																				putPiece(arg0,gui,gui.rQueen[i], ma);
																				i = gui.rQueen.length+1;
																			}
																			else if(i+1 == gui.rQueen.length){
																				for(int j = 0 ;j < gui.lQueen.length;j++){
																					if(gui.lQueen[j].isMoving()){
																						putPiece(arg0,gui,gui.lQueen[j], ma);
																						j = gui.lQueen.length+1;
																					}
																					else if(j+1 == gui.lQueen.length){
																						if(gui.rKing.isMoving())
																							putPiece(arg0,gui,gui.rKing,ma);
																						else{
																							if(gui.lKing.isMoving())
																								putPiece(arg0,gui,gui.lKing,ma);
																							else
																								pickUpPiece(arg0,gui,isWhite);
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		killPieces(gui);
		removeDoubleMove(gui);
		enablePromotion(gui);
		}
	}
	
	
	
	private void enablePromotion(GUI gui){
		for(int x = 0;x < gui.board.length;x++){
			if(x < gui.board.length){
				if(whichPiece(gui,gui.board[x][0]) != null){
					if(whichPiece(gui,gui.board[x][0]).getType() == "rpawn" && Chess.isWhite()){
						gui.promotion.setAlive(true);
						gui.prKnight.setAlive(true);
						gui.prRook.setAlive(true);
						gui.prBishop.setAlive(true);
						gui.prQueen.setAlive(true);
						x = gui.board.length;
						setMove(getMove() + "-");
					}
	//				else
	//					turnPromotionOff(gui);
				}
	//			else
	//				turnPromotionOff(gui);
			}
			if(x < gui.board.length){
				if(whichPiece(gui,gui.board[x][7]) != null){
					if(whichPiece(gui,gui.board[x][7]).getType() == "lpawn" && !Chess.isWhite()){
						gui.promotion.setAlive(true);
						gui.plKnight.setAlive(true);
						gui.plRook.setAlive(true);
						gui.plBishop.setAlive(true);
						gui.plQueen.setAlive(true);
						x = gui.board.length;
						setMove(getMove() + "-");
					}
	//				else
	//					turnPromotionOff(gui);
				}
	//			else
	//				turnPromotionOff(gui);
			}
		}
	}
	
	public void turnPromotionOff(GUI gui){
		gui.promotion.setAlive(false);
		gui.promotion.setAlive(false);
		gui.prKnight.setAlive(false);
		gui.prRook.setAlive(false);
		gui.prBishop.setAlive(false);
		gui.prQueen.setAlive(false);
		gui.plKnight.setAlive(false);
		gui.plRook.setAlive(false);
		gui.plBishop.setAlive(false);
		gui.plQueen.setAlive(false);
	}
	
	private void removeDoubleMove(GUI gui){
		for(int x = 0;x < gui.rPawn.length;x++)
			if(gui.rPawn[x].getY() != 350 && gui.rPawn[x].isAlive())
				gui.rPawn[x].setGoTwice(false);
		for(int x = 0;x < gui.lPawn.length;x++)
			if(gui.lPawn[x].getY() != 100 && gui.lPawn[x].isAlive())
				gui.lPawn[x].setGoTwice(false);
	}
	
	private void pickUpPiece(MouseEvent arg0, GUI gui, boolean isWhite){
		if(isWhite){
			if(arg0.getX() < gui.rKing.getFarX() && arg0.getX() > gui.rKing.getX() &&
				arg0.getY() < gui.rKing.getFarY() && arg0.getY() > gui.rKing.getY() && gui.rKing.isAlive())
				gui.rKing.setMoving(true);
			else
				gui.rKing.setMoving(false);
			for(int x = 0;x < gui.rPawn.length;x++){
				if(arg0.getX() < gui.rPawn[x].getFarX() && arg0.getX() > gui.rPawn[x].getX() &&
					arg0.getY() < gui.rPawn[x].getFarY() && arg0.getY() > gui.rPawn[x].getY() && gui.rPawn[x].isAlive())
					gui.rPawn[x].setMoving(true);
				else
					gui.rPawn[x].setMoving(false);
			}
			for(int x = 0;x < gui.rQueen.length;x++){
				if(arg0.getX() < gui.rQueen[x].getFarX() && arg0.getX() > gui.rQueen[x].getX() &&
					arg0.getY() < gui.rQueen[x].getFarY() && arg0.getY() > gui.rQueen[x].getY() && gui.rQueen[x].isAlive())
					gui.rQueen[x].setMoving(true);
				else
					gui.rQueen[x].setMoving(false);
			}
			for(int x = 0;x < gui.rRook.length;x++){
				if(arg0.getX() < gui.rRook[x].getFarX() && arg0.getX() > gui.rRook[x].getX() &&
					arg0.getY() < gui.rRook[x].getFarY() && arg0.getY() > gui.rRook[x].getY() && gui.rRook[x].isAlive())
					gui.rRook[x].setMoving(true);
				else
					gui.rRook[x].setMoving(false);
			}
			for(int x = 0;x < gui.rBishop.length;x++){
				if(arg0.getX() < gui.rBishop[x].getFarX() && arg0.getX() > gui.rBishop[x].getX() &&
					arg0.getY() < gui.rBishop[x].getFarY() && arg0.getY() > gui.rBishop[x].getY() && gui.rBishop[x].isAlive())
					gui.rBishop[x].setMoving(true);
				else
					gui.rBishop[x].setMoving(false);
			}
			for(int x = 0;x < gui.rKnight.length;x++){
				if(arg0.getX() < gui.rKnight[x].getFarX() && arg0.getX() > gui.rKnight[x].getX() &&
					arg0.getY() < gui.rKnight[x].getFarY() && arg0.getY() > gui.rKnight[x].getY() && gui.rKnight[x].isAlive())
					gui.rKnight[x].setMoving(true);
				else
					gui.rKnight[x].setMoving(false);
			}
		}
		else{
			if(arg0.getX() < gui.lKing.getFarX() && arg0.getX() > gui.lKing.getX() &&
				arg0.getY() < gui.lKing.getFarY() && arg0.getY() > gui.lKing.getY() && gui.lKing.isAlive())
					gui.lKing.setMoving(true);
			else
				gui.lKing.setMoving(false);
			for(int x = 0;x < gui.lPawn.length;x++){
				if(arg0.getX() < gui.lPawn[x].getFarX() && arg0.getX() > gui.lPawn[x].getX() &&
					arg0.getY() < gui.lPawn[x].getFarY() && arg0.getY() > gui.lPawn[x].getY() && gui.lPawn[x].isAlive())
					gui.lPawn[x].setMoving(true);
				else
					gui.lPawn[x].setMoving(false);
			}
			for(int x = 0;x < gui.lQueen.length;x++){
				if(arg0.getX() < gui.lQueen[x].getFarX() && arg0.getX() > gui.lQueen[x].getX() &&
					arg0.getY() < gui.lQueen[x].getFarY() && arg0.getY() > gui.lQueen[x].getY() && gui.lQueen[x].isAlive())
					gui.lQueen[x].setMoving(true);
				else
					gui.lQueen[x].setMoving(false);
			}
			for(int x = 0;x < gui.lRook.length;x++){
				if(arg0.getX() < gui.lRook[x].getFarX() && arg0.getX() > gui.lRook[x].getX() &&
					arg0.getY() < gui.lRook[x].getFarY() && arg0.getY() > gui.lRook[x].getY() && gui.lRook[x].isAlive())
					gui.lRook[x].setMoving(true);
				else
					gui.lRook[x].setMoving(false);
			}
			for(int x = 0;x < gui.lBishop.length;x++){
				if(arg0.getX() < gui.lBishop[x].getFarX() && arg0.getX() > gui.lBishop[x].getX() &&
					arg0.getY() < gui.lBishop[x].getFarY() && arg0.getY() > gui.lBishop[x].getY() && gui.lBishop[x].isAlive())
					gui.lBishop[x].setMoving(true);
				else
					gui.lBishop[x].setMoving(false);
			}
			for(int x = 0;x < gui.lKnight.length;x++){
				if(arg0.getX() < gui.lKnight[x].getFarX() && arg0.getX() > gui.lKnight[x].getX() &&
					arg0.getY() < gui.lKnight[x].getFarY() && arg0.getY() > gui.lKnight[x].getY() && gui.lKnight[x].isAlive())
					gui.lKnight[x].setMoving(true);
				else
					gui.lKnight[x].setMoving(false);
			}
		}
	}
	
	private void putPiece(MouseEvent arg0, GUI gui, Piece piece, MoveAnalyzer ma){
		for(int x = 0;x < gui.board.length;x++){
			for(int y = 0;y < gui.board[x].length;y++){
				if(arg0.getX() < gui.board[x][y].getFarX() && arg0.getX() > gui.board[x][y].getX() &&
					arg0.getY() < gui.board[x][y].getFarY() && arg0.getY() > gui.board[x][y].getY() &&
					gui.board[x][y].isAlive()){
					if(canMove(gui,gui.board[x][y],piece) && spaceEmpty(gui,gui.board[x][y])||
						canAttack(gui,gui.board[x][y],piece) ||
							
						canMove(gui,gui.board[x][y],piece) && !spaceEmpty(gui,gui.board[x][y]) &&
						isPieceWhite(gui,piece) && !isPieceWhite(gui,whichPiece(gui,gui.board[x][y]))
						&& !isPawn(piece) ||
						
						canMove(gui,gui.board[x][y],piece) && !spaceEmpty(gui,gui.board[x][y]) &&
						!isPieceWhite(gui,piece) && isPieceWhite(gui,whichPiece(gui,gui.board[x][y]))
						&& !isPawn(piece) ||
						
						canCastle(gui,gui.board[x][y],piece)){
						
						if(canAttack(gui,gui.board[x][y],piece))
							piece.setAttacked(true);
						
						if(isPieceWhite(gui,piece) && !isPieceWhite(gui,whichPiece(gui,gui.board[x][y])) 
							&& !spaceEmpty(gui,gui.board[x][y]) ||
							!isPieceWhite(gui,piece) && isPieceWhite(gui,whichPiece(gui,gui.board[x][y]))
							&& !spaceEmpty(gui,gui.board[x][y]))
							piece.setAttacked(true);
						
						if(canCastle(gui,gui.board[x][y],piece)){
							//This block of moving pieces is having a bit of trouble
							//need to properly move the correct rook, and have the
							//other client interpret that correctly
							if(gui.board[x][y].getX() > piece.getX()){
								board2Piece(piece,gui.board[x][y]);
								if(Chess.isWhite()){
									board2Piece(gui.rRook[1],gui.board[x-1][y]);
									gui.rRook[1].setMoved(true);
								}
								else{
									board2Piece(gui.lRook[1],gui.board[x-1][y]);
									gui.lRook[1].setMoved(true);
								}
								setMove("0-0");
								piece.setMoving(false);
								piece.setMoved(true);
								Chess.setHasPutPiece(true);
							}
							else{
								board2Piece(piece,gui.board[x][y]);
								if(Chess.isWhite()){
									board2Piece(gui.rRook[0],gui.board[x+1][y]);
									gui.rRook[0].setMoved(true);
								}
								else{
									board2Piece(gui.lRook[0],gui.board[x+1][y]);
									gui.lRook[0].setMoved(true);
								}
								setMove("0-0-0");
								piece.setMoving(false);
								piece.setMoved(true);
								Chess.setHasPutPiece(true);
							}
						}
						else{	
							origX = piece.getX();
							origY = piece.getY();
							board2Piece(piece,gui.board[x][y]);
							piece.setMoving(false);
							setMove(
									ma.moveEncoder(
											gui.board[x][y].coord2ChessX(
												origX, false)
											, gui.board[x][y].coord2ChessY(
												origY, false)
											, gui.board[x][y].coord2ChessX(
												gui.board[x][y].getX(), false)
											, gui.board[x][y].coord2ChessY(
												gui.board[x][y].getY(), false)
										)
								);
							if(piece.hasAttacked())
								setMove(getMove() + "+");
							piece.setMoved(true);
							origX = 0;
							origY = 0;
							//The following line is the one above but "condensed"
							//p2p.setMove(ma.moveEncoder(gui.board[x][y].coord2ChessX(gui.board[x][y].getX(), false), gui.board[x][y].coord2ChessY(gui.board[x][y].getY(), false), gui.board[x][y].coord2ChessX(gui.board[x][y].getFarX(), true), gui.board[x][y].coord2ChessY(gui.board[x][y].getFarY(), true)));
							Chess.setHasPutPiece(true);
						}
					}
				}
			}
		}
	}
	
	public boolean spaceEmpty(GUI gui, Board board){
		for(int x = 0; x < gui.rPawn.length;x++)
			if(gui.rPawn[x].getX() == board.getX() && gui.rPawn[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.lPawn.length;x++)
			if(gui.lPawn[x].getX() == board.getX() && gui.lPawn[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.rRook.length;x++)
			if(gui.rRook[x].getX() == board.getX() && gui.rRook[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.lRook.length;x++)
			if(gui.lRook[x].getX() == board.getX() && gui.lRook[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.rBishop.length;x++)
			if(gui.rBishop[x].getX() == board.getX() && gui.rBishop[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.lBishop.length;x++)
			if(gui.lBishop[x].getX() == board.getX() && gui.lBishop[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.rKnight.length;x++)
			if(gui.rKnight[x].getX() == board.getX() && gui.rKnight[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.lKnight.length;x++)
			if(gui.lKnight[x].getX() == board.getX() && gui.lKnight[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.rQueen.length;x++)
			if(gui.rQueen[x].getX() == board.getX() && gui.rQueen[x].getY() == board.getY())
				return false;
		for(int x = 0; x < gui.lQueen.length;x++)
			if(gui.lQueen[x].getX() == board.getX() && gui.lQueen[x].getY() == board.getY())
				return false;
		if(gui.rKing.getX() == board.getX() && gui.rKing.getY() == board.getY())
			return false;
		if(gui.lKing.getX() == board.getX() && gui.lKing.getY() == board.getY())
			return false;
		return true;
	}
	
	public boolean canCastle(GUI gui, Board board, Piece piece){
		//this method needs a method that doesn't exist yet
		//the board square the king is standing on, the king is trying to move to, and the one the king
		//is trying to go through need to be checked will put the king in check/the king is already in
		//check
		//"The king may not move out of, through, or into check."
		if(piece.getType() == "king"){
			if(!piece.hasMoved()){
				if(isPieceWhite(gui, piece)){
					if(board.getX() == piece.getX() - 100 && board.getY() == piece.getY()){
						if(spaceEmpty(gui,gui.board[1][7]) && spaceEmpty(gui,gui.board[2][7]) && spaceEmpty(gui,gui.board[3][7])){
							if(gui.rRook[0].isAlive() && !gui.rRook[0].hasMoved())
								return true;
							else
								return false;
						}
						else
							return false;
					}
					else if(board.getX() == piece.getX() + 100 && board.getY() == piece.getY()){
						if(spaceEmpty(gui,gui.board[5][7]) && spaceEmpty(gui,gui.board[6][7])){
							if(gui.rRook[1].isAlive() && !gui.rRook[1].hasMoved())
								return true;
							else
								return false;
						}
						else
							return false;
					}
					else
						return false;
				}
				else{
					if(board.getX() == piece.getX() - 100 && board.getY() == piece.getY()){
						if(spaceEmpty(gui,gui.board[1][0]) && spaceEmpty(gui,gui.board[2][0]) && spaceEmpty(gui,gui.board[3][0])){
							if(gui.lRook[0].isAlive() && !gui.lRook[0].hasMoved())
								return true;
							else
								return false;
						}
						else
							return false;
					}
					else if(board.getX() == piece.getX() + 100 && board.getY() == piece.getY()){
						if(spaceEmpty(gui,gui.board[5][0]) && spaceEmpty(gui,gui.board[6][0])){
							if(gui.lRook[1].isAlive() && !gui.lRook[1].hasMoved())
								return true;
							else
								return false;
						}
						else
							return false;
					}
					else
						return false;
				}
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean canMove(GUI gui, Board board, Piece piece){
		if(piece.getType() == "rpawn"){
			if(piece.getX() == board.getX() && piece.getY() - 50 == board.getY()){
				return true;
			}
			else if(piece.canGoTwice() && piece.getX() == board.getX() && piece.getY() - 100 == board.getY()){
				for(int x = 0;x < gui.board.length;x++){
					if(board == gui.board[x][4]){
						if(!spaceEmpty(gui,gui.board[x][5])){
							return false;
						}
					}
				}
				return true;
			}
			else
				return false;
		}
		else if(piece.getType() == "lpawn"){
			if(piece.getX() == board.getX() && piece.getY() + 50 == board.getY()){
				return true;
			}
			else if(piece.canGoTwice() && piece.getX() == board.getX() && piece.getY() + 100 == board.getY()){
				for(int x = 0;x < gui.board.length;x++){
					if(board == gui.board[x][3]){
						if(!spaceEmpty(gui,gui.board[x][2])){
							return false;
						}
					}
				}
				return true;
			}
			else
				return false;
		}
		else if(piece.getType() == "rook"){
			if(piece.getX() == board.getX()){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0 ;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getX() == piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY() &&
									board.getY() > piece.getY() && whichPiece(gui,gui.board[x][y]).getY() < board.getY() &&
									whichPiece(gui,gui.board[x][y]) != piece && whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY() &&
									board.getY() < piece.getY() && whichPiece(gui,gui.board[x][y]).getY() > board.getY() &&
									whichPiece(gui,gui.board[x][y]) != piece && whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
							}
						}
					}
				}
					return true;
			}
			else if(piece.getY() == board.getY()){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0 ;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getY() == piece.getY()){
								if(whichPiece(gui,gui.board[x][y]).getX() > piece.getX() &&
									board.getX() > piece.getX() && whichPiece(gui,gui.board[x][y]).getX() < board.getX() &&
									whichPiece(gui,gui.board[x][y]) != piece && whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
								else if(whichPiece(gui,gui.board[x][y]).getX() < piece.getX() &&
									board.getX() < piece.getX() && whichPiece(gui,gui.board[x][y]).getX() > board.getX() &&
									whichPiece(gui,gui.board[x][y]) != piece && whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
							}
						}
					}
				}
					return true;
			}
			else
				return false;
		}
		else if(piece.getType() == "bishop"){
			if(absValue(board.getX() - piece.getX()) == absValue(board.getY() - piece.getY())){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getX() > piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() < board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() < board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY()) &&
										whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() < board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() > board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY()) &&
										whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
							}
							else if(whichPiece(gui,gui.board[x][y]).getX() < piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() > board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() < board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY()) &&
										whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() > board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() > board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY()) &&
										whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
							}
						}
					}
				}
				return true;
			}
			else
				return false;
		}
		else if(piece.getType() == "knight"){
			if(board.getX() == piece.getX() - 50){
				if(board.getY() == piece.getY() + 100 || board.getY() == piece.getY() - 100)
					return true;
				else
					return false;
			}
			else if(board.getX() == piece.getX() - 100){
				if(board.getY() == piece.getY() + 50 || board.getY() == piece.getY() - 50)
					return true;
				else
					return false;
			}
			else if(board.getX() == piece.getX() + 50){
				if(board.getY() == piece.getY() + 100 || board.getY() == piece.getY() - 100)
					return true;
				else
					return false;
			}
			else if(board.getX() == piece.getX() + 100){
				if(board.getY() == piece.getY() + 50 || board.getY() == piece.getY() - 50)
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else if(piece.getType() == "queen"){
			if(piece.getX() == board.getX()){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0 ;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getX() == piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY() &&
									board.getY() > piece.getY() && whichPiece(gui,gui.board[x][y]).getY() < board.getY()
									&& whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY() &&
									board.getY() < piece.getY() && whichPiece(gui,gui.board[x][y]).getY() > board.getY()
									&& whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
							}
						}
					}
				}
					return true;
			}
			else if(piece.getY() == board.getY()){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0 ;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getY() == piece.getY()){
								if(whichPiece(gui,gui.board[x][y]).getX() > piece.getX() &&
									board.getX() > piece.getX() && whichPiece(gui,gui.board[x][y]).getX() < board.getX()
									&& whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
								else if(whichPiece(gui,gui.board[x][y]).getX() < piece.getX() &&
									board.getX() < piece.getX() && whichPiece(gui,gui.board[x][y]).getX() > board.getX()
									&& whichPiece(gui,gui.board[x][y]).isAlive())
									return false;
							}
						}
					}
				}
					return true;
			}
			if(absValue(board.getX() - piece.getX()) == absValue(board.getY() - piece.getY())){
				for(int x = 0; x < gui.board.length;x++){
					for(int y = 0;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							if(whichPiece(gui,gui.board[x][y]).getX() > piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() < board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() < board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY())
										&& whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() < board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() > board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY())
										&& whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
							}
							else if(whichPiece(gui,gui.board[x][y]).getX() < piece.getX()){
								if(whichPiece(gui,gui.board[x][y]).getY() > piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() > board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() < board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY())
										&& whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
								else if(whichPiece(gui,gui.board[x][y]).getY() < piece.getY()){
									if(whichPiece(gui,gui.board[x][y]).getX() > board.getX() &&
										whichPiece(gui,gui.board[x][y]).getY() > board.getY() && 
										absValue(whichPiece(gui,gui.board[x][y]).getX()-piece.getX()) ==
										absValue(whichPiece(gui,gui.board[x][y]).getY()-piece.getY())
										&& whichPiece(gui,gui.board[x][y]).isAlive())
										return false;
								}
							}
						}
					}
				}
				return true;
			}
			else
				return false;
		}
		else if(piece.getType() == "king"){
			if(piece.getX() == board.getX() && absValue(board.getY() - piece.getY()) == 50 ||
				piece.getY() == board.getY() && absValue(board.getX() - piece.getX()) == 50)
				return true;
			else if(absValue(board.getX() - piece.getX()) == absValue(board.getY() - piece.getY()) &&
				absValue(board.getX() - piece.getX()) == 50)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean canAttack(GUI gui, Board board, Piece piece){
		if(piece.getType() == "rpawn"){
			if(!spaceEmpty(gui,board) && absValue(board.getX() - piece.getX()) == 50 &&
				board.getY() - piece.getY() == -50){
				if(isPieceWhite(gui,piece) && !isPieceWhite(gui,whichPiece(gui,board)))
					return true;
				else if(isPieceWhite(gui,piece) && !isPieceWhite(gui,whichPiece(gui,board)))
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else if(piece.getType() == "lpawn"){
			if(!spaceEmpty(gui,board) && absValue(board.getX() - piece.getX()) == 50 &&
				board.getY() - piece.getY() == 50){
				if(!isPieceWhite(gui,piece) && isPieceWhite(gui,whichPiece(gui,board)))
					return true;
				else if(!isPieceWhite(gui,piece) && isPieceWhite(gui,whichPiece(gui,board)))
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public boolean isPieceWhite(GUI gui, Piece piece){
		for(int x = 0;x < gui.rPawn.length;x++)
			if(gui.rPawn[x] == piece)
				return true;
		for(int x = 0;x < gui.rRook.length;x++)
			if(gui.rRook[x] == piece)
				return true;
		for(int x = 0;x < gui.rBishop.length;x++)
			if(gui.rBishop[x] == piece)
				return true;
		for(int x = 0;x < gui.rQueen.length;x++)
			if(gui.rQueen[x] == piece)
				return true;
		for(int x = 0;x < gui.rKnight.length;x++)
			if(gui.rKnight[x] == piece)
				return true;
		if(gui.rKing == piece)
			return true;
		return false;
	}
	
	public Piece whichPiece(GUI gui, Board board){
		if(!spaceEmpty(gui,board)){
			for(int x = 0;x < gui.rPawn.length;x++)
				if(gui.rPawn[x].getX() == board.getX() && gui.rPawn[x].getY() == board.getY())
					return gui.rPawn[x];
			for(int x = 0;x < gui.rRook.length;x++)
				if(gui.rRook[x].getX() == board.getX() && gui.rRook[x].getY() == board.getY())
					return gui.rRook[x];
			for(int x = 0;x < gui.rBishop.length;x++)
				if(gui.rBishop[x].getX() == board.getX() && gui.rBishop[x].getY() == board.getY())
					return gui.rBishop[x];
			for(int x = 0;x < gui.rQueen.length;x++)
				if(gui.rQueen[x].getX() == board.getX() && gui.rQueen[x].getY() == board.getY())
					return gui.rQueen[x];
			for(int x = 0;x < gui.rKnight.length;x++)
				if(gui.rKnight[x].getX() == board.getX() && gui.rKnight[x].getY() == board.getY())
					return gui.rKnight[x];
			if(gui.rKing.getX() == board.getX() && gui.rKing.getY() == board.getY())
				return gui.rKing;
			
			for(int x = 0;x < gui.lPawn.length;x++)
				if(gui.lPawn[x].getX() == board.getX() && gui.lPawn[x].getY() == board.getY())
					return gui.lPawn[x];
			for(int x = 0;x < gui.lRook.length;x++)
				if(gui.lRook[x].getX() == board.getX() && gui.lRook[x].getY() == board.getY())
					return gui.lRook[x];
			for(int x = 0;x < gui.lBishop.length;x++)
				if(gui.lBishop[x].getX() == board.getX() && gui.lBishop[x].getY() == board.getY())
					return gui.lBishop[x];
			for(int x = 0;x < gui.lQueen.length;x++)
				if(gui.lQueen[x].getX() == board.getX() && gui.lQueen[x].getY() == board.getY())
					return gui.lQueen[x];
			for(int x = 0;x < gui.lKnight.length;x++)
				if(gui.lKnight[x].getX() == board.getX() && gui.lKnight[x].getY() == board.getY())
					return gui.lKnight[x];
			if(gui.lKing.getX() == board.getX() && gui.lKing.getY() == board.getY())
				return gui.lKing;
			
			return null;
		}
		else
			return null;
	}
	
	public Piece whichPiece1(GUI gui, Board board){
		if(!spaceEmpty(gui,board)){
			if(gui.lKing.getX() == board.getX() && gui.lKing.getY() == board.getY())
				return gui.lKing;
			for(int x = 0;x < gui.lKnight.length;x++)
				if(gui.lKnight[x].getX() == board.getX() && gui.lKnight[x].getY() == board.getY())
					return gui.lKnight[x];
			for(int x = 0;x < gui.lQueen.length;x++)
				if(gui.lQueen[x].getX() == board.getX() && gui.lQueen[x].getY() == board.getY())
					return gui.lQueen[x];
			for(int x = 0;x < gui.lBishop.length;x++)
				if(gui.lBishop[x].getX() == board.getX() && gui.lBishop[x].getY() == board.getY())
					return gui.lBishop[x];
			for(int x = 0;x < gui.lRook.length;x++)
				if(gui.lRook[x].getX() == board.getX() && gui.lRook[x].getY() == board.getY())
					return gui.lRook[x];
			for(int x = 0;x < gui.lPawn.length;x++)
				if(gui.lPawn[x].getX() == board.getX() && gui.lPawn[x].getY() == board.getY())
					return gui.lPawn[x];
			
			if(gui.rKing.getX() == board.getX() && gui.rKing.getY() == board.getY())
				return gui.rKing;
			for(int x = 0;x < gui.rKnight.length;x++)
				if(gui.rKnight[x].getX() == board.getX() && gui.rKnight[x].getY() == board.getY())
					return gui.rKnight[x];
			for(int x = 0;x < gui.rQueen.length;x++)
				if(gui.rQueen[x].getX() == board.getX() && gui.rQueen[x].getY() == board.getY())
					return gui.rQueen[x];
			for(int x = 0;x < gui.rBishop.length;x++)
				if(gui.rBishop[x].getX() == board.getX() && gui.rBishop[x].getY() == board.getY())
					return gui.rBishop[x];
			for(int x = 0;x < gui.rRook.length;x++)
				if(gui.rRook[x].getX() == board.getX() && gui.rRook[x].getY() == board.getY())
					return gui.rRook[x];
			for(int x = 0;x < gui.rPawn.length;x++)
				if(gui.rPawn[x].getX() == board.getX() && gui.rPawn[x].getY() == board.getY())
					return gui.rPawn[x];
			
			return null;
		}
		else
			return null;
	}
	
	public boolean isPawn(Piece piece){
		if(piece.getType() == "rpawn" || piece.getType() == "lpawn")
			return true;
		else
			return false;
	}
	
	private int absValue(int num){
		if(num < 0){
			return -num;
		}
		else
			return num;
	}
	
	public void movePiece(String move, GUI gui, MoveAnalyzer ma){
		if(move != "STOP"){
			if(move.toCharArray()[1] == '-'){
				if(move.length() == 3){
					if(!Chess.isWhite()){
						gui.rKing.setX(gui.rKing.getX() + 100);
						gui.rKing.setFarX(gui.rKing.getX() + 50);
						gui.rRook[1].setX(gui.rRook[1].getX() - 100);
						gui.rRook[1].setFarX(gui.rRook[1].getX() + 50);
						gui.rKing.setMoved(true);
						gui.rRook[1].setMoved(true);
					}
					else{
						gui.lKing.setX(gui.lKing.getX() + 100);
						gui.lKing.setFarX(gui.lKing.getX() + 50);
						gui.lRook[1].setX(gui.lRook[1].getX() - 100);
						gui.lRook[1].setFarX(gui.lRook[1].getX() + 50);
						gui.lKing.setMoved(true);
						gui.lRook[1].setMoved(true);
					}
				}
				else if(move.length() == 5){
					if(!Chess.isWhite()){
						gui.rKing.setX(gui.rKing.getX() - 100);
						gui.rKing.setFarX(gui.rKing.getX() + 50);
						gui.rRook[0].setX(gui.rRook[0].getX() + 150);
						gui.rRook[0].setFarX(gui.rRook[0].getX() + 50);
						gui.rKing.setMoved(true);
						gui.rRook[0].setMoved(true);
					}
					else{
						gui.lKing.setX(gui.lKing.getX() - 100);
						gui.lKing.setFarX(gui.lKing.getX() + 50);
						gui.lRook[0].setX(gui.lRook[0].getX() + 150);
						gui.lRook[0].setFarX(gui.lRook[0].getX() + 50);
						gui.lKing.setMoved(true);
						gui.lRook[0].setMoved(true);
					}
				}
			}
			else if(move.toCharArray()[0] == 'P'){
				for(int x = 0;x < gui.board.length;x++){
					for(int y = 0 ;y < gui.board[x].length;y++){
						if(whichPiece(gui,gui.board[x][y]) != null){
							Piece piece = whichPiece(gui,gui.board[x][y]);
							if(piece.getType() == "rpawn" && piece.getY() == 50){
								if(move.toCharArray()[2] == 'K'){
									for(int a = 0; a < gui.rKnight.length;a++){
										if(!gui.rKnight[a].isAlive()){
											piece.setAlive(false);
											gui.rKnight[a].setAlive(true);
											gui.rKnight[a].setX(piece.getX());
											gui.rKnight[a].setY(piece.getY());
											gui.rKnight[a].setFarX(piece.getFarX());
											gui.rKnight[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.rKnight.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'B'){
									for(int a = 0; a < gui.rBishop.length;a++){
										if(!gui.rBishop[a].isAlive()){
											piece.setAlive(false);
											gui.rBishop[a].setAlive(true);
											gui.rBishop[a].setX(piece.getX());
											gui.rBishop[a].setY(piece.getY());
											gui.rBishop[a].setFarX(piece.getFarX());
											gui.rBishop[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.rBishop.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'R'){
									for(int a = 0; a < gui.rRook.length;a++){
										if(!gui.rRook[a].isAlive()){
											piece.setAlive(false);
											gui.rRook[a].setAlive(true);
											gui.rRook[a].setX(piece.getX());
											gui.rRook[a].setY(piece.getY());
											gui.rRook[a].setFarX(piece.getFarX());
											gui.rRook[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.rRook.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'Q'){
									for(int a = 0; a < gui.rQueen.length;a++){
										if(!gui.rQueen[a].isAlive()){
											piece.setAlive(false);
											gui.rQueen[a].setAlive(true);
											gui.rQueen[a].setX(piece.getX());
											gui.rQueen[a].setY(piece.getY());
											gui.rQueen[a].setFarX(piece.getFarX());
											gui.rQueen[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.rQueen.length;
										}
									}
								}
							}
							if(piece.getType() == "lpawn" && piece.getY() == 400){
								if(move.toCharArray()[2] == 'K'){
									for(int a = 0; a < gui.lKnight.length;a++){
										if(!gui.lKnight[a].isAlive()){
											piece.setAlive(false);
											gui.lKnight[a].setAlive(true);
											gui.lKnight[a].setX(piece.getX());
											gui.lKnight[a].setY(piece.getY());
											gui.lKnight[a].setFarX(piece.getFarX());
											gui.lKnight[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.lKnight.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'B'){
									for(int a = 0; a < gui.lBishop.length;a++){
										if(!gui.lBishop[a].isAlive()){
											piece.setAlive(false);
											gui.lBishop[a].setAlive(true);
											gui.lBishop[a].setX(piece.getX());
											gui.lBishop[a].setY(piece.getY());
											gui.lBishop[a].setFarX(piece.getFarX());
											gui.lBishop[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.lBishop.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'R'){
									for(int a = 0; a < gui.lRook.length;a++){
										if(!gui.lRook[a].isAlive()){
											piece.setAlive(false);
											gui.lRook[a].setAlive(true);
											gui.lRook[a].setX(piece.getX());
											gui.lRook[a].setY(piece.getY());
											gui.lRook[a].setFarX(piece.getFarX());
											gui.lRook[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.lRook.length;
										}
									}
								}
								else if(move.toCharArray()[2] == 'Q'){
									for(int a = 0; a < gui.lQueen.length;a++){
										if(!gui.lQueen[a].isAlive()){
											piece.setAlive(false);
											gui.lQueen[a].setAlive(true);
											gui.lQueen[a].setX(piece.getX());
											gui.lQueen[a].setY(piece.getY());
											gui.lQueen[a].setFarX(piece.getFarX());
											gui.lQueen[a].setFarY(piece.getFarY());
											
											piece.setX(0);
											piece.setY(0);
											piece.setFarX(0);
											piece.setFarY(0);
											a = gui.lQueen.length;
										}
									}
								}
							}
						}
					}
				}
			}
			else{
				ma.moveDecoder(move);
				Piece piece = new Piece("","");
				for(int x = 0;x < gui.board.length;x++){
					for(int y = 0; y < gui.board[x].length;y++){
						if(gui.board[x][y].getX() ==
							gui.board[x][y].chess2CoordX(ma.getFrom1().toCharArray()[0], false) &&
							gui.board[x][y].getY() ==
							gui.board[x][y].chess2CoordY(ma.getFrom(), false) &&
							whichPiece(gui,gui.board[x][y]) != null){
							
							piece = whichPiece(gui,gui.board[x][y]);
							piece.setX(gui.board[x][y].chess2CoordX(ma.getTo1().toCharArray()[0], false));
							piece.setFarX(gui.board[x][y].chess2CoordX(ma.getTo1().toCharArray()[0], true));
							piece.setY(gui.board[x][y].chess2CoordY(ma.getTo(),false));
							piece.setFarY(gui.board[x][y].chess2CoordY(ma.getTo(),true));
							if(move.length() >= 5){
								if(move.toCharArray()[4] == '+')
									piece.setAttacked(true);
							}
							piece.setMoved(true);
							killPieces(gui);
						}
					}
				}
			}
		}
	}
}
