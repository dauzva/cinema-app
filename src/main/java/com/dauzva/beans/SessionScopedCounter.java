package com.dauzva.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;

@SessionScoped
@Getter
public class SessionScopedCounter implements Serializable {
    private int counter = 0;
    public void incrementCounter() {
        counter++;
    }
}
