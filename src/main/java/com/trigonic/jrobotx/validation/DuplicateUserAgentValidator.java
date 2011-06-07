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

import java.util.HashSet;
import java.util.Set;

import com.trigonic.jrobotx.Record;

public class DuplicateUserAgentValidator extends AbstractValidator {
	private Set<String> previous = new HashSet<String>();
	
	@Override
	public void accept(Record item) {
		Set<String> current = item.getUserAgents();
		
		for (String userAgent : current) {
			if (previous.contains(userAgent)) {
				addError("User agent " + userAgent + " appears in multiple rules, but only the first rule matching will be used.");
			} else {
				for (String compare : previous) {
					if (previous.contains(compare)) {
						addError("User agent " + userAgent + " masked by earlier record for " + compare);
					}
				}
			}
		}
		
		previous.addAll(current);
	}
}
