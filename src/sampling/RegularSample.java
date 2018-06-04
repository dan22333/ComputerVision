package sampling;

import main.Driver;
import utility.Point2D;

public class RegularSample extends Sampler {
	public RegularSample (int samples) {
		this.samples = samples;
	}

	@Override
	public Point2D sample(int row, int col, int x, int y) {
		Point2D point = new Point2D();
		point.x = x-Driver.world.viewplane.width/2+(col+0.5)/samples;
		point.y = y-Driver.world.viewplane.height/2+(row+0.5)/samples;

		return point;
	}
}
