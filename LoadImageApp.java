
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


/**
 * explanation about the stages of the program:
 */
public class LoadImageApp extends Component {
    public static void main(String[] args) {

    	//The inputs of the program:
    	String InputPath = null; //Full path to the input image
    	int NumCol = 0; //Number of columns of the resized output image
    	int NumRow = 0; //Number of rows of the resized output image
    	int energy = 0; //An argument with three possible values, where '0' = regular energy without entropy term,
    					//'1' = regular energy with entropy term and '2' = forward energy
    	String OutputPath = null; //Full path to the output image

    	if (args.length > 0) {
    	    try {
    	        NumCol = Integer.parseInt(args[1]);
    	        NumRow = Integer.parseInt(args[2]);
    	        energy = Integer.parseInt(args[3]);
    	    } catch (NumberFormatException e) {
    	        System.err.println("Arguments must be an integeres.");
    	        System.exit(1);
    	    }
    	    if (energy <0 || energy>2) {
    	    	System.out.println("The value of the energy is wrong");
    	    	System.exit(energy);
    	    	
    	    }
    	    InputPath = args[0];
    	    OutputPath = args[4];
    	}
    	
    	// Open the input image
        BufferedImage img;
        try {
            img = ImageIO.read(new File(InputPath));
        } catch(IOException e) {
            System.err.println("Can't open " + InputPath);
            return;
        }
        BufferedImage newImage = img;
        
        //defining the variables:
        int width = img.getWidth();
        int height = img.getHeight();
        System.out.printf("the width of the picture is %s \n",width);
        System.out.printf("the height of the picture is %s \n",height);
        int Num_vertical_seams = width - NumCol;
        int Num_horizontal_seams = height - NumRow;
        //checking if we have to make the picture smaller or larger 
        //and in which direction	
        
        //make the new picture, each step remove only one seam from the same direction.
        while (Num_horizontal_seams > 0) { //have to make the img smaller on horizontal axis
        	// Get the new image without one seam.
            System.out.printf("we need to remove from the pictures %d seam in horizontal direction \n",Num_horizontal_seams);

        	newImage = carveSeam(newImage, "horizontal",energy);

        	Num_horizontal_seams = Num_horizontal_seams - 1;
        }
        while (Num_vertical_seams > 0) {
        	// Get the new image without one seam.
            System.out.printf("we need to remove from the pictures %d seam in vertical direction \n",Num_vertical_seams);

        	newImage = carveSeam(newImage, "vertical", energy);

        	Num_vertical_seams = Num_vertical_seams - 1;
        }
        //make the new picture, each step add only one seam from the same direction.
        while (Num_horizontal_seams < 0) { //have to make the img smaller on horizontal axis
        	// Get the new image without one seam.
            System.out.printf("we need to add the pictures %d seam in horizontal direction \n",Num_horizontal_seams);

        	newImage = addSeam(newImage, "horizontal",energy);

        	Num_horizontal_seams = Num_horizontal_seams + 1;
        }
        while (Num_vertical_seams < 0) {
        	// Get the new image without one seam.
            System.out.printf("we need to add the pictures %d seam in vertical direction \n",Num_vertical_seams);

        	newImage = addSeam(newImage, "vertical", energy);

        	Num_vertical_seams = Num_vertical_seams + 1;
        }
        // Create the new image file.
        try {
            File outputfile = new File(OutputPath);
            ImageIO.write(newImage, "png", outputfile);
            System.out.printf("the new size is %d %d", newImage.getWidth(),newImage.getHeight());
        } catch (IOException e) {
            System.err.println("Trouble saving " + OutputPath);
            return;
        }

    }
    //carveSeam() receives an image and removes one seam from it by the direction.
    private static BufferedImage carveSeam (BufferedImage img, String direction, int energy) {
    	BufferedImage newImage = null;
    	double[][] energyTable = EnergyComputation(img);
    	if (energy ==1) {
    		double[][] entropyTable = EntropyComputation(img);
    		double[][] combainedTable = CombineTables(img, energyTable, entropyTable);
    		energyTable = combainedTable;
    	}
    	if (energy == 2) {
    		double[][] forwardEnergy = forwardEnergy(img);
    		energyTable = forwardEnergy;
    	}
    	int [][] seam = SeamFinder(energyTable, direction, "general");
    	newImage = seamRemove(img, seam, direction);
    	return newImage;
    }
  //addSeam() receives an image and adds one seam to it by the direction.
    private static BufferedImage addSeam (BufferedImage img, String direction, int energy) {
    	BufferedImage newImage = null;
    	double[][] energyTable = EnergyComputation(img);
    	if (energy ==1) {
    		double[][] entropyTable = EntropyComputation(img);
    		double[][] combainedTable = CombineTables(img, energyTable, entropyTable);
    		energyTable = combainedTable;
    	}
    	if (energy == 2) {
    		double[][] forwardEnergy = forwardEnergy(img);
    		energyTable = forwardEnergy;
    	}
    	int [][] seam = SeamFinder(energyTable, direction, "general");
    	newImage = seamAdd(img, seam, direction);
    	return newImage;
    }
    
