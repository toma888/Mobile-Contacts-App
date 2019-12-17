package com.example.myapplication.domain;


import java.util.Comparator;

public class GroupableComparator implements Comparator<Groupable<String>> {

    @Override
    public int compare(Groupable<String> g1, Groupable<String> g2) {
        if (g1.isHeader() && g1.getCompareField().substring(0, 1)
                .equals(g2.getCompareField()
                        .substring(0, 1)) && !g2.isHeader())
            return 1;
        if (!g1.isHeader() && g1.getCompareField().substring(0, 1)
                .equals(g2.getCompareField()
                        .substring(0, 1)) && g2.isHeader())
            return -1;
        return g1.getCompareField().compareTo(g2.getCompareField());
    }
}
