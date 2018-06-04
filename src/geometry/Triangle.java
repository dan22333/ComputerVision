package geometry;

import utility.Color;
//import utility.Normal;
import utility.Point3D;
import utility.Ray;
//import utility.Vector3D;

public class Triangle extends GeometricObject {
	private	Point3D	v0, v1, v2;
	//private Normal	normal;
	
	public Triangle (Point3D a, Point3D b, Point3D c){
		v0 = a;
		v1 = b;
		v2 = c;
		//normal = (new Normal((v1.sub(v0)).cross(v2.sub(v0)).hat()));  
		this.color = new Color(color);
	}
	public double hit(Ray ray) {
		double a = v0.x - v1.x, b = v0.x - v2.x, c = ray.direction.x, d = v0.x - ray.origin.x; 
		double e = v0.y - v1.y, f = v0.y - v2.y, g = ray.direction.y, h = v0.y - ray.origin.y;
		double i = v0.z - v1.z, j = v0.z - v2.z, k = ray.direction.z, l = v0.z - ray.origin.z;

		double m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		double q = g * i - e * k, s = e * j - f * i;

		double inv_denom  = 1.0 / (a * m + b * q + c * s);

		double e1 = d * m - b * n - c * p;
		double beta = e1 * inv_denom;

		if (beta < 0.0) {
			return 0.0;  // no intersection
		}

		double r = e * l - h * i;
		double e2 = a * n + d * q + c * r;
		double gamma = e2 * inv_denom;

		if (gamma < 0.0 ) {
			return 0.0;  // no intersection
		}
			
		if (beta + gamma > 1.0) {
			return 0.0;  // no intersection
		}
		
		double e3 = a * p - b * r + d * s;
		double t = e3 * inv_denom;

		if(t > 10E-9) {
			return t;  // intersection
		}
		return 0.0;
	}

}
