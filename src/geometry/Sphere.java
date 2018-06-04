package geometry;

import utility.Color;
import utility.Point3D;
import utility.Ray;

public class Sphere extends GeometricObject {
	
	public Point3D center;
	public double radius;
	
	public Sphere(Point3D center, double radius, Color color) {
		this.center = new Point3D(center);
		this.radius = radius;
		this.color = new Color(color);
	}

	@Override
	public double hit(Ray ray) {
		//(p-c)*(p-c) = r^2
		//(o+td-c)*(o+td-c) - r^2 = 0;
		//(d*d)t^2 + (2 (o-c)t)*d + (o-c)*(o-c)-r^2 = 0;
		//a*t^2 +2bt+c=0
		
		double a = ray.direction.dot(ray.direction);
		double b = 2*ray.origin.sub(center).dot(ray.direction);
		double c = ray.origin.sub(center).dot(ray.origin.sub(center))-radius*radius;
		double discriminant = b*b -4*a*c; //can't be negative
		
		if (discriminant < 0.0) {
			return 0.0; // doesn't hit
		}
		else {
			double t = (-b - Math.sqrt(discriminant)) / (2*a);
			if ( t> 10E-9) {
				return t;

			}else {
				return 0.0; //no intersection
			}
			
		}
	}
}
