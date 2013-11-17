package com.springapp.mvc.service;


import com.springapp.mvc.entity.Role;
import com.springapp.mvc.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RoleService.class})
public class UserServiceTest {

    private Role role1;
    private UserServiceImpl userServiceImpl;
    List<Role> list;

    @Before
    public void setUp() {
        userServiceImpl = new UserServiceImpl();
        role1 = new Role();
        role1.setId(2);
        list = new ArrayList<Role>();
        for (int i = 1; i < 6; i++) {
            Role role = new Role();
            role.setId(i);
            list.add(role);
        }
    }

    @Test
    public void testGetRole() throws NoSuchFieldException, IllegalAccessException {
        Integer roleId = 2;
        RoleService roleService = createMock(RoleService.class);
        expect(roleService.get(roleId)).andReturn(role1);
        Field roleService1 = userServiceImpl.getClass().getDeclaredField("roleService");
        roleService1.setAccessible(true);
        roleService1.set(userServiceImpl, roleService);
        replayAll(roleService);
        assertEquals(role1, userServiceImpl.getRole(roleId));
        verifyAll();
    }

    @Test
    public void testGetAllRoles() throws NoSuchFieldException, IllegalAccessException {
        RoleService roleService = createMock(RoleService.class);
        expect(roleService.getAll()).andReturn(list);
        Field roleService1 = userServiceImpl.getClass().getDeclaredField("roleService");
        roleService1.setAccessible(true);
        roleService1.set(userServiceImpl, roleService);
        replayAll(roleService);
        assertEquals(list, userServiceImpl.getAllRoles());
        verifyAll();
    }
}
