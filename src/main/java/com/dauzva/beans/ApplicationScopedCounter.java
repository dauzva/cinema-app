package com.dauzva.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;

@ApplicationScoped
@Getter
public class ApplicationScopedCounter {
    private int counter = 0;
    public void incrementCounter() {
        counter++;
    }
}
