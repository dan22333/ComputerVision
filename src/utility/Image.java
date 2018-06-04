package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Driver;

public class Image {
	public BufferedImage buffer;
	public File image;
	
	public Image(String filename) {
		image = new File (filename);
		buffer = new BufferedImage(Driver.world.viewplane.width, Driver.world.viewplane.height, BufferedImage.TYPE_INT_RGB);		
	}
	
	public void write(String filetype) {
		try {
			ImageIO.write(buffer, filetype, image);
		} catch (IOException e) {
			System.out.println("can't write image");
			System.exit(1);
		}
	}
	

}
