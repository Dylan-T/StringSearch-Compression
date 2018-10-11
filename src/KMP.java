/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
	int[] matchTable; 
	public KMP(String pattern, String text) {
		matchTable = buildTable(text);
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public int search(String pattern, String text) {
		char[] patternArr = pattern.toCharArray();
		char[] textArr = text.toCharArray();
		int k = 0;
		int i = 0;
		int n = text.length();
		int m = pattern.length();
		
		while(k+i < n) {
			if(patternArr[i] == textArr[k+i]) {
				i++;
				if(i == m) {
					return k;
				}
			}else if(matchTable[i] == -1) {
				i = 0;
				k += i + 1;
			}else {
				k += i - matchTable[i];
				i = matchTable[i];
			}
		}
		return -1;
	}
	
	public static int bruteSearch(String pattern, String text) {
		char[] patternArr = pattern.toCharArray();
		char[] textArr = text.toCharArray();
		int patI = 0;
		int textI = 0;
		
		while(textI + patI < textArr.length) {
			if(textArr[textI + patI] == patternArr[patI]) {
				patI++;
				if(patI == patternArr.length) {
					return textI;
				}
			}else {
				textI++;
				patI = 0;
			}
		}
		return -1;
	}
	
	public int[] buildTable(String text) {
		char[] textArr = text.toCharArray();
		int m = text.length();
		int[] matchTable = new int[m];
		matchTable[0] = -1;
		matchTable[1] = 0;
		int j = 0;
		int pos = 2;
		
		while(pos < m) {
			if(textArr[pos-1] == textArr[j]) {
				matchTable[pos] = j+1;
				pos++;
				j++;
			}else if(j < 0) {
				j = matchTable[j];
			}else {
				matchTable[pos] = 0;
				pos++;
			}
		}
		
		return matchTable;
	}
}
