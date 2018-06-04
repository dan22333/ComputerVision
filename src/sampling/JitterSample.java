package sampling;

import java.util.Random;

import main.Driver;
import utility.Point2D;

public class JitterSample extends Sampler {
	public Random random;
	public JitterSample (int samples) {
		this.samples = samples;
		random = new Random();
	}
	
	@Override
	public utility.Point2D sample(int row, int col, int x, int y) {
		// TODO Auto-generated method stub
		Point2D point = new Point2D();
		point.x = x - Driver.world.viewplane.width/2+(col+random.nextFloat())/samples;
		point.y = y - Driver.world.viewplane.height/2+(row + random.nextFloat())/samples;
		return point;
		
	}

}
