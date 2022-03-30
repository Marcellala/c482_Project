package model;

public class Outsourced extends Part {

    /**
     * company name for part
     */
    private String companyName;

    /**
     * Constructor for new instance of an outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for company name
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }

    /**
     * Getter for company name
     */
    public String getCompanyName() {

        return companyName;
    }
}



