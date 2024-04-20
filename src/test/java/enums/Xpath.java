package enums;

public enum Xpath {

    LoginUsername("//input[@id='user-name']"),
    LoginPassword("//input[@id='password']"),
    LoginButton("//input[@id='login-button']"),
    LoginErrorMSG("//div[@class='error-message-container error']"),
    Logout("//a[@id='logout_sidebar_link']"),
    BurgerMenu("//button[@id='react-burger-menu-btn']"),
    ItemButtons("//button[contains(@class,'btn_inventory')]"),
    ItemsAddedNames("//div[@class='inventory_item_name']"),
    InventoryItem("//div[@class='inventory_item']"),
    ItemIMGs("//img[@class='inventory_item_img']"),
    ShoppingCart("//div[@class='shopping_cart_container']"),
    Checkout("//button[@id='checkout']"),
    Continue("//input[@id='continue']"),
    CheckoutErrorMessage("//div[@class='error-message-container error']"),
    FirstName("//input[@id='first-name']"),
    LastName("//input[@id='last-name']"),
    ZipCode("//input[@id='postal-code']"),
    ItemPrice("//div[@class='inventory_item_price']"),
    TotalPrice("//div[@class='summary_total_label']"),
    FinnishButton("//button[@id='finish']"),
    PurchaseConfirmation("//h2[@class='complete-header']"),
    BackHome("//button[@id='back-to-products']");

    public final String label;

    Xpath(String label) {
        this.label = label;
    }
}