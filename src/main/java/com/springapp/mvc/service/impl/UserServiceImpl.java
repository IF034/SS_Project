package com.springapp.mvc.service.impl;

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.RoleService;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final int PAGE_SIZE = 3;

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

    @Override
    public Page<User> getUsersPage(Integer pageNumber) {
        PageRequest pageRequest =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "nickname");
        return userRepository.findAll(pageRequest);
    }

}
