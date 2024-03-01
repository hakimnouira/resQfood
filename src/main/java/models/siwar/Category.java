package models;

public class Category {
    private int dcategory_id;
    private String dcategory_name, dcategory_description;
    public Category(){}

    public Category(int dcategory_id, String dcategory_name, String dcategory_description) {
        this.dcategory_id = dcategory_id;
        this.dcategory_name = dcategory_name;
        this.dcategory_description = dcategory_description;
    }

    public Category(String dcategory_name, String dcategory_description) {
        this.dcategory_name = dcategory_name;
        this.dcategory_description = dcategory_description;
    }

    public int getDcategory_id() {
        return dcategory_id;
    }

    public void setDcategory_id(int dcategory_id) {
        this.dcategory_id = dcategory_id;
    }

    public String getDcategory_name() {
        return dcategory_name;
    }

    public void setDcategory_name(String dcategory_name) {
        this.dcategory_name = dcategory_name;
    }

    public String getDcategory_description() {
        return dcategory_description;
    }

    public void setDcategory_description(String dcategory_description) {
        this.dcategory_description = dcategory_description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "dcategory_id=" + dcategory_id +
                ", dcategory_name='" + dcategory_name + '\'' +
                ", dcategory_description='" + dcategory_description + '\'' +
                '}';
    }
}
