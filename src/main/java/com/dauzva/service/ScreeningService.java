package com.dauzva.service;

import com.dauzva.dao.jpa.ScreeningDAO;
import com.dauzva.dao.mybatis.ScreeningMyBatisDAO;
import com.dauzva.entities.Hall;
import com.dauzva.entities.Movie;
import com.dauzva.entities.Screening;
import com.dauzva.mybatis.entities.ScreeningMB;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ScreeningService {

    @Inject
    private ScreeningDAO screeningDAO;               // JPA DAO

    @Inject
    private ScreeningMyBatisDAO screeningMyBatisDAO; // MyBatis DAO

    @PersistenceContext
    private EntityManager em;

    // --- Methods used by the UI ---

    public List<Screening> getAllScreenings() {
        return screeningDAO.findAll();
    }

    public List<ScreeningMB> getAllScreeningsMyBatis() {
        return screeningMyBatisDAO.findAll();
    }

    public List<Movie> getAllMovies() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }

    public List<Hall> getAllHalls() {
        return em.createQuery("SELECT h FROM Hall h JOIN FETCH h.cinema", Hall.class).getResultList();
    }

    @Transactional
    public void addScreening(Screening screening) {
        // Rule 1: start time must be in the future
        if (screening.getStartTime() == null) {
            throw new ValidationException("Start time is required.");
        }
        if (screening.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Start time must be in the future.");
        }

        // Rule 2: price must be positive
        if (screening.getPrice() == null || screening.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Ticket price must be greater than zero.");
        }

        // Rule 3: hall must not already have a screening within 5 hours of this one
        boolean hallBusy = screeningDAO.findAll().stream()
                .filter(s -> s.getHall().getId().equals(screening.getHall().getId()))
                .anyMatch(s -> {
                    long hoursDiff = Math.abs(
                            java.time.Duration.between(s.getStartTime(), screening.getStartTime()).toHours()
                    );
                    return hoursDiff < 5;
                });

        if (hallBusy) {
            throw new ValidationException(
                    "Hall '" + screening.getHall().getName() +
                            "' already has a screening within 5 hours of that time."
            );
        }
        screeningDAO.save(screening);
    }
}