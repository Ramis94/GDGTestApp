package com.gramis.gdgtestapp.model;

/**
 * Created by GRamis on 13.07.2016.
 */
public class City {

    private Integer id;

    private String name;

    private String temperature;

    private boolean box;

    public City(Integer id, String name, String temperature, boolean box) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.box = box;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
