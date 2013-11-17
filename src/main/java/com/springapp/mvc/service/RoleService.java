package com.springapp.mvc.service;

import com.springapp.mvc.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String roleName);

    List<Role> getAll();

    Role get(int roleId);
}

