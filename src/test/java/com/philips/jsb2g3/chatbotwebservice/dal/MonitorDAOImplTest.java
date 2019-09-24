package com.philips.jsb2g3.chatbotwebservice.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

import org.junit.Test;
import org.mockito.Mockito;

public class MonitorDAOImplTest {
    @Test
    public void saveTest() {
        Monitor m = new Monitor();
        
        EntityManager em = Mockito.mock(EntityManager.class);

        MonitorDAOImpl mdi = new MonitorDAOImpl(); 
        mdi.setEntityManager(em);

        assertEquals(m, mdi.save(m));
    }

    @Test
    public void findAllTest() {
        List<Monitor> monitors = List.of(new Monitor());

        Query q = Mockito.mock(Query.class);
        Mockito.when(q.getResultList()).thenReturn(monitors);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        MonitorDAOImpl mdi = new MonitorDAOImpl(); 
        mdi.setEntityManager(em);

        assertEquals(monitors, mdi.findAll());
    }

    @Test
    public void findByNameTest() {
        Monitor m = new Monitor();

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.find(Monitor.class, "name")).thenReturn(m);

        MonitorDAOImpl mdi = new MonitorDAOImpl(); 
        mdi.setEntityManager(em);

        assertEquals(m, mdi.findByName("name"));
    }

    @Test
    public void deleteByNameTest() {
        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);
        Mockito.when(q.executeUpdate()).thenReturn(1);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);

        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        mdi.deleteByName("name");
    }

    @Test
    public void getByContextTest1() {
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        assertEquals(new ArrayList<Monitor>(), mdi.getByData(null));
    }

    @Test
    public void getByContextTest2() {
        Data data = new Data();
        data.setModel("model");
        
        Query q = Mockito.mock(Query.class);
        Mockito.when(q.getResultList()).thenReturn(null);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(q);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }

    @Test
    public void getByContextTest3() {
        Data data = new Data();
        data.setBrand("brand");
        
        TypedQuery<Object> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(tq.getResultList()).thenReturn(null);


        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(tq);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }

    @Test
    public void getByContextTest4() {
        Data data = new Data();
        data.setScreenSize("ss");
        
        TypedQuery<Object> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(tq.getResultList()).thenReturn(null);


        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(tq);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }

    @Test
    public void getByContextTest5() {
        Data data = new Data();
        data.setScreenType("st");
        
        TypedQuery<Object> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(tq.getResultList()).thenReturn(null);


        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(tq);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }

    @Test
    public void getByContextTest6() {
        Data data = new Data();
        
        TypedQuery<Object> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(tq.getResultList()).thenReturn(null);


        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(tq);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }

    @Test
    public void getByContextTest7() {
        Data data = new Data();
        data.setScreenType("st");
        data.setScreenSize("ss");
        data.setBrand("brand");
        
        TypedQuery<Object> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(tq.getResultList()).thenReturn(null);


        Query q = Mockito.mock(Query.class);
        Mockito.when(q.setParameter(Mockito.anyString(), Mockito.anyString())).thenReturn(q);

        EntityManager em = Mockito.mock(EntityManager.class);
        Mockito.when(em.createQuery(Mockito.anyString(), Mockito.any())).thenReturn(tq);
        
        MonitorDAOImpl mdi = new MonitorDAOImpl();
        mdi.setEntityManager(em);

        assertNull(mdi.getByData(data));
    }
}