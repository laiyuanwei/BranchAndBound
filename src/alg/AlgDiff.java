package alg;

import java.util.HashSet;

public class AlgDiff {
	private HashSet<AlgElement> subset;
	private float diffValue = 0;
	/**
	 * @return the subset
	 */
	public HashSet<AlgElement> getSubset() {
		return subset;
	}
	/**
	 * @param subset the subset to set
	 */
	public void setSubset(HashSet<AlgElement> subset) {
		this.subset = subset;
	}
	/**
	 * @return the diffValue
	 */
	public float getDiffValue() {
		return diffValue;
	}
	/**
	 * @param diffValue the diffValue to set
	 */
	public void setDiffValue(float diffValue) {
		this.diffValue = diffValue;
	}
	
}
