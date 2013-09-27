package alg;

import java.util.HashSet;

public class AlgResult {
	private HashSet<AlgElement> subset = new HashSet<AlgElement>();
	private float Height = 0;
	
	public HashSet<AlgElement> getSubset() {
		return subset;
	}
	public void setSubset(HashSet<AlgElement> subset) {
		this.subset = subset;
	}
	public float getHeight() {
		return Height;
	}
	public void setHeight(float height) {
		Height = height;
	}
	
}
