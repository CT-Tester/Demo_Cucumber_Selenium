package stepDefinitions;

import io.cucumber.java.en.And;
import org.openqa.selenium.*;
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
import java.util.List;

public class chartStepDefinitions {

    public WebDriver driverMob;
    private Scenario scenario;

    @When("^I login as username: \"([^\"]*)\" and password: \"([^\"]*)\"$")
    public void loginUser(String username, String password) {
        driverMob=loginStepDefinitions.getDriver();
        driverMob.findElement(By.xpath(Xpath.LoginUsername.label)).sendKeys(username);
        driverMob.findElement(By.xpath(Xpath.LoginPassword.label)).sendKeys(password);
        driverMob.findElement(By.xpath(Xpath.LoginButton.label)).click();
    }

    @And("^I add one of every single item in the main page$")
    public void addItemsToChart(){
        List<WebElement> itemsToAdd = driverMob.findElements(By.xpath())
    }
}
