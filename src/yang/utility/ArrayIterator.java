package yang.utility;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This utility class implements a basic iterator for arbitrary arrays.
 */
public class ArrayIterator<T> implements Iterator<T> {

	private int next;
	private T[] data;

	public ArrayIterator(T[] data) {
		this.data = data;
		next = 0;
	}

	@Override
	public boolean hasNext() {
		return next < data.length;
	}

	@Override
	public T next() {
		if (hasNext())
			return data[next++];
		else
			throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
