package com.dubravko.kfleck.kfleckdemo.model;

/**
 * Created by dp on 17.12.2017.
 */
public class RestlicheZutat {

    private String name;

    public RestlicheZutat(String titel) {
        this.name = titel;
    }

    public RestlicheZutat(){}

    public String getTitel() {
        return name;
    }

    public void setTitel(String titel) {
        this.name = titel;
    }
}
