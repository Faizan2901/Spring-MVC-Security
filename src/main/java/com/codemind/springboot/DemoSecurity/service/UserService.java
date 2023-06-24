package com.codemind.springboot.DemoSecurity.service;

import com.codemind.springboot.DemoSecurity.entity.User;
import com.codemind.springboot.DemoSecurity.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String name);

    void save(WebUser user);

}
