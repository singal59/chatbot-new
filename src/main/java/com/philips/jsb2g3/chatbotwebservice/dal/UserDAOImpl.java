/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class UserDAOImpl implements UserDAO {

  @PersistenceContext
  EntityManager em;

  @Override
  public User save(User ud) {
    em.persist(ud);
    return ud;
  }

  @Override
  public User findById(int id) {
    return em.find(User.class, id);
  }

  @Override
  public List<User> findAll() {
    return em.createQuery("select ud from User ud")
        .getResultList();
  }

  @Override
  public User findByName(String name) {
    return (User) em.createQuery("select m from User m where m.name=:name ")
        .setParameter("name", name)
        .getSingleResult();
  }

  @Override
  public void deleteById(int id) {
    em.createQuery("delete from User m where m.id=:id").setParameter("id", id)
    .executeUpdate();
  }
}