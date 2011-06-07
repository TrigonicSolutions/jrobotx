/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trigonic.jrobotx.util;

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
