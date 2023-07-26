package com.example.pa;
/**
 *
 * @author Nicolas Hardin
 *
 */

public class Outsourced extends Part{

    private String companyName;

    /**
     * constructor
     * @param id the outsourced part id
     * @param name the outsourced part name
     * @param price the outsourced part price
     * @param stock the outsourced part stock
     * @param min the outsourced part minimum stock
     * @param max the outsourced part maximum stock
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {

        super(id, name, price, stock, min, max);;

    }

    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName){

        this.companyName = companyName;

    }

    /**
     * @return the company name
     */
    public String getCompanyName(){

        return companyName;

    }
}
