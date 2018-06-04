package main;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import scene.Camera;
import utility.Color;
import utility.Point2D;
import utility.Point3D;
import utility.Ray;

public class Tracer {

	public void trace(int x, int y) {

		Color color = new Color(0.0F, 0.0F, 0.0F);
		boolean hit = false;
		

		for (int row = 0; row < Driver.sampler.samples; row++) {

			for (int col = 0; col < Driver.sampler.samples; col++) {

				Point2D point = Driver.sampler.sample(row, col, x, y);
				Ray ray = Driver.projection.createRay(point);

				double min = Double.MAX_VALUE;
				Color tempColor = Driver.world.background;
				
				for( int i = 0; i< Driver.world.objects.size();i++) {
					double temp = Driver.world.objects.get(i).hit(ray);
					
					if (temp != 0.0 && temp<min){
						min = temp;
						hit = true;
						tempColor = Driver.world.objects.get(i).color;
					}
				}
				color.add(tempColor); // we will add the closest object
			}
		}

		color.divide(Driver.sampler.samples*Driver.sampler.samples);
		if (hit) 
			Driver.image.buffer.setRGB(x, Driver.world.viewplane.height - y - 1, color.toInteger());
		else
			Driver.image.buffer.setRGB(x, Driver.world.viewplane.height - y - 1, Driver.world.background.toInteger());
		}
	
	public void parseScene(String sceneFileName) throws IOException, RayTracerException
	{
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		System.out.println("Started parsing scene file " + sceneFileName);



		while ((line = r.readLine()) != null)
		{
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#'))
			{  // This line in the scene file is a comment
				continue;
			}
			else
			{
				String code = line.substring(0, 3).toLowerCase();
				// Split according to white space characters:
				String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

				if (code.equals("cam"))
				{
					double x = Double.parseDouble(params[0]);
					double y = Double.parseDouble(params[1]);
					double z = Double.parseDouble(params[2]);
					
					Point3D position = new Point3D(x,y,z);
					
					x = Double.parseDouble(params[3]);
					y = Double.parseDouble(params[4]);
					z = Double.parseDouble(params[5]);
					
					Point3D lookat = new Point3D(x,y,z);

					x = Double.parseDouble(params[6]);
					y = Double.parseDouble(params[7]);
					z = Double.parseDouble(params[8]);
					
					Point3D up = new Point3D(x,y,z);
					float screen_distance = Float.parseFloat(params[9]);
					float screen_width = Float.parseFloat(params[10]);

					Camera camera = new Camera(position, lookat, up, screen_distance, screen_width);	
					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				}
				else if (code.equals("set"))
				{
                                        // Add code here to parse general settings parameters

					System.out.println(String.format("Parsed general settings (line %d)", lineNum));
				}
				else if (code.equals("mtl"))
				{
                                        // Add code here to parse material parameters

					System.out.println(String.format("Parsed material (line %d)", lineNum));
				}
				else if (code.equals("sph"))
				{
                                        // Add code here to parse sphere parameters

                                        // Example (you can implement this in many different ways!):
					// Sphere sphere = new Sphere();
                                        // sphere.setCenter(params[0], params[1], params[2]);
                                        // sphere.setRadius(params[3]);
                                        // sphere.setMaterial(params[4]);

					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				}
				else if (code.equals("pln"))
				{
                                        // Add code here to parse plane parameters

					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				}
				else if (code.equals("lgt"))
				{
                                        // Add code here to parse light parameters

					System.out.println(String.format("Parsed light (line %d)", lineNum));
				}
				else
				{
					System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
				}
			}
		}

                // It is recommended that you check here that the scene is valid,
                // for example camera settings and all necessary materials were defined.

		System.out.println("Finished parsing scene file " + sceneFileName);

	}
	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName)
	{
		long startTime = System.currentTimeMillis();

		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[Driver.width * Driver.imageHeight * 3];


                // Put your ray tracing code here!
                //
                // Write pixel color values in RGB format to rgbData:
                // Pixel [x, y] red component is in rgbData[(y * this.imageWidth + x) * 3]
                //            green component is in rgbData[(y * this.imageWidth + x) * 3 + 1]
                //             blue component is in rgbData[(y * this.imageWidth + x) * 3 + 2]
                //
                // Each of the red, green and blue components should be a byte, i.e. 0-255


		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

                // The time is measured for your own conveniece, rendering speed will not affect your score
                // unless it is exceptionally slow (more than a couple of minutes)
		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

                // This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}




	//////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName)
	{
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
	    int height = buffer.length / width / 3;
	    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
	    ColorModel cm = new ComponentColorModel(cs, false, false,
	            Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    SampleModel sm = cm.createCompatibleSampleModel(width, height);
	    DataBufferByte db = new DataBufferByte(buffer, width * height);
	    WritableRaster raster = Raster.createWritableRaster(sm, db, null);
	    BufferedImage result = new BufferedImage(cm, raster, false, null);

	    return result;
	}

}
