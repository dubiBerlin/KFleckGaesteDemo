package com.dubravko.kfleck.kfleckdemo.model;

/**
 * Created by dp on 21.11.2017.
 * This class keeps the position and the state of the star button
 *
 *
 */
public class StatusStarBtn {

    private int position;
    private int status;

    public StatusStarBtn(){

    }

    public StatusStarBtn(int pos, int status){
        this.position = pos;
        this.status = status;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
