package com.example.drrbni.Models;

public class Filters {

    private String employment = null;

    public Filters() {}

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public boolean hasEmployment() {
        return (employment != null);
    }

}
