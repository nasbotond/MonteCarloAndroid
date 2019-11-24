package rectangle_code;

import java.util.Comparator;


class CoordinateComparator implements Comparator<CoordinateWithPoints>
{
	private CompareMode compareMode;
	
	public CoordinateComparator(CompareMode compareMode)
	{
		this.compareMode = compareMode;
	}

	@Override
	public int compare(CoordinateWithPoints one, CoordinateWithPoints two)
	{
		switch(compareMode)
		{
			case X:
				return (one.getX()).compareTo((two.getX()));
			case Y:
				return (one.getY()).compareTo((two.getY()));
			default:
				return (one.getX()).compareTo((two.getX()));
		}
	}
}
