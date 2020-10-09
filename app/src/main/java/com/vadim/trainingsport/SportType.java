package com.vadim.trainingsport;

public class SportType {
    private int id;
    private String name;
    private long date;

    public SportType(int id, String name, long date) {
        this.id=id;
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}