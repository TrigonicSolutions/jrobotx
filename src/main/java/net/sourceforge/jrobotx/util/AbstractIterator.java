package net.sourceforge.jrobotx.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An abstract {@link Iterator}.  This implementation treats <tt>null</tt> as a marker and cannot support iteration sequences
 * that can return it as a valid value.
 */
public abstract class AbstractIterator<E> implements Iterator<E>  {
	private boolean nextNeeded = true;
	private E next;
	private E last;
	
	public boolean hasNext() {
		if (nextNeeded) {
			next = getNext();
			nextNeeded = false;
		}
		return next != null;
	}
	
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		nextNeeded = true;
		last = next;
		return next;
	}
	
	public void remove() {
		if (last == null) {
			throw new IllegalStateException();
		}
		removeLast(last);
	}
	
	/**
	 * Implement this to return the next value in the iteration, or <tt>null</tt> if none remain.
	 */
	protected abstract E getNext();
	
	/**
	 * Override this to support {@link #remove()}.
	 */
	protected void removeLast(E last) {
		throw new UnsupportedOperationException();
	}
}
