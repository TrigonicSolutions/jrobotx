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
