import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	static int WINDOW_SIZE = 400;
	
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	public String compress(String input) {
		char[] textArr = input.toCharArray();
		StringBuilder out = new StringBuilder();
		int cursor = 0;
		
		//Loop through all the text
		while(cursor < textArr.length) {
			int length = 0;
			int prevMatch = 0;
			while(true) {
				//Find a match
				int match = stringMatch(cursor, length, input);
				if(match != -1) { //If there's a match store the index and search again for longer match
					prevMatch = match;
					length++;
				}else { //if no match
					
					int windowSize = Math.min(WINDOW_SIZE, cursor); //how far back it searched
					
                    int offset = windowSize - prevMatch; //how far behind current position the previous match was
                    
                    Character nextChar;
                    if (cursor + length < input.length()) { //If within the text
                        nextChar = input.charAt(cursor + length); //char after matching pattern
                    } else { //If you've reached the end of the text
                        nextChar = null;
                    }
					
					out.append(new Tuple(offset, length, nextChar).toString()); //add the tuple to output
					cursor += length + 1; //move cursor
					break;
				}
			}
		}
		
		return out.toString();
	}
	
	/**
	 * Checks within the search window to check for strings
	 * matching your current pattern
	 * @param cursor
	 * @param length
	 * @param text
	 * @return
	 */
	private int stringMatch(int cursor, int length, String text) {
		
		int searchWindow = 0; //index to start searching from (0 if it would go before the start of the text)
		if (cursor - WINDOW_SIZE < 0) {
			searchWindow = 0;
		}else {
			searchWindow = cursor - WINDOW_SIZE;
		}

		//Get this substring of text to search
        String searchStr = text.substring(searchWindow, cursor);

        // Make sure you dont go past the end of the text
        if (text.length() < cursor + length + 1) {
            return -1;
        }
        
        //Get the pattern we are looking for
        String pattern = text.substring(cursor, cursor + length + 1);

        //Use my brute force to find the first index of this pattern
        return KMP.bruteSearch(pattern, searchStr);
	}

	/**
	 * Converts the text into tuples then adds the 
	 * corresponding characters to the output
	 * text format [int|int|char][int|int|char][int|int|char]
	 */
	public String decompress(String compressed) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Tuple> tuples = new ArrayList<>();
        int cursor = 0;

        // translate into a list of tuples
        Scanner scan = new Scanner(new BufferedReader(new StringReader(compressed)));
        scan.useDelimiter("\\]"); //Each scan gets a single tuple [int|int|char]
        
        while (scan.hasNext()) { //Scan through entire text
            String tuple = scan.next();//get tuple
            String[] tokens = tuple.split("\\|"); //split to get tuples values
            int offset = Integer.valueOf(tokens[0].substring(1)); //get offset skipping "["
            int length = Integer.valueOf(tokens[1]); //get length

            Character c = null; //null if end of text
            if (tokens.length > 2) {
                c = tokens[2].charAt(0); //get char ignoring "]"
            }
            tuples.add(new Tuple(offset, length, c)); //add this to the tuple list
        }
        scan.close();
        
        //Translate the tuple list into text
        for (Tuple tuple : tuples) {
            if (tuple.offset == 0 && tuple.length == 0) { //If it's just a single char with no pattern
                sb.append(tuple.c);
                cursor++;
            } else {
                sb.append(sb.substring(cursor - tuple.offset, cursor - tuple.offset + tuple.length)); //append the repeated string
                cursor += tuple.length;
                
                if (tuple.c != null) { //if the end of text is reached
                    sb.append(tuple.c);
                }
                cursor++;
            }
        }

        return sb.toString(); //returns the decompressed text
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
		Character c;
		
		public Tuple(int o, int l, Character c) {
			offset = o;
			length = l;
			this.c = c;
		}
		
		public String toString() {
			return "["+offset+"|"+length+"|"+c+"]";
		}
	}
}
