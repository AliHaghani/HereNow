package me.alihaghani.herenow;

import java.util.HashSet;

/**
 * Created by shivashisht on 2016-01-23.
 */
public class Contacts extends HashSet {
    private HashSet<String> numbers;

    public Contacts() {
        this.numbers = new HashSet<String>();
    }
    public void add(String phoneNo) {
        this.numbers.add(phoneNo);
    }
}
