package geometry;

import utility.Color;
import utility.Normal;
import utility.Ray;

// Point normal form to define a plane
public class Plane extends GeometricObject {
	Normal normal;
	double offset;
	
	public Plane(double offset, Normal normal, Color color) {
		this.offset = offset;
		this.normal = new Normal(normal);
		this.color = new Color(color);
	}

// taking a ray and determine where the ray intersect
	public double hit(Ray ray) {
		//ray : a+t*b, where a is the origin of the ray going in direction b.
		//plane: n*x=d, the set of points x which have the same offset d, measured in direction n
		//n*(a+t*b) = d
		//n*a+t*n*b = d
		// t*n*b = d-n*a
		//t= (d-n*a)/(n*b)
		double t = (offset - ray.origin.dot(normal))/(ray.direction.dot(normal));
		if(t > 10E-9) {
			return t; // return t if the t value is greater than a smaller value we defined. if it's not zero they intersact!
		}else {
			return 0.0;	// didn't intersact any object
		}
		
	}
}
