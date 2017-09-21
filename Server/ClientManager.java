package Server;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientManager {
	private int port = 12121;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String IP = "";
	private String LFIP = "";
	//LFIP looking for IP
	private String move = "";
	private String serverIP;
	private Socket connection;
	
	private boolean whichClient = false;
	//one of these for every two clients
	//false means first, true means second
	
	private ObjectOutputStream output1;
	private ObjectInputStream input1;
	private Socket connection1;
	private String IP1 = "";
	private String LFIP1 = "";
	
	public void setMove(String move){this.move = move;}
	public void setServerIP(String serverIP){this.serverIP = serverIP;}

	public String getMove(){return move;}
	public String getServerIP(){return serverIP;}
	
	
	private ServerSocket server;
	
	
	public void startRunning(){
		try {server = new ServerSocket(port, 100);}
		catch (IOException e1) {e1.printStackTrace();}
		try{
			connection = server.accept();
			//tries to connect to the server
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			IP = connection.getInetAddress().toString();
			LFIP = readMove(input);
			//one set of connections to client
			connection1 = server.accept();
			//tries to connect to the server
			output1 = new ObjectOutputStream(connection1.getOutputStream());
			output1.flush();
			input1 = new ObjectInputStream(connection1.getInputStream());
			IP1 = connection1.getInetAddress().toString();
			LFIP1 = readMove(input1);
			//second set of connections to client
			//checks if any the IP and LFIP are the same of any two Clients
			//if they are, then do this:
			
			//random number generator here tells if the first or second client will be white
			sendMove("WHITE",output);
			sendMove("BLACK",output1);
			//this is temp, they will be in array form and rng will determine white or black
			whichClient = false;
			do{
				setMove("");
				while(getMove() == ""){
					if(!whichClient){
						setMove(readMove(input));
						if(getMove().toCharArray()[getMove().length()-1] == '-'){
							System.out.println(getMove() + "1.");
							sendMove(getMove(),output1);
							setMove(readMove(input));
							System.out.println(getMove() + "2.");
						}
					}
					else{
						setMove(readMove(input1));
						if(getMove().toCharArray()[getMove().length()-1] == '-'){
							System.out.println(getMove() + "3.");
							sendMove(getMove(),output);
							setMove(readMove(input1));
							System.out.println(getMove() + "4.");
						}
					}
				}
				//listens for a move from the client that started white, then switches every time it gets the messege
				if(!whichClient){
					sendMove(getMove(),output1);
				}
				else{
					sendMove(getMove(),output);
				}
				whichClient = (whichClient) ? false : true;
			}while(move != "STOP");
		}
		catch(EOFException e){e.printStackTrace();}
		catch(IOException e){e.printStackTrace();}
		finally{
			try{
				output.close();
				input.close();
				connection.close();
				output1.close();
				input1.close();
				connection1.close();
			}
			catch(IOException e){e.printStackTrace();}
			//this try catch tries to close all the thingies
		}
	}
	
	private void sendMove(String move,ObjectOutputStream output){
		try{
			output.writeObject(move);
			output.flush();
		}
		catch(IOException e){e.printStackTrace();}
	}
	private String readMove(ObjectInputStream input){
		String move = "";
		try{
			move = (String) input.readObject().toString();
		}catch(ClassNotFoundException e){e.printStackTrace();move = "STOP";}
		catch(IOException e){e.printStackTrace();move = "STOP";}
		return move;
	}
	
}
