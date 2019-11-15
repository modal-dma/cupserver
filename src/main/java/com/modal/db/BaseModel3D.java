package com.modal.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseModel3D {

	public ArrayList<Point3D> points;
	
	public BaseModel3D()
	{
		points = new ArrayList<Point3D>();
	}
	
	public static class Point3D
	{
		public String xLabel;
		public String yLabel;
		public BigDecimal val;
		public BigDecimal data1;
		public BigDecimal data2;
		
		public String toString()
		{
			return xLabel + ", " + yLabel + ", " + val;
		}
	}
}
