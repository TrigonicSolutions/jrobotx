package net.sourceforge.jrobotx.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.jrobotx.Record;

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
