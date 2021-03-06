package fr.dauphine.javaavance.phineloops.model;

import java.util.Set;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class Cluster {
	//private Set<Shape> shapeSet = new HashSet<>();
	//FASTUTILS
	private Set<Shape> shapeSet = new ObjectOpenHashSet<>(20);
	//KOLOBOKE
	//private HashObjSet<Shape> shapeSet =  HashObjSets.getDefaultFactory().withHashConfig(HashConfig.getDefault())newMutableSet();
	//GUAVA
	//private HashMultiset<Shape> shapeSet =HashMultiset.create();
	//TROVE
	//private Set<Shape> shapeSet = new THashSet<Shape>();
	//HPPC
	//private ObjectHashSet<Shape> shapeSet =  new ObjectHashSet<Shape>();
	private boolean computed = false;
	private int minI;
	private int minJ;
	private int maxI;
	private int maxJ;

	
	/**
	 * Add a shape to cluster
	 * @param shape
	 */
	public void add(Shape shape) {
		shapeSet.add(shape);
		computed = false;
	}

	/**
	 * Check if cluster accept the shape
	 * @param newShape :shape to check
	 * @return true if shape can be accept to the cluster
	 */
	public boolean accept(Shape newShape) {
		
		for(Shape s : shapeSet) {
			if(newShape.isConnectedTo(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if cluster contains a specific shapes
	 * @param shape : the shape
	 * @return true if cluster contains the shape
	 */
	public boolean contains(Shape shape) {
		return shapeSet.contains(shape);
	}


	public Set<Shape> getShapeSet(){
		return shapeSet;
	}

	/**
	 * Merge two clusters
	 * @param c : cluster to merge
	 */
	public void merge(Cluster c) {
		shapeSet.addAll(c.getShapeSet());
		//updateLimits(c);
	}

	
	/**
	 * Compute the limit of the cluster (maxI, maxJ, minI, minJ)
	 */
	private void computeLimit() {
		minI = Integer.MAX_VALUE;
		minJ = Integer.MAX_VALUE;
		maxI = 0;
		maxJ = 0;

		for(Shape s : shapeSet) {
			if(s.getI()<minI) {
				minI=s.getI();
			}
			if(s.getJ()<minJ) {
				minJ=s.getJ();
			}
			if(s.getI()>maxI) {
				maxI=s.getI();
			}
			if(s.getJ()>maxJ) {
				maxJ=s.getJ();
			}
		}
		computed = true;
	}
	
	
	
	
	
	public int getMinI() {
		if(!computed){
			computeLimit();
		}
		return minI;
	}

	public int getMinJ() {
		if(!computed){
			computeLimit();
		}
		return minJ;
	}

	public int getMaxI() {
		if(!computed){
			computeLimit();
		}
		return maxI;
	}

	public int getMaxJ() {
		if(!computed){
			computeLimit();
		}
		return maxJ;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("min I :"+getMinI()+"   max i :"+getMaxI()+" ");
		sb.append("min J :"+getMinJ()+"   max J :"+getMaxJ()+" ");
		sb.append(shapeSet.size());
		return sb.toString();
	}
}
