package be.kdg.model;

import java.util.ArrayList;

public class Player {


    private final String name;
    private final Table table;

    public String getName() {
        return name;
    }

    public Player(String name, Table table) {
        this.name = name;
        this.table = table;
    }
    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }



}
