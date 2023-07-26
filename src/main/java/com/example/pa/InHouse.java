package com.example.pa;
/**
 *
 * @author Nicolas Hardin
 *
 */

public class InHouse extends Part{

    private int machineId;

    /**
     * constructor
     * @param id in house part id
     * @param name in house part name
     * @param price in house part price
     * @param stock in house part stock
     * @param min in house part minimum stock
     * @param max in house part maximum stock
     */
    public InHouse(int id, String name, double price, int stock, int min, int max) {

        super(id, name, price, stock, min, max);

    }

    /**
     * @param machineId the machine id to set
     */
    public void setMachineId(int machineId){

        this.machineId = machineId;

    }

    /**
     * @return the machine id
     */
    public int getMachineId(){

        return machineId;

    }
}
