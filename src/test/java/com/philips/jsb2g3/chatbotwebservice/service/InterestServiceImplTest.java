package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.dal.InterestDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.junit.Test;
import org.mockito.Mockito;

public class InterestServiceImplTest {
 
    @Test
    public void addNewInterestTest1() {
        InterestServiceImpl isi = new InterestServiceImpl();
        assertFalse(isi.addNewInterest(null));
    }

    @Test
    public void addNewInterestTest2() {        
        InterestServiceImpl isi = new InterestServiceImpl();

        Interest i = new Interest();
        assertFalse(isi.addNewInterest(i));
    }

    @Test
    public void addNewInterestTest3() {
        Monitor m = new Monitor();
        Interest i = new Interest();
        i.setMonitor(m);
        
        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.findByName(null)).thenReturn(null);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setMonitorService(ms);

        assertFalse(isi.addNewInterest(i));
    }

    

    @Test
    public void addNewInterestTest4() {
        Monitor m = new Monitor();
        m.setName("name");

        User u = new User();

        Interest i = new Interest();
        i.setMonitor(m);
        i.setUser(u);
        
        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.findByName("name")).thenReturn(m);

        UserService us = Mockito.mock(UserService.class);
        Mockito.when(us.exists(u)).thenReturn(false);
        Mockito.when(us.addNewUser(u)).thenReturn(u);

        InterestDAO dao = Mockito.mock(InterestDAO.class);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setMonitorService(ms);
        isi.setUserService(us);
        isi.setInterestDAO(dao);

        assertTrue(isi.addNewInterest(i));
    }






    @Test
    public void addNewInterestTest5() {
        Monitor m = new Monitor();
        m.setName("name");

        User u = new User();

        Interest i = new Interest();
        i.setMonitor(m);
        i.setUser(u);
        
        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.findByName("name")).thenReturn(m);

        UserService us = Mockito.mock(UserService.class);
        Mockito.when(us.exists(u)).thenReturn(true);
        Mockito.when(us.findAll()).thenReturn(List.of(u));

        InterestDAO dao = Mockito.mock(InterestDAO.class);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setMonitorService(ms);
        isi.setUserService(us);
        isi.setInterestDAO(dao);

        assertTrue(isi.addNewInterest(i));
    }

    @Test
    public void findAllTest() {
        List<Interest> interests = List.of(new Interest());
        
        InterestDAO dao = Mockito.mock(InterestDAO.class);
        Mockito.when(dao.findAll()).thenReturn(interests);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setInterestDAO(dao);

        assertEquals(interests, isi.findAll());
    }

    @Test
    public void getInterestsTest1() {
        InterestServiceImpl isi = new InterestServiceImpl();
        assertTrue(isi.getInterests(null).size() == 0);
    }

    @Test
    public void getInterestsTest2() {
        User u = new User("name", "contact", "email");
        Monitor m = new Monitor("name", "brand", "size", "type");
        
        Interest i = new Interest();
        i.setUser(new User("name", "contact", "email"));
        i.setMonitor(m);

        List<Interest> interests = List.of(i);

        InterestDAO dao = Mockito.mock(InterestDAO.class);
        Mockito.when(dao.findAll()).thenReturn(interests);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setInterestDAO(dao);

        List<Monitor> monitors = isi.getInterests(u);
        assertEquals(m, monitors.get(0));
    }

    @Test
    public void getUsersTest() {
        Date from = new Date(System.currentTimeMillis());
        Date to = new Date(System.currentTimeMillis());

        InterestDAO dao = Mockito.mock(InterestDAO.class);
        Mockito.when(dao.between(from, to)).thenReturn(null);

        InterestServiceImpl isi = new InterestServiceImpl();
        isi.setInterestDAO(dao);

        assertNull(isi.getUsers(from, to));
    }
}