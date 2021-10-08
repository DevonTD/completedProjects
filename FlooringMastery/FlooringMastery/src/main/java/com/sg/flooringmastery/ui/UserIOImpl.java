/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author devon
 */
public class UserIOImpl implements UserIO {

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public BigDecimal readDecimal(String prompt) {
        Scanner myDecimal = new Scanner(System.in);
        System.out.println(prompt);
        BigDecimal input = myDecimal.nextBigDecimal();
        return input;
    }

    @Override
    public BigDecimal readDecimal(String prompt, BigDecimal min) {
        Scanner myDecimal = new Scanner(System.in);
        BigDecimal userInput = new BigDecimal("0");
        while (userInput.compareTo(min) == -1 ) {
            System.out.println(prompt);
            userInput = myDecimal.nextBigDecimal();

            if (userInput.compareTo(min) == -1) {
                System.out.println("Number cannot be less than: " + min);
            } 
        }
        return userInput;
    }

    @Override
    public double readDouble(String prompt) {
        Scanner myDouble = new Scanner(System.in);
        System.out.println(prompt);
        double userInput = myDouble.nextDouble();
        return userInput;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        Scanner myLong = new Scanner(System.in);
        double userInput = 0;
        while (userInput < min || userInput > max) {
            System.out.println(prompt);
            userInput = myLong.nextDouble();

            if (userInput < min) {
                System.out.println("Number cannot be less than: " + min);
            } else if (userInput > max) {
                System.out.println("Number cannot be greater than: " + max);
            }
        }
        return userInput;
    }

    @Override
    public float readFloat(String prompt) {
        Scanner myFloat = new Scanner(System.in);
        System.out.println(prompt);
        float userInput = myFloat.nextFloat();
        return userInput;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        Scanner myFloat = new Scanner(System.in);
        float userInput = 0;
        while (userInput < min || userInput > max) {
            System.out.println(prompt);
            userInput = myFloat.nextFloat();

            if (userInput < min) {
                System.out.println("Number cannot be less than: " + min);
            } else if (userInput > max) {
                System.out.println("Number cannot be greater than: " + max);
            }
        }
        return userInput;
    }

    @Override
    public int readInt(String prompt) {
        Scanner myInt = new Scanner(System.in);
        System.out.println(prompt);
        String userInput = myInt.nextLine();
        int userValue = Integer.parseInt(userInput);
        return userValue;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        Scanner myInt = new Scanner(System.in);
        int userInput = 0;
        String userValue;

        while (userInput < min || userInput > max) {
            System.out.println(prompt);
            userValue = myInt.nextLine();
            userInput = Integer.parseInt(userValue);
            if (userInput < min) {
                System.out.println("Number cannot be less than: " + min);
            } else if (userInput > max) {
                System.out.println("Number cannot be greater than: " + max);
            }
        }
        return userInput;
    }

    @Override
    public long readLong(String prompt) {
        Scanner myLong = new Scanner(System.in);
        System.out.println(prompt);
        String userInput = myLong.nextLine();
        long userValue = Integer.parseInt(userInput);
        return userValue;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        Scanner myLong = new Scanner(System.in);
        long userInput = 0;
        String userValue;

        while (userInput < min || userInput > max) {
            System.out.println(prompt);
            userValue = myLong.nextLine();
            userInput = Integer.parseInt(userValue);
            if (userInput < min) {
                System.out.println("Number cannot be less than: " + min);
            } else if (userInput > max) {
                System.out.println("Number cannot be greater than: " + max);
            }
        }
        return userInput;
    }

    @Override
    public String readString(String prompt) {
        Scanner myInput = new Scanner(System.in); //Declares scanner
        System.out.println(prompt); // Gives a prompt 
        String userValue = myInput.nextLine(); // Reads the value input
        return userValue; // Returns that value
    }
}
