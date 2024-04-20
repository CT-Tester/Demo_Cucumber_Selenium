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
        List<WebElement> itemsToAdd = driverMob.findElements(By.xpath(Xpath.ItemButtons.label));
        for(int i=0; i<itemsToAdd.size(); i++)
            itemsToAdd.get(i).click();
        driverMob.findElement(By.xpath(Xpath.ShoppingCart.label)).click();
    }

    @Then("^All added items should be displayed$")
    public void checkItemsAdded(){
        String itemName;
        String[] listItemNames ={"Sauce T-Shirt (Red)", "Sauce Labs Onesie", "Sauce Labs Fleece Jacket", "Sauce Labs Bolt T-Shirt",
        "Sauce Labs Bike Light", "Sauce Labs Backpack"};
        boolean tShirtRed=false, onesie=false, fleeceJacket=false, boltTShirt=false, bikeLight=false,backPack=false;
        List<WebElement> itemsAdded = driverMob.findElements(By.xpath((Xpath.ItemsAddedNames.label)));
        for(int i=0; i<itemsAdded.size(); i++) {
            itemName = itemsAdded.get(i).getAttribute("innerHtml");
            int j;
            for (j = 0; j < listItemNames.length; j++) {
                if (itemName.equals(listItemNames[j]))
                    break;
            }
            switch (j) {
                case 0:
                    tShirtRed = true;
                    break;
                case 1:
                    onesie = true;
                    break;
                case 2:
                    fleeceJacket = true;
                    break;
                case 3:
                    boltTShirt = true;
                    break;
                case 4:
                    bikeLight = true;
                    break;
                case 5:
                    backPack = true;
                    break;
            }
        }
        if (!(tShirtRed&&onesie&&fleeceJacket&&boltTShirt&&bikeLight&&backPack)) {
            scenario.isFailed();
            scenario.log("--ERROR--: Item name not valid");
            pageScreenshot();
        }
    }

    @And("^I do a checkout of the selected items$")
    public void checkout(){
        //Click on checkout button
        driverMob.findElement(By.xpath(Xpath.Checkout.label)).click();

    }

    public void pageScreenshot(){
        byte[] screenshot = ((TakesScreenshot) driverMob).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"png", "image");
    }
}
