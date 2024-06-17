# TestAutomationIntegration

The project is to implement application test automation for web, android and iOS platform.
Test results are published using Extend Reporter and Report Portal
Sample test application taken is swag labs

# Machine setup

* Install JDK 17 or higher
* Install IntelliJ Idea Community Edition
* Install the following plugins
    * .ignore
    * Cucumber for Java
    * Gherkin
    * Maven
    * Properties
    * Shell Script
    * Git
    * Terminal

# Execution

To run Tests with tag @swaglab, execute below command - 

Using shell command - 

    chmod +x setupOutputDirectory.sh
    
    chmod +x runTests.sh
    
    ./runTests.sh configFilePath=./config/swaglab.properties Tag=@swaglab Platform=web

Using maven command - 

    mvn compile exec:java -Dexec.mainClass="com.runner.Main" -Dexec.args="./config/swaglab.properties @swaglab web"

To execute tests on Docker
    
    Execute above commands, but make sure to add property RUN_IN_CI=true in config file which will be passed in arguments.

To execute tests on BrowserStack

    Execute above commands, but make sure to add property RUN_IN_CLOUD_PLATFORM=true in config file which wull be passed in arguments





