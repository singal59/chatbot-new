/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

public interface UserDAO {

  User save(User ud);

  User findById(int id);

  List<User> findAll();

  User findByName(String name);

  void deleteById(int id);

}
