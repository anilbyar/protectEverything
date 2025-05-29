package com.example.protecteverything.Utility;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.protecteverything.MainActivity;
import com.example.protecteverything.Models.SiteWithCode;
import com.example.protecteverything.SigninActivity;
import com.example.protecteverything.SiteCodesFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Helper {
    public static String longpass = SigninActivity.longpass;
    public static String shortpass = SigninActivity.shortpass;
    public static int LENGTH_LONG = 16;
    public static int LENGTH_SHORT = 12;
    // Generate site according to password length
    public static SiteWithCode generateCode(String siteName, int length){
        SiteWithCode site = new SiteWithCode();
        site.setSiteName(siteName);
        site.setLength(length);

        ArrayList<Integer> arraylist = new ArrayList<>();
        Random random = new Random();
        for (int i=0;i<length;i++){
            arraylist.add(random.nextInt(30));
        }

        StringBuilder code = new StringBuilder();
        for (int i=0;i<length;i++){
            code.append(arraylist.get(i));
            if (i!=length-1) code.append("_");
        }
        site.setIntArrayList(arraylist);
        site.setSiteCode(code.toString());
        return site;
    }

    public static void copyToClipboard(Context context, String string){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Site Code", string);
        clipboard.setPrimaryClip(clipData);
    }

    //  Create password according to the length of the site code
    public static String createPassword(String siteCode) {
        siteCode = siteCode.trim();
        String[] codeArray = siteCode.split("_");

        int len = codeArray.length;
        if (len!=LENGTH_LONG && len!=LENGTH_SHORT){
            return null;
        }

        String password;
        if (len==LENGTH_LONG) password = longpass;
        else password = shortpass;

        int[] codeIntArray = new int[len];

        for (int i = 0; i < len; i++) {
            try {
                codeIntArray[i] = Integer.parseInt(codeArray[i]);
                CharSequence c = password.substring(i,i+1);

                if (isUpper(c)){
                    while (password.charAt(i) + codeIntArray[i] > 90) {
                        codeIntArray[i] -= 26;
                    }
                } else if (isLower(c)){
                    while (password.charAt(i) + codeIntArray[i] > 122) {
                        codeIntArray[i] -= 26;
                    }
                } else if (isNum(c)) {
                    while (password.charAt(i) + codeIntArray[i] > 57) {
                        codeIntArray[i] -= 10;
                    }
                } else {
                    while (password.charAt(i) + codeIntArray[i] > 47) {
                        codeIntArray[i] -= 15;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }

        StringBuilder createdPassword = new StringBuilder(new String(""));
        for (int i = 0; i < len; i++) {
            createdPassword.append((char) (password.charAt(i) + codeIntArray[i]));
        }

        return createdPassword.toString();
    }

    public static boolean isUpper(CharSequence c){
        return Pattern.matches("[A-Z]", c);
    }
    public static boolean isLower(CharSequence c){
        return Pattern.matches("[a-z]", c);
    }
    public static boolean isNum(CharSequence c){
        return Pattern.matches("[0-9]", c);
    }

    public static ArrayList<SiteWithCode> searchSite(String name){
        ArrayList<SiteWithCode> foundsites = new ArrayList<>();
        for (SiteWithCode site: SiteCodesFragment.codeArrayList){
            if (isSubstring(name, site.getSiteName())){
                foundsites.add(site);
            }
        }
        return foundsites;
    }

    public static boolean isSubstring(String firstString, String secondString){
        firstString = firstString.toLowerCase().trim();
        secondString = secondString.toLowerCase().trim();
        int len1 = firstString.length();
        int len2 = secondString.length();
        if (len1>len2) return false;

        for (int i=0;i<=len2-len1;i++){
            String sub = secondString.substring(i, len1+i);
            if (firstString.equals(sub)) {
                return true;
            }
        }
        return false;
    }
    public static String getLongPass(){
        return shuffleString("AAAAaaaa1111!!!!");
    }
    public static String getShortPass(){
        return shuffleString("AAAaaa111!!!");
    }
    private static String shuffleString(String string)
    {
        StringBuilder shuffled = new StringBuilder();
        for (int i = 0;i<string.length();i++){
            shuffled.append(string.charAt(i));
        }
        Random rand = new Random();
        for (int i = 0;i<string.length();i++){
            int ind = rand.nextInt(string.length());
            char temp = shuffled.charAt(ind);
            shuffled.setCharAt(ind, shuffled.charAt(i));
            shuffled.setCharAt(i, temp);
        }

        return shuffled.toString();
    }
}
