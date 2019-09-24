package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.dal.UserDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.junit.Test;
import org.mockito.Mockito;

public class UserServiceImplTest {
    
    @Test
    public void addnewUserTest() {
        User user = new User();
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.save(user)).thenReturn(user);

        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertEquals(user, usi.addNewUser(user));
    }

    @Test
    public void findByIdTest() {
        User user = new User();
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findById(1)).thenReturn(user);

        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertEquals(user, usi.findById(1));
    }

    @Test
    public void findAll() {
        List<User> users = List.of(new User());
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findAll()).thenReturn(users);

        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertEquals(users, usi.findAll());
    }

    @Test
    public void existsTrue() {
        User u = new User();
        List<User> users = List.of(u);
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findAll()).thenReturn(users);
        
        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertTrue(usi.exists(u));
    }

    @Test
    public void existsFail1() {
        List<User> users = List.of(new User());
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findAll()).thenReturn(users);
        
        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertFalse(usi.exists(new User("", "", "")));
    }

    @Test
    public void existsFail2() {
        List<User> users = null;
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findAll()).thenReturn(users);
        
        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertFalse(usi.exists(new User("", "", "")));
    }

    @Test
    public void existsFail3() {
        List<User> users = List.of();
        UserDAO dao = Mockito.mock(UserDAO.class);
        Mockito.when(dao.findAll()).thenReturn(users);
        
        UserServiceImpl usi = new UserServiceImpl();
        usi.setDao(dao);

        assertFalse(usi.exists(new User("", "", "")));
    }
}