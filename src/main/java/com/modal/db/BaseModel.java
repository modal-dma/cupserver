package com.modal.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BaseModel {

	public ArrayList<String> labels;
	public ArrayList<ArrayList<BigDecimal>> dataset;
	
	public BaseModel()
	{
		labels = new ArrayList<String>();
		dataset = new ArrayList<ArrayList<BigDecimal>>();
	}
}
