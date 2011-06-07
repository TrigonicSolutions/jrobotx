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

package com.trigonic.jrobotx;

import static com.trigonic.jrobotx.Constants.ALLOW;
import static com.trigonic.jrobotx.Constants.ANY;
import static com.trigonic.jrobotx.Constants.COMMENT_DELIM;
import static com.trigonic.jrobotx.Constants.DISALLOW;
import static com.trigonic.jrobotx.Constants.FIELD_DELIM;
import static com.trigonic.jrobotx.Constants.SITEMAP;
import static com.trigonic.jrobotx.Constants.USER_AGENT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.trigonic.jrobotx.util.AbstractIterator;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * An {@link Iterator} over {@link Record} objects returned from a robots.txt source.  After iteration, the record that first
 * {@link Record#matches(String) matched} "*" may be retrieved using {@link #getDefaultRecord()}.
 */
public class RecordIterator extends AbstractIterator<Record> {
	private static final Logger LOG = Logger.getLogger(RecordIterator.class);
	
	private BufferedReader reader;
	private List<String> pushBack = new ArrayList<String>();
	private Record defaultRecord;
	private Set<String> unknownFields = new HashSet<String>();
	
	public RecordIterator(BufferedReader reader) {
		this.reader = reader;
	}
	
	public RecordIterator(Reader reader) {
		if (reader instanceof BufferedReader) {
			this.reader = (BufferedReader) reader;
		} else {
			this.reader = new BufferedReader(reader);
		}
	}
	
	public RecordIterator(InputStream inputStream) {
		this(new InputStreamReader(inputStream));
	}
	
	/**
	 * Retrieve the Record that first matched "*".  This may not be available until the end of the iteration.
	 */
	public Record getDefaultRecord() {
		return defaultRecord;
	}
	
	/**
	 * Retrieve fields from the supplied robots.txt that are unknown and ignored.  (Fields that are known but ignored, like
	 * "Sitemap", are not included in this list.)
	 */
	public Set<String> getUnknownFields() {
		return unknownFields;
	}
	
	@Override
	protected Record getNext() {
		Record record = null;
		
		if (reader != null) {
			try {
				Set<String> userAgents = new HashSet<String>();
				List<String[]> rules = new ArrayList<String[]>();
				boolean inUserAgents = true;
	
				String line;
				while ((line = readLine()) != null) {
					String[] pieces = splitLine(line);
					if (pieces[0].equals("")) {
					    // nothing on this line
					} else if (pieces[0].equals(USER_AGENT)) {
						if (inUserAgents) {
							userAgents.add(pieces[1]);
						} else {
							pushBack.add(0, line);
							break;
						}
					} else if (pieces[0].equals(ALLOW) || pieces[0].equals(DISALLOW)) {
						inUserAgents = false;
						rules.add(pieces);
					} else if (pieces[0].equals(SITEMAP)) {
						// ignore, can't use this
					} else {
						unknownFields.add(pieces[0]);
					}
				}
				
				if (line == null) {
	                IOUtils.closeQuietly(reader);
	                reader = null;				    
				}
				
				if (userAgents.size() > 0 || rules.size() > 0) {
				    record = new Record(userAgents, rules);
				}
			} catch (IOException e) {
			    // TODO: how to handle this appropriately?
				LOG.info("read failed", e);
			}
			
			if (record != null && record.matches(ANY)) {
				defaultRecord = record;
			}
		}
		
		return record;
	}
	
	private String readLine() throws IOException {
		String result = null;
		
		if (pushBack.size() > 0) {
		    result = pushBack.remove(0);
		} else {
            result = reader.readLine();
		}
		
		return result;
	}
	
	private String[] splitLine(String line) {
		String[] results = new String[2];
		
		// strip comments
		String[] pieces = line.trim().split(COMMENT_DELIM, 2);
		
		// split into field/value pair
		pieces = pieces[0].split(FIELD_DELIM, 2);
		
		// trim spaces on both the field and value
		for (int i = 0; i < pieces.length; ++i) {
			results[i] = pieces[i].trim();			
		}

		return results;
	}
}
