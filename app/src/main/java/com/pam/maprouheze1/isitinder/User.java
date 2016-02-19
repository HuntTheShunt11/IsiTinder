package com.pam.maprouheze1.isitinder;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by maprouheze1 on 27/01/2016.
 */
public class User {
    public String gender;
    public Name name;
    public Location location;
    public String email;
    public String username;
    public String password;
    public int dob;
    public Picture picture;

    public long getAge(){
        return (new Date().getTime()/1000 - dob)/31556952;
    }
    public Location getLocation(){ return location;}
    public String getEmail() { return email;}


}