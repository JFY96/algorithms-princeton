import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private int N = 0;

	private Node first;
	private Node last;

	private class Node {
		Item item;
		Node next;
		Node prev;
	}

	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return first == null;
	}

	// return the number of items on the deque
	public int size() {
		return N;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null) throw new IllegalArgumentException("Invalid argument - null");
		Node oldNode = first;
		first = new Node();
		first.item = item;
		first.next = oldNode;
		first.prev = null;
		if (oldNode != null) oldNode.prev = first;
		if (last == null) last = first;
		N++;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null) throw new IllegalArgumentException("Invalid argument - null");
		Node oldNode = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.prev = oldNode;
		if (oldNode != null) oldNode.next = last;
		if (isEmpty()) first = last;
		else if (first.next == null) first.next = last;
		N++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) throw new java.util.NoSuchElementException("Cannot remove from empty deque");
		Item item = first.item;
		first = first.next;
		if (isEmpty()) last = null;
		else first.prev = null;
		N--;
		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (isEmpty()) throw new java.util.NoSuchElementException("Cannot remove from empty deque");
		Item item = last.item;
		if (last.prev == null) {
			first = null;
			last = null;
		} else if (last.prev == first) {
			last = last.prev;
			first.next = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		N--;
		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node iterateNode = first;
	
		public boolean hasNext() {
			return iterateNode != null;
		}

		public void remove() {
			/* not supported */
			throw new UnsupportedOperationException("Remove not supported");
		}

		public Item next() {
			if (iterateNode == null) throw new java.util.NoSuchElementException("No more items to iterate over.");
			Item item = iterateNode.item;
			iterateNode = iterateNode.next;
			return item;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		System.out.println("Size: "+deque.size());
		System.out.println("isEmpty: "+deque.isEmpty());
		deque.addFirst("first");
		System.out.println("Size: "+deque.size());
		for (String s : deque) System.out.println(s);
		System.out.println("isEmpty: "+deque.isEmpty());
		deque.addLast("last");
		System.out.println("Size: "+deque.size());
		for (String s : deque) System.out.println(s);
		for (String s : deque) System.out.println(s);
		System.out.println("removed " + deque.removeLast());
		System.out.println("Size: "+deque.size());
		for (String s : deque) System.out.println(s);
		System.out.println("isEmpty: "+deque.isEmpty());
		deque.addFirst("first again");
		deque.addFirst("first 2");
		deque.addFirst("first 3");
		deque.addFirst("first 4");
		deque.addLast("last again");
		deque.addLast("last 2");
		deque.addFirst("first 5");
		System.out.println("removed " + deque.removeLast());
		System.out.println("removed " + deque.removeLast());
		System.out.println("removed " + deque.removeLast());
		System.out.println("removed " + deque.removeLast());
		System.out.println("removed " + deque.removeLast());
		System.out.println("removed " + deque.removeFirst());
		System.out.println("Size: "+deque.size());
		for (String s : deque) System.out.println(s);
	}

}