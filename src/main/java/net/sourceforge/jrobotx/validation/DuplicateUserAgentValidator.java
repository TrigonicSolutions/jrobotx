package net.sourceforge.jrobotx.validation;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.jrobotx.Record;

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
