/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.philips.jsb2g3.chatbotwebservice.domain.Context;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class MonitorDAOImpl implements MonitorDAO {

  @PersistenceContext
  EntityManager em;

  public static final String STRING="select m from Monitor m";

  @Override
  public Monitor save(Monitor m) {
    em.persist(m);
    return m;
  }

  @Override
  public Monitor findByName(String name) {
    return em.find(Monitor.class, name);
  }

  @Override
  public List<Monitor> findAll() {
    return em.createQuery(STRING)
        .getResultList();
  }

  @Override
  public void deleteByName(String name) {
    em.createQuery("delete from Monitor m where m.name=:name").setParameter("name", name)
    .executeUpdate();
  }

  @Override
  public List<String> getAllBrands() {
    final List<String> brandsList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!brandsList.contains(monitor.getBrand())) {
        brandsList.add(monitor.getBrand());
      }
    }
    return brandsList;
  }

  @Override
  public List<String> getAllSizes() {

    final List<String> screenSizesList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!screenSizesList.contains(monitor.getSize())) {
        screenSizesList.add(monitor.getSize());
      }
    }
    return screenSizesList;

  }

  @Override
  public List<String> getAllScreenTypes() {
    final List<String> screenTypesList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!screenTypesList.contains(monitor.getType())) {
        screenTypesList.add(monitor.getType());
      }
    }
    return screenTypesList;
  }

  @Override
  public List<Monitor> findByGivenBrandGivenSizeGivenScreenType(String brand, String size, String screenType) {

    return em.createQuery("select m from Monitor m where m.size=:size and m.brand=:brand and m.type=:type")
        .setParameter("brand", brand)
        .setParameter("size",size)
        .setParameter("type", screenType)
        .getResultList();
  }

  @Override
  public List<Monitor> getByContext(Context context) {
    Query query = constructQuery(context);
    return query.getResultList();
  }

  private Query constructQuery(Context context) {
    String base = "select m from Monitor m";

    if (context.getModel() != null) {
      return em.createQuery(base + " where m.name=:name").setParameter("name", context.getModel());
    }

    boolean useAnd = false;
    boolean brandIsNull = false;
    boolean sizeIsNull = false;
    boolean typeIsNull = false;

    if (context.getBrand() != null && context.getBrand().length() > 0) {
      base += " where m.brand='" + context.getBrand() + "'";
      useAnd = true;
    } else {
      brandIsNull = true;
    }

    if (context.getScreenSize() != null && context.getScreenSize().length() > 0) {
      if (useAnd) base += " and m.size='" + context.getScreenSize() + "'";
      else base += " where m.size='" + context.getScreenSize() + "'";
      useAnd = true;
    } else {
      sizeIsNull = true;
    }

    if (context.getScreenType() != null && context.getScreenType().length() > 0) {
      if (useAnd) base += " and m.type='" + context.getScreenType() + "'";
      else base += " where m.type='" + context.getScreenType() + "'";
    } else {
      typeIsNull = true;
    }

    if (brandIsNull && sizeIsNull && typeIsNull) {
      return em.createQuery(base);
    }

    Query query = em.createQuery(base, Monitor.class);
    return query;
  }

}