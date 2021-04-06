package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
@Named("user")
@SessionScoped
public class UserBean implements Serializable {

    private Employee employee;
    private final DBConnector dbc = new DBConnector();
    private LocalDateTime start = LocalDateTime.now();
    private LocalDateTime end = this.start.plusDays(7);
    private LocalDateTime timelineStart;
    private LocalDateTime timelineEnd;
    private TimelineModel<String, ?> model;
    private boolean editAvailiblity;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getTimelineStart() {
        return timelineStart;
    }

    public void setTimelineStart(LocalDateTime timelineStart) {
        this.timelineStart = timelineStart;
    }

    public LocalDateTime getTimelineEnd() {
        return timelineEnd;
    }

    public void setTimelineEnd(LocalDateTime timelineEnd) {
        this.timelineEnd = timelineEnd;
    }

    public void attemptLogin(String username, String password) {
        System.out.println("n: " + username + "; p: " + password);
        try {
            this.employee = dbc.getEmployeeByLogin(username, password);
            if (this.employee.getEmployeeID() == null) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void attemptRegistration(String name, String email, String password) {
        String username = email.split("@")[0];

        if (dbc.usernameExists(username)) {
            attemptLogin(username, password);
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void getShiftTimes() {
        // Set Timeline start to be 12:00am day of
        this.timelineStart = this.start.minus(this.start.getHour(), ChronoUnit.HOURS);
//        this.timelineEnd = this.timelineStart.plusDays(1);
        this.timelineEnd = this.end;

        try {
            this.model = dbc.getCompanyShifts(employee.getEmployeeCompany().getCompany_id(), start, end);
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TimelineModel<String, ?> getModel() {
        this.getShiftTimes();
        return model;
    }

    public void setModel(TimelineModel<String, ?> model) {
        this.model = model;
    }

    public boolean isEditAvailiblity() {
        return editAvailiblity;
    }

    public void setEditAvailiblity(boolean editAvailiblity) {
        System.out.println("Edit avail set to " + editAvailiblity);
        this.editAvailiblity = editAvailiblity;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();

        return "logout";
    }

    public void updateTimes() {
        this.editAvailiblity = false;
        DBConnector dbc = new DBConnector();
        System.out.println(this.employee.getAvailable_hours().toJson());
        dbc.updateAvailableTimes(this.employee.getEmployeeID(), this.employee.getAvailable_hours().toJson());
    }

}
