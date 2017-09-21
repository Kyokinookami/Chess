package Client;
import java.awt.Image;
import java.awt.Toolkit;


public class FontImg{

	private int length = 0;
	private int height = 0;
	private Image image;
	private Toolkit tk = Toolkit.getDefaultToolkit();	

	public int getLength(){return length;}
	public int getHeight(){return height;}
	public Image getAPIImg(){return image;}
	
	public void setLength(int length){this.length = length;}
	public void setHeight(int height){this.height = height;}
	public void setImg(String a){image = tk.getImage(getClass().getResource(a));}
	
	public FontImg(int length, int height, String image){
		setHeight(height);
		setLength(length);
		setImg(image);
	}
}
