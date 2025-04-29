package com.ps;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String option = "";

        while (!option.equalsIgnoreCase("X")) {
            System.out.println("\n=== MENU ===");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Show All Transactions");
            System.out.println("X) Exit");
            System.out.print("Enter option: ");
            option = input.nextLine();





        }
    }
}