import java.util.Date;

public class Medicine {
    private String name;
    private String manufacturer;
    private int quantity;
    private Date expiryDate;
    private double pricePerTablet;
    private MedicineCategory category;

    public Medicine(String name, String manufacturer, int quantity, Date expiryDate, double pricePerTablet, MedicineCategory category) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.pricePerTablet = pricePerTablet;
        this.category = category;
    }

    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Date getExpiryDate() { return expiryDate; }
    public double getPricePerTablet() { return pricePerTablet; }
    public MedicineCategory getCategory() { return category; }
}
