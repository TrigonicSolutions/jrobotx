package net.sourceforge.jrobotx.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.jrobotx.util.URLEncodeTokenizer.Token;

import org.junit.Test;

public class URLEncodeTokenizerTest {
    @Test
    public void test() {
        String specialChars = "/";
        String string = "%Z/%2f%2F %20%5";
        char[] characters = { '%', 'Z', '/', '/', '/', ' ', ' ', '%', '5' };
        boolean[] special = { false, false, true, false, false, false, false, false, false };
        assertEquals(special.length, characters.length);

        URLEncodeTokenizer tokens = new URLEncodeTokenizer(string, specialChars);
        for (int i = 0; i < characters.length; ++i) {
            assertTrue(tokens.hasNext());
            Token token = tokens.next();
            assertEquals("index " + i + " expected:" + characters[i] + " but was:" + token.getCharacter(), characters[i], token.getCharacter());
            assertEquals(special[i], token.isSpecial());
        }
        assertFalse(tokens.hasNext());
    }
}
