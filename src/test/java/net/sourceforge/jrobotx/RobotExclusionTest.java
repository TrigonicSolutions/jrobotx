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

package net.sourceforge.jrobotx;

import static net.sourceforge.jrobotx.Constants.ROBOTS_TXT;
import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import net.sourceforge.jrobotx.util.URLInputStreamFactory;

import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class RobotExclusionTest {
    private static final String[] SITE_URLS = { //
    "http://www.fict.org/", "http://www.fict.org/index.html", "http://www.fict.org/robots.txt",
            "http://www.fict.org/server.html", "http://www.fict.org/services/fast.html",
            "http://www.fict.org/services/slow.html", "http://www.fict.org/orgo.gif",
            "http://www.fict.org/org/about.html", "http://www.fict.org/org/plans.html",
            "http://www.fict.org/%7Ejim/jim.html", "http://www.fict.org/%7Emak/mak.html" };

    private static final String ROBOTS_TXT_FILE = ROBOTS_TXT.replace("/", "");

    private static final String UNHIPBOT = "UnhipBot/0.1";
    private static final String WEBCRAWLER = "WebCrawler/3.0";
    private static final String EXCITE = "Excite/1.0";
    private static final String OTHER = "Otherbot 9000/0.5";

    private static final String[] USER_AGENTS = { UNHIPBOT, WEBCRAWLER, EXCITE, OTHER };

    private IMocksControl mockControl;
    private URLInputStreamFactory mockURLInputStreamFactory;
    private URL robotsTxtUrl;

    @Before
    public void setup() throws Exception {
        mockControl = createStrictControl();
        mockURLInputStreamFactory = mockControl.createMock(URLInputStreamFactory.class);

        robotsTxtUrl = new URL("http://www.fict.org/robots.txt");
    }

    @Test
    public void testGetNonHttp() throws Exception {
        for (String userAgent : USER_AGENTS) {
            for (String urlString : SITE_URLS) {
                mockControl.reset();
                mockControl.replay();

                RobotExclusion robotExclusion = new RobotExclusion(mockURLInputStreamFactory);
                assertTrue(robotExclusion.allows(new URL(urlString.replaceAll("http", "ftp")), userAgent));
                mockControl.verify();
            }
        }
    }

    @Test
    public void testAccess() throws Exception {
        testAccess("http://www.fict.org/", false, true, false);
        testAccess("http://www.fict.org/index.html", false, true, false);
        testAccess("http://www.fict.org/robots.txt", true, true, true);
        testAccess("http://www.fict.org/server.html", false, true, true);
        testAccess("http://www.fict.org/services/fast.html", false, true, true);
        testAccess("http://www.fict.org/services/slow.html", false, true, true);
        testAccess("http://www.fict.org/orgo.gif", false, true, false);
        testAccess("http://www.fict.org/org/about.html", false, true, true);
        testAccess("http://www.fict.org/org/plans.html", false, true, false);
        testAccess("http://www.fict.org/%7Ejim/jim.html", false, true, false);
        testAccess("http://www.fict.org/%7Emak/mak.html", false, true, true);
    }

    private void testAccess(String urlString, boolean unhipbot, boolean webcrawlerAndExcite, boolean other)
            throws Exception {
        URL url = new URL(urlString);
        testAccess(url, UNHIPBOT, unhipbot);
        testAccess(url, WEBCRAWLER, webcrawlerAndExcite);
        testAccess(url, EXCITE, webcrawlerAndExcite);
        testAccess(url, OTHER, other);
    }

    private void testAccess(URL url, String userAgent, boolean allowed) throws Exception {
        mockControl.reset();

        if (!url.getFile().equals(ROBOTS_TXT)) {
            expect(mockURLInputStreamFactory.openStream(eq(robotsTxtUrl))).andReturn(
                    getClass().getResourceAsStream(ROBOTS_TXT_FILE));
        }
        mockControl.replay();

        RobotExclusion robotExclusion = new RobotExclusion(mockURLInputStreamFactory);
        assertEquals(userAgent + (allowed ? " not" : "") + " allowed " + url, allowed, robotExclusion.allows(url,
                userAgent));
        mockControl.verify();
    }

}
