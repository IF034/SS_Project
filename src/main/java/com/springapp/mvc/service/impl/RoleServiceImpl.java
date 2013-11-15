package com.springapp.mvc.service.impl;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import com.springapp.mvc.entity.Role;
import com.springapp.mvc.repository.RoleRepository;
import com.springapp.mvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role getRole(String roleName) {
       return roleRepository.getRole(roleName); //TODO correct impl
    }

    @Override
    public List<Role> getAll() {
       return roleRepository.findAll();
    }

    @Override
    public Role get(int roleId) {
        return roleRepository.findOne(roleId);
    }
}