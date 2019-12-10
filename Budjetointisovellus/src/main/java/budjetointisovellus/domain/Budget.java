package budjetointisovellus.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Budjetti-käsitettä vastaava luokka. Voi sisältää yhden tai useamman erän. 
 * Laskee sisältyvien erien summan.
 */
public class Budget {

    private String name;
    private ArrayList<Transaction> events;

    public Budget(String name) {
        this.name = name;
        this.events = new ArrayList<>();
    }

    public ArrayList getEvents() {
        return events;
    }

    public void setEvent(String name, int amount) {
        events.add(new Transaction(name, amount));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void removeEvent(String name) {
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
    
    
    
}