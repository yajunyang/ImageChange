
package yang.utility;

import java.util.Iterator;

/**
 * This class implements an array with flexible bottom and top index,
 * similar to an array in Pascal.
 */
public class LinearContainer<T> implements Iterable<T> {
	
	private int botIndex, topIndex;
	private T[] data;
	
	@SuppressWarnings("unchecked")
	public LinearContainer(int n) {
		this.botIndex = 0;
		topIndex = n-1;
		data = (T[]) new Object[n];
	}
	
	@SuppressWarnings("unchecked")
	public LinearContainer(int botIndex, int topIndex) {
		this.botIndex = botIndex;
		this.topIndex = topIndex;
		data = (T[]) new Object[topIndex - botIndex + 1];
	}
	
	public T getElement(int k) {
		return data[k-getBotIndex()];
	}
	
	public void setElement(int k, T elem) {
		data[k-getBotIndex()] = elem;
	}

	public int getBotIndex() {
		return botIndex;
	}
	
	public int getTopIndex() {
		return topIndex;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<T>(data);
	}

}
