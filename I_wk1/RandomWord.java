import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Write a program RandomWord.java that reads a sequence of words from standard input and prints one of those words uniformly at random. 
 * Do not store the words in an array or list.
 * Instead, use Knuths method: when reading the ith word, select it with probability 1/i to be the champion, replacing the previous champion. 
 * After reading all of the words, print the surviving champion.
 */
public class RandomWord {
	public static void main(String[] args) {
		int i = 0;
		String champion = "";
		while (!StdIn.isEmpty()) {
			String word = StdIn.readString();
			i++;
			if (StdRandom.bernoulli(1 / (double) i)) {
				champion = word;
			}
		}
		StdOut.println(champion);
	}
}