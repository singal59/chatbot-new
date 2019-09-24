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
import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Service
public class MonitorServiceImpl implements MonitorService {
  private static final String BRAND= "brand";
  private static final String SCREENTYPE= "screentype";
  private static final String SCREENSIZE= "screensize";
  private static final String ISMODEL= "isModel";
  private static final String DISPLAY= "display";
  private static final String VALUES= "values";
  private static final String UPDATE= "update";

  MonitorDAO dao;

  @Override
  @Autowired
  public void setDao(MonitorDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean addNewMonitor(Monitor m) {
    if (m == null) {
      return false;
    }
    if (!m.isValid()) {
      return false;
    }

    dao.save(m);
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

  // refactor
  @Override
  public boolean deleteMonitor(Monitor m) {
    if (m == null) {
      return false;
    }
    if (m.getName() == null) {
      return false;
    }
    if (m.getName().length() == 0) {
      return false;
    }

    final Monitor monitor = dao.findByName(m.getName());
    if (monitor != null) {
      dao.deleteByName(m.getName());
    } else {
      return false;
    }

    return true;
  }

  @Override
  public String getDisplayContext(Data data) {
    if (data == null) {
      return "";
    }
    data.trim();
    final JSONObject data2 = parseData(data);
    return data2.toJSONString();
  }


  @SuppressWarnings("unchecked")
  private JSONObject parseData(Data data) {

    if (data.getBy() == null || data.getBy().equals("")) {
      final JSONObject data1 = new JSONObject();
      final JSONArray array = new JSONArray();
      array.add(BRAND);
      array.add(SCREENSIZE);
      array.add(SCREENTYPE);

      data1.put(ISMODEL, false);
      data1.put(VALUES, array);
      data1.put(DISPLAY, "Choose by");
      data1.put(UPDATE, "by");
      return data1;
    }

    List<Monitor> monitors;
    final String[] by = data.getBy().split(" ");
    final boolean allHaveValues = isAllPaired(by, data);

    monitors = dao.getByData(data);
    if (allHaveValues) {
      return whenAllHaveValues(by, monitors);
    } else {
      return whenAllDontHaveValues(by, monitors);
    }

  }

  private JSONObject whenAllHaveValues(String[] by, List<Monitor> monitors) {
    final JSONObject data = new JSONObject();
    final Map<String, List<String>> distincts = getDistincts(monitors);

    final Set<String> list = distincts.keySet();
    boolean allSuggestions = true;
    for (final String key : list) {
      final List<String> items = distincts.get(key);
      if (items.size() > 1) {
        allSuggestions = false;
        break;
      }
    }

    if (allSuggestions) {
      final JSONArray array = new JSONArray();
      for (final Monitor m : monitors) {
        array.add(m.getName());
      }

      data.put(ISMODEL, true);
      data.put(VALUES, array);
      data.put(DISPLAY, "Suggested model(s)");
      data.put(UPDATE, "none");

    } else {
      final Set<String> toRemove = new HashSet<>(List.of(by));
      list.removeAll(toRemove);

      final JSONArray array = new JSONArray();
      for (final String m : list) {
        array.add(m);
      }

      data.put(ISMODEL, false);
      data.put(VALUES, array);
      data.put(DISPLAY, "Choose by");
      data.put(UPDATE, "by");
    }

    return data;

  }

  private JSONObject whenAllDontHaveValues(String[] by, List<Monitor> monitors) {
    final JSONObject data = new JSONObject();
    final Map<String, List<String>> distincts = getDistincts(monitors);
    final List<String> values = distincts.get(by[by.length - 1]);

    final JSONArray array = new JSONArray();
    for (final String m : values) {
      array.add(m);
    }

    data.put(ISMODEL, false);
    data.put(VALUES, array);
    data.put(DISPLAY, "Select " + by[by.length - 1]);
    data.put(UPDATE, by[by.length - 1]);
    return data;
  }

  private boolean isAllPaired(String[] by, Data data) {
    boolean allHaveValues = true;
    for (final String element : by) {
      if (!dataHasValue(element, data)) {
        allHaveValues = false;
        break;
      }
    }
    return allHaveValues;
  }

  private Map<String, List<String>> getDistincts(List<Monitor> monitors) {
    if (monitors == null || monitors.isEmpty()) {
      return new HashMap<>();
    }
    final Set<String> brands = new HashSet<>();
    final Set<String> screentype = new HashSet<>();
    final Set<String> screensize = new HashSet<>();

    for (final Monitor m : monitors) {
      brands.add(m.getBrand());
      screensize.add(m.getSize());
      screentype.add(m.getType());
    }

    final Map<String, List<String>> map = new HashMap<>();
    map.put(BRAND, new ArrayList<>(brands));
    map.put(SCREENSIZE, new ArrayList<>(screensize));
    map.put(SCREENTYPE, new ArrayList<>(screentype));

    return map;
  }


  private boolean dataHasValue(String key, Data data) {
    switch (key) {
      case BRAND :
        return data.isBrandValid();

      case SCREENSIZE :
        return data.isScreenSizeValid();

      case SCREENTYPE :
        return data.isScreenTypeValid();

      default: return false;
    }

  }
}