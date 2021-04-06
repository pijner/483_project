package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username = "";
    private String password = "";
    private boolean loginError = false;
    private String errorMessage = "";

    private DBConnector dbConnector;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginError() {
        return loginError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String attemptLogin() {
        if (username.isEmpty()) {
            setLoginError("Please enter a username.");
            return "login";
        }
        if (password.isEmpty()) {
            setLoginError("Please enter a password.");
            return "login";
        }

        dbConnector = new DBConnector();

        if (!usernameExistsInDatabase()) {
            setLoginError("Username does not exist.");
            return "login";
        }
        if (!namePassIsValid()) {
            setLoginError("Incorrect password.");
            return "login";
        }
        errorMessage = "";
        loginError = false;
        return "dashboard";
    }

    private void setLoginError(String errorMsg) {
        errorMessage = errorMsg;
        loginError = true;
    }

    private boolean usernameExistsInDatabase() {
        return dbConnector.usernameExists(username);
    }

    private boolean namePassIsValid() {
        try {
            final Employee employee = dbConnector.getEmployeeByLogin(username, password);
            return employee.getEmployeeID() != null;
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
