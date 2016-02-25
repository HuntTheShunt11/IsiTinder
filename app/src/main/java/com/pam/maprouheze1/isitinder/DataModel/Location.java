package com.pam.maprouheze1.isitinder.DataModel;

/**
 * Created by maprouheze1 on 27/01/2016.
 */
public class Location {
    public String street;
    public String city;
    public String state;
    public String zip;

    public String toString(){
        return street +" "+ zip+" "+ city +" "+ state;
    }
}