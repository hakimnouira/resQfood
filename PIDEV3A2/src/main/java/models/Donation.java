package models;
import javafx.beans.property.*;

public class Donation {
    private final IntegerProperty donation_id = new SimpleIntegerProperty();
    private final StringProperty donation_category = new SimpleStringProperty();
    private final DoubleProperty donation_amount = new SimpleDoubleProperty();
    private final StringProperty food_name = new SimpleStringProperty();
    private final DoubleProperty food_quantity = new SimpleDoubleProperty();
    private final IntegerProperty dcategory_id = new SimpleIntegerProperty();
    private final IntegerProperty udonor_id = new SimpleIntegerProperty();

    public Donation() {
    }

    public Donation(int donation_id, String donation_category, double donation_amount, String food_name, double food_quantity, int dcategory_id, int udonor_id) {
        this.donation_id.set(donation_id);
        this.donation_category.set(donation_category);
        this.donation_amount.set(donation_amount);
        this.food_name.set(food_name);
        this.food_quantity.set(food_quantity);
        this.dcategory_id.set(dcategory_id);
        this.udonor_id.set(udonor_id);
    }
    public Donation(String donation_category, double donation_amount, int dcategory_id, int udonor_id) {
        this.donation_category.set(donation_category);
        this.donation_amount.set(donation_amount);
        this.dcategory_id.set(dcategory_id);
        this.udonor_id.set(udonor_id);
    }
    public Donation(String donation_category, double donation_amount, String food_name, double food_quantity, int dcategory_id, int udonor_id) {
        this.donation_category.set(donation_category);
        this.donation_amount.set(donation_amount);
        this.food_name.set(food_name);
        this.food_quantity.set(food_quantity);
        this.dcategory_id.set(dcategory_id);
        this.udonor_id.set(udonor_id);
    }

    public IntegerProperty donation_idProperty() {
        return donation_id;
    }

    public int getDonation_id() {
        return donation_id.get();
    }

    public void setDonation_id(int donation_id) {
        this.donation_id.set(donation_id);
    }

    public StringProperty donation_categoryProperty() {
        return donation_category;
    }

    public String getDonation_category() {
        return donation_category.get();
    }

    public void setDonation_category(String donation_category) {
        this.donation_category.set(donation_category);
    }

    public DoubleProperty donation_amountProperty() {
        return donation_amount;
    }

    public double getDonation_amount() {
        return donation_amount.get();
    }

    public void setDonation_amount(double donation_amount) {
        this.donation_amount.set(donation_amount);
    }

    public StringProperty food_nameProperty() {
        return food_name;
    }

    public String getFood_name() {
        return food_name.get();
    }

    public void setFood_name(String food_name) {
        this.food_name.set(food_name);
    }

    public DoubleProperty food_quantityProperty() {
        return food_quantity;
    }

    public double getFood_quantity() {
        return food_quantity.get();
    }

    public void setFood_quantity(double food_quantity) {
        this.food_quantity.set(food_quantity);
    }

    public IntegerProperty dcategory_idProperty() {
        return dcategory_id;
    }

    public int getDcategory_id() {
        return dcategory_id.get();
    }

    public void setDcategory_id(int dcategory_id) {
        this.dcategory_id.set(dcategory_id);
    }

    public IntegerProperty udonor_idProperty() {
        return udonor_id;
    }

    public int getUdonor_id() {
        return udonor_id.get();
    }

    public void setUdonor_id(int udonor_id) {
        this.udonor_id.set(udonor_id);
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donation_id=" + donation_id +
                ", donation_category='" + donation_category + '\'' +
                ", donation_amount=" + donation_amount +
                ", food_name='" + food_name + '\'' +
                ", food_quantity=" + food_quantity +
                ", dcategory_id=" + dcategory_id +
                ", udonor_id=" + udonor_id +
                '}';
    }


}