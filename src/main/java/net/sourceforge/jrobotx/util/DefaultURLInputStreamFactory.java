package net.sourceforge.jrobotx.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DefaultURLInputStreamFactory implements URLInputStreamFactory {
	public InputStream openStream(URL url) throws IOException {
		return url.openStream();
	}
}
