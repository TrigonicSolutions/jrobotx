package net.sourceforge.jrobotx.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class AbstractIteratorTest {
	@Test
	public void test() {
		Integer[] testValues = { 23, 42, 71, -1 };
		Iterator<Integer> valueIter = new TestIterator<Integer>(testValues);
		for (int i = 0; i < testValues.length; ++i) {
			assertTrue(valueIter.hasNext());
			assertEquals(testValues[i], valueIter.next());
		}
		assertFalse(valueIter.hasNext());
	}
	
	private static class TestIterator<E> extends AbstractIterator<E> {
		private List<E> values;
		
		public TestIterator(E... values) {
			this.values = new ArrayList<E>(Arrays.asList(values));
		}
		
		@Override
		protected E getNext() {
			return values.isEmpty() ? null : values.remove(0);
		}
	}
}
