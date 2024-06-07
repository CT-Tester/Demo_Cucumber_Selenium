
### Objective

This project has been created to automate the tests of a fictional online shop (https://qa-challenge.codesubmit.io/)

### Test plan 

These are the functional test cases that have been implemented so far:

Login feaature
--------------
-01 Login operation using all the available credentials
-02 Log out operations using all valid credentials
-03 Main page item images should be correct
-04 Check Login response time

Chart feature
-------------
-01 Buy all items available, complete the checkout, verify that the total price plus shipping is correct.

### How to run it
This project was developed to run using Chrome webdriver version v124_0_6367_155 for Windows 64. Before to execute the 
project verify if your browser version is compatible to this Chromedriver version. 
You can check it here: https://googlechromelabs.github.io/chrome-for-testing/
To update the webdriver version, just download the correct wedriver version for your Chrome browser, unzip it and replace 
the chromedriver.exe in /src/test/resources/drivers. 

You can run it using the maven command: "mvn test". 

### Test results-Report
After the test execution you can check the report located in ./GeneratedReport/index.html.


