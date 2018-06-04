package main;

import java.io.IOException;

import projection.Perspective;
import projection.Projection;
import sampling.RegularSample;
import sampling.Sampler;
import scene.World;
import utility.Color;
import utility.Image;
import utility.Point3D;

public class Driver {

	public static World world;
	public static Image image;
	public static Tracer tracer; 
	public static Sampler sampler;
	public static Projection projection;
	
	public static void main(String[] args) throws IOException, RayTracerException {
		
		long start = System.nanoTime();
		int width = 500, height = 500;
		if (args.length < 2)
			throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

		String sceneFileName = args[0];
		String outputFileName = args[1];

		if (args.length > 3)
		{
			width = Integer.parseInt(args[2]);
			height = Integer.parseInt(args[3]);
		}

		// Parse scene file:
		tracer.parseScene(sceneFileName);

		// Render scene:
		tracer.renderScene(outputFileName);
	
		/*
		world = new World(width, height, 1.0 ,new Color(0.0F,0.0F,0.0F));
		image = new Image("Image.png");
		tracer = new Tracer();
		sampler = new RegularSample(4);
		projection = new Perspective( new Point3D(-200.0,200.0,600), new Point3D (0.0,0.0,0.0),30.0);
		
		//BufferedImage buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//Random random = new Random();
		//Sphere sphere = new Sphere(new Point3D(0.0,0.0,0.0), 70.0, new Color(1.0F, 0.0F, 0.0F));
		
		for (int y=0; y < world.viewplane.height ;y++) {
			
			for(int x=0; x < world.viewplane.width; x++) {
				tracer.trace(x,y);
			}
		}
		image.write("PNG");
		long end = System.nanoTime();
		System.out.println("Loop Time:" + (end-start)/100000000.0F);
	*/
	}
	

}
