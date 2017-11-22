package com.dubravko.kfleck.kfleckdemo.shared;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by dp on 16.11.2017.
 */
public class Helper {

    // round the double value on two numbers after comma
    public static double roundDouble(double value){
        return value = Math.round(100.0 * value) / 100.0;
    }

    public static void printMap(Map mp) {
        System.out.println("************ HELPER printMap() ***************");
        Iterator it = mp.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

}
