package scene;

import java.util.ArrayList;

import geometry.GeometricObject;
import geometry.Plane;
import geometry.Sphere;
import utility.Color;
import utility.Normal;
import utility.Point3D;

public class World {
	
	public ViewPlane viewplane;
	public ArrayList<GeometricObject> objects;
	public Color background;
	public int num_shd_ray; //Number of shadow rays (small integer, 1-10)
	public int max_rec_level;//Maximum recursion level (integer, usually around 10)
	public Camera camera;
	
	public World(int width, int height, double size, Color bg,Camera camera) {
		
		viewplane = new ViewPlane(width, height, size);
		background = new Color(bg);
		camera = new Camera(camera);
		
		/*objects = new ArrayList<GeometricObject>();
		objects.add(new Sphere(new Point3D(0.0, 0.0, 0.0), 50, new Color(1.0F, 0.0F, 0.0F)));
		objects.add(new Sphere(new Point3D(-150.0, 0.0, 0.0), 50, new Color(0.0F, 1.0F, 0.0F)));
		objects.add(new Sphere(new Point3D(150.0, 0.0, 0.0), 50, new Color(0.0F, 0.0F, 1.0F)));
		objects.add(new Plane(new Point3D(0.0,0.0,0.0), new Normal(0.0,1.0,0.2), new Color(1.0F,1.0F,0.0F)));
		*/
	}
}
