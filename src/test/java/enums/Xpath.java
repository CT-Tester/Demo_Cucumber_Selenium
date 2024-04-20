package enums;

public enum Xpath {

    LoginUsername("//input[@id='user-name']"),
    LoginPassword("//input[@id='password']"),
    LoginButton("//input[@id='login-button']"),
    BurgerMenu("//button[@id='react-burger-menu-btn']"),
    ItemButtons("//button[contains(@class,'btn_inventory')]"),
    ItemsAddedNames("//div[@class='inventory_item_name']"),
    ShoppingCart("//div[@class='shopping_cart_container']"),
    Checkout("//button[@id='checkout']");

    public final String label;

    Xpath(String label) {
        this.label = label;
    }
}