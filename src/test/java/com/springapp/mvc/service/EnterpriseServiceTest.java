package com.springapp.mvc.service;

import com.springapp.mvc.entity.City;
import com.springapp.mvc.entity.Enterprise;
import com.springapp.mvc.repository.CategoryRepository;
import com.springapp.mvc.repository.EnterpriseRepository;
import com.springapp.mvc.service.impl.CategoryServiceImpl;
import com.springapp.mvc.service.impl.EnterpriseServiceImpl;
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
@PrepareForTest({ EnterpriseRepository.class, CategoryRepository.class })
public class EnterpriseServiceTest {

    private EnterpriseServiceImpl enterpriseServiceImpl;
    private CategoryServiceImpl categoryServiceImpl;
    List<City> list = new ArrayList<City>();
    @Before
    public void setUp() {
        enterpriseServiceImpl = new EnterpriseServiceImpl();

        for (int i = 1; i<4; i++){
            Enterprise er = new Enterprise();
            City c = new City();
            c.setId(i);
            er.setCity(c);
            list.add(c);
        }

    }
    @Test
    public void testCountEnterpriseByCategory() throws NoSuchFieldException, IllegalAccessException {
        int categoryId = 2;
        int count = 4;

        EnterpriseRepository enterpriseRepository = createMock(EnterpriseRepository.class);
        expect(enterpriseRepository.countEnterpriseByCategory(categoryId)).andReturn((long)5);
        Field enterpriseRepository1 =  enterpriseServiceImpl.getClass().getDeclaredField("enterpriseRepository");
        enterpriseRepository1.setAccessible(true);
        enterpriseRepository1.set(enterpriseServiceImpl, enterpriseRepository);
        replayAll(enterpriseRepository);
        assertEquals((Integer)5, (Integer)(enterpriseServiceImpl.countEnterpriseByCategory(categoryId)));
        verifyAll();
    }

    @Test
    public void testCountEnterprisesInAllCities() throws NoSuchFieldException, IllegalAccessException {
        int categoryId = 4;
        List<Integer> l = new ArrayList<Integer>();
        l.add(10);
        l.add(11);
        l.add(12);

        CityService cityService = createMock(CityService.class);
        expect(cityService.getAll()).andReturn(list);
        Field cityService2 =  enterpriseServiceImpl.getClass().getDeclaredField("cityService");
        cityService2.setAccessible(true);
        cityService2.set(enterpriseServiceImpl, cityService);

        EnterpriseRepository enterpriseRepository = createMock(EnterpriseRepository.class);
        expect(enterpriseRepository.countEnterpriseByCity(1)).andReturn((long)10);
        expect(enterpriseRepository.countEnterpriseByCity(2)).andReturn((long)11);
        expect(enterpriseRepository.countEnterpriseByCity(3)).andReturn((long)12) ;

        Field enterpriseRepository1 =  enterpriseServiceImpl.getClass().getDeclaredField("enterpriseRepository");
        enterpriseRepository1.setAccessible(true);
        enterpriseRepository1.set(enterpriseServiceImpl, enterpriseRepository);
        replayAll(cityService, enterpriseRepository);
        assertEquals(l, enterpriseServiceImpl.countEnterprisesInAllCities());
        verifyAll();
    }

}
