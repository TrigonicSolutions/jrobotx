package net.sourceforge.jrobotx.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.jrobotx.Record;

public abstract class AbstractValidator implements Validator {
	private List<String> errors = new ArrayList<String>();
	
	public void begin(Iterator<Record> iterator) {
		errors.clear();
	}
	
	public abstract void accept(Record item);
	
	public String[] getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void addError(String msg) {
		if (!errors.contains(msg)) {
			errors.add(msg);
		}
	}	
}
