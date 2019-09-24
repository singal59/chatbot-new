package com.philips.jsb2g3.chatbotwebservice.dal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.philips.jsb2g3.chatbotwebservice.domain.Interest;
import com.philips.jsb2g3.chatbotwebservice.domain.User;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Transactional
@Repository
public class InterestDAOImpl implements InterestDAO {

    @PersistenceContext
    EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addNewInterest(Interest interest) {
        em.persist(interest);
    }

    @Override
    public List<Interest> findAll() {
        return em.createQuery("select i from Interest i").getResultList();
    }

    @Override
    public List<User> between(Date from, Date to) {
        List<Interest> interests = em.createQuery("select i from Interest i where i.date >= :from and i.date <= :to").setParameter("from", from)
            .setParameter("to", to).getResultList();
        Set<User> set = new HashSet<>();
        for (Interest i : interests) {
            set.add(i.getUser());
        }

        return new ArrayList<>(set);
    }
}