package com.example.protecteverything.Models;


import java.util.ArrayList;
import java.util.Comparator;

public class SiteWithCode {
    String siteName;
    String siteCode;
    int length;
    ArrayList<Integer> intArrayList;


    public SiteWithCode(){}

    public SiteWithCode(String name, ArrayList<Integer> intArrayList){
        this.siteName = name;
        this.intArrayList = intArrayList;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public ArrayList<Integer> getIntArrayList() {
        return intArrayList;
    }

    public void setIntArrayList(ArrayList<Integer> intArrayList) {
        this.intArrayList = intArrayList;
    }

    public static class SortByName implements Comparator<SiteWithCode>{
        @Override
        public int compare(SiteWithCode s1, SiteWithCode s2) {
            return s1.getSiteName().compareTo(s2.getSiteName());
        }
    }
}
