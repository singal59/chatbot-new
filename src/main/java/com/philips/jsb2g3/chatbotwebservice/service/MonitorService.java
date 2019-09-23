/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Context;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;


public interface MonitorService {

  void setDao(MonitorDAO dao);

  boolean addNewMonitor(Monitor toBeAdded);

  Monitor findByName(String name);

  boolean deleteMonitor(Monitor monitor);

  List<Monitor> findAll();

  String getDisplayContext(Context context);

}
