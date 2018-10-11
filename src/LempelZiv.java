/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	int windowSize = 100;
	
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		char[] textArr = input.toCharArray();
		StringBuilder out = new StringBuilder();
		int cursor = 0;
		
		while(cursor < textArr.length) {
			int length = 0;
			int prevMatch = 0;
			while(true) {
				int match = stringMatch(cursor, length, input);
				if(match != -1) {
					prevMatch = match;
					length++;
				}else {
					out.append(new Tuple(prevMatch, length, input.charAt(cursor+length)).toString());
					cursor += length + 1;
					break;
				}
			}
		}
		
		return out.toString();
	}

	private int stringMatch(int cursor, int length, String text) {
		
		int searchWindow = 0;
		if(cursor - windowSize < 0) {
			searchWindow = 0;
		}else {
			searchWindow = cursor - windowSize;
		}

        String searchStr = text.substring(searchWindow, cursor);

        // Can't go outside the text
        if (text.length() < cursor + length + 1) {
            return -1;
        }

        String pattern = text.substring(cursor, cursor + length + 1);

        return KMP.bruteSearch(pattern, searchStr);
	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		// TODO fill this in.
		return "";
	}

	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {
		return "";
	}
	
	private class Tuple{
		int offset;
		int length;
		char c;
		
		public Tuple(int o, int l, char c) {
			offset = o;
			length = l;
			this.c = c;
		}
		
		public String toString() {
			return "["+offset+"|"+length+"|"+c+"]";
		}
	}
}
