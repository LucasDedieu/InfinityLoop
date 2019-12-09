package fr.dauphine.javaavance.phineloops.model;

import java.util.ArrayList;
import java.util.Arrays;

public class XShape extends Shape {
	private int[] domain = {40};
	
	public XShape(int orientation,int i, int j) {
		super(ShapeType.XShape, orientation,i,j);
		connections = Arrays.asList(Connection.NORTH,Connection.SOUTH,Connection.EAST,Connection.WEST);
	}
	public void rotate() {
	}

	public int[] getDomain() {
		return domain;
	}
	
	public String getSymbol() {
		return "╋";
	}
	
	public int getMaxRotation() {
		return 0;
	}
}
