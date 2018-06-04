package projection;

import main.Driver;
import utility.Point2D;
import utility.Point3D;
import utility.Ray;

public class Perspective extends Projection {
	public Perspective(Point3D eye, Point3D lookat, double FOV) {
		this.eye = new Point3D(eye);
		this.lookat = new Point3D(lookat);
		this.distance = Driver.world.viewplane.height/2/Math.tan(Math.toRadians(FOV));
		
		compute_uvw();
		
	}

	@Override
	public Ray createRay(Point2D point) {
		Ray ray = new Ray(new Point3D(eye), u.mult(point.x).add(v.mult(point.y).sub(w.mult(distance))));
		ray.direction.normalize();
		return ray;
	}

}
