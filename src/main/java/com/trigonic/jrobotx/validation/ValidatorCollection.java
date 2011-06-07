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

package com.trigonic.jrobotx.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.trigonic.jrobotx.Record;

public class ValidatorCollection extends ArrayList<Validator> implements Validator {
	private static final long serialVersionUID = 1L;
	
	public void begin(Iterator<Record> iterator) {
		for (Validator validator : this) {
			validator.begin(iterator);
		}
	}
	
	public void accept(Record item) {
		for (Validator validator : this) {
			validator.accept(item);
		}
	}
	
	public String[] getErrors() {
		List<String> errors = new ArrayList<String>();
		for (Validator validator : this) {
			errors.addAll(Arrays.asList(validator.getErrors()));
		}
		return errors.toArray(new String[errors.size()]);
	}
}
