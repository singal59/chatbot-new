package com.philips.jsb2g3.chatbotwebservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.InterestDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

@Service
public class InterestServiceImpl implements InterestService {

  @Autowired
  InterestDAO iDAO;

  @Autowired
  MonitorService mService;

  @Autowired
  UserService uService;

  public void setInterestDAO(InterestDAO interestDAO) {
    this.iDAO = interestDAO;
  }

  public void setMonitorService(MonitorService ms) {
    this.mService = ms;
  }

  public void setUserService(UserService us) {
    this.uService = us;
  }

  @Override
  public boolean addNewInterest(Interest interest) {
    if (interest != null && interest.getMonitor() != null) {
      final Monitor m = mService.findByName(interest.getMonitor().getName());
      interest.setMonitor(m);
      if (m != null) {
        interest.setUser(getUser(interest.getUser()));
        iDAO.addNewInterest(interest);
        return true;
      }
    }
    return false;
  }

  private User getUser(User user) {
    if (!uService.exists(user)) {

      return uService.addNewUser(user);
    } else {
      final List<User> users = uService.findAll();
      for (final User u : users) {
        if (u.equals(user)) {
          return u;
        }
      }
    }

    return null;
  }

  @Override
  public List<Interest> findAll() {
    return iDAO.findAll();
  }

  @Override
  public List<Monitor> getInterests(User user) {
    if (user == null) {
      return new ArrayList<>();
    }

    final List<Interest> interests = findAll();
    final List<Monitor> monitors = new ArrayList<>();
    for (final Interest i : interests) {
      if (i.getUser().equals(user)) {
        monitors.add(i.getMonitor());
      }
    }

    return monitors;
  }

  @Override
  public List<User> getUsers(Date from, Date to) {
    return iDAO.between(from, to);
  }
}