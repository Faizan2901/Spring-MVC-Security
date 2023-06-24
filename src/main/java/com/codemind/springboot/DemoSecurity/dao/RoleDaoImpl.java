package com.codemind.springboot.DemoSecurity.dao;

import com.codemind.springboot.DemoSecurity.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao{

    private EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String name) {
        // retrieve/read from database using name
        TypedQuery<Role> theQuery = entityManager.createQuery("FROM Role WHERE name=:roleName", Role.class);
        theQuery.setParameter("roleName", name);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }
}
