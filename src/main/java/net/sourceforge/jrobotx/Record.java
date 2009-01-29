package net.sourceforge.jrobotx;

import static net.sourceforge.jrobotx.Constants.ALLOW;
import static net.sourceforge.jrobotx.Constants.ANY;
import static net.sourceforge.jrobotx.Constants.URL_ENCODING_SPECIAL_CHARS;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.sourceforge.jrobotx.util.URLEncodeTokenizer;
import net.sourceforge.jrobotx.util.URLEncodeTokenizer.Token;

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
		boolean result = true;
		
		for (String[] rule : rules) {
			if (rule[1].length() > 0 && ruleMatches(rule[1], path)) {
				result = rule[0].equals(ALLOW);
				break;
			}
		}
		
		return result;
	}
	
	static boolean ruleMatches(String rule, String path) {
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
}
