package net.sourceforge.jrobotx.util;

public interface Visitor<T> {
	void accept(T item);
}
