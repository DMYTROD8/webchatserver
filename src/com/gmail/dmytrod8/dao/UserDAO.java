package com.gmail.dmytrod8.dao;

import com.gmail.dmytrod8.User;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.dmytrod8.User.STATUS.*;

public class UserDAO {
    private final List<User> store = new ArrayList<>();

    public User getById(int id) {

        User result = new User();
        result.setId(-1);

        for (User user : store) {
            if (user.getId() == id) {
                result = user;
            }
        }

        return result;
    }

    public String getLoginById(int id) {

        String result = "-";
        User tempUser = new User();

        for (User user : store) {
            if (user.getId() == id) {
                result = user.getLogin();
            }
        }

        return result;
    }

    public int getIdByLogin(String login) {

        int result = -1;
        User tempUser = new User();

        for (User user : store) {
            if (user.getLogin().equals(login)) {
                result = user.getId();
            }
        }

        return result;
    }


    public User getUserByLoginPassword(final String login, final String password) {

        User result = new User();
        result.setId(-1);

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user;
            }
        }

        return result;
    }

    public boolean add(final User user) {

        for (User u : store) {
            if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())) {
                return false;
            }
        }

        return store.add(user);
    }

    public int userIsExist(final String login, final String password) { //return id if exist or -1

        int result = -1;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user.getId();
                break;
            }
        }

        return result;
    }

    public boolean userIsExist(final String login) {

        boolean result = false;

        for (User user : store) {
            if (user.getLogin().equals(login)) {
                result = true;
                break;
            }
        }

        return result;
    }


    public int getFreeId() {

        int freeId = -1;

        for (User user : store) {
            freeId = user.getId() + 1;
        }

        return freeId;
    }

    public ArrayList<String> getListAllUsers() {

        ArrayList<String> tempArr = new ArrayList<>();

        for (User user : store) {
            tempArr.add(user.getLogin());
        }

        return tempArr;
    }

    public String getUserStatusByLogin(String login) {

        int id = getIdByLogin(login);
        if (id == -1) {
            return null;
        }

        User user = getById(id);

        String status = String.valueOf(user.getStatus());

        return status;
    }

    public void changeUserStatusById(int userId, String newStatus) {
        if (newStatus.equals("0")) {
            getById(userId).setStatus(Available);
        } else if (newStatus.equals("1")) {
            getById(userId).setStatus(Busy);
        } else if (newStatus.equals("2")) {
            getById(userId).setStatus(Away);
        }

    }


    @Override
    public String toString() {
        return "UserDAO{" +
                "store=" + store +
                '}';
    }
}
