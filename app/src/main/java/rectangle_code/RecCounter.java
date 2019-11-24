package rectangle_code;

import exceptions.InvalidQuotaException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


public class RecCounter
{
	public RecCounter()
	{
	}
	
	public static void sortByX(List<CoordinateWithPoints> storage)  // returns ArrayList sorted based on y coordinates
	{
		Collections.sort(storage, new CoordinateComparator(CompareMode.X));
	}
	
	public static void sortByY(List<CoordinateWithPoints> storage)  // returns ArrayList sorted based on y coordinates
	{
		Collections.sort(storage, new CoordinateComparator(CompareMode.Y));
	}
	
	public static OnEdgeCounts numEnclosed(List<CoordinateWithPoints> storage, CoordinateWithPoints one, CoordinateWithPoints two)  // returns an array containing the number of points enclosed in and the number of points on any edge of the rectangle defined by the points passed as parameters respectively
	{
		int enclosedCount = 0;
		int onEdgeCount = 0;
		double x1;
		double x2;
		double y1;
		double y2;
			
		if(one.getX() <= two.getX())
		{
			x1 = one.getX();
			x2 = two.getX();
		}
		else
		{
			x1 = two.getX();
			x2 = one.getX();
		}
		
		if(one.getY() <= two.getY())
		{
			y1 = one.getY();
			y2 = two.getY();
		}
		else
		{
			y1 = two.getY();
			y2 = one.getY();
		}
		
		List<CoordinateWithPoints> onEdge = new ArrayList<CoordinateWithPoints>();
		
		for(CoordinateWithPoints point: storage)
		{
			if(point.getX() >= x1 && point.getX() <= x2 && point.getY() >= y1 && point.getY() <= y2)
			{
				enclosedCount = enclosedCount + 1;
				
				if(((point.getX()).equals(x1) && point.getY() <= y2 && point.getY() >= y1) || ((point.getX()).equals(x2) && point.getY() <= y2 && point.getY() >= y1) || ((point.getY()).equals(y1) && point.getX() <= x2 && point.getX() >= x1) || ((point.getY()).equals(y2) && point.getX() <= x2 && point.getX() >= x1))
				{
					onEdgeCount = onEdgeCount + 1;
					
					onEdge.add(point);
				}
			}
		}
		
		return new OnEdgeCounts(enclosedCount, onEdgeCount, onEdge);
	}
	
