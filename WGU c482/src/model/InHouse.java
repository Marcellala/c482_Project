package model;

public class InHouse extends Part {

    /**
     *Machine ID for part
     */
    private int machineId;

    /**
     * Constructor for new instance of an InHouse part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter for Machine ID
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }

    /**
     * Getter for Machine ID
     */
    public int getMachineId() {

        return machineId;
    }
}

