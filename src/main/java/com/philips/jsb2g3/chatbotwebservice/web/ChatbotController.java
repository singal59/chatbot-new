/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.service.InterestService;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;
import com.philips.jsb2g3.chatbotwebservice.service.UserService;

@RestController
public class ChatbotController {
  private static final String TRYAGAIN= "TRY AGAIN";
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
  @PostMapping(value = "/api/interact")
  public String interaction(@RequestBody Data data) {
    return monitorService.getDisplayContext(data);
  }

  @CrossOrigin(value = "http://localhost:4200")
  @GetMapping(value = "/api/interest")
  public List<Interest> getAllInterests() {
    return interestService.findAll();
  }

  @CrossOrigin(value = "http://localhost:4200")
  @PostMapping(value = "/api/interest")
  public ResponseEntity<String> addNewInterest(@RequestBody Interest interest) {

    final boolean result = interestService.addNewInterest(interest);
    if (result) {
      return ResponseEntity.ok().body("CREATED");
    } else {
      return ResponseEntity.badRequest().body(TRYAGAIN);
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @GetMapping(value = "/api/monitor")
  public List<Monitor> getAllMonitors() {
    return monitorService.findAll();
  }

  @CrossOrigin(value = "http://localhost:4200")
  @PostMapping(value = "/api/monitor")
  public ResponseEntity<String> addMonitor(@RequestBody Monitor monitor) {
    final boolean result = monitorService.addNewMonitor(monitor);
    if (result) {
      return ResponseEntity.ok().body("CREATED");
    } else {
      return ResponseEntity.badRequest().body(TRYAGAIN);
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @DeleteMapping(value = "/api/monitor")
  public ResponseEntity<String> deleteMonitor(@RequestBody Monitor monitor) {
    final boolean result = monitorService.deleteMonitor(monitor);
    if (result) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.badRequest().body(TRYAGAIN);
    }
  }

  @CrossOrigin(value = "http://localhost:4200")
  @PostMapping(value = "/api/iuser")
  public ResponseEntity<Object> getInterestedUsers (@RequestBody String source) {
    try {
      final JSONObject object = (JSONObject) new JSONParser().parse(source);
      final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      final Date from = formatter.parse(object.get("from").toString());
      final Date to = formatter.parse(object.get("to").toString());
      return ResponseEntity.ok().body(interestService.getUsers(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())));
    } catch (final Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}