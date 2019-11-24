package rectangle_code;

import java.util.List;


class OnEdgeCounts
{
	private final int enclosedCount;
	private final int onEdgeCount;
	private final List<CoordinateWithPoints> onEdgeCoordinates;

	OnEdgeCounts(int enclosedCount, int onEdgeCount, final List<CoordinateWithPoints> onEdgeCoordinates)
	{
		this.enclosedCount = enclosedCount;
		this.onEdgeCount = onEdgeCount;
		this.onEdgeCoordinates = onEdgeCoordinates;
	}
	
	int getEnclosedCount()
	{
		return enclosedCount;
	}
	
	int getOnEdgeCount()
	{
		return onEdgeCount;
	}
	
	List<CoordinateWithPoints> getOnEdgeCoordinates()
	{
		return onEdgeCoordinates;
	}
}
