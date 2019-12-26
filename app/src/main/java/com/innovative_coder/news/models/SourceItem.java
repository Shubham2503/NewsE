package com.innovative_coder.news.models;

public class SourceItem {

    public String name;
    public  String imagelink;
    public String country,category,description,ic,id,language,url;

    public SourceItem() {
    }

    public SourceItem(String name, String imagelink, String country, String category, String description, String ic, String id, String language, String url) {
        this.name = name;
        this.imagelink = imagelink;
        this.country = country;
        this.category = category;
        this.description = description;
        this.ic = ic;
        this.id = id;
        this.language = language;
        this.url = url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
