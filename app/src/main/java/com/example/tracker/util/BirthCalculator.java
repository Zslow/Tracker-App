package com.example.tracker.util;

import java.util.Calendar;

public class BirthCalculator {

    public static String getYear(String age){


        return  Integer.toString(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(age));

    }
}