	private static boolean reducible(  // returns true if the rectangle is reducible, and returns false if the rectangle is not reducible or if the rectangle did not reach the quota to begin with
									 List<CoordinateWithPoints> storage,
									 int quota,  // quota
									 List<CoordinateWithPoints> xs,  // ArrayList of points sorted by the x coordinates in ascending order
									 List<CoordinateWithPoints> ys,  // ArrayList of points sorted by the y coordinates in ascending order
									 int startindex1,  // index of the point in xs whose x coordinate defines the left (first) side of the rectangle
									 int startindex2,  // index of the point in ys whose y coordinate defines the bottom (third) side of the rectangle
									 int index1,  // index of the point in xs whose x coordinate defines the right (second) side of the rectangle
									 int index2)  // index of the point in ys whose y coordinate defines the top (fourth) side of the rectangle
									 // *indices startindex1, startindex2, index1, and index2 must be passed in the correct order for the method to work as expected
	{		
		for(int i = 1; i <= 4; i = i + 1)
		{
			if(i == 1)
			{
				if(startindex1 == index1 || (xs.get(startindex1).getX()).equals(xs.get(index1).getX()))
				{
				} 
				else
				{
					int shift = 1;
				
					while(startindex1 < xs.size() - 1 && (xs.get(startindex1).getX()).equals(xs.get(startindex1 + shift).getX()))
					{
						shift = shift + 1;
					}
						
					if(numEnclosed(storage, new CoordinateWithPoints(xs.get(startindex1 + shift).getX(), ys.get(startindex2).getY()), new CoordinateWithPoints(xs.get(index1).getX(), ys.get(index2).getY())).getEnclosedCount() >= quota)  // quota still reached after first side is moved in
					{
						return true;
					}
				}
			}
			else if(i == 2)
			{
				if(index1 == startindex1 || (xs.get(index1).getX()).equals(xs.get(startindex1).getX()))
				{
				}
				else
				{
					int shift = 1;
					
					while(index1 > 1 && (xs.get(index1).getX()).equals(xs.get(index1 - shift).getX()))
					{
						shift = shift + 1;
					}
				
					if(numEnclosed(storage, new CoordinateWithPoints(xs.get(startindex1).getX(), ys.get(startindex2).getY()), new CoordinateWithPoints(xs.get(index1 - shift).getX(), ys.get(index2).getY())).getEnclosedCount() >= quota)  // quota still reached after second side is moved in
					{
						return true;
					}
				}
			}
			else if(i == 3)
			{
				if(startindex2 == index2 || (ys.get(startindex2).getY()).equals(ys.get(index2).getY()))
				{
				} 
				else
				{
					int shift = 1;
				
					while(startindex2 < ys.size() - 1 && (ys.get(startindex2).getY()).equals((ys.get(startindex2 + shift)).getY()))
					{
						shift = shift + 1;
					}
			
					if(numEnclosed(storage, new CoordinateWithPoints(xs.get(startindex1).getX(), ys.get(startindex2 + shift).getY()), new CoordinateWithPoints(xs.get(index1).getX(), ys.get(index2).getY())).getEnclosedCount() >= quota)  // quota still reached after third side is moved in
					{
						return true;
					}
				}
			}
			else /* if(i == 4) */
			{
				if(index2 == startindex2 || (ys.get(index2).getY()).equals(ys.get(startindex2).getY()))
				{
				} 
				else
				{
					int shift = 1;
				
					while(index2 > 1 && (ys.get(index2).getY()).equals(ys.get(index2 - shift).getY()))
					{
						shift = shift + 1;
					}
					
					if(numEnclosed(storage, new CoordinateWithPoints(xs.get(startindex1).getX(), ys.get(startindex2).getY()), new CoordinateWithPoints(xs.get(index1).getX(), ys.get(index2 - shift).getY())).getEnclosedCount() >= quota)  // quota still reached after fourth side is moved in
					{
						return true;
					}
				}
			}
		}
			
		return false;
	}
	
