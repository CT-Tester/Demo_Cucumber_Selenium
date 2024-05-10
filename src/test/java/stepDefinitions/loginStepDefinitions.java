package stepDefinitions;

import org.openqa.selenium.*;

import runners.RunnerTest;
import enums.Xpath;
import enums.ItemDescription;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.Scenario;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;

public class loginStepDefinitions {
    public static WebDriver driverMob;
    private Scenario scenario;
    private Long loadtime;
    //static int estimatedTime=10000000;

    @Given ("^I start a chrome session and the url is \"([^\"]*)\"$")
    public void startApp(String url) throws Exception
    {
        driverMob = RunnerTest.getDriver();
        driverMob.manage().window().setPosition(new Point(2000, 0));
        driverMob.manage().window().maximize();
        driverMob.get(url);
    }

    @When("^I try to login with these values: \"([^\"]*)\" and \"([^\"]*)\"$")
    public void loginUser(String username, String password) {
        driverMob.findElement(By.xpath(Xpath.LoginUsername.label)).sendKeys(username);
        driverMob.findElement(By.xpath(Xpath.LoginPassword.label)).sendKeys(password);
        driverMob.findElement(By.xpath(Xpath.LoginButton.label)).click();
        //Check if there is any error message displayed related to the user account state
        assertEquals(driverMob.findElements(By.xpath(Xpath.LoginErrorMSG.label)).size(), 0);
    }
    @When("^I try to login with these values: \"([^\"]*)\" and \"([^\"]*)\" and check response time$")
    public void iTryToLoginWithTheseValuesUserNameAndPasswordAndCheckResponseTime(String username, String password) {
        loginUser(username, password);
    }
    @Then("^Response time should not be longer than 100 miliseconds$")
    public void responseTimeShouldNotBeLong() {
        //I generate one timeout exception in case that the Burger Menu control takes longer than 100 miliseconds to be displayed.
        WebDriverWait wait=new WebDriverWait(driverMob, Duration.ofMillis(100));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Xpath.BurgerMenu.label)));
    }

    @Then("^Main page should appear$")
    public void waitForMainPage(){
        //I generate one timeout exception in case that the Burger Menu control takes longer than 10 seconds to be displayed.
        WebDriverWait wait = new WebDriverWait(driverMob, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(driverMob.findElement(By.xpath(Xpath.BurgerMenu.label))));
    }

    @Then("I do a logout")
    public void iDoALogout() {
        WebDriverWait wait=new WebDriverWait(driverMob, Duration.ofSeconds(10));
        //I click on upper left menu button (burger button)
        wait.until(ExpectedConditions.elementToBeClickable(driverMob.findElement(By.xpath(Xpath.BurgerMenu.label))));
        driverMob.findElement(By.xpath(Xpath.BurgerMenu.label)).click();
        //Then I logout
        wait.until(ExpectedConditions.elementToBeClickable(driverMob.findElement(By.xpath(Xpath.Logout.label))));
        driverMob.findElement(By.xpath(Xpath.Logout.label)).click();

    }

    @And("^I check item images$")
    public void iCheckItemImages() {
        String itemTitle;
        String itemSRC;
        //This procedure compares the image name of every single item to the expected image names.
        //I got all image names displayed.
        List<WebElement> allItems=driverMob.findElements(By.xpath(Xpath.InventoryItem.label));
        for (WebElement item:allItems){
            //I got item title
            itemTitle=item.findElement(By.xpath("//div[@class='inventory_item_name ']")).getAttribute("innerText");
            //I got image path and then extract the name.
            itemSRC=item.findElement(By.xpath(Xpath.ItemIMGs.label)).getAttribute("src");
            itemSRC=itemSRC.substring(itemSRC.lastIndexOf("/")+1);
            //For every single item title, I compare the expected image name to the one displayed.
            switch (itemTitle) {
                case "Sauce Labs Backpack" -> assertEquals(itemSRC, "sauce-backpack-1200x1500.0a0b85a3.jpg");
                case "Sauce Labs Bike Light" -> assertEquals(itemSRC, "bike-light-1200x1500.37c843b0.jpg");
                case "Sauce Labs Bolt T-Shirt" -> assertEquals(itemSRC, "bolt-shirt-1200x1500.c2599ac5.jpg");
                case "Sauce Labs Fleece Jacket" -> assertEquals(itemSRC, "sauce-pullover-1200x1500.51d7ffaf.jpg");
                case "Sauce Labs Onesie" -> assertEquals(itemSRC, "red-onesie-1200x1500.2ec615b2.jpg");
                case "STest.allTheThings() T-Shirt (Red)" -> assertEquals(itemSRC, "red-tatt-1200x1500.30dadef4.jpg");
            }
            //pageScreenshot(scenario);
        }
    }

    @And("^I check item text$")
    public void iCheckItemText() {
        String itemTitle;
        String itemDescription;
        //This procedure compare the item title and description to the expected values.
        //Those expected values are stored in the ItemDecription enum file.
        List<WebElement> allItems=driverMob.findElements(By.xpath(Xpath.InventoryItem.label));
        for (WebElement item:allItems){
            itemTitle=item.findElement(By.xpath("//div[@class='inventory_item_name ']")).getAttribute("innerText").toUpperCase(Locale.ROOT).replace(" ","");
            itemDescription=item.findElement(By.xpath("//div[@class='inventory_item_desc']")).getAttribute("innerText").toUpperCase(Locale.ROOT).replace(" ","");
            switch (itemTitle) {
                case "Sauce Labs Backpack" -> {
                    assertEquals(itemTitle, ItemDescription.BackPackTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.BackpackDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
                case "Sauce Labs Bike Light" -> {
                    assertEquals(itemTitle, ItemDescription.BikeLightTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.BikeLightDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
                case "Sauce Labs Bolt T-Shirt" -> {
                    assertEquals(itemTitle, ItemDescription.BoltTShirtTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.BoltTShirtDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
                case "Sauce Labs Fleece Jacket" -> {
                    assertEquals(itemTitle, ItemDescription.FleeceJacketTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.FleeceJacketDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
                case "Sauce Labs Onesie" -> {
                    assertEquals(itemTitle, ItemDescription.OnesieTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.OnesieDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
                case "STest.allTheThings() T-Shirt (Red)" -> {
                    assertEquals(itemTitle, ItemDescription.RedTShirtTitle.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                    assertEquals(itemDescription, ItemDescription.RedTShirtDescription.label.toUpperCase(Locale.ROOT).replace(" ", ""));
                }
            }
        }
    }

    /*public void pageScreenshot(Scenario scenario){
        byte[] screenshot = ((TakesScreenshot) driverMob).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"png", "image");
    }*/

}
