package me.alihaghani.herenow;

import java.util.HashSet;

/**
 * Created by shivashisht on 2016-01-23.
 */
public class Contacts extends HashSet {
    private static HashSet<String> numbers;


    public static void add(String phoneNo) {
        if(numbers == null)
            numbers = new HashSet<String>();
        numbers.add(phoneNo);
    }

    public static HashSet<String> getNumbers(){
        return Contacts.numbers;
    }


}
