package app.data;

public class Extra {
    private String id;
    private String name;
    private double price;
    private String imagepath;

    public Extra(String id, String name, double price, String imagepath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagepath = imagepath;
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

    @Override
    public String toString() {
        return "Extra{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imagepath='" + imagepath + '\'' +
                '}';
    }
}
