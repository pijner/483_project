package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private DBConnector dbConnector;

    private boolean registerError = false;
    private String errorMessage = "";

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

    public boolean isRegisterError() {
        return registerError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String attemptRegister() {
        if (email.isEmpty()) {
            setRegisterError("Please enter an email.");
            return "register";
        }
        if (!isValidEmail()) {
            setRegisterError("Not a valid email address.");
            return "register";
        }
        if (firstPassword.isEmpty() || secondPassword.isEmpty()) {
            setRegisterError("Please enter a password.");
            return "register";
        }
        if (!passwordsMatch()) {
            setRegisterError("Passwords do not match.");
            return "register";
        }

        dbConnector = new DBConnector();
        final String username = stripEmail(email);
        System.out.println("username: " + username);

        if (usernameExistsInDatabase(username)) {
            setRegisterError("Email already exists.");
            return "register";
        }
        errorMessage = "";
        registerError = false;
        return "dashboard";
    }

    private void setRegisterError(String errorMsg) {
        errorMessage = errorMsg;
        registerError = true;
    }

    private boolean isValidEmail() {
        final Pattern VALID_EMAIL_ADDRESS_REGEX
                = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean passwordsMatch() {
        return firstPassword.equals(secondPassword);
    }

    private boolean usernameExistsInDatabase(String username) {
        return dbConnector.usernameExists(username);
    }

    private String stripEmail(String email) {
        // Hacky
        return email.split("@")[0];
    }

}
