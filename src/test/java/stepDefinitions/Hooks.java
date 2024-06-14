package stepDefinitions;

import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static stepDefinitions.loginStepDefinitions.driverMob;

public class Hooks extends runners.RunnerTest{
    //WebDriver Manager
    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterStep
    public void takeScreenshotOnFailure(Scenario scenario) {
    //I took an screenshot after every scenario failed
        if (scenario.isFailed()) {

            TakesScreenshot ts = (TakesScreenshot) driverMob;

            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
        }

    }
    @After
    public void takeScreenshotMainPageItems(Scenario scenario){
    //I took an screenshot of the main page after the scenario to check the item images is completed
        if(scenario.getName().equals("TC03_Login_check_main_page_elements_then_logout"))
        {
            TakesScreenshot ts = (TakesScreenshot) driverMob;

            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
        }
    }

    @AfterAll
    public static void before_or_after_all() throws Exception {
        //I close the chrome driver session
        try {
            driverMob.close();
        } catch (Exception e1) {
            try {
                driverMob.quit();
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        //I generate Cluecumber report in ${project.basedir}\GeneratedReport
        generateCluecumberRep();
    }
    private static void generateCluecumberRep() throws InterruptedException, IOException {
        //This procedure executes the maven command mvn "cluecumber-report:reporting" to generate the Cluecumber report
        // in ${project.basedir}\GeneratedReport
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec("cmd.exe /c mvn cluecumber-report:reporting");
        p.waitFor();
        int exitValue = p.exitValue();
        if (exitValue == 1){
            //Case the command has not ended correctly, get the error message and display it.
            String linee;
            InputStream stackTrace= p.getErrorStream();
            InputStreamReader isrerror = new InputStreamReader(stackTrace);
            BufferedReader bre = new BufferedReader(isrerror);
            while ((linee = bre.readLine()) != null) {
                System.out.println(linee);
            }
        }
    }
}
