import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The main class for this program.
 */
public class Driver {
    /**
     * Main method for this program.
     * THe starting point.
     * @param args - ignored in this program
     */
    public static void main(String[] args) throws FileNotFoundException {
        readData();
    }

    /**
     * Reads in each line as a String puts it into a priority
     * queue and performs huffman coding on it.
     */
    public static void readData() throws FileNotFoundException {
        File f = new File("src/input.txt");
        PrintStream writer = new PrintStream("src/output.txt");
        Scanner scan = new Scanner(f);
        String line;

        while (scan.hasNextLine()) {
            int count = 0;
            line = scan.nextLine();
            HuffNode[] arr = new HuffNode[line.length()];
            while(count < line.length()) {
                char c = line.charAt(count);
                int alreadyIn = contains(c, arr);
                if (alreadyIn > -1) {
                    arr[alreadyIn].setMyFrequency(arr[alreadyIn].getMyFrequency() + 1);
                } else {
                    arr[count] = new HuffNode(c, 1);
                }
                count++;
            }
            PriorityQueue q = makeQueue(arr);
            huffManCode(q);
            HashMap<HuffNode, String> map = new HashMap<>();
            compressedBits(q.getRoot(), "", map);
            writer.println(print(map, arr));
            String encodedBits = encode(map, line);
            writer.println(encodedBits+"[" + encodedBits.length() + "]");
            String decodedBits = decode(q.getRoot(), encodedBits);
            writer.println(decodedBits + "["+ decodedBits.length()*8 + "]");
            String compressionRat = String.format("%,.2f", (double)
                    (decodedBits.length()*8)/(double)encodedBits.length());
            writer.println("[Compression Ratio: " + compressionRat + "]\n");

        }
    }

    /**
     * Takes a priority queue and performs the huffman
     * sorting algorithm to create a huffman tree.
     * @param q - the priority queue which will
     *          be turing into a huffman tree.
     */
    public static void huffManCode(PriorityQueue q) {
        HuffNode temp1;
        HuffNode temp2;
        while (q.getCount() > 0) {
            temp1 = q.removeNext();
            temp2 = q.removeNext();
            var combined = new HuffNode('\u0000',
                    temp1.getMyFrequency()+temp2.getMyFrequency());
            combined.setMyLeft(temp1);
            combined.setMyRight(temp2);
            q.addElement(combined);
        }
    }

    /**
     * Takes in a huffman tree and adds the proper key pair values
     * for its frequency table.
     * @param n - The root huffman node.
     * @param s - The String which we will be used
     *          to add to the map.
     * @param map - The map which will contain Huffman node key
     *            and its distinct code
     */
    public static void compressedBits(HuffNode n, String s,
                                        HashMap<HuffNode, String> map) {
        if (n.getMyLeft() == null && n.getMyRight() == null) {
            map.put(n, s);
        } else {
            compressedBits(n.getMyLeft(), s+"0", map);

            compressedBits(n.getMyRight(), s+"1", map);
        }
    }

    /**
     * Takes in an encoded string and decodes it
     * based off of the huffman tree that was passed
     * along with it.
     * @param n The root huffman node
     * @param s The string we are decoding using the
     *          tree.
     * @return The decoded string
     */
    public static String decode(HuffNode n, String s) {
        int index = 0;
        HuffNode rootClone = n;
        StringBuilder answer = new StringBuilder();
        while((n.getMyLeft() != null || n.getMyRight() != null) && index < s.length()) {
            if (s.charAt(index) == '0') {
                n = n.myLeft;
            } else {
                n = n.myRight;
            }
            if (n.getMyChar() != '\u0000') {
                answer.append(n.getMyChar());
                n = rootClone;
            }

            index++;
        }
        return answer.toString();
    }

    /**
     * This method takes an array of
     * HuffNode Objects and adds them
     * to a priority queue.
     * @param arr The array that will be put into a queue
     * @return the priority queue
     */
    public static PriorityQueue makeQueue(HuffNode[] arr) {
        PriorityQueue q = new PriorityQueue(arr.length);
        for (HuffNode n : arr) {
            if (n != null) {
                q.add(n);
            }
        }
        return q;
    }

    /**
     *  Takes in a string and its map equivalent,
     *  and creates an encoded huffman string
     * @return the encoded string
     */
    public static String encode(HashMap<HuffNode, String> map, String s) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            for (HuffNode n : map.keySet()) {
                if (n.getMyChar() == s.charAt(i)) {
                    answer.append(map.get(n));
                }
            }
        }
        return answer.toString();
    }

    /**
     * Checks if the HuffNode array contains
     * a value or not.
     * @param c - the character we are looking for
     * @param arr - the array
     * @return the index it is in, or -1
     */
    public static int contains(char c, HuffNode[] arr) {
        int count = 0;
        for (HuffNode n : arr) {
            try {
                if (n.getMyChar() == c) {
                    return count;
                }
            } catch (NullPointerException ignored) {

            }
            count++;
        }
        return -1;
    }

    /**
     * Prints out the frequency table using a map
     * with all of the indicated pairs
     * @param map - the map with huffnode and code value pairs
     * @return The frequency tables string
     */
    public static String print(HashMap<HuffNode, String> map, HuffNode[] arr) {
        StringBuilder s = new StringBuilder();
        s.append("""
                ======================================
                char      frequency       code
                --------------------------------------
                """);
        for (HuffNode n : arr) {
            if (n != null) {
                s.append(n.getMyChar()).append("         ").append(n.getMyFrequency())
                        .append("                ").append(map.get(n)).append(" \n");
            }
        }
        return s.toString();
    }
}