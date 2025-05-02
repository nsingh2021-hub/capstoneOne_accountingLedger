package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    static final String FILE = "transactions.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nD) Deposit P) Payment L) Ledger X) Exit");
            String c = sc.nextLine().toUpperCase();
            if (c.equals("X")) break;
            if (c.equals("D") || c.equals("P")) handleTransaction(sc, c.equals("D"));
            else if (c.equals("L")) ledgerMenu(sc);
        }
    }

    static void handleTransaction(Scanner sc, boolean isDeposit) {
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Vendor: ");
        String vendor = sc.nextLine();
        System.out.print("Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (!isDeposit) amount *= -1;
        String line = LocalDate.now() + "|" + LocalTime.now().withNano(0) + "|" + desc + "|" + vendor + "|" + amount;
        try (FileWriter w = new FileWriter(FILE, true)) { w.write(line + "\n"); }
        catch (IOException e) { System.out.println("Error saving."); }
    }

    static void ledgerMenu(Scanner sc) {
        List<String> data = readAll();
        System.out.println("\nA) All D) Deposits P) Payments R) Reports");
        String c = sc.nextLine().toUpperCase();
        if (c.equals("A")) print(data);
        else if (c.equals("D")) print(filter(data, true));
        else if (c.equals("P")) print(filter(data, false));
        else if (c.equals("R")) reportMenu(sc, data);
    }

    static void reportMenu(Scanner sc, List<String> data) {
        System.out.println("1) Month To Date 2) Prev Month 3) Year To Date 4) Prev Year");
        String c = sc.nextLine();
        LocalDate now = LocalDate.now();
        List<String> result = new ArrayList<>();
        for (String t : data) {
            String[] parts = t.split("\\|");
            LocalDate d = LocalDate.parse(parts[0]);
            if ((c.equals("1") && d.getMonth() == now.getMonth() && d.getYear() == now.getYear()) ||
                    (c.equals("2") && d.getMonthValue() == now.minusMonths(1).getMonthValue() && d.getYear() == now.getYear()) ||
                    (c.equals("3") && d.getYear() == now.getYear()) ||
                    (c.equals("4") && d.getYear() == now.getYear() - 1)) result.add(t);
        }
        print(result);
    }

    static List<String> readAll() {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line; while ((line = br.readLine()) != null) list.add(0, line);
        } catch (IOException e) { System.out.println("No file found."); }
        return list;
    }

    static void print(List<String> list) {
        if (list.isEmpty()) System.out.println("No transactions.");
        else for (String l : list) System.out.println(l);
    }

    static List<String> filter(List<String> list, boolean deposits) {
        List<String> result = new ArrayList<>();
        for (String l : list) {
            double amt = Double.parseDouble(l.split("\\|")[4]);
            if ((deposits && amt > 0) || (!deposits && amt < 0)) result.add(l);
        }
        return result;
    }
}







