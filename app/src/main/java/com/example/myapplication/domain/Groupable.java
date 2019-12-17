package com.example.myapplication.domain;

public interface Groupable<T> {
    boolean isHeader();

    String getItemId();

    int getItemHash();

    T getCompareField();
}
