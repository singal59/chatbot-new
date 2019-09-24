package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class MonitorServiceImplTest {

    @Test
    public void addNewMonitorTest1() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertFalse(msi.addNewMonitor(null));
    }

    @Test
    public void addNewMonitorTest2() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertFalse(msi.addNewMonitor(new Monitor()));
    }

    @Test
    public void addNewMonitorTest3() {
        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);
        assertTrue(msi.addNewMonitor(new Monitor("1", "2", "3", "4")));
    }

    @Test
    public void deleteMonitorTest1() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertFalse(msi.deleteMonitor(null));
    }

    @Test
    public void deleteMonitorTest2() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertFalse(msi.deleteMonitor(new Monitor()));
    }

    @Test
    public void deleteMonitorTest3() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertFalse(msi.deleteMonitor(new Monitor("", "", "", "")));
    }

    @Test
    public void deleteMonitorTest4() {
        Monitor m = new Monitor("name", "brand", "ss", "st");

        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.findByName("name")).thenReturn(m);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);

        assertTrue(msi.deleteMonitor(m));
    }

    @Test
    public void deleteMonitorTest5() {
        Monitor m = new Monitor("name", "brand", "ss", "st");

        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.findByName("name")).thenReturn(null);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);

        assertFalse(msi.deleteMonitor(m));
    }

    @Test
    public void findByNameTest() {
        Monitor m = new Monitor("name", "", "", "");

        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.findByName("name")).thenReturn(m);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);

        assertEquals(m, msi.findByName("name"));
    }

    @Test
    public void findAllTest() {
        Monitor m = new Monitor("name", "", "", "");
        List<Monitor> monitors = List.of(m);

        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.findAll()).thenReturn(monitors);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);

        assertEquals(monitors, msi.findAll());
    }

    @Test
    public void getDisplayContextTest1() {
        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertEquals("", msi.getDisplayContext(null));
    }

    @Test
    public void getDisplayContextTest2() {
        Data data = new Data();
        data.setBrand("");
        data.setScreenSize("");
        data.setScreenType("");
        data.setBy("");

        JSONObject data1 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("brand");
        array.add("screensize");
        array.add("screentype");

        data1.put("isModel", false);
        data1.put("values", array);
        data1.put("display", "Choose by");
        data1.put("update", "by");

        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertEquals(data1.toJSONString(), msi.getDisplayContext(data));
    }

    @Test
    public void getDisplayContextTest3() {
        Data data = new Data();

        JSONObject data1 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("brand");
        array.add("screensize");
        array.add("screentype");

        data1.put("isModel", false);
        data1.put("values", array);
        data1.put("display", "Choose by");
        data1.put("update", "by");

        MonitorServiceImpl msi = new MonitorServiceImpl();
        assertEquals(data1.toJSONString(), msi.getDisplayContext(data));
    }

    @Test
    public void getDisplayContextTest4() {
        Data data = new Data();
        data.setBrand("");
        data.setScreenSize("");
        data.setScreenType("");
        data.setBy("brand");

        JSONObject data1 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("b2");
        array.add("b1");

        data1.put("isModel", false);
        data1.put("values", array);
        data1.put("display", "Select brand");
        data1.put("update", "brand");

        List<Monitor> monitors = List.of(new Monitor("m1", "b1", "s1", "t1"), new Monitor("m2", "b1", "s1", "t2"), new Monitor("m3", "b2", "s2", "t1"));
        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.getByData(data)).thenReturn(monitors);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);

        assertEquals(data1.toJSONString(), msi.getDisplayContext(data));
    }

    @Test
    public void getDisplayContextTest5() {
        Data data = new Data();
        data.setBrand("b1");
        data.setScreenSize("");
        data.setScreenType("");
        data.setBy("brand");

        JSONObject data1 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("screensize");
        array.add("screentype");

        data1.put("isModel", false);
        data1.put("values", array);
        data1.put("display", "Choose by");
        data1.put("update", "by");

        List<Monitor> monitors = List.of(new Monitor("m1", "b1", "s1", "t1"), new Monitor("m2", "b1", "s1", "t2"), new Monitor("m3", "b2", "s2", "t1"));
        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.getByData(data)).thenReturn(monitors);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);
        
        assertEquals(data1.toJSONString(), msi.getDisplayContext(data));
    }

    @Test
    public void getDisplayContextTest6() {
        Data data = new Data();
        data.setBrand("");
        data.setScreenSize("");
        data.setScreenType("t2");
        data.setBy("screentype");

        JSONObject data1 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("m2");
        array.add("m3");

        data1.put("isModel", true);
        data1.put("values", array);
        data1.put("display", "Suggested model(s)");
        data1.put("update", "none");

        List<Monitor> monitors = List.of(new Monitor("m2", "b1", "s1", "t2"), new Monitor("m3", "b1", "s1", "t2"));
        MonitorDAO dao = Mockito.mock(MonitorDAO.class);
        Mockito.when(dao.getByData(data)).thenReturn(monitors);

        MonitorServiceImpl msi = new MonitorServiceImpl();
        msi.setDao(dao);
        
        assertEquals(data1.toJSONString(), msi.getDisplayContext(data));
    }
}