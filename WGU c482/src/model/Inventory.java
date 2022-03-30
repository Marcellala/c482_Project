package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Random;

public class Inventory {

    /**
     * A list of all the parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * adds part to inventory and generates unique ID
     */
    public static void addPart(Part part){
       Random rand = new Random();
       int ID = rand.nextInt(1000);
       boolean good = false;
       while (!good)
       {
           Part thisPartLookup = lookupPart(ID);
           if (thisPartLookup == null)
           {
               good = true;
           }
       }
       part.setId(ID);
       allParts.add(part);

    }

    /**
     * the getter to get all parts in the inventory list
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /**
     * A list of all the products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds product to inventory and generates unique partID that cannot be modified
     */
    public static void addProduct(Product product){
        Random rand = new Random();
        int ID = rand.nextInt(1000);
        boolean good = false;
        while (!good)
        {
            Product thisProductlookup = lookupProduct(ID);
            if (thisProductlookup == null)
            {
                good = true;
            }
        }
        product.setId(ID);
        allProducts.add(product);

    }
    /**
     * the getter to get all products in inventory list
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * Adds test data to parts Table
     */
    public static void addTestData(){
        InHouse i = new InHouse(1,"Brakes",15,10,1, 11,123);
        Inventory.addPart(i);

        Outsourced o = new Outsourced(2,"Wheel",11, 16,1,17,"Acme Inc");
        Inventory.addPart(o);

        Product p = new Product(1000,"Giant Bike",299.99,5,1,6);
        Inventory.addProduct((p));

        Product p1 = new Product(1001,"Tricycle",99.99,3,1,4);
        Inventory.addProduct(p1);
    }

    /**
     * Method to search parts list by name
     * @param partialName
     * @return namedParts
     */
    public static ObservableList<Part> lookupPart(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /**
     * Method to search parts list by part ID
     * @param partID
     * @return p
     */
    public static Part lookupPart(int partID) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        return null;
    }

    /**
     * Method to search products list by name
     * @param partialName
     * @return namedProducts
     */
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /**
     * Method to search products list by product ID
     * @param productId
     * @return p
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Method to update parts in list of parts
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        for (Part p : allParts) {
            if (p.getId() == selectedPart.getId()) {
                allParts.set(index, selectedPart);
            }
        }
    }

    /**
     * Method to update products in list of products
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index,Product selectedProduct){
        for (Product p : allProducts) {
            if (p.getId() == selectedProduct.getId()) {
                allProducts.set(index, selectedProduct);
            }
        }
    }

    /**
     * removes selected part from parts list
     * @param selectedPart
     */
    public static void deletePart(Part selectedPart){

    }

    /**
     * removes selected product from parts list
     * @param selectedProduct
     */
    public static void deleteProduct(Product selectedProduct){

    }

}
