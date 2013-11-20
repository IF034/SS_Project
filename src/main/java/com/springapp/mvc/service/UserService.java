package com.springapp.mvc.service;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<Role> getAllRoles();

    Role getRole(int roleId);

    User getUser(String username);

    User getUserByOpenIdIdentity(String identity);

    List<User> getAll();

    void add(User user);

    void update(User user);

    User get(int userId);

    void delete(int userId);

    Page<User> getUsersPage(Integer pageNumber);
}
