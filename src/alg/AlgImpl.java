package alg;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class AlgImpl {
	
	private AlgFile algFile = new AlgFile();
	private HashSet<HashSet<AlgElement>> allSubset = new HashSet<HashSet<AlgElement>>();
	private float avg = 0;
	private TreeSet<AlgDiff> diffSet;
	private TreeSet<AlgResult> resultSet;
	
	public void initData(){
		//init file
		algFile.init();
		//caculate subset
		HashSet<AlgElement> es = algFile.getElementSet();
		Iterator<AlgElement> iter = es.iterator();
		TreeSet<String> set = new TreeSet<String> ();
		while(iter.hasNext()){
			set.add(iter.next().getID());
		}
		ArrayList<TreeSet<String>> subset = AlgSubSet.getSubset(set);
//		System.out.println("一共有" + subset.size() + "个子集。");
//		for(TreeSet<String> ts : subset)
//		{
//			System.out.println(ts.toString());
//		}
		parseSubset(subset);
		float average = algFile.getAllElementHeight()/algFile.getBucketNum();
		avg = Math.round(average*100)/100f;
		System.out.println("average value:" + avg);
		initDiffSet();
	}
	
	public void caculateResult(){
		SetCompare sc = new SetCompare();
		resultSet = new TreeSet<AlgResult>(sc);
		for(int i=0; i<algFile.getBucketNum()-1; i++){
			HashSet<AlgElement> subset = findMinDiffSet();
			AlgResult result = new AlgResult();
			//print out the content of bucket
			System.out.println("bucket" + (i+1) + ":");
			String str = "[";
			Iterator<AlgElement> iter = subset.iterator();
			float height = getSubsetHeiget(subset);
			result.setHeight(height);
			while(iter.hasNext()){
				str += "(";
				AlgElement ae = iter.next();
				result.getSubset().add(ae);
				str += "ID:" + ae.getID();
				str += " h:" + String.format("%1.2f",ae.getHeight());
				str += ")";
			}
			str += "]";
			System.out.println(str);
			resultSet.add(result);
			//remove chosen element from diffset
			iter = subset.iterator();
			while(iter.hasNext()){
				AlgElement ae = iter.next();
				removeChosenElement(ae);
				algFile.getElementSet().remove(ae);
			}
		}
		System.out.println("bucket" + algFile.getBucketNum() + ":");
		String str = "[";
		HashSet<AlgElement> es = algFile.getElementSet();
		Iterator<AlgElement> iter = es.iterator();
		AlgResult result = new AlgResult();
		float height = 0;
		while(iter.hasNext()){
			str += "(";
			AlgElement ae = iter.next();
			result.getSubset().add(ae);
			height += ae.getHeight();
			str += "ID:" + ae.getID();
			str += " h:" + String.format("%1.2f",ae.getHeight());
			str += ")";
		}
		str += "]";
		System.out.println(str);
		result.setHeight(height);
		resultSet.add(result);
		Iterator<AlgResult> it = resultSet.iterator();
		AlgResult ar = it.next();
		System.out.println("Result is " + String.format("%1.2f",ar.getHeight()));
	}
	
	private void parseSubset(ArrayList<TreeSet<String>> subset){
		Iterator<TreeSet<String>> alIter = subset.iterator();
		while(alIter.hasNext()){
			TreeSet<String> ts = alIter.next();
			Iterator<String> iter = ts.iterator();
			HashSet<AlgElement> hs = new HashSet<AlgElement>();
			while(iter.hasNext()){
				String eId = iter.next();
				AlgElement element = algFile.getElementById(eId);
				hs.add(element);
			}
			allSubset.add(hs);
		}
		//print out result
		Iterator<HashSet<AlgElement>> assIter = allSubset.iterator();
		while(assIter.hasNext()){
			HashSet<AlgElement> set = assIter.next();
			Iterator<AlgElement> iter = set.iterator();
			String str = "[";
			while(iter.hasNext()){
				str += "(";
				AlgElement element = iter.next();
				str += "ID:" + element.getID();
				str += " h:" + String.format("%1.2f",element.getHeight());
				str += ")";
			}
			str += "]";
			System.out.println(str);
		}
	}
	
	private float getSubsetHeiget(HashSet<AlgElement> subset){
		float height = 0;
		Iterator<AlgElement> iter = subset.iterator();
		while(iter.hasNext()){
			AlgElement element = iter.next();
			height += element.getHeight();
		}
		return height;
	}
	
	private class SetComparator implements Comparator<AlgDiff>{

		public int compare(AlgDiff d1, AlgDiff d2) {
			if(d1.getDiffValue() == d2.getDiffValue()){
				return 0;
			}
			else if (d1.getDiffValue() > d2.getDiffValue()){
				return 1;
			}
			else{
				return -1;
			}
		}
	}
	
	private class SetCompare implements Comparator<AlgResult>{

		public int compare(AlgResult r1, AlgResult r2) {
			if(r1.getHeight() == r2.getHeight()){
				return 0;
			}
			else if (r1.getHeight() > r2.getHeight()){
				return -1;
			}
			else{
				return 1;
			}
		}
	}
	
	private void initDiffSet(){
		Iterator<HashSet<AlgElement>> assIter = allSubset.iterator();
		SetComparator sc = new SetComparator();
		diffSet = new TreeSet<AlgDiff>(sc);
		while(assIter.hasNext()){
			HashSet<AlgElement> set = assIter.next();
			float diff = Math.abs(avg - getSubsetHeiget(set));
			AlgDiff algDiff = new AlgDiff();
			algDiff.setSubset(set);
			algDiff.setDiffValue(diff);
			diffSet.add(algDiff);
		}
	}
	
	private HashSet<AlgElement> findMinDiffSet(){
		Iterator<AlgDiff> dfsIter = diffSet.iterator();
		while(dfsIter.hasNext()){
			AlgDiff ad = dfsIter.next();
			return ad.getSubset();
		}
		return null;
	}
	
	public boolean PruneChecking(double average, double LB, double UB){
		double Left=Math.abs(LB-average);
		double right=Math.abs(UB-average);
		if(Left>right){
		
			return true;
		}
		else
		    return false;
	}
	
	private void removeChosenElement(AlgElement element){
		Iterator<AlgDiff> dfsIter = diffSet.iterator();
		while(dfsIter.hasNext()){
			AlgDiff ad = dfsIter.next();
			HashSet<AlgElement> subset = ad.getSubset();
			Iterator<AlgElement> iter = subset.iterator();
			while(iter.hasNext()){
				AlgElement ale = iter.next();
				if(ale.getID().equals(element.getID()))
				{
					diffSet.remove(ad);
					dfsIter = diffSet.iterator();
					break;
				}
			}
		}
	}
}
