package alg;

import java.util.ArrayList;
import java.util.TreeSet;

public class AlgSubSet {
	
	//Calculating from 0 to the number of subset, represented in binary form and save in the array result
	
	public static String[] getBinaryValue(TreeSet<String> set)
	{
		int size = set.size();
		int m = (int)Math.pow(2,size) - 1;
		String[] result = new String[m+1];
     		for(int i=m;i>-1;i--)
		{
			StringBuffer sb = new StringBuffer(Integer.toBinaryString(i));
			int length = sb.length();
                        if(length < size)
			{
				for(int j=0;j<size-length;j++)
				{
					sb.insert(0, "0");
				}
			}
			result[i] = sb.toString();
		}
		return result;
	}
    
	//generate the subset based on the binary 
	public static ArrayList<TreeSet<String>> getSubset(TreeSet<String> set)
	{
		ArrayList<TreeSet<String>> result = new ArrayList<TreeSet<String>> ();
        
		//put the set item into array 
		
		String[] items = new String[set.size()];
		int i = 0;
		for(String item : set)
		{
			items[i++] = item;
		}
        
		//invoke the binary string generator
		
		String[] binaryValue = getBinaryValue(set);
		//generate the subset based the binary sequence
	
		for(int j=0;j<binaryValue.length;j++)
		{
			String value = binaryValue[j];
			TreeSet<String> subset = new TreeSet<String> ();
			for(int k=0;k<value.length();k++)
			{
				if(value.charAt(k) == '1')	subset.add(items[k]);
			}
			result.add(subset);
		}

		return result;
	}
}
