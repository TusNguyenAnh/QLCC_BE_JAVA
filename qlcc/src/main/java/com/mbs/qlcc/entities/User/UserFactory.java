package com.mbs.qlcc.entities.User;


public class UserFactory implements IUserFactory {
    @Override
    public User create(String username, String password, String res_id, String staff_id, String complex_id, boolean status) {
        return new User(username, password, res_id, staff_id, status, complex_id);
    }
}
