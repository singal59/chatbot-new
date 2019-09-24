/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.UserDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDAO dao;

  @Autowired
  public void setDao(UserDAO dao) {
    this.dao = dao;
  }

  @Override
  public User addNewUser(User user) {
    return dao.save(user);
  }

  @Override
  public User findById(int id) {
    return dao.findById(id);
  }

  @Override
  public List<User> findAll() {
    return dao.findAll();
  }

  @Override
  public boolean exists(User user) {
    final List<User> users = findAll();
    if (users == null || users.isEmpty()) {
      return false;
    }

    for (final User u : users) {
      if (u.equals(user)) {
        return true;
      }
    }
    return false;
  }

}
