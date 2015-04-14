package com.example.artemstafeev.trojanow;

/**
 * Created by artemstafeev on 3/25/15.
 * Defines the main class USer
 */
public class User {
    String email;
    String username;
    String password;
    /*Constructor*/
    public User(String e, String u, String p) {
        email = e;
        username = u;
        password = p;
    }
    //methods
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String e)
    {
        email=e;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String p)
    {
        password=p;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String u)
    {
        username=u;
    }
}
