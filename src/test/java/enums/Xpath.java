package enums;

public enum Xpath {

    LoginUsername("//input[@id='user-name']"),
    LoginPassword("//input[@id='password']"),
    LoginButton("//input[@id='login-button']"),
    BurgerMenu("//button[@id='react-burger-menu-btn']"),
    ItemButtons("//button[contains(@id,'')]");

    public final String label;

    Xpath(String label) {
        this.label = label;
    }
}