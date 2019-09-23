package com.philips.jsb2g3.chatbotwebservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.dal.InterestDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImpl implements InterestService {

    @Autowired
    InterestDAO iDAO;

    @Autowired
    MonitorService mService;

    @Autowired
    UserService uService;

    @Override
    public boolean addNewInterest(Interest interest) {
        if (interest != null && interest.getMonitor() != null) {
            Monitor m = mService.findByName(interest.getMonitor().getName());
            interest.setMonitor(m);
            if (m != null) {
                if (!uService.exists(interest.getUser())) {
                    User u = uService.addNewUser(interest.getUser());
                    interest.setUser(u);
                } else {
                    List<User> users = uService.findAll();
                    for (User u : users) {
                        if (u.equals(interest.getUser())) {
                            interest.setUser(u);
                            break;
                        }
                    }
                }
                iDAO.addNewInterest(interest);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Interest> findAll() {
        return iDAO.findAll();
    }

    @Override
    public List<Monitor> getInterests(User user) {
        if (user == null) return new ArrayList<>();

        List<Interest> interests = findAll();
        List<Monitor> monitors = new ArrayList<>();
        for (Interest i : interests) {
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