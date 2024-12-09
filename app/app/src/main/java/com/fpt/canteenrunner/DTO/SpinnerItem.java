package com.fpt.canteenrunner.DTO;

public class SpinnerItem {
    private String id;
    private String name;

    public SpinnerItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
