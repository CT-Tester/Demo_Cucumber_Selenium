
Objective
---------
This project has been created to automate the tests of a fictional online shop (https://qa-challenge.codesubmit.io/). 
The tests have been automated using Selenium and Cucumber frameworks, and the test results are generated using Cluecumber 
reports.

Test plan 
---------
These are the functional test cases that have been implemented so far:

#### Login feature

- 01 Login operation using all the available credentials

- 02 Log out operations using all valid credentials

- 03 Main page item images should be correct

- 04 Check Login response time

#### Chart feature

- 01 Buy all items available, complete the checkout, verify that the total price plus shipping is correct.

History
-------------
- June 7th 2024
This project was developed to run using Chrome webdriver version v124_0_6367_155 for Windows 64. Before to execute the 
project verify if your browser version is compatible to this Chromedriver version.

You can check it here: https://googlechromelabs.github.io/chrome-for-testing/
To update the webdriver version, just download the correct wedriver version for your Chrome browser, unzip it and replace
the chromedriver.exe in /src/test/resources/drivers.

- June 13th 2024
We have modified the code to include Boni Garcia library to manage automatically Chrome Driver versions. This library 
sets up the correct version of the ChromeDriver according to the version of Chrome browser  and the operative 
system.
You can find more information about this library here: https://bonigarcia.dev/webdrivermanager/

How to run it
-------------
You can run it using the maven command: "mvn test". 

Test results-Report
-------------------
After the test execution you can check the report located in ./GeneratedReport/index.html.


