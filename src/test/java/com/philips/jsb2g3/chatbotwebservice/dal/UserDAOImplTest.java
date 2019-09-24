package com.philips.jsb2g3.chatbotwebservice.dal;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.junit.Test;
import org.mockito.Mockito;

public class UserDAOImplTest {
    
    @Test
    public void saveTest() {
        User u = new User();
        
        EntityManager em = Mockito.mock(EntityManager.class);

        UserDAOImpl udi = new UserDAOImpl(); 
        udi.setEntityManager(em);

        assertEquals(u, udi.save(u));
    }

    @Test
    public void findByIdTest() {
        User u = new User();

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.find(User.class, 1)).thenReturn(u);

        UserDAOImpl udi = new UserDAOImpl(); 
        udi.setEntityManager(em);

        assertEquals(u, udi.findById(1));
    }

    @Test
    public void findAllTest() {
        List<User> users = List.of(new User());

        Query q = Mockito.mock(Query.class);
        Mockito.when(q.getResultList()).thenReturn(users);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        UserDAOImpl udi = new UserDAOImpl(); 
        udi.setEntityManager(em);

        assertEquals(users, udi.findAll());
    }

    @Test
    public void findByNameTest() {
        User user = new User();

        Query q = Mockito.mock(Query.class);
        Mockito.when(q.getSingleResult()).thenReturn(user);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        UserDAOImpl udi = new UserDAOImpl(); 
        udi.setEntityManager(em);

        assertEquals(user, udi.findByName("name"));
    }

    @Test
    public void deleteByIdTest() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyInt())).thenReturn(q);
        Mockito.when(q.executeUpdate()).thenReturn(1);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        UserDAOImpl udi = new UserDAOImpl();
        udi.setEntityManager(em);

        udi.deleteById(1);
    }
}