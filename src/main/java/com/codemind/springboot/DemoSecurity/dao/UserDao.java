package com.codemind.springboot.DemoSecurity.dao;

import com.codemind.springboot.DemoSecurity.entity.User;

public interface UserDao {

    User findByUserName(String name);

    void save(User user);
}
