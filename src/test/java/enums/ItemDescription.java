package enums;

public enum ItemDescription {
    BackPackTitle("Sauce Labs Backpack"),
    BackpackDescription("Carry all the things with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection."),
    BikeLightTitle("Sauce Labs Bike Light"),
    BikeLightDescription("A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included."),
    BoltTShirtTitle("Sauce Labs Bolt T-Shirt"),
    BoltTShirtDescription("Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt."),
    FleeceJacketTitle("Sauce Labs Fleece Jacket"),
    FleeceJacketDescription("It's not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office."),
    OnesieTitle("Sauce Labs Onesie"),
    OnesieDescription("Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel."),
    RedTShirtTitle("T-Shirt (Red)"),
    RedTShirtDescription("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
    public final String label;

    ItemDescription(String label) {
        this.label = label;
    }
}
