package budjetointisovellus.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Budget {

    private String name;
    private HashMap<String, Integer> events;

    public Budget(String name) {
        this.name = name;
        this.events = new HashMap<>();
    }

    public HashMap<String, Integer> getEvents() {
        return events;
    }

    public void setEvent(String name, int amount) {
        events.put(name, amount);
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
        
        for (Map.Entry<String, Integer> entry : events.entrySet()) {
            balance += entry.getValue();
        }
        
        return balance;
    }

    @Override
    public String toString() {
        return this.name + ", " + getBalance();
    }
    
    
    
}
