package app.data;

import java.util.List;

public class Meal {
    private String id;
    private String name;
    private String category;
    private double price;
    private String imagepath;
    private List<Extra> availableExtras;

    public Meal(String id, String name, String category, double price, String imagepath, List<Extra> availableExtras) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imagepath = imagepath;
        this.availableExtras = availableExtras;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public List<Extra> getAvailableExtras() {
        return availableExtras;
    }

    public void setAvailableExtras(List<Extra> availableExtras) {
        this.availableExtras = availableExtras;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", imagepath='" + imagepath + '\'' +
                ", extras=" + availableExtras +
                '}';
    }
}
