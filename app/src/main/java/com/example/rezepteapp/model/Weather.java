package com.example.rezepteapp.model;

public class Weather {

    private double temp;
    private String name;

    public Weather(){
        this(0, "");
    }

    public Weather(double temp) {
        this(temp, "");
    }

    public Weather(String name) {
        this(0, name);
    }

    public Weather(double temp, String name) {
        this.temp = temp;
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
