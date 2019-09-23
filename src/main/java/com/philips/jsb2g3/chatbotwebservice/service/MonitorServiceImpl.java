/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Context;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Service
public class MonitorServiceImpl implements MonitorService {

  MonitorDAO dao;

  @Override
  @Autowired
  public void setDao(MonitorDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean addNewMonitor(Monitor toBeAdded) {
    if (toBeAdded.getBrand() == null || toBeAdded.getBrand().length() == 0) {
      return false;
    }

    if (toBeAdded.getName() == null || toBeAdded.getName().length() == 0) {
      return false;
    }

    if (toBeAdded.getSize() == null || toBeAdded.getSize().length() == 0) {
      return false;
    }
    
    if (toBeAdded.getType() == null || toBeAdded.getType().length() == 0) {
      return false;
    }

    dao.save(toBeAdded);
    return true;
  }

  @Override
  public Monitor findByName(String name) {
    return dao.findByName(name);
  }

  @Override
  public List<Monitor> findAll() {
    return dao.findAll();
  }
  @Override
  public boolean deleteMonitor(Monitor m) {
    if (m == null) return false;
    if (m.getName() == null) return false;
    if (m.getName().length() == 0) return false;
    
    Monitor monitor = dao.findByName(m.getName());
    if (monitor != null) dao.deleteByName(m.getName());
    else return false;

    return true;
  }

  @Override
  public String getDisplayContext(Context context) {
    if (context != null) {
      context.trim();
    }
    JSONObject data = parseContext(context);
    return data.toJSONString();
  }

  @SuppressWarnings("unchecked")
  private JSONObject parseContext(Context context) {
    JSONObject data = new JSONObject();
    if (context.getBy() == null || context.getBy().equals("")) {
      JSONArray array = new JSONArray();
      array.add("brand");
      array.add("screensize");
      array.add("screentype");

      data.put("isModel", false);
      data.put("values", array);
      data.put("display", "Choose by");
      data.put("update", "by");
      return data;
    }

    List<Monitor> monitors = new ArrayList<>();
    boolean allHaveValues = true;
    String[] by = context.getBy().split(" ");
    for (int i=0; i<by.length; i++) {
      if (!contextHasValue(by[i], context)) {
        allHaveValues = false;
      }
    }

    monitors = dao.getByContext(context);
    if (monitors.size() == 1) {
      data.put("isModel", true);
      data.put("values", monitors.get(0).getName());
      data.put("display", "Suggested Model");
      data.put("update", "none");
      return data;
    } else if (allHaveValues) {

      Map<String, List<String>> distincts = getDistincts(monitors);
      
      Set<String> toRemove = new HashSet<>(List.of(by));
      Set<String> list = distincts.keySet();
      list.removeAll(toRemove);

      JSONArray array = new JSONArray();
      for (String m : list) {
        array.add(m);
      }
        
      data.put("isModel", false);
      data.put("values", array);
      data.put("display", "Choose by");
      data.put("update", "by");

    } else {
      // 
      Map<String, List<String>> distincts = getDistincts(monitors);
      List<String> values = distincts.get(by[by.length - 1]);

      JSONArray array = new JSONArray();
      for (String m : values) {
        array.add(m);
      }

      data.put("isModel", false);
      data.put("values", array);
      data.put("display", "Select " + by[by.length - 1]);
      data.put("update", by[by.length - 1]);
      return data;

    }
    
    return data;
  }

  private Map<String, List<String>> getDistincts(List<Monitor> monitors) {
    if (monitors == null || monitors.size() ==0) return null;
    Set<String> brands = new HashSet<>();
    Set<String> screentype = new HashSet<>();
    Set<String> screensize = new HashSet<>();

    for (Monitor m : monitors) {
      brands.add(m.getBrand());
      screensize.add(m.getSize());
      screentype.add(m.getType());
    }

    Map<String, List<String>> map = new HashMap<>();
    map.put("brand", new ArrayList<String>(brands));
    map.put("screensize", new ArrayList<String>(screensize));
    map.put("screentype", new ArrayList<String>(screentype));

    System.out.println(map);
    return map;
  }

  private boolean contextHasValue(String key, Context context) {
    switch (key) {
      case "brand" : 
      return context.getBrand() != null && !context.getBrand().equals("");

      case "screensize" :
      return context.getScreenSize() != null && !context.getScreenSize().equals("");

      case "screentype" :
      return context.getScreenType() != null && !context.getScreenType().equals("");
    }

    return false;
  }
}