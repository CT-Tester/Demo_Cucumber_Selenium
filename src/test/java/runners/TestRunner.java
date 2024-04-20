package runners;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.runner.RunWith;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/Login.feature"},
        glue= {"stepDefinitions"},
        plugin = { "pretty", "html:Test_report", "json:target/cucumber-report/cucumber.json"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests{
    public TestNGCucumberRunner testNGCucumberRunner;
    //public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();

    //Getter and setter of the WebDriver
    public static WebDriver getDriver() {
        return dr.get();
    }
    public void setWebDriver(WebDriver driver) {
        dr.set(driver);
    }


    public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>() {
        protected WebDriver initialValue() {

            boolean checkDriver = false;
            RemoteWebDriver driver;
            try {
                //Driver for Chrome 123.0.6312.123(chrome driver v123.0.6312.122) and Windows operative system
                System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\drivers\\chromedriver.exe");
                try {
                    checkDriver = new File(".\\src\\test\\resources\\drivers\\chromedriver.exe").isFile();
                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
                if (!checkDriver)
                    System.out.println("ChromeDriver doesn´t  exist");

                //new DesiredCapabilities();
                System.out.println(System.getProperties().values());
                ChromeOptions options = new ChromeOptions();

                //This capability is set to true to prevent the certificate warning pop up messages
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WIN10);

                options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                options.addArguments("--disable-impl-side-painting", "--enable-gpu-rasterization", "--force-gpu-rasterization", "--whitelist-ips=\"\"");
                //This line is added to avoid the pop-up requesting permission to use the microphone.
                options.addArguments("--allow-file-access-from-files", "--use-fake-device-for-media-stream", "--use-fake-ui-for-media-stream");

                driver = new ChromeDriver();

            } catch (Exception e) {
                e.printStackTrace();
                throw new AssertionError("Cannot create Remote Webdriver driver");
            }
            return driver;
        }
    };
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }


    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    //public void feature(CucumberFeatureWrapper cucumberFeature, String myFeatureFile) throws IOException {
    public void feature(CucumberFeatureWrapper cucumberFeature) {

        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
        boolean fileClosed = false;

        //First, I check the file ./target/cucumber-report/cucumber.json is closed.
        FileOutputStream jsonFile = null; //".//target//cucumber-report//Cucumber.json"
        while (!fileClosed)
        {
            try {
                // Make sure that the output stream is in Append mode. Otherwise you will
                // truncate your file, which probably isn't what you want to do :-)
                jsonFile = new FileOutputStream(".//target//cucumber-report//Cucumber.json", true);
                // -> file was closed
            } catch(Exception e) {
                e.printStackTrace();
                fileClosed = true;
            } finally {
                if(jsonFile != null) {
                    try {
                        jsonFile.close();
                        fileClosed = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        fileClosed = true;
                    }

                }
            }
        }

        //Second, I execute maven command to generate the test report.
        Process p=Runtime.getRuntime().exec("cmd.exe /c mvn cluecumber-report:reporting");
        p.waitFor();
        int exitValue = p.exitValue();
        if (exitValue == 1){
            String linee;
            InputStream stackTrace= p.getErrorStream();
            InputStreamReader isrerror = new InputStreamReader(stackTrace);
            BufferedReader bre = new BufferedReader(isrerror);
            while ((linee = bre.readLine()) != null) {
                System.out.println(linee);
            }
        }
    }

    @AfterTest(alwaysRun = true)
    public void tearApp() {
        try{
            getDriver().close();
        } catch (Exception e){
            getDriver().quit(); //This is done to avoid the timeout errors after the test is completed, due the app is not closed.
        }
    }
}
