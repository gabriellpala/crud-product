package model.dao;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private User user;

    public Product() {
        this(0);
    }

    public Product(int id) {
        this.id = id;
        setName("");
        setDescription("");
        setPrice(0.0);
        setUser(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
