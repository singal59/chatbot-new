package com.philips.jsb2g3.chatbotwebservice.web;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.User;
import com.philips.jsb2g3.chatbotwebservice.service.InterestService;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ChatbotControllerTest {
    
  





    

    @Test
    public void addNewInterestPass() {
        Interest interest = new Interest();

        InterestService is = Mockito.mock(InterestService.class);
        Mockito.when(is.addNewInterest(interest)).thenReturn(true);

        ChatbotController cc = new ChatbotController();
        cc.setInterestService(is);

        ResponseEntity<String> response = cc.addNewInterest(interest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CREATED", response.getBody());
    }

    @Test
    public void addNewInterestFail() {
        Interest interest = new Interest();

        InterestService is = Mockito.mock(InterestService.class);
        Mockito.when(is.addNewInterest(interest)).thenReturn(false);

        ChatbotController cc = new ChatbotController();
        cc.setInterestService(is);

        ResponseEntity<String> response = cc.addNewInterest(interest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("TRY AGAIN", response.getBody());
    }

    @Test
    public void getAllMonitorsTest() {
        List<Monitor> monitors = List.of(new Monitor());
        
        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.findAll()).thenReturn(monitors);

        ChatbotController cc = new ChatbotController();
        cc.setMonitorService(ms);

        assertEquals(monitors, cc.getAllMonitors());
    }

    @Test
    public void addMonitorPass() {
        Monitor monitor = new Monitor();

        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.addNewMonitor(monitor)).thenReturn(true);

        ChatbotController cc = new ChatbotController();
        cc.setMonitorService(ms);

        ResponseEntity<String> response = cc.addMonitor(monitor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CREATED", response.getBody());
    }

    @Test
    public void addMonitorFail() {
        Monitor monitor = new Monitor();

        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.addNewMonitor(monitor)).thenReturn(false);

        ChatbotController cc = new ChatbotController();
        cc.setMonitorService(ms);

        ResponseEntity<String> response = cc.addMonitor(monitor);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("TRY AGAIN", response.getBody());
    }

    @Test
    public void deleteMonitorPass() {
        Monitor monitor = new Monitor();

        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.deleteMonitor(monitor)).thenReturn(true);

        ChatbotController cc = new ChatbotController();
        cc.setMonitorService(ms);

        ResponseEntity<String> response = cc.deleteMonitor(monitor);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteMonitorFail() {
        Monitor monitor = new Monitor();

        MonitorService ms = Mockito.mock(MonitorService.class);
        Mockito.when(ms.deleteMonitor(monitor)).thenReturn(false);

        ChatbotController cc = new ChatbotController();
        cc.setMonitorService(ms);

        ResponseEntity<String> response = cc.deleteMonitor(monitor);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("TRY AGAIN", response.getBody());
    }

    @Test
    public void getInterestedUsersFail() {
        ChatbotController cc = new ChatbotController();

        ResponseEntity<Object> response = cc.getInterestedUsers(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getInterestedUsersPass() {

        List<User> users = List.of(new User());
        
        InterestService is = Mockito.mock(InterestService.class);
        Mockito.when(is.getUsers(Mockito.any(), Mockito.any())).thenReturn(users);
        
        ChatbotController cc = new ChatbotController();
        cc.setInterestService(is);

        ResponseEntity<Object> response = cc.getInterestedUsers("{\"from\":\"2019-01-01\",\"to\":\"2019-02-03\"}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }
}