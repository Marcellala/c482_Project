package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    /**
     * a list of associated parts for each product
     * @return
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * ID for product
     */
    private int id;

    /**
     * Name for product
     */
    private String name;

    /**
     * Price for product
     */
    private double price;

    /**
     * Stock for product
     */
    private int stock;

    /**
     * minimum level for product
     */
    private int min;

    /**
     * maximum level for product
     */
    private int max;

    /**
     * constructor for new instance of a product
     * @return
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
   }

    /**
     * defines the getters for product class
     * @return
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    /**
     * defines the setters for product class
     * @return
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * sets associated part
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = FXCollections.observableArrayList();
        this.associatedParts.addAll(associatedParts);
    }

    /**
     * Adds associated part to product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes associated part from product
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Getter for list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }



}
