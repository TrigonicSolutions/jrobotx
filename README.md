# JRobotX

Library to provide compliance with the Web Robot Exclusion protocol (robots.txt)

## Usage

    import com.trigonic.jrobotx.RobotExclusion;

    // ...
    
    RobotExclusion robotExclusion = new RobotExclusion();
    if (robotExclusion.allows(url, userAgentString)) {
        // do something with url
    }
    
