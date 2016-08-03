package com.gramis.gdgtestapp.model;

/**
 * Created by GRamis on 13.07.2016.
 */
public class City {

    private Integer id;

    private String name;

    private int image;

    private boolean box;

    public City(Integer id, String name, int image, boolean box) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.box = box;
    }

    public City() {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isBox() {
        return box;
    }

    public void setBox(boolean box) {
        this.box = box;
    }
}
