package com.dauzva.controllers;

import com.dauzva.entities.Hall;
import com.dauzva.entities.Movie;
import com.dauzva.entities.Screening;
import com.dauzva.mybatis.entities.ScreeningMB;
import com.dauzva.service.ScreeningService;
import com.dauzva.service.ValidationException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

// @Named  → makes this bean available in JSF pages as #{screeningController}
// @ViewScoped → this bean lives as long as the user stays on the same page
@Named
@ViewScoped
@Getter @Setter
public class ScreeningController implements Serializable {

    @Inject
    private ScreeningService screeningService;

    private List<Screening> screenings;
    private List<ScreeningMB> screeningsMB;
    private Screening newScreening = new Screening();
    private Integer selectedMovieId;
    private Integer selectedHallId;

    public List<Screening> getScreenings() {
        if (screenings == null) screenings = screeningService.getAllScreenings();
        return screenings;
    }

    public List<ScreeningMB> getScreeningsMB() {
        if (screeningsMB == null) screeningsMB = screeningService.getAllScreeningsMyBatis();
        return screeningsMB;
    }

    public List<Movie> getMovieList() {
        return screeningService.getAllMovies();
    }

    public List<Hall> getHallList() {
        return screeningService.getAllHalls();
    }

    public String saveScreening() {
        // Resolve Movie and Hall from selected IDs
        Movie movie = screeningService.getAllMovies().stream()
                .filter(m -> m.getId().equals(selectedMovieId))
                .findFirst().orElse(null);

        Hall hall = screeningService.getAllHalls().stream()
                .filter(h -> h.getId().equals(selectedHallId))
                .findFirst().orElse(null);

        if (movie == null) {
            addError("movie", "Please select a movie.");
            return null;  // null = stay on the same page
        }
        if (hall == null) {
            addError("hall", "Please select a hall.");
            return null;
        }

        newScreening.setMovie(movie);
        newScreening.setHall(hall);

        try {
            screeningService.addScreening(newScreening);
        } catch (ValidationException e) {
            // Send the business rule error message to JSF to display on the page
            addError("addForm", e.getMessage());
            return null;  // stay on the form page, show the error
        }

        // Success — reset and redirect
        newScreening = new Screening();
        selectedMovieId = null;
        selectedHallId = null;
        screenings = null;
        screeningsMB = null;
        return "screenings?faces-redirect=true";
    }

    // Helper: adds a JSF error message visible via <p:messages> on the page
    private void addError(String componentId, String message) {
        FacesContext.getCurrentInstance().addMessage(
                componentId,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null)
        );
    }
}