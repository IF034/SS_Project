package com.springapp.mvc.service;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.entity.User;

import java.util.List;

public interface UserService {
    public List<Role> getAllRoles();
    public Role getRole(int roleId);
    public User getUser(String username);
    public User getUserByOpenIdIdentity(String identity);
    public List<User> getAll();
    public void add(User user);
    public void update(User user);
    public User get(int userId);
    public void delete(int userId);
}
