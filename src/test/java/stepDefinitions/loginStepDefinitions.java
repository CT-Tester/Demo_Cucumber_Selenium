package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import runners.TestRunner;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import enums.Xpath;

import java.time.Duration;

public class loginStepDefinitions extends TestRunner{
    public WebDriver driverMob;
    private Scenario scenario;

    @Given ("^I start a chrome session and the url is \"([^\"]*)\"$")
    public void startApp(String url) throws Exception
    {
        driverMob = TestRunner.getDriver();
        driverMob.manage().window().setPosition(new Point(2000, 0));
        driverMob.manage().window().maximize();
        driverMob.get(url);
        Thread.sleep(2000);
    }

    @When("^I try to login with these values: \"([^\"]*)\" and \"([^\"]*)\"$")
    public void loginUser(String username, String password) {
        driverMob.findElement(By.xpath(Xpath.LoginUsername.label)).sendKeys(username);
        driverMob.findElement(By.xpath(Xpath.LoginPassword.label)).sendKeys(password);
        driverMob.findElement(By.xpath(Xpath.LoginButton.label)).click();
    }

    @Then("^Main page should appear$")
    public void waitForMainPage(){
        WebDriverWait wait = new WebDriverWait(driverMob, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(driverMob.findElement(By.xpath(Xpath.BurgerMenu.label))));
    }


    public void pageScreenshot(){
        byte[] screenshot = ((TakesScreenshot) driverMob).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"png", "image");
    }
    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @AfterSuite
    public void AfterTest(){
        try{
            driverMob.close();
        } catch(Exception e){
            driverMob.quit();
        }
    }


}
