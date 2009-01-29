package net.sourceforge.jrobotx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

public class RecordTest {
    @Test
    public void test() {
        Set<String> userAgents = new HashSet<String>(Arrays.asList("netscape", "mozilla"));
        List<String[]> rules = new ArrayList<String[]>();
        rules.add(new String[] {"Disallow", ""});
        rules.add(new String[] {"Disallow", "/foo/bar/"});
        rules.add(new String[] {"Allow", "/foo/"});
        rules.add(new String[] {"Disallow", "/"});
        Record record = new Record(userAgents, rules);
        
        assertTrue(record.matches("Netscape/1.0"));
        assertTrue(record.matches("Mozilla Compatible Thing"));
        assertFalse(record.matches("Opera/1.0"));
        assertFalse(record.matches("Netscrape"));
        
        assertTrue(record.allows("/foo/index.html"));
        assertFalse(record.allows("/foo/bar/index.html"));
        assertFalse(record.allows("/index.html"));
    }
}
