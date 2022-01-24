import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s;
	private int N = 0;
	private int arrSize = 0;

	// construct an empty randomized queue
	public RandomizedQueue() {
		resize(1);
	}

	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++) copy[i] = s[i];
		s = copy;
		arrSize = capacity;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return N == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return N;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException("Invalid argument - null");

		if (N == arrSize) resize(2 * arrSize);
		s[N++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) throw new java.util.NoSuchElementException("Cannot remove from empty queue");
		
		// random index and swap with last element
		int randomIndex = StdRandom.uniform(N);
		if (randomIndex != (N-1)) {
			Item temp = s[randomIndex];
			s[randomIndex] = s[N-1];
			s[N-1] = temp;
		}

		Item item = s[--N];
		s[N] = null;
		if (N > 0 && N == arrSize/4) resize(arrSize/2);
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) throw new java.util.NoSuchElementException("Cannot use method on empty queue");
		return s[StdRandom.uniform(N)];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		private int i = N;
		private Item[] sCopy;

		public RandomizedQueueIterator() {
			sCopy = (Item[]) new Object[N];
			for (int j = 0; j < N; j++) sCopy[j] = s[j];
		}
	
		public boolean hasNext() {
			return i > 0;
		}

		public void remove() {
			/* not supported */
			throw new UnsupportedOperationException("Remove not supported");
		}

		public Item next() {
			if (i <= 0) throw new java.util.NoSuchElementException("No more items to iterate over.");
			int randomIndex = StdRandom.uniform(i); 
			if (randomIndex != (i-1)) {
				Item temp = sCopy[randomIndex];
				sCopy[randomIndex] = sCopy[i-1];
				sCopy[i-1] = temp;
			}
			Item item = sCopy[--i];
			sCopy[i] = null;
			return item;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		queue.enqueue(5);
		queue.enqueue(4);
		queue.enqueue(9);
		queue.enqueue(8);
		queue.enqueue(7);
		queue.enqueue(6);
		queue.enqueue(1);
		System.out.println("dequeue " + queue.dequeue());
		for (int a : queue) {
			for (int b : queue) System.out.print(a + "-" + b + " ");
			System.out.println();
		}
		System.out.println("dequeue " + queue.dequeue());
		System.out.println("dequeue " + queue.dequeue());
		System.out.println("dequeue " + queue.dequeue());
		System.out.println("dequeue " + queue.dequeue());
		System.out.println("dequeue " + queue.dequeue());
		for (int a : queue) System.out.print(a + " ");
		System.out.println();
	}
}
