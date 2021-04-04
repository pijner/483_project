package com.gameofthreads.project.controller;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
@Named("registerBean")
@SessionScoped
public class RegisterBean implements Serializable {
    private String email;
    private String firstPassword;
    private String secondPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
    
    public void validatePasswordConfirmation() {
        // TODO: check that passwords match
    }
    
    public void validateEmail() {
        // TODO: 1. Check that email is valid (i.e. regex)
        // TODO: 2. Check that email is not already taken (i.e. database query)
    }
    
}
