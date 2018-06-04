package geometry;

import utility.Color;
import utility.Ray;

public abstract class GeometricObject {
	
	public Color color; //Diffuse color
	public Color specular_color;
	public float phong_spec_coef;
	public Color ref_ol;
	public float transparency;
	
	public abstract double hit(Ray ray);
}
