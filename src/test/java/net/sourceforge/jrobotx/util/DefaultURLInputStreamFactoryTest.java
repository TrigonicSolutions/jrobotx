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

package net.sourceforge.jrobotx.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DefaultURLInputStreamFactoryTest {
    @Test
    public void test() throws IOException {
        URL testUrl = getClass().getResource("test.txt");
        List<String> lines = (List<String>) IOUtils.readLines(new DefaultURLInputStreamFactory().openStream(testUrl));
        assertEquals(1, lines.size());
        assertEquals("This is a test.", lines.get(0));
    }
}
