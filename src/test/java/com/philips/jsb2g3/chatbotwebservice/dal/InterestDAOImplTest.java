package com.philips.jsb2g3.chatbotwebservice.dal;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.junit.Test;
import org.mockito.Mockito;

public class InterestDAOImplTest {
    @Test
    public void addNewInterestTest() {
        
        EntityManager em = Mockito.mock(EntityManager.class);

        InterestDAOImpl idi = new InterestDAOImpl();
        idi.setEntityManager(em);

        idi.addNewInterest(new Interest());
    }

    @Test
    public void findAllTest() {
        List<Interest> interests = List.of(new Interest());

        Query q = Mockito.mock(Query.class);
        Mockito.when(q.getResultList()).thenReturn(interests);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        InterestDAOImpl udi = new InterestDAOImpl();
        udi.setEntityManager(em);

        assertEquals(interests, udi.findAll());
    }

    @Test
    public void betweenTest() {
        User u = new User();
        Interest i = new Interest();
        i.setUser(u);

        List<Interest> interests = List.of(i);

        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(q);
        Mockito.when(q.getResultList()).thenReturn(interests);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        InterestDAOImpl idi = new InterestDAOImpl();
        idi.setEntityManager(em);

        List<User> users = idi.between(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        assertEquals(u, users.get(0));
    }
}