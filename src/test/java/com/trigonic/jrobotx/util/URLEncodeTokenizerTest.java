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

package com.trigonic.jrobotx.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.trigonic.jrobotx.util.URLEncodeTokenizer.Token;

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
