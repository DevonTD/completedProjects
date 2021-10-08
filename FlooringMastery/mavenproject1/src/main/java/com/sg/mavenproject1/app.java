/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mavenproject1;

/**
 *
 * @author devon
 */
public class app {
    public static void main(String[] args) {
        String convert = "5";
        int converted = romanToInt(convert);
        System.out.println(converted);   
    }
    public static int romanToInt(String s){
        int roman = Integer.parseInt(s);
        return roman;
    }
}
