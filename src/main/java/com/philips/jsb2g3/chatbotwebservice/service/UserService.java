/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

public interface UserService {

  User addNewUser(User toBeAdded);

  User findById(int id);

  List<User> findAll();

  boolean exists(User user);
}
