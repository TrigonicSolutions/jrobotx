package net.sourceforge.jrobotx.validation;

import java.util.Iterator;

import net.sourceforge.jrobotx.Record;
import net.sourceforge.jrobotx.util.Visitor;

public interface Validator extends Visitor<Record> {
	void begin(Iterator<Record> iterator);
	String[] getErrors();
}
