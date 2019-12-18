package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUserDao implements UserDao {

    private String userFile;
    private ArrayList<User> users;

    public FileUserDao(String file) throws Exception {
        users = new ArrayList<>();
        this.userFile = file;
        try {
            Scanner reader = new Scanner(new File(this.userFile));
            while (reader.hasNextLine()) {
                User user = new User(reader.nextLine());
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println("Initializing new user file");
            FileWriter writer = new FileWriter(new File(userFile));
            writer.close();
        }
    }

    @Override
    public User create(User user) throws Exception {
        users.add(user);
        save();
        return user;
    }

    private void save() throws IOException {
        try (FileWriter writer = new FileWriter(new File(userFile))) {
            for (User user : users) {
                writer.write(user.getUsername() + "\n");
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername()
                .equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

}
