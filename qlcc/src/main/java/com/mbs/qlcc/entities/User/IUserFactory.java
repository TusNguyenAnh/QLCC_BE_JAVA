package com.mbs.qlcc.entities.User;


public interface IUserFactory {
    User create(String username, String password, String res_id, String staff_id,
                   String complex_id, boolean status);
}
