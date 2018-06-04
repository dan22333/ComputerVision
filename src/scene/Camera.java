package scene;

import utility.Point3D;

public class Camera {
	public Point3D position;
	public Point3D lookat;
	public Point3D up;
	public float screen_distance;
	public float screen_width;
	
	public  Camera(Point3D position, Point3D lookat, Point3D up,float screen_distance,float screen_width) {
		this.position = position;
		this.lookat = lookat;
		this.up = up;
		this.screen_distance = screen_distance;
		this.screen_width = screen_width;
	}
	public  Camera(Camera camera) {
		this.position = camera.position;
		this.lookat = camera.lookat;
		this.up = camera.up;
		this.screen_distance = camera.screen_distance;
		this.screen_width = camera.screen_width;
	}

}
