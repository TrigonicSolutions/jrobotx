package net.sourceforge.jrobotx.validation;

import java.util.Iterator;

import net.sourceforge.jrobotx.Record;
import net.sourceforge.jrobotx.RecordIterator;

public class IgnoredFieldValidator extends AbstractValidator {
	@Override
	public void begin(Iterator<Record> iterator) {
		super.begin(iterator);
		
		if (iterator instanceof RecordIterator) {
			RecordIterator recordIterator = (RecordIterator) iterator;
			for (String ignoredField : recordIterator.getUnknownFields()) {
				addError("Field " + ignoredField + " not understood and ignored");
			}
		}
	}
	
	@Override
	public void accept(Record item) {
		// does nothing here
	}
}
