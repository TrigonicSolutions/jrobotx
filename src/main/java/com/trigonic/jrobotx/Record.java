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

import com.trigonic.jrobotx.util.URLEncodeTokenizer;
import com.trigonic.jrobotx.util.URLEncodeTokenizer.Token;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.trigonic.jrobotx.Constants.*;

public class Record {
	private Set<String> userAgents;
	private List<String[]> rules;
	
	public Record(Set<String> userAgents, List<String[]> rules) {
		this.userAgents = userAgents;
		this.rules = rules;
	}
	
	public Set<String> getUserAgents() {
		return Collections.unmodifiableSet(userAgents);
	}
	
	public List<String[]> getRules() {
		return Collections.unmodifiableList(rules);
	}
	
	public boolean matches(String userAgentString) {
		boolean result = false;
		
		userAgentString = userAgentString.toLowerCase();
		for (String userAgent : userAgents) {
			if (userAgent.equals(ANY) || userAgentString.contains(userAgent)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public boolean allows(String path) {
		boolean allowed = false;
		boolean disallowed = false;

		for (String[] rule : rules) {
			if (rule[1].length() > 0 && ruleMatches(rule[1], path)) {
                if (rule[0].equalsIgnoreCase(ALLOW)) {
                    allowed = true;
                } else
                    disallowed = true;
			}
		}
		
		return allowed || !disallowed;
	}
	
	static boolean ruleMatches(String rule, String path) {
        if (rule.contains("*") || rule.endsWith("$"))
            return ruleMatchesWildcard(rule, path);
        else
            return ruleMatchesStandard(rule, path);
	}

    private static boolean ruleMatchesStandard(String rule, String path) {
        boolean result = true;

        URLEncodeTokenizer ruleTokens = new URLEncodeTokenizer(rule, URL_ENCODING_SPECIAL_CHARS);
        URLEncodeTokenizer pathTokens = new URLEncodeTokenizer(path, URL_ENCODING_SPECIAL_CHARS);
        while (ruleTokens.hasNext() && pathTokens.hasNext()) {
            Token token1 = ruleTokens.next();
            Token token2 = pathTokens.next();
            if (!token1.equals(token2)) {
                result = false;
                break;
            }
        }

        if (result && ruleTokens.hasNext()) {
            result = false;
        }

        return result;
    }

    private static boolean ruleMatchesWildcard(String rule, String path) {
        //if rule ends with $, matcher.find will have to match the full path
        final String pattern = "^" + //add ^ to match from the start of the path
                rule.replaceAll("\\*", ".*") //replace wildcards with pattern equivalent
                        .replaceAll("\\?", "\\\\?"); //escape question marks (maybe other characters to escape)
        return Pattern.compile(pattern).matcher(path).find();
    }
}
