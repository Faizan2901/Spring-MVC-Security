package com.codemind.springboot.DemoSecurity.service;

import com.codemind.springboot.DemoSecurity.dao.RoleDao;
import com.codemind.springboot.DemoSecurity.dao.UserDao;
import com.codemind.springboot.DemoSecurity.entity.Role;
import com.codemind.springboot.DemoSecurity.entity.User;
import com.codemind.springboot.DemoSecurity.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String name) {

        return userDao.findByUserName(name);
    }


    @Override
    public void save(WebUser webUser) {
        User user=new User();

        // assign user details to the user object
        user.setUserName(webUser.getUserName());
        user.setFirstName(user.getFirstName());
        user.setEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(webUser.getPassword()));
        user.setLastName(webUser.getLastName());

        // give user default role of "employee"
        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        // save user in the database
        userDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }

}
