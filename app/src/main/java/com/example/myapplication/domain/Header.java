package com.example.myapplication.domain;


public class Header implements Groupable<String> {
    private char firstLetter;
    private String id;

    public Header(char firstLetter, String id) {
        this.firstLetter = firstLetter;
        this.id = id;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getItemId() {
        return id;
    }

    @Override
    public int getItemHash() {
        return hashCode();
    }

    @Override
    public String getCompareField() {
        return String.valueOf(firstLetter);
    }
}
