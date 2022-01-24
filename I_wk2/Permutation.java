import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			q.enqueue(str);
		}
		for (int i = 0; i < k; i++) {
			if (!q.isEmpty()) System.out.println(q.dequeue());
		}
	}
}