    // ************* TODO - Bonus ************************************************
    public static String chooseDirection(BufferedImage img){ // chose the direction of the seam
    	return "Horizontal";
    }
    
    
    //computing the entropy
    public static double[][] EntropyComputation(BufferedImage img){
    
    	int width = img.getWidth();
    	int height = img.getHeight();
    	double[][] entropyTable = new double[width][height];
    	
    	//*********** TODO *******************
    	
    	return entropyTable;
    }
    
    
    // The combined table is a combination between the entropy table and the energy table with the proportion of our choise
    public static double[][] CombineTables(BufferedImage img, double[][] energyTable, double[][] entropyTable) {
    	int width = img.getWidth();
    	int height = img.getHeight();
    	double[][] CombainedTable = new double[width][height];
    	
    	//*********** TODO *******************
    	
    	return CombainedTable;
    }
  //computing the forward energy
    public static double[][] forwardEnergy(BufferedImage img){
    
    	int width = img.getWidth();
    	int height = img.getHeight();
    	double[][] energyTable = new double[width][height];
    	
    	//*********** TODO *******************
    	
    	return energyTable;
    }
    //computing the energy
    public static double[][] EnergyComputation(BufferedImage img){
    	
    	int width = img.getWidth();
    	int height = img.getHeight();
    	double[][] energyTable = new double[width][height];//the output
    	int current_pixel, temp,current_red,current_blue,current_green;
    	//define the array that will store the info about the neighbors
    	int[][] val;
        val = new int[8][4];
        //loop through all the pixels in the picture and compute it's energy
    	for (int x = 0; x < width; x++) {
    		for (int y = 0; y < height; y++) {
    			current_pixel = img.getRGB(x,y); // get RGB of current pixel
    			current_red = (current_pixel & 0x00ff0000) >> 16;
    			current_green = (current_pixel & 0x0000ff00) >> 8;
    			current_blue = (current_pixel & 0x000000ff);
    			//Deal with the edges:
    			if (y == 0) { //the first column
    				temp = img.getRGB(x, y+1) ;
    				val[0][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[0][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[0][2] = (temp & 0x000000ff);//bue color in temp
    				val[0][3] = (Math.abs(current_red-val[0][0])+Math.abs(current_green-val[0][1])+Math.abs(current_blue-val[0][2]))/3;
    				if(x == 0) { // the up-left corner
    					temp = img.getRGB(x+1,y+1);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;

    					temp = img.getRGB(x+1,y);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;

    					energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3])/3;
    				}
    				else if (x == width - 1){ // the down-left corner
    					temp = img.getRGB(x-1,y+1);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
    					
    					temp = img.getRGB(x-1,y);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
    					energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3])/3;
    				}
    				else{ // the rest of the first column
    					temp = img.getRGB(x-1,y);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
    					
    					temp = img.getRGB(x-1,y+1);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
    					
        				temp = img.getRGB(x+1,y+1);
        				val[3][0] = (temp & 0x00ff0000) >> 16;
        				val[3][1] = (temp & 0x0000ff00) >> 8;;
        				val[3][2] = (temp & 0x000000ff);
        				val[3][3] = (Math.abs(current_red-val[3][0])+Math.abs(current_green-val[3][1])+Math.abs(current_blue-val[3][2]))/3;
    					
        				temp = img.getRGB(x+1,y);
        				val[4][0] = (temp & 0x00ff0000) >> 16;
        				val[4][1] = (temp & 0x0000ff00) >> 8;;
        				val[4][2] = (temp & 0x000000ff);
        				val[4][3] = (Math.abs(current_red-val[4][0])+Math.abs(current_green-val[4][1])+Math.abs(current_blue-val[4][2]))/3;
    					
        				energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3]+val[3][3]+val[4][3])/5;
    				}
    			}
    			else if( y == height) { // the last column
    				temp = img.getRGB(x, y-1);
    				val[0][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[0][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[0][2] = (temp & 0x000000ff);//bue color in temp
    				val[0][3] = (Math.abs(current_red-val[0][0])+Math.abs(current_green-val[0][1])+Math.abs(current_blue-val[0][2]))/3;
    				if(x == 0) { // the up-right corner
    					temp = img.getRGB(x+1,y-1);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
    					
    					temp = img.getRGB(x+1,y);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
    					
    					energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3])/3;
    				}
    				else if (x == width - 1){ // the down-right corner
    					temp = img.getRGB(x-1,y-1);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
    					
    					temp = img.getRGB(x-1,y);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
    					
        				energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3])/3;
    				}
    				else{ // the rest of the last column
    					temp = img.getRGB(x-1,y);
    					val[1][0] = (temp & 0x00ff0000) >> 16;
        				val[1][1] = (temp & 0x0000ff00) >> 8;;
        				val[1][2] = (temp & 0x000000ff);
        				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
    					
    					temp = img.getRGB(x-1,y-1);
    					val[2][0] = (temp & 0x00ff0000) >> 16;
        				val[2][1] = (temp & 0x0000ff00) >> 8;;
        				val[2][2] = (temp & 0x000000ff);
        				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
    					
    					temp = img.getRGB(x+1,y-1);
    					val[3][0] = (temp & 0x00ff0000) >> 16;
        				val[3][1] = (temp & 0x0000ff00) >> 8;;
        				val[3][2] = (temp & 0x000000ff);
        				val[3][3] = (Math.abs(current_red-val[3][0])+Math.abs(current_green-val[3][1])+Math.abs(current_blue-val[3][2]))/3;
    					
    					temp = img.getRGB(x+1,y);
    					val[4][0] = (temp & 0x00ff0000) >> 16;
        				val[4][1] = (temp & 0x0000ff00) >> 8;;
        				val[4][2] = (temp & 0x000000ff);
        				val[4][3] = (Math.abs(current_red-val[4][0])+Math.abs(current_green-val[4][1])+Math.abs(current_blue-val[4][2]))/3;
    					
        				energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3]+val[3][3]+val[4][3])/5;    				
        				}
    			}
    			//this pixel is not on the edge:
    			if ((x >= 1) && (x <= width-2 ) && (y >= 1) && (y <= height-2)) {
    				temp = img.getRGB(x-1, y-1);
    				val[0][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[0][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[0][2] = (temp & 0x000000ff);//bue color in temp
    				val[0][3] = (Math.abs(current_red-val[0][0])+Math.abs(current_green-val[0][1])+Math.abs(current_blue-val[0][2]))/3;
				
    				temp = img.getRGB(x-1, y);
    				val[1][0] = (temp & 0x00ff0000) >> 16;
    				val[1][1] = (temp & 0x0000ff00) >> 8;;
    				val[1][2] = (temp & 0x000000ff);
    				val[1][3] = (Math.abs(current_red-val[1][0])+Math.abs(current_green-val[1][1])+Math.abs(current_blue-val[1][2]))/3;
				
    				temp = img.getRGB(x-1, y+1);
    				val[2][0] = (temp & 0x00ff0000) >> 16;
    				val[2][1] = (temp & 0x0000ff00) >> 8;;
    				val[2][2] = (temp & 0x000000ff);
    				val[2][3] = (Math.abs(current_red-val[2][0])+Math.abs(current_green-val[2][1])+Math.abs(current_blue-val[2][2]))/3;
				
    				temp = img.getRGB(x, y + 1);
    				val[3][0] = (temp & 0x00ff0000) >> 16;
    				val[3][1] = (temp & 0x0000ff00) >> 8;;
    				val[3][2] = (temp & 0x000000ff);
    				val[3][3] = (Math.abs(current_red-val[3][0])+Math.abs(current_green-val[3][1])+Math.abs(current_blue-val[3][2]))/3;
				
    				temp = img.getRGB(x-1, y+1);
    				val[4][0] = (temp & 0x00ff0000) >> 16;
    				val[4][1] = (temp & 0x0000ff00) >> 8;;
    				val[4][2] = (temp & 0x000000ff);
    				val[4][3] = (Math.abs(current_red-val[4][0])+Math.abs(current_green-val[4][1])+Math.abs(current_blue-val[4][2]))/3;
    			
    				temp = img.getRGB(x-1, y);
    				val[5][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[5][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[5][2] = (temp & 0x000000ff);//bue color in temp
    				val[5][3] = (Math.abs(current_red-val[5][0])+Math.abs(current_green-val[5][1])+Math.abs(current_blue-val[5][2]))/3;
				
    				temp = img.getRGB(x-1, y-1);
    				val[6][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[6][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[6][2] = (temp & 0x000000ff);//bue color in temp
    				val[6][3] = (Math.abs(current_red-val[6][0])+Math.abs(current_green-val[6][1])+Math.abs(current_blue-val[6][2]))/3;
				
    				temp = img.getRGB(x, y-1);
    				val[7][0] = (temp & 0x00ff0000) >> 16; //red color in temp
    				val[7][1] = (temp & 0x0000ff00) >> 8;//green color in temp
    				val[7][2] = (temp & 0x000000ff);//bue color in temp
    				val[7][3] = (Math.abs(current_red-val[7][0])+Math.abs(current_green-val[7][1])+Math.abs(current_blue-val[7][2]))/3;
				
    				energyTable[x][y] = (val[0][3]+val[1][3]+val[2][3]+val[3][3]+val[4][3]+val[5][3]+val[6][3]+val[7][3])/8;
    		
    			}
    		}
    	}
    	
    	
    	return energyTable;
    }

    //computing the vertical seam (the simple version) with dynamic programming
    // If we need to compute the horizontal seam, we transposed the energy table (thus the image)
    private static int[][] SeamFinderGeneral (double[][] energyTable){
    	int[][] seam;
    	int width = energyTable.length;
    	int height = energyTable[0].length;
    	
    	double[][] dynamic = new double[width][height]; // temmporal table for dynamic programming
    	int[][] backtracker = new int[width][height];
    	double min;
    	seam = new int[energyTable[0].length][2];//The seam that we will return
    	//Loop the energy table to find the lowest energy path
    	for(int y=0; y<height; y++) {
    		for(int x=0; x<width; x++) {
    			if (y == 0) { // the first row
    				dynamic [x][y] = energyTable [x][y];
    				backtracker [x][y] = -1;
    			}
    			else {
    				//the energy table except the first row
    				//check if we are on the edges:
    				if (x ==0) {
    					min = Math.min(dynamic[x][y-1], dynamic[x+1][y-1]);
    					if (min == dynamic[x][y-1]) {
    						// note to the backtracker
    						backtracker [x][y] =1;
    					}
    					else {
    						backtracker[x][y] = 2;
    					}
    				
    				} else if ( x==(width -1)) {
    					min = Math.min(dynamic [x][y-1], dynamic[x-1][y-1]);
    					if(min == dynamic [x][y-1]) {
    						backtracker[x][y] =1;
    					} else {
    						backtracker [x][y] = 0;
    					}
    				} else {
    					min = Math.min(dynamic[x-1][y-1], Math.min(dynamic[x][y-1], dynamic[x+1][y-1]));
    					if (min == dynamic [x-1][y-1]) {
    						backtracker [x][y] =0;
    					} else if (min == dynamic [x][y-1]) {
    						backtracker [x][y] =1;
    					} else {
    						backtracker [x][y] =2;
    					}
    				}
    				dynamic [x][y] = energyTable [x][y] +min;
    			}
    		}
    	}
    	
    	
    	// after computing path, backtrack the minimum
    	// searching in the last row for the minimum
    	double min_number = dynamic[0][height-1];
    	int min_indx =0;
    	for (int x =0; x< width; x++) {
    		if (min_number > dynamic[x][height-1]) {
    			min_indx =x;
    			min_number = dynamic [x][height-1];
    		}
    		
    	}
    	
    	//after finding the minimum, we will backtrack the trace to it
    	int y_indx = height -1;
    	int x_indx = min_indx;
    	seam[y_indx][0] = x_indx;
    	seam[x_indx][1] = y_indx;
    	int back;
    	while( y_indx >0) {
    		back = backtracker [x_indx][y_indx];
    		if (back !=-1) {
    			if (back ==0) {
    				x_indx -= 1;
    			}else if (back ==1) {
    				;
    			} else {
    				x_indx += 1;
    			}
    		}else {
    			;
    		}
    		y_indx =y_indx -1;
    		seam[y_indx][0] = x_indx;
    		seam[y_indx][1] = y_indx;
    	}
    	
    	return seam;
    }
  
    
    //************TODO*********************
    //computing the vertical seam (the general version) with dynamic programming
    private static int[][] SeamFinderSimple (double[][] energyTable){
    	int width = energyTable.length;
    	int height = energyTable[0].length;
    	
    	double[][] dynamic = new double[width][height]; // temmporal table for dynamic programming
    	int[][] backtracker = new int[width][height];
    	double min;
    	int[][] seam = new int[energyTable[0].length][2];//The seam that we will return
    	//Loop the energy table to find the lowest energy path
    	// after computing path, find the min
    	//backtrack the minimum
    	
    	return seam;
    }
    
    
    // SeamFinder Calls the functions that suppose to find the seam, according to the direction and simple/general.
    // The seam (a path in the picture) with minimum total energy
    private static int[][] SeamFinder (double[][] energyTable, String direction, String type) {
    	//direction is either horizontal or vertical
    	//type is simple or general

    	if (direction.equals("vertical")) {
        	int[][] seam = new int[energyTable[0].length][2];//The seam that we will return

    		if(type.equals("simple")) {
    			seam = SeamFinderSimple(energyTable);	
    		}
    		else if (type.equals("general")) {
    			seam = SeamFinderGeneral(energyTable);
    		}
    		else {
    			System.out.println("the type in SeamFinder is incorrect");
    		}	
    	return seam;
    	}
    	else if (direction.equals("horizontal")) {
    		
    		//transposing the picture (it's energyTable)
    		double[][] transposedTable = new double[energyTable[0].length][energyTable.length]; 
    		
    		for (int i=0; i< energyTable[0].length; i++ ) {
    			for (int j=0; j<energyTable.length; j++) {
    				transposedTable[i][j] = energyTable[j][i];
    			}
    		}
    		int[][] transposedSeam = new int[transposedTable[0].length][2];
	    	int[][] seam = new int[transposedTable[0].length][2];//The seam that we will return

    		if(type.equals("simple")) {    			
    			transposedSeam = SeamFinderSimple(energyTable);	
    			for(int x =0; x<energyTable[0].length; x++) {
    				seam [x][0] = transposedSeam[x][1];
    				seam [x][1] = transposedSeam[x][0];
    			}
    		}

    		else if (type.equals("general")) {
    			transposedSeam = SeamFinderGeneral(energyTable);
    			//System.out.printf("the transpose seam is in size %d %d", transposedSeam.length,transposedSeam[0].length);
    			
    			for(int x =0; x < transposedSeam.length; x++) {
    				seam [x][0] = transposedSeam[x][1];
    				seam [x][1] = transposedSeam[x][0];
    			}
    		}
    		else {
    			System.out.println("the type in SeamFinder is incorrect");
    			System.exit (1);
    		}
    		return seam;
    	}
    	
    	else {
    		System.out.println("Incorrect direction in SeamFinder. must be vertical or horizontal");
    		System.exit (1);
    	}
    	int [][] temp = new int[1][1]; 
    	return temp;
    }

    // seamRemove is the function the receives the image and the "path" (coordintaes) of the seam
    // it has to remove. by creating a new image and coping there all the pixels of the original
    // image, except for the seam.
    private static BufferedImage seamRemove (BufferedImage img, int[][] seam, String direction) {
    	BufferedImage newImage = null;
    	int width = img.getWidth();
    	int height = img.getHeight();
    	if (direction.equals("vertical")) {
    		newImage = new BufferedImage (width -1, height, BufferedImage.TYPE_INT_ARGB);
    		for (int y=0; y< height; y++) {
    			boolean shift = false;
    			for (int x =0 ; x < width-1; x++) {
    				// check if the pixel is in the seam
    				boolean in_seam = false;
    				if ((seam[y][0] == x) && (seam[y][1] == y)) {
    					in_seam = true;
    					shift = true;
    				}
    			if (!in_seam) {
    				if(shift) {
    					newImage.setRGB(x-1, y, img.getRGB(x, y));
    				} else {
    					newImage.setRGB(x, y, img.getRGB(x, y));
    					
    				}
    			}
    			}
    			
    		}
    	}
    	else if (direction.equals("horizontal")) {
    		newImage = new BufferedImage (width, height-1, BufferedImage.TYPE_INT_ARGB);
    		for (int x =0; x<width; x++) {
    			boolean shift = false;
    			for(int y=0; y< height-1; y++) {
    				// check if the pixel in the seam
    				boolean in_seam = false;
    				if((seam[x][0] == x) && (seam [x][1] == y)) {
    					in_seam = true;
    					shift = true;
    				}
    			if (!in_seam) {
    				if(shift) {
    					newImage.setRGB(x, y-1,img.getRGB(x, y));
    				} else {
    					newImage.setRGB(x, y, img.getRGB(x, y));
    				}
    			}
    			}
    		}
    	} 
    
    	return newImage;
    }
   
    //  seamAdd is the function the receives the image and the "path" (coordintaes) of the seam
    // it has to double. By creating a new image and coping there all the pixels of the original
    // image, except for the seam which is copied twice.
    private static BufferedImage seamAdd (BufferedImage img, int[][] seam, String direction) {
    	BufferedImage newImage = null;
    	int width = img.getWidth();
    	int height = img.getHeight();
    	if (direction.equals("vertical")) {
    		newImage = new BufferedImage (width+1, height, BufferedImage.TYPE_INT_ARGB);
			for (int y= 0 ; (y < height -1)  ; y++) {
				int flag = 0; 

				for (int x = 0 ; (x < width)&& (flag == 0); x++) {
    					// check if the pixel is in the seam
    					boolean in_seam = false;
    					if ((seam[y][0] == x) && (seam[y][1] == y)) {
    						in_seam = true;
    					}
    				
    					if (!in_seam) {
    						newImage.setRGB(x, y, img.getRGB(x, y));    					
    					}else {
    						newImage.setRGB(x, y, img.getRGB(x, y));    					
    						for (int k = x+1; k< width -1; k++) {
    							newImage.setRGB(k, y, img.getRGB(k, y));
    						}
    				
    				}
    				flag =1;
				}
    			}
    			
    	}
    	
    	else if (direction.equals("horizontal")) {
    		newImage = new BufferedImage (width, height+1, BufferedImage.TYPE_INT_ARGB);
			for (int x= 0 ; (x < width -1)  ; x++) {
				int flag = 0;
				for (int y = 0 ; (y < height)&& (flag == 0); y++) {
				 
    				// check if the pixel is in the seam
    				boolean in_seam = false;
    				if ((seam[x][0] == x) && (seam[x][1] == y)) {
    					in_seam = true;
    				}
    				
    				if (!in_seam) {
    						newImage.setRGB(x, y, img.getRGB(x, y));    					
    				}else {
						newImage.setRGB(x, y, img.getRGB(x, y));    					
    					for (int k = y+1; k< height; k++) {
    						newImage.setRGB(x, k, img.getRGB(x, k));
    					}
    				}
    				flag =1;
				}
			}
    	} 
    
    	return newImage;
    }
}

