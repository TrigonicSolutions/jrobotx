# Spring Framework Utilities

This project intends to collect frequently requested utilities for and extensions to the Spring Framework.

## Gradle Usage

    repositories {
        add(new org.apache.ivy.plugins.resolver.URLResolver()) {
            name = 'GitHub Spring Utils Repo'
            addArtifactPattern 'http://cloud.github.com/downloads/AlanKrueger/spring-utils/[module]-[revision].[ext]'
        }
        mavenCentral()
    }

    dependencies {
        classpath 'com.trigonic:spring-utils:0.1'
    }


## Spring XML Usage

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:util="http://www.springframework.org/schema/util"
        xmlns:xutils="http://trigonic.com/schema/spring/xutils"
        xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd        
            http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-3.0.xsd
            http://trigonic.com/schema/spring/xutils http://trigonic.com/schema/spring/xutils.xsd">

        ...
    
        <!-- importing an optional resource -->
        <xutils:import resource="foo.xml" optional="true" />    
    
        <!-- importing a resource with an alternate if it doesn't exist -->
        <xutils:import resource="foo.xml" alternate="bar.xml" />    
    
        <!-- the same where both are optional -->
        <xutils:import resource="foo.xml" alternate="bar.xml" optional="true" />    

        ...
        
    </beans>
