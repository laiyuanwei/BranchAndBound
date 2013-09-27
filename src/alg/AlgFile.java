package alg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class AlgFile {
	private final String FILE_NAME = "ins1.dat";
	private final String FILE_PATH = "input" + File.separator;
	private int bucketNum = 0;
	private int elementNum = 0;
	private float allElementHeight = 0;
	private HashSet<AlgElement> elementSet = new HashSet<AlgElement>();
	
	public void init() {
		try {
			File file = new File(FILE_PATH + FILE_NAME);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int index = 0;
			while ( (line = br.readLine()) != null){
				index++;
				// bucket number
				if(index==1){
					bucketNum = Integer.parseInt(line);
					continue;
				}
				// element number
				if(index==2){
					elementNum = Integer.parseInt(line);
					continue;
				}
				//element height
				AlgElement element = new AlgElement();
				String elementId = "e" + (index-2);
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				float height = Float.parseFloat(st.nextToken());
				allElementHeight += height;
				element.setID(elementId);
				element.setHeight(height);
				elementSet.add(element);
			}//while( (line = br.readLine()) != null)
		br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return the bucketNum
	 */
	public int getBucketNum() {
		return bucketNum;
	}

	/**
	 * @return the elementNum
	 */
	public int getElementNum() {
		return elementNum;
	}	
	
	/**
	 * @return the allElementHeight
	 */
	public float getAllElementHeight() {
		return allElementHeight;
	}

	public HashSet<AlgElement> getElementSet() {
		return elementSet;
		
	}
	
	public AlgElement getElementById(String ID){
		Iterator<AlgElement> iter = elementSet.iterator();
		while(iter.hasNext()){
			AlgElement element = iter.next();
			if(element.getID().equals(ID)){
				return element;
			}
		}
		return null;
	}
}
