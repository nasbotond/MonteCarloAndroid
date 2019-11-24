package rectangle_code;


public class CoordinateWithPoints implements Comparable<CoordinateWithPoints>
{
	private final double x;
	private final double y;
	private int points;  // number of times position was pivotal (TODO: number may become too large to store?)
	private double value;  // Banzhaf index of position
	
	public CoordinateWithPoints(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.points = 0;
		this.value = 0;
	}
	
	public CoordinateWithPoints(double x, double y, int points)
	{
		this.x = x;
		this.y = y;
		this.points = points;
		this.value = 0;
	}
	
	public Double getX()
	{
		return x;
	}
	
	public Double getY()
	{
		return y;
	}
	
	public Integer getPoints()
	{
		return points;
	}
	
	public void incrementPoints(int increment)
	{
		points = points + increment;
	}
	
	public Double getValue()
	{
		return value;
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	@Override
	public int compareTo(CoordinateWithPoints coordinate)
	{
		if(x > coordinate.x)
		{
			return 1;
		}
		else if(x < coordinate.x)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + "): " + points + " -> " + value;
	}
}
