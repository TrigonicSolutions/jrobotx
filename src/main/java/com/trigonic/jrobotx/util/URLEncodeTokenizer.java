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

public class URLEncodeTokenizer extends AbstractIterator<URLEncodeTokenizer.Token> {
    private String string;
    private String specialChars;
    private int pos;
    
    public URLEncodeTokenizer(String urlEncodedString, String specialChars) {
        this.string = urlEncodedString;
        this.specialChars = specialChars;
    }
    
    @Override
    protected Token getNext() {
        Token result = null;
        
        if (pos < string.length()) {
            char ch = string.charAt(pos++);
            boolean special = false;
            if (ch == '%' && isHexDigits(string, pos, 2)) {
                ch = (char) Integer.parseInt(string.substring(pos, pos + 2), 16);
                pos += 2;
            } else if (specialChars.indexOf(ch) >= 0) {
                special = true;
            }
            result = new Token(ch, special);
        }
        
        return result;
    }
    
    private static boolean isHexDigits(String str, int pos, int length) {
        boolean result = false;
        if (pos + length - 1 < str.length()) {
            for (int i = 0; i < length; ++i) {
                result = isHexDigit(str.charAt(pos + i));
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }
    
    private static boolean isHexDigit(char ch) {
        return Character.isDigit(ch) || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f');
    }
    
    public static class Token implements Comparable<Token> {
        private char ch;
        private boolean special;
        
        public Token(char ch, boolean special) {
            this.ch = ch;
            this.special = special;
        }
        
        public char getCharacter() {
            return ch;
        }
        
        public boolean isSpecial() {
            return special;
        }
        
        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if (obj != null && obj instanceof Token) {
                Token other = (Token) obj;
                result = (ch == other.ch) && (special == other.special);
            }
            return result;
        }
        
        public int compareTo(Token other) {
            int result = CompareUtils.compare(ch, other.ch);
            if (result == 0) {
                result = CompareUtils.compare(special, other.special); 
            }
            return result;
        }
    }
}
