package com.mobilesmashers.Tasks;

public class Task {
    private int number;
    private String type;

    public Task(int number, String type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }
}
