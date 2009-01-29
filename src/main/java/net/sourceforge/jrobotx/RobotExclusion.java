package net.sourceforge.jrobotx;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.sourceforge.jrobotx.util.DefaultURLInputStreamFactory;
import net.sourceforge.jrobotx.util.URLInputStreamFactory;

import org.apache.log4j.Logger;

import static net.sourceforge.jrobotx.Constants.*;

public class RobotExclusion {
	private static final Logger LOG = Logger.getLogger(RobotExclusion.class);
	
	private static final Set<String> SUPPORTED_PROTOCOLS = new HashSet<String>(Arrays.asList(HTTP, HTTPS));
	
	private URLInputStreamFactory urlInputStreamFactory;
	
	public RobotExclusion(URLInputStreamFactory urlInputStreamFactory) {
		this.urlInputStreamFactory = urlInputStreamFactory;
	}
	
	public RobotExclusion() {
		this(new DefaultURLInputStreamFactory());
	}

	/**
	 * Get a robot exclusion {@link RecordIterator} for the server in the specified {@link URL}, or null if none is
	 * available. If the protocol is not supported--that is, not HTTP-based--null is returned.
	 */
	public RecordIterator get(URL url) {
		RecordIterator recordIter = null;
		
		if (!SUPPORTED_PROTOCOLS.contains(url.getProtocol().toLowerCase())) {
			return null;
		}

		try {
			// TODO: this should support error conditions as described in the protocol draft
			// TODO: use some kind of caching
			URL robotsUrl = new URL(url, ROBOTS_TXT);
			recordIter = new RecordIterator(urlInputStreamFactory.openStream(robotsUrl));
		} catch (IOException e) {
			LOG.info("Failed to fetch " + url, e);
		}

		return recordIter;
	}

	/**
	 * Get a robot exclusion {@link Record} for the specified {@link URL}, or null if none is available.  This uses {@link #get(URL)}
	 * and iterates through the {@link RecordIterator} to find a matching {@link Record}.
	 */
	public Record get(URL url, String userAgentString) {
		Record result = null;
		RecordIterator recordIter = get(url);
		if (recordIter != null) {
			while (recordIter.hasNext()) {
				Record record = recordIter.next();
				if (record.matches(userAgentString)) {
					result = record;
					break;
				}
			}
			if (result == null) {
				result = recordIter.getDefaultRecord();
			}
		}
		return result;
	}

	/**
	 * Determine whether the specified {@link URL} is allowed for the specified user agent string.  This uses {@link #get(URL, String)}
	 * and returns whether the matching record allows the {@link URL#getPath() path} specified in the URL.  
	 */
	public boolean allows(URL url, String userAgentString) {
	    // shortcut - /robots.txt might not exist, but it must be allowed
        if (Record.ruleMatches(ROBOTS_TXT, url.getFile())) {
            return true;
        }
            
		Record record = get(url, userAgentString);
		return record == null || record.allows(url.getPath());
	}
}
