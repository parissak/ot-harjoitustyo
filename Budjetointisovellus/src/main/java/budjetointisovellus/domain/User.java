package budjetointisovellus.domain;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String username;
    private ArrayList<Budget> budgets;
    
    public User(String username) {
        this.budgets = new ArrayList<>();
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.username;
    }

    
    
    
    
}
