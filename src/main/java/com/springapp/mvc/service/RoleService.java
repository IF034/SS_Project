package com.springapp.mvc.service;

import com.springapp.mvc.entity.Role;

import java.util.List;

public interface RoleService {
    public Role getRole(String roleName);
    public List<Role> getAll();
    public Role get(int roleId);
}

