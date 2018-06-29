import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class foodBoard{

	Integer sizeX;
	Integer sizeY;
	int[][] multD;

	public foodBoard( Integer sX, Integer sY ){

		sizeX = sX;
		sizeY = sY;
		multD = new int[sizeX][sizeY];

		for (int i = 0; i<multD.length; i++) {
    		for (int j = 0; j<multD[i].length; j++) {
        		multD[i][j] = 10 ;
    		}
		}

	
	}

	public void printBoard(){
		for (int i = 0; i<multD.length; i++) {
    		for (int j = 0; j<multD[i].length; j++) {
        		System.out.print(multD[i][j] + "\t");
    		}
    		System.out.println();
		}
	}

	public void regrowBoard(){
		for (int i = 0; i<multD.length; i++) {
    		for (int j = 0; j<multD[i].length; j++) {
        		multD[i][j] += 1;
    		}
    	}
	}

	public void changeBoard(int i, int j, int k){
		multD[i][j]+=k;

	}

	public int getValue(int i, int j){

		return multD[i][j];
	}

	

	public void saveImage(int imageNo){

		try {
		    BufferedImage image = new  BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
		    for(int i = 0; i<multD.length; i++){
		        for(int c = 0; c<multD[i].length; c++) {
		            int a = (multD[i][c]/imageNo) * 255;
		            if(a > 255){
		            	a=255;
		            }
		            Color newColor = new Color(a,a,a);
		            image.setRGB(i,c,newColor.getRGB());
        		}
    		}
		    File output = new File("foodImages/GrayScale" + imageNo+".jpg");
		    ImageIO.write(image, "jpg", output);
		}

		catch(Exception e) {}
	}


	/* Test to see if food Board works
	public static void main(String[] args){
		ConwayBoard foodBoard = new ConwayBoard( 5, 10 );
		foodBoard.regrowBoard();
		foodBoard.printBoard();
		
	}
	*/
}