package com.codemind.springboot.DemoSecurity.dao;

import com.codemind.springboot.DemoSecurity.entity.Role;

public interface RoleDao {

    Role findRoleByName(String name);
}
