package budjetointisovellus.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Budjetti-käsitettä vastaava luokka. Voi sisältää yhden tai useamman erän.
 * Laskee sisältyvien erien summan.
 */
public class Budget {

    private String name;
    private ArrayList<Transaction> events;
    private User user;

    public Budget(String name, User loggedIn) {
        this.user = loggedIn;
        this.name = name;
        this.events = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList getTransactions() {
        return this.events;
    }

    public void setTransaction(String name, int amount) {
        this.events.add(new Transaction(name, amount));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeTransactions(String name) {
        this.events.remove(name);
    }

    public int getBalance() {
        int balance = 0;
        for (Transaction event : events) {
            balance += event.getAmount();
        }
        return balance;
    }

    @Override
    public String toString() {
        return this.name + ", " + getBalance();
    }

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//
//        final Budget other = (Budget) obj;
//
//        if (other.getUser().equals(this.user) && other.getName().equals(this.name)) {
//            return true;
//        }
//        return false;
//    }

}
