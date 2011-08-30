# JRobotX

Library to provide compliance with the Web Robot Exclusion protocol (robots.txt)

Forked to allow parsing of Googlebot-style pattern matching rules

## Usage

    import com.trigonic.jrobotx.RobotExclusion;

    // ...
    
    RobotExclusion robotExclusion = new RobotExclusion();
    if (robotExclusion.allows(url, userAgentString)) {
        // do something with url
    }
    
