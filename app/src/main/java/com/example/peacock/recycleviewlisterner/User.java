package com.example.peacock.recycleviewlisterner;

/**
 * Created by peacock on 26/5/17.
 */

public class User {

    private String name;
    private String time, content;

    public User() {

    }

    //getting content value
    public String getContent() {
        return content;
    }

    //setting content value
    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
