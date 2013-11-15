package com.springapp.mvc.service;

import com.springapp.mvc.entity.EnterpriseRatio;
import com.springapp.mvc.repository.EnterpriseRatioRepository;
import com.springapp.mvc.service.impl.EnterpriseRatioServiceImpl;
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
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EnterpriseRatioRepository.class })
public class EnterpriseRatioServiceTest {

    private EnterpriseRatioServiceImpl enterpriseRatioServiceImpl;
    List<EnterpriseRatio> list = new ArrayList<EnterpriseRatio>();
    @Before
    public void setUp() {
        enterpriseRatioServiceImpl = new EnterpriseRatioServiceImpl();

    }
    @Test
    public void testGetVotes() throws NoSuchFieldException, IllegalAccessException {
        Integer enterpriseNumber = 5;
        Integer votes = 3;
        EnterpriseRatioRepository enterpriseRatioRepository = createMock(EnterpriseRatioRepository.class);
        expect(enterpriseRatioRepository.getVotes(enterpriseNumber)).andReturn((long)votes);
        Field enterpriseRatioRepository1 =  enterpriseRatioServiceImpl.getClass().getDeclaredField("enterpriseRatioRepository");
        enterpriseRatioRepository1.setAccessible(true);
        enterpriseRatioRepository1.set(enterpriseRatioServiceImpl, enterpriseRatioRepository);
        replayAll(enterpriseRatioRepository);
        assertEquals((Integer)3, enterpriseRatioServiceImpl.getVotes(enterpriseNumber));
        verifyAll();
    }
    @Test
    public void testGetVoteValue() throws IllegalAccessException, NoSuchFieldException {
        Integer enterpriseNumber = 3;
        Integer voteValue = 15;
        Integer votes = 5;
        EnterpriseRatioRepository enterpriseRatioRepository = createMock(EnterpriseRatioRepository.class);
        expect(enterpriseRatioRepository.getVoteValue(enterpriseNumber)).andReturn((long)voteValue);
        Field enterpriseRatioRepository1 =  enterpriseRatioServiceImpl.getClass().getDeclaredField("enterpriseRatioRepository");
        enterpriseRatioRepository1.setAccessible(true);
        enterpriseRatioRepository1.set(enterpriseRatioServiceImpl, enterpriseRatioRepository);
        expect(enterpriseRatioServiceImpl.getVotes(enterpriseNumber)).andReturn(votes);
        replayAll(enterpriseRatioRepository);
        assertEquals((Integer)3, enterpriseRatioServiceImpl.getVoteValue(enterpriseNumber));
        verifyAll();
    }

    @Test
    public void testUserAlreadyVote() throws NoSuchFieldException, IllegalAccessException {
        Integer enterpriseNumber = 7;
        Integer userId = 7;
        EnterpriseRatioRepository enterpriseRatioRepository = createMock(EnterpriseRatioRepository.class);
        expect(enterpriseRatioRepository.userAlreadyVote(userId, enterpriseNumber)).andReturn(true);
        Field enterpriseRatioRepository1 =  enterpriseRatioServiceImpl.getClass().getDeclaredField("enterpriseRatioRepository");
        enterpriseRatioRepository1.setAccessible(true);
        enterpriseRatioRepository1.set(enterpriseRatioServiceImpl, enterpriseRatioRepository);
        replayAll(enterpriseRatioRepository);
        assertEquals(true, enterpriseRatioServiceImpl.userAlreadyVote(userId, enterpriseNumber));
        verifyAll();

    }
    }
