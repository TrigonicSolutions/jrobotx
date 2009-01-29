package net.sourceforge.jrobotx.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface URLInputStreamFactory {
	InputStream openStream(URL url) throws IOException;
}