	public static int count(List<CoordinateWithPoints> storage, boolean isStorageSortedBasedOnX, int quota) throws InvalidQuotaException  // rectangle finding algorithm
	{
		int recCount = 0;  // number of rectangles that reach the quota
		int points = 0;  // number of points on the edges of the rectangles that reach the quota
		
		if(quota <= 0)
		{
			throw new InvalidQuotaException("The quota received is invalid (zero or negative).");
		}
		else
		{
			List<CoordinateWithPoints> sortedByX;
			
			if(!isStorageSortedBasedOnX)
			{
				sortedByX = new ArrayList<CoordinateWithPoints>(storage);
				
				sortByX(sortedByX);  // sort points based on x coordinates
			}
			
			sortedByX = storage;
			
			final List<CoordinateWithPoints> sortedByY = new ArrayList<CoordinateWithPoints>(storage);
					
			sortByY(sortedByY);  // sort points based on y coordinates
			
			Map<String, Boolean> startPoints = new LinkedHashMap<String, Boolean>();  // keeps track of starting points so that duplicates can be filtered out
			Map<String, Boolean> rectangles = new LinkedHashMap<String, Boolean>();  // keeps track of rectangles so that duplicates can be filtered out
			
			for(int i = 0; i < sortedByX.size(); i = i + 1)  // traverses through x coordinates of starting points
			{		
				for(int j = 0; j < sortedByY.size(); j = j + 1)  // traverses through y coordinates of starting points
				{
					// due to the two sorted, duplicate ArrayLists, each combination of points will be accessed twice (this needs to be accounted for): starting points are kept track of
				
					if((sortedByX.get(i).getX() <= sortedByY.get(j).getX() && sortedByY.get(j).getY() <= sortedByX.get(i).getY()) &&  // checks validity of starting point (in bottom left corner of the rectangle defined by the two points)
					   startPoints.containsKey(generateStringKeyForCoordinate(sortedByX.get(i).getX(), sortedByY.get(j).getY())) == false)  // checks if starting point was found before
					{
						CoordinateWithPoints start = new CoordinateWithPoints(sortedByX.get(i).getX(), sortedByY.get(j).getY());  // sets starting point as x coordinate of xs and y coordinate of ys
						
						// System.out.println("starting point found: " + start);
						
						startPoints.put(generateStringKeyForCoordinate(start), true);  // adds starting point to ArrayList keeping track of starting points
				
						for(int k = 0; k < sortedByY.size(); k = k + 1)  // increments y coordinate each time to find all rectangles from previously calculated starting point
						{
							for(int m = 0; m < sortedByX.size(); m = m + 1)  // increments x coordinate each time to find all rectangles from previously calculated starting point
							{
								if(  // checks if enclosure (within starting point and (mth x-point's x coordinate, kth y-point's y coordinate)) is worth checking
								   sortedByX.get(m).getY() >= start.getY() && sortedByX.get(m).getY() <= sortedByY.get(k).getY() &&  // y coordinate of mth x-point is between the starting point's and the kth y-point's y coordinate
							 	   sortedByX.get(m).getX() >= start.getX() &&
								   rectangles.containsKey(generateStringKeyForRectangle(start, new CoordinateWithPoints(sortedByX.get(m).getX(), sortedByY.get(k).getY()))) == false)  // checks if rectangle was found before
								{								
									OnEdgeCounts numPoints = numEnclosed(storage, start, new CoordinateWithPoints(sortedByX.get(m).getX(), sortedByY.get(k).getY()));  // number of points within the rectangle
									
									if(numPoints.getEnclosedCount() < quota)
									{
										// continue
									}
									else if(reducible(storage, quota, sortedByX, sortedByY, i, j, m, k) == true)
									{
										// continue
									}
									else  // if rectangle reaches quota and is non-reducible
									{
										rectangles.put(generateStringKeyForRectangle(start, new CoordinateWithPoints(sortedByX.get(m).getX(), sortedByY.get(k).getY())), true);
										
										recCount = recCount + 1;  // increment recCount
										points = points + numPoints.getOnEdgeCount();
										
										for(CoordinateWithPoints point: numPoints.getOnEdgeCoordinates())
										{
											point.incrementPoints(1);
										}
									
										// System.out.println("rectangle found: " + start + ", " + new Coordinate(xs.get(m).getX(), ys.get(k).getY()) + "\n\tquota: " + quota + "\n\tpoints enclosed: " + numPoints.getEnclosedCount() + "\n\tnumber of rectangles found so far (including this one): " + recCount + "\n\tpoints on edge: " + numPoints.getOnEdgeCount() + "\n\ttotal points: " + points);
									
										break;  // once an acceptable rectangle is found, the rest of x does not have to be checked because the rest of the rectangles will have more than quota number of points and will be reducible
									}
								}
							}
						}
					}
				}
			}
		}
		
		return points;
	}
	
	private static String generateStringKeyForCoordinate(CoordinateWithPoints coordinate)
	{
		return "(" + coordinate.getX() + ", " + coordinate.getY() + ")";
	}
	
	private static String generateStringKeyForCoordinate(double x, double y)
	{
		return "(" + x + ", " + y + ")";
	}
	
	private static String generateStringKeyForRectangle(CoordinateWithPoints coordinateOne, CoordinateWithPoints coordinateTwo)
	{
		return generateStringKeyForCoordinate(coordinateOne) + ", " + generateStringKeyForCoordinate(coordinateTwo);
	}
}
