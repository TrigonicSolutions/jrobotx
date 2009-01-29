package net.sourceforge.jrobotx;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class RecordIteratorTest {
    private IMocksControl mockControl;
    private BufferedReader mockReader;
    
    @Before
    public void setup() {
        mockControl = createStrictControl();
        mockReader = mockControl.createMock(BufferedReader.class);
    }
    
    @Test
    public void testHasNext() throws IOException {
        expect(mockReader.readLine()).andReturn("User-agent: foo");
        expect(mockReader.readLine()).andReturn("User-agent: bar");
        expect(mockReader.readLine()).andReturn("Allow: /");
        expect(mockReader.readLine()).andReturn("User-agent: baz");
        replay(mockReader);
        
        RecordIterator recordIter = new RecordIterator(mockReader);
        assertTrue(recordIter.hasNext());
        verify(mockReader);
    }
    
    @Test
    public void testNext() throws IOException {
        expect(mockReader.readLine()).andReturn("User-agent: foo");
        expect(mockReader.readLine()).andReturn("User-agent: bar");
        expect(mockReader.readLine()).andReturn("Allow: /");
        expect(mockReader.readLine()).andReturn("User-agent: baz");
        expect(mockReader.readLine()).andReturn("Disallow: /narf/");
        expect(mockReader.readLine()).andReturn(null);
        mockReader.close();
        replay(mockReader);
        
        RecordIterator recordIter = new RecordIterator(mockReader);
        assertTrue(recordIter.hasNext());
        
        Record record = recordIter.next();
        assertEquals(2, record.getUserAgents().size());
        assertTrue(record.getUserAgents().contains("foo"));
        assertTrue(record.getUserAgents().contains("bar"));
        assertFalse(record.getUserAgents().contains("baz"));
        assertEquals(1, record.getRules().size());
        assertEquals("Allow", record.getRules().get(0)[0]);
        assertEquals("/", record.getRules().get(0)[1]);
        assertTrue(recordIter.hasNext());
        
        record = recordIter.next();
        assertEquals(1, record.getUserAgents().size());
        assertFalse(record.getUserAgents().contains("foo"));
        assertFalse(record.getUserAgents().contains("bar"));
        assertTrue(record.getUserAgents().contains("baz"));
        assertEquals(1, record.getRules().size());
        assertEquals("Disallow", record.getRules().get(0)[0]);
        assertEquals("/narf/", record.getRules().get(0)[1]);
        assertFalse(recordIter.hasNext());
        
        verify(mockReader);
    }
}
