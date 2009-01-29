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
