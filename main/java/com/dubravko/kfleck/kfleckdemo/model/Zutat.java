package com.dubravko.kfleck.kfleckdemo.model;

/**
 * Created by dp on 14.11.2017.
 */
public class Zutat {

    private String name;
    private String liter;

    public Zutat(){}

    public Zutat(String name, String liter) {
        this.name = name;
        this.liter = liter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }


}
