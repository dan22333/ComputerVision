package projection;

import utility.Point2D;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

public abstract class Projection {
	
	public Point3D eye; //camera
	public Point3D lookat; //the point that is referensed, will change depandes where we look
	public double distance; // the distance between the camera and the object (viewplane).
	public Vector3D u,v,w;
	public Vector3D up;
	
	public void compute_uvw() { //orthinormal basis
		w = eye.sub_vec(lookat);
		w.normalize();
		
		//Vector3D up = new Vector3D (0.00424,1.0,0.00764);
		
		u = up.cross(w);
		u.normalize();
		
		v = w.cross(u);
		v.normalize();
	}
	public abstract Ray createRay(Point2D point);
	
}
