package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.RoleService;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;


    @Override
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @Override
    public Role getRole(int roleId) {
        return roleService.get(roleId);
    }

    @Override
    public User getUser(String nickname) {
        return userRepository.getUser(nickname);
    }

    @Override
    public User getUserByOpenIdIdentity(String identity) {
        return userRepository.getUserByOpenIdIdentity(identity);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User get(int userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public void delete(int userId) {
        userRepository.delete(userId);
    }

}
