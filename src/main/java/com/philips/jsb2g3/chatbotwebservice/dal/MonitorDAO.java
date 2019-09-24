/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;

import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

public interface MonitorDAO {

  Monitor save(Monitor m);

  Monitor findByName(String name);

  List<Monitor> findAll();

  void deleteByName(String name);

  List<Monitor> getByData(Data data);
}
