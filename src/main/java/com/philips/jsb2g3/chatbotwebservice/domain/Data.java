package com.philips.jsb2g3.chatbotwebservice.domain;

public class Data {
    String brand;
    String model;
    String screenType;
    String screenSize;

    String by;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public void trim() {
        if (brand != null) brand = brand.trim();
        if (screenSize != null) screenSize = screenSize.trim();
        if (screenType != null) screenType = screenType.trim();
        if (by != null) by = by.trim();
    }

    public boolean isBrandValid() {
        return this.brand != null && this.brand.length() > 0;
    }

    public boolean isScreenSizeValid() {
        return this.screenSize != null && this.screenSize.length() > 0;
    }

    public boolean isScreenTypeValid() {
        return this.screenType != null && this.screenType.length() > 0;
    }
}