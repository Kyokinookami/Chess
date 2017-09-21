package Server;
import java.applet.Applet;


public class Main {
	static javax.swing.JFrame window = new javax.swing.JFrame();
	static Applet applet = new Server();
	
	public static void main(String[] args){
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.setSize(Server.sWidth+6,Server.sHeight+28);
		//6 and 28 are part of the actual window I'm guessing; anyways they are needed
		window.setTitle("P2P Chess Manager");
		window.setResizable(false);
		window.add(applet);
		applet.init();
		applet.start();
		window.setVisible(true);
		//System.out.println(System.getProperty("user.dir"));
	}
}
