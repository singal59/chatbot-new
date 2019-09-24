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
import com.philips.jsb2g3.chatbotwebservice.domain.Data;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class MonitorDAOImpl implements MonitorDAO {

  @PersistenceContext
  EntityManager em;

  public void setEntityManager(EntityManager em) {
    this.em = em;
  }

  private String baseString = "select m from Monitor m";

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
    return em.createQuery(baseString)
        .getResultList();
  }

  @Override
  public void deleteByName(String name) {
    em.createQuery("delete from Monitor m where m.name=:name").setParameter("name", name)
    .executeUpdate();
  }

  @Override
  public List<Monitor> getByData(Data data) {
    if (data == null) {
      return new ArrayList<>();
    }

    final Query query = constructQuery(data);
    return query.getResultList();
  }


  private Query constructQuery(Data data) {

    if (data.getModel() != null) {
      return em.createQuery(baseString + " where m.name=:name").setParameter("name", data.getModel());
    }

    boolean useAnd = false;
    boolean brandIsNull = false;
    boolean sizeIsNull = false;
    boolean typeIsNull = false;

    if (data.isBrandValid()) {
      baseString += " where m.brand='" + data.getBrand() + "'";
      useAnd = true;
    } else {
      brandIsNull = true;
    }

    if (data.isScreenSizeValid()) {
      if (useAnd) {
        baseString += " and m.size='" + data.getScreenSize() + "'";
      } else {
        baseString += " where m.size='" + data.getScreenSize() + "'";
      }
      useAnd = true;
    } else {
      sizeIsNull = true;
    }

    if (data.isScreenTypeValid()) {
      if (useAnd) {
        baseString += " and m.type='" + data.getScreenType() + "'";
      } else {
        baseString += " where m.type='" + data.getScreenType() + "'";
      }
    } else {
      typeIsNull = true;
    }

    if (brandIsNull && sizeIsNull && typeIsNull) {
      return em.createQuery(baseString);
    }

    return em.createQuery(baseString, Monitor.class);
  }

}