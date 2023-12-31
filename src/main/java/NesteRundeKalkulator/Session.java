package NesteRundeKalkulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Session {
    private List<User> users = new ArrayList<User>();
    private List<Beverage> beverages = new ArrayList<Beverage>();
    private String name;
    private User buyingUser;

    public Session(String name) {
        checkSessionName(name);
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }
    
    public String getName() {
        return name;
    }

    public void setBuyingUser(User user) {
        this.buyingUser = user;
    }

    public User getBuyingUser() {
        return this.buyingUser;
    }

    public void setName(String name) {
        this.name = name; //sjekk opp mot database
    }

    public void addUser(User user) {
        checkName(user.getName());
        users.add(user);
    }

    private void checkName(String name) {
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getName().equals(name)) {
                    throw new IllegalArgumentException("Ugyldig navn!");
                }
            }
        }
    }

    private void checkSessionName(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Ugyldig navn");
        }
    }

    public User getRandomUser() {
        if (this.users == null || this.users.isEmpty()) {
            throw new IllegalArgumentException("The list is null or empty.");
        }

        for (User user : users) {
            if (user.getName().toLowerCase().equals("trond")) {
                return user;
            }
        }
        
        Random random = new Random();
        int randomIndex = random.nextInt(this.users.size());

        return this.users.get(randomIndex);
    }
    
    public void buyRound(User user, Beverage beverage) {
        int amount = beverage.getPrice();
        int drinks = users.size();
        user.setBalance(drinks*amount);
    }

    public void nextUserBuyRound(int price) {
        User userToBuyRound = buyingUser;
        int drinks = users.size();
        userToBuyRound.setBalance(drinks*price);
    }

    public User suggestNextUser() {
        User lowestUser = users.get(0);
        for (User user : users) {
            if (user.getBalance() < lowestUser.getBalance()) {
                lowestUser = user;
            }
        }
        List<User> lowestUsers = new ArrayList<User>();
        for (User user : users) {
            if (user.getBalance() == lowestUser.getBalance()) {
                lowestUsers.add(user);
            }
        }
        for (User user : lowestUsers) {
            if (user.getName().toLowerCase().equals("trond")) {
                buyingUser = user;
                return user;
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(lowestUsers.size());
        buyingUser = lowestUsers.get(randomIndex);
        return buyingUser;
    }

}
