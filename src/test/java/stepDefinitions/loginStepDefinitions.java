package stepDefinitions;

import enums.ItemDescription;
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
import java.util.Locale;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class loginStepDefinitions extends TestRunner{
    public WebDriver driverMob;
    private Scenario scenario;
    private Long loadtime;
    //static int estimatedTime=10000000;

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
        //Check if there is any error message displayed related to the user account state
        assertEquals(driverMob.findElements(By.xpath(Xpath.LoginErrorMSG.label)).size(), 0);
    }
    @When("^I try to login with these values: \"([^\"]*)\" and \"([^\"]*)\" and check response time$")
    public void iTryToLoginWithTheseValuesUserNameAndPasswordAndCheckResponseTime(String username, String password) {
        loginUser(username, password);
        loadtime = (Long)((JavascriptExecutor)driverMob).executeScript("return performance.timing.loadEventEnd - performance.timing.navigationStart;");
    }
    @Then("^Response time should not be longer than 30 miliseconds$")
    public void responseTimeShouldNotBeLong() {
        assertTrue(loadtime<=30);
    }

    @Then("^Main page should appear$")
    public void waitForMainPage(){
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
        List<WebElement> allItems=driverMob.findElements(By.xpath(Xpath.InventoryItem.label));
        for (WebElement item:allItems){
            itemTitle=item.findElement(By.xpath("//div[@class='inventory_item_name ']")).getAttribute("innerText");
            itemSRC=item.findElement(By.xpath(Xpath.ItemIMGs.label)).getAttribute("src");
            itemSRC=itemSRC.substring(itemSRC.lastIndexOf("/")+1);
            switch(itemTitle){
                case "Sauce Labs Backpack":
                    assertEquals(itemSRC,"sauce-backpack-1200x1500.0a0b85a3.jpg");
                    break;
                case "Sauce Labs Bike Light":
                    assertEquals(itemSRC,"bike-light-1200x1500.37c843b0.jpg");
                    break;
                case "Sauce Labs Bolt T-Shirt":
                    assertEquals(itemSRC,"bolt-shirt-1200x1500.c2599ac5.jpg");
                    break;
                case "Sauce Labs Fleece Jacket":
                    assertEquals(itemSRC,"sauce-pullover-1200x1500.51d7ffaf.jpg");
                    break;
                case "Sauce Labs Onesie":
                    assertEquals(itemSRC,"red-onesie-1200x1500.2ec615b2.jpg");
                    break;
                case "STest.allTheThings() T-Shirt (Red)":
                    assertEquals(itemSRC,"red-tatt-1200x1500.30dadef4.jpg");
                    break;
            }
            pageScreenshot();
        }
    }

    @And("^I check item text$")
    public void iCheckItemText() {
        String itemTitle;
        String itemDescription;
        List<WebElement> allItems=driverMob.findElements(By.xpath(Xpath.InventoryItem.label));
        for (WebElement item:allItems){
            itemTitle=item.findElement(By.xpath("//div[@class='inventory_item_name ']")).getAttribute("innerText").toUpperCase(Locale.ROOT).replace(" ","");
            itemDescription=item.findElement(By.xpath("//div[@class='inventory_item_desc']")).getAttribute("innerText").toUpperCase(Locale.ROOT).replace(" ","");
            switch(itemTitle){
                case "Sauce Labs Backpack":
                    assertEquals(itemTitle, ItemDescription.BackPackTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.BackpackDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
                case "Sauce Labs Bike Light":
                    assertEquals(itemTitle, ItemDescription.BikeLightTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.BikeLightDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
                case "Sauce Labs Bolt T-Shirt":
                    assertEquals(itemTitle, ItemDescription.BoltTShirtTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.BoltTShirtDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
                case "Sauce Labs Fleece Jacket":
                    assertEquals(itemTitle, ItemDescription.FleeceJacketTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.FleeceJacketDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
                case "Sauce Labs Onesie":
                    assertEquals(itemTitle, ItemDescription.OnesieTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.OnesieDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
                case "STest.allTheThings() T-Shirt (Red)":
                    assertEquals(itemTitle, ItemDescription.RedTShirtTitle.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    assertEquals(itemDescription, ItemDescription.RedTShirtDescription.label.toUpperCase(Locale.ROOT).replace(" ",""));
                    break;
            }
        }
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
