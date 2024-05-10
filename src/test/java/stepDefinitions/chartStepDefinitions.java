package stepDefinitions;

import org.openqa.selenium.*;
import runners.RunnerTest;
import enums.Xpath;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.Scenario;

import java.util.List;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import static org.testng.Assert.assertEquals;
public class chartStepDefinitions {

    public WebDriver driverMob;
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @When("^I login as username: \"([^\"]*)\" and password: \"([^\"]*)\"$")
    public void loginUser(String username, String password) {
        driverMob=RunnerTest.getDriver();
        driverMob.findElement(By.xpath(Xpath.LoginUsername.label)).sendKeys(username);
        driverMob.findElement(By.xpath(Xpath.LoginPassword.label)).sendKeys(password);
        driverMob.findElement(By.xpath(Xpath.LoginButton.label)).click();
    }

    @And("^I add one of every single item in the main page$")
    public void addItemsToChart(){
        //For every item item displayed I click on its add button.
        List<WebElement> itemsToAdd = driverMob.findElements(By.xpath(Xpath.ItemButtons.label));
        for (WebElement itemToAdd : itemsToAdd) itemToAdd.click();
        driverMob.findElement(By.xpath(Xpath.ShoppingCart.label)).click();
    }

    @Then("^All added items should be displayed$")
    public void checkItemsAdded(){
        //THis procedure checks if the expected item names of the listItemNames array are the same names of the displayed items.
        String itemName;
        //Expected items
        String[] listItemNames ={"Sauce T-Shirt (Red)", "Sauce Labs Onesie", "Sauce Labs Fleece Jacket", "Sauce Labs Bolt T-Shirt",
        "Sauce Labs Bike Light", "Sauce Labs Backpack"};
        //Item flags, every item flag is set to true when the the item has been processed.
        boolean tShirtRed=false, onesie=false, fleeceJacket=false, boltTShirt=false, bikeLight=false,backPack=false;
        //List of items displayed in the page
        List<WebElement> itemsAdded = driverMob.findElements(By.xpath((Xpath.ItemsAddedNames.label)));
        for(WebElement itemAdded:itemsAdded) {
            //I get the name of the item
            itemName = itemAdded.getAttribute("innerText");
            int j;
            for (j = 0; j < listItemNames.length; j++) {
                //If the item name of the page is the same to one of my expected names, I set the flag of that item to true.
                if (itemName.equals(listItemNames[j]))
                    break;
            }
            switch (j) {
                case 0 -> tShirtRed = true;
                case 1 -> onesie = true;
                case 2 -> fleeceJacket = true;
                case 3 -> boltTShirt = true;
                case 4 -> bikeLight = true;
                case 5 -> backPack = true;
            }
        }
        //Now I assert that all the item flags are true. This is that all of items have been processed and displayed.
        assert tShirtRed&&onesie&&fleeceJacket&&boltTShirt&&bikeLight&&backPack: "--ERROR--: Item name not valid";
        //assertTrue("--ERROR--: Item name not valid",tShirtRed&&onesie&&fleeceJacket&&boltTShirt&&bikeLight&&backPack);
        //Otherwise, I take an screenshot for the report.
        if (!(tShirtRed&&onesie&&fleeceJacket&&boltTShirt&&bikeLight&&backPack)) {
            scenario.log("--ERROR--: Item name not valid");
            pageScreenshot();
        }
    }

    @And("^I do a checkout of the selected items$")
    public void checkout(){
        //This procedure check the page is requesting the mandatory data and displaying the error messaged in case the text boxes are not filled.
        //Click on checkout button
        driverMob.findElement(By.xpath(Xpath.Checkout.label)).click();
        //Check the error messages are displayed for missing information
        driverMob.findElement(By.xpath(Xpath.Continue.label)).click();
        assert driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: First Name is required"): "--ERROR--: Missing name but no error message displayed.";
        //assertTrue("--ERROR--: Missing name but no error message displayed.",driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: First Name is required"));
        //Set First Name and check error message related to last name is displayed
        driverMob.findElement(By.xpath(Xpath.FirstName.label)).sendKeys("Juan");
        driverMob.findElement(By.xpath(Xpath.Continue.label)).click();
        assert driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: Last Name is required"): "--ERROR--: Missing last name but no error message displayed.";
        //assertTrue("--ERROR--: Missing last name but no error message displayed.", driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: Last Name is required"));
        //Set Last Name and check error message related to last name is displayed
        driverMob.findElement(By.xpath(Xpath.LastName.label)).sendKeys("Joya");
        driverMob.findElement(By.xpath(Xpath.Continue.label)).click();
        assert driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: Postal Code is required"): "--ERROR--: Missing zip code but no error message displayed.";
        //assertTrue("--ERROR--: Missing zip code but no error message displayed.", driverMob.findElement(By.xpath(Xpath.CheckoutErrorMessage.label)).getAttribute("innerText").equals("Error: Postal Code is required"));
        //Set the zip code and check the checkout is performed
        driverMob.findElement(By.xpath(Xpath.ZipCode.label)).sendKeys("KT13 9UT");
        driverMob.findElement(By.xpath(Xpath.Continue.label)).click();
    }

    public void pageScreenshot(){
        byte[] screenshot = ((TakesScreenshot) driverMob).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot,"png", "image");
    }

    @And("^The total price plus shipping should be correct and the purchase is completed$")
    public void theTotalPricePlusShippingShouldBeCorrectAndThePurchaseIsCompleted() {
        double expectedTotalPrice=0;
        final double shippingCost=10.40;
        //Check the prices of all the purchased items and add their values.
        List<WebElement> itemPrices=driverMob.findElements(By.xpath(Xpath.ItemPrice.label));
        for (WebElement itemPrice : itemPrices)
            //I get the string from the price webelement, remove the "$", convert it to double and then add it to the variable expectedTotalPrice
            expectedTotalPrice=expectedTotalPrice + Double.parseDouble(itemPrice.getAttribute("innerText").substring(itemPrice.getAttribute("innerText").indexOf("$")+1));
        //Then I add the shipping cost
        expectedTotalPrice=expectedTotalPrice+shippingCost;
        //Then I compare the expected price to the total price displayed.
        String totalPrice=driverMob.findElement(By.xpath(Xpath.TotalPrice.label)).getAttribute("innerText");
        assertEquals(Double.parseDouble(totalPrice.substring(totalPrice.indexOf("$")+1)),expectedTotalPrice);


        //Finally, I click on "Finish" button
        driverMob.findElement(By.xpath(Xpath.FinnishButton.label)).click();
        //assertEquals(driverMob.findElement(By.xpath(Xpath.PurchaseConfirmation.label)).getAttribute("innerText"),"Thank you for your order!");
        driverMob.findElement(By.xpath(Xpath.BackHome.label)).click();
    }

}
