import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	HuffNode huffTree;
	Map<Character, Integer> freq;
	Map<Character, String> coding;
	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		freq = buildFrequencies(text);
		huffTree = buildTree(freq);
		coding = buildCoding(huffTree);
	}


	/**
	 *
	 * @param text to be encoded/decoded
	 * @return map of character frequencies
	 */
	public Map<Character, Integer> buildFrequencies(String text){
		HashMap<Character, Integer> freq = new HashMap<Character, Integer>();
		char[] textArr = text.toCharArray();

        // count char frequencies
		for(char c: textArr) {
			if(!freq.containsKey(c)) {
				freq.put(c, 1);
			}else {
				freq.put(c, freq.get(c) + 1);
			}
		}
		return freq;
	}

	/**
	 *
	 * @param text that it is building a tree of
	 * @return the root node of the tree
	 */
	public HuffNode buildTree(Map<Character, Integer> freq) {

		//Make the priority queue
        PriorityQueue<HuffNode> queue = new PriorityQueue<HuffNode>(256, new FrequencyComparator());

        //fill with singletons
        for(char c: freq.keySet()) {
        	HuffNode n = new HuffNode(c, freq.get(c), null, null);
        	queue.offer(n);
        }

        while(queue.size() > 1) {
        	HuffNode tLeft = queue.poll();
        	HuffNode tRight = queue.poll();

        	//Create parent for these nodes
        	HuffNode parent = new HuffNode(null, tLeft.frequency+tRight.frequency, tLeft, tRight);
        	queue.add(parent);
        }

        //Traverse tree to assign codes
		return queue.peek();
	}

	/**
	 *
	 * @param root node of the Huffman tree
	 * @return Map of each character and their coding string.
	 */
	public HashMap<Character, String> buildCoding(HuffNode root){
		HashMap<Character, String> codingMap = new HashMap<Character, String>();
		Stack<HuffNode> stack = new Stack<HuffNode>();
		stack.push(root);

		while(!stack.isEmpty()) {
			HuffNode current = stack.pop();
			HuffNode l = current.left;
			HuffNode r = current.right;

			if(l != null && r != null) {
				l.coding = current.coding + '0';
				r.coding = current.coding + '1';
				stack.push(l);
				stack.push(r);
			}else {
				codingMap.put(current.value, current.coding);
			}
		}

		return codingMap;

	}

	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		char[] textArr = text.toCharArray();
		StringBuilder eText = new StringBuilder(); //Used a string builder as it was super slow otherwise
		for(char c: textArr) {
			eText.append(coding.get(c));
		}
		return eText.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		//need table of codes used
		// Label the edges of tree with 0's 1's
		// we get a trie which can be used like a scanner to split the coded file into separate codes to be decoded
		char[] textArr = encoded.toCharArray();
		StringBuilder dText = new StringBuilder();
		HuffNode root = huffTree;
		HuffNode node = root;

		for(char c: textArr) {
			if(c == '0') {
				node = node.left;
				if(node.left == null) {
					dText.append(node.value);
					node =root;
				}
			}else if(c == '1') {
				node = node.right;
				if(node.right == null) {
					dText.append(node.value);
					node =root;
				}
			}
		}
		return dText.toString();
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
		String coding = "";

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
