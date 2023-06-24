package com.codemind.springboot.DemoSecurity.dao;

import com.codemind.springboot.DemoSecurity.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUserName(String name) {

        // retrieve/read from database using username
        TypedQuery<User> myQuery=entityManager.createQuery("FROM User WHERE userName=:uName",User.class);
        myQuery.setParameter("uName",name);

        User user=null;

        try {
            user=myQuery.getSingleResult();
        }catch (Exception e){
            user=null;
        }

        return user;
    }

    @Override
    @Transactional
    public void save(User user) {

        entityManager.merge(user);
    }
}
