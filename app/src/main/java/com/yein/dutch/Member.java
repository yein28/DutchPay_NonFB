package com.yein.dutch;

import java.io.Serializable;

public class Member implements Serializable{
    private String id;
    private String rate;
    private String money;
    private String date;
    private String bank;
    private String account;

    Member(String id, String rate, String money){
        setId(id);
        setRate(rate);
        setMoney(money);
    }

    Member(String id, String rate, String money, String date){
        setId(id);
        setRate(rate);
        setMoney(money);
        setDate(date);
    }

    Member(String id, String rate, String money, String date, String bank, String account){
        setId(id);
        setRate(rate);
        setMoney(money);
        setDate(date);
        setBank(bank);
        setAccount(account);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
