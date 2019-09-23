/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.philips.jsb2g3.chatbotwebservice.domain.Context;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.service.InterestService;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;
import com.philips.jsb2g3.chatbotwebservice.service.UserService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

  MonitorService monitorService;
  UserService userService;
  InterestService interestService;

  @Autowired
  public void setMonitorService(MonitorService monitorService) {
    this.monitorService = monitorService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setInterestService(InterestService interestService) {
    this.interestService = interestService;
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/interact", method = RequestMethod.POST)
  public String interaction(@RequestBody Context context) {
    return monitorService.getDisplayContext(context);
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/interest", method = RequestMethod.GET)
  public List<Interest> getAllInterests() {
    return interestService.findAll();
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/interest", method = RequestMethod.POST)
  public ResponseEntity<String> addNewInterest(@RequestBody Interest interest) {

    boolean result = interestService.addNewInterest(interest);
    if (result) {
      return ResponseEntity.ok().body("CREATED");
    } else {
      return ResponseEntity.badRequest().body("TRY AGAIN");
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/monitor", method = RequestMethod.GET)
  public List<Monitor> getAllMonitors() {
    return monitorService.findAll();
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/monitor", method = RequestMethod.POST) 
  public ResponseEntity<Object> addMonitor(@RequestBody Monitor monitor) {
    boolean result = monitorService.addNewMonitor(monitor);
    if (result) {
      return ResponseEntity.ok().body("CREATED");
    } else {
      return ResponseEntity.badRequest().body("TRY AGAIN");
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/monitor", method = RequestMethod.DELETE)
  public ResponseEntity<Object> deleteMonitor(@RequestBody Monitor monitor) {
    boolean result = monitorService.deleteMonitor(monitor);
    if (result) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.badRequest().body("TRY AGAIN");
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @RequestMapping(value = "/api/iuser", method = RequestMethod.POST)
  public ResponseEntity<Object> getInterestedUsers (@RequestBody String source) {
    try {
      JSONObject object = (JSONObject) new JSONParser().parse(source);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date from = formatter.parse(object.get("from").toString());
      Date to = formatter.parse(object.get("to").toString());
      return ResponseEntity.ok().body(interestService.getUsers(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())));
    } catch (ParseException e) {
      return ResponseEntity.badRequest().build();
    } catch(java.text.ParseException pe) {
      return ResponseEntity.badRequest().build();
    }
  }
}