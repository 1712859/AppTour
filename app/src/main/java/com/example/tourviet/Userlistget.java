package com.example.tourviet;

import java.util.List;

public class Userlistget {
    String searchKey;
    List<gest_user> users;

    public Userlistget(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<gest_user> getUsers() {
        return users;
    }

    public void setUsers(List<gest_user> users) {
        this.users = users;
    }
}
