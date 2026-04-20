package com.dauzva.dao.jpa;

import com.dauzva.entities.Screening;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ScreeningDAO {

    // JPA injects the EntityManager — it is our connection to the database
    @PersistenceContext
    private EntityManager em;

    // Read all screenings, join-fetch related movie and hall to avoid lazy loading issues
    public List<Screening> findAll() {
        return em.createQuery(
                "SELECT s FROM Screening s JOIN FETCH s.movie JOIN FETCH s.hall",
                Screening.class
        ).getResultList();
    }

    public Screening findById(Integer id) {
        return em.find(Screening.class, id);
    }

    @Transactional
    public void save(Screening screening) {
        em.persist(screening);  // INSERT into DB
    }

    @Transactional
    public void update(Screening screening) {
        em.merge(screening);    // UPDATE in DB
    }

    @Transactional
    public void delete(Integer id) {
        Screening s = em.find(Screening.class, id);
        if (s != null) em.remove(s);  // DELETE from DB
    }
}