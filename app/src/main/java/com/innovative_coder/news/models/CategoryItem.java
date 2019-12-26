package com.innovative_coder.news.models;

public class CategoryItem {

    public String name;
    public  String imagelink;

    public CategoryItem() {
    }

    public CategoryItem(String name, String imagelink) {
        this.name = name;
        this.imagelink = imagelink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }
}
