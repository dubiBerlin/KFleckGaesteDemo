package com.dubravko.kfleck.kfleckdemo.model;

/**
 * Created by dp on 14.11.2017.
 */
public class Zutat {

    private String name;
    private String liter;
    private int status;
    private int position;
    private String activityName;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
