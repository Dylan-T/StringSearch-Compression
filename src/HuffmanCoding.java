import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		// TODO fill this in.
	}
	
	/**
	 * 
	 * @param text that it is building a tree of
	 * @return the root node of the tree
	 */
	public HuffNode buildTree(String text) {
		
		char[] input = text.toCharArray();

        // tabulate frequency counts
        int[] freq = new int[256];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }
		
		//Make the priority queue
        PriorityQueue<HuffNode> queue = new PriorityQueue<HuffNode>(256, new FrequencyComparator());
        
        //fill with singletons
        for(int i = 0; i < freq.length; i++) {
        	if(freq[i] > 0) {
        		queue.add(new HuffNode((char)i, freq[i], null, null));
        	}
        }
		
        while(queue.size() > 1) {
        	HuffNode tLeft = queue.poll();
        	HuffNode tRight = queue.poll();
        	
        	//Create parent for these nodes
        	HuffNode parent = new HuffNode(null, tLeft.frequency+tRight.frequency, tLeft, tRight);
        	queue.add(parent);
        }
		return queue.peek();
		
	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		// TODO fill this in.
		return "";
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		// TODO fill this in.
		return "";
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't wan to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {
		return "";
	}
	
	private class HuffNode implements Comparable<HuffNode>{
		Character value;
		HuffNode left;
		HuffNode right;
		int frequency;
		
		public HuffNode(Character val, int freq, HuffNode left, HuffNode right) {
			value = val;
			frequency = freq;
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(HuffNode other) {
			return frequency - other.frequency;
		}
		
	}
	
	private class FrequencyComparator implements Comparator<HuffNode>{

		@Override
		public int compare(HuffNode h1, HuffNode h2) {
			return h1.frequency - h2.frequency;
		}
		
	}
}
