package runners;

import java.io.File;


import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.cucumber.junit.CucumberOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;


@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
        glue="stepDefinitions",
        plugin={ "pretty", "html:CucumberReport/Report.html", "json:CucumberReport/Report.json", "junit:CucumberReport/Report.xml"},

        monochrome=true
)

public class RunnerTest {

    //Getter and setter of the WebDriver
    public static WebDriver getDriver() {
        return dr.get();
    }
    public void setWebDriver(WebDriver driver) {
        dr.set(driver);
    }

    public static ThreadLocal<WebDriver> dr = ThreadLocal.withInitial(() -> {

        boolean checkDriver = false;
        RemoteWebDriver driver;
        try {
            //Driver for Chrome 124.0.6367.119(chrome driver v124.0.6367.155) and Windows operative system
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
    });

}
