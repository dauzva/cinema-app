package com.dauzva.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;

@RequestScoped
@Getter
public class RequestScopedCounter {
    private int counter = 0;
    public void incrementCounter() {
        counter++;
    }
}
