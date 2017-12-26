package com.dubravko.kfleck.kfleckdemo.model;

/**
 * Created by dp on 03.12.2017.
 */
public class GewaehlteZutat {

    private String name;
    private double liter;
    private int position;
    private String activityName;

    public GewaehlteZutat(){}

    public GewaehlteZutat(String name, double liter, int position, String activityName) {
        this.name = name;
        this.liter = liter;
        this.position = position;
        this.activityName = activityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
