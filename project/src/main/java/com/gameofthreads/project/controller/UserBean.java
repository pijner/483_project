/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author Prahar
 */

@Named("user")
@RequestScoped
public class UserBean implements Serializable {

    private Employee employee;
    private final DBConnector dbc = new DBConnector();
    private LocalDateTime start;
    private LocalDateTime end;
    private TimelineModel<String, ?> model;

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

    
    public String attemptLogin(String username, String password) {
        try {
            this.employee = dbc.getEmployeeByLogin(username, password);
            if(this.employee.getEmployeeID() == null){
//                LoginBean lb = (LoginBean) FacesUtils.getManagedBean("loginBean");
                return null;
            }
            return "dashboard.xhtml";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            return "login.xhtml";
        }
    }

    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    
    public void getShiftTimes(){
        this.start = LocalDateTime.now();
        this.end = this.start.plusDays(7);
        
        try {
            this.model = dbc.getCompanyShifts(employee.getEmployeeCompany().getCompany_id(), start, end);
            
//        ArrayList<Shift> shifts = this.employee.getEmployeeShifts();
//        this.model = new TimelineModel<>();
//        
//        for(Shift s: shifts){
//            TimelineEvent e = TimelineEvent.builder()
//                    .startDate(s.getStartTime())
//                    .endDate(s.getEndTime())
//                    .group(this.employee.getName())
//                    .data(s.getShift_id())
//                    .build();
//            
//            this.model.add(e);
//            System.out.println("Shift " + s.getShift_id() + " added");
//        }
            
//        start = LocalDate.of(-140, 1, 1).atStartOfDay();
//        end = LocalDate.of(-140, 1, 2).atStartOfDay();
//
//        String[] NAMES = new String[] {"User 1", "User 2", "User 3", "User 4", "User 5", "User 6"};
//                
//        model = new TimelineModel<>();
//        for (String name : NAMES) {
//            LocalDateTime end = start.minusHours(12).withMinute(0).withSecond(0).withNano(0);
//
//            for (int i = 0; i < 5; i++) {
//                LocalDateTime start = end.plusHours(Math.round(Math.random() *5));
//                end = start.plusHours(4 + Math.round(Math.random() *5));
//
//                long r = Math.round(Math.random() * 2);
//                String availability = (r == 0 ? "Unavailable" : (r == 1 ? "Available" : "Maybe"));
//  
//                // create an event with content, start / end dates, editable flag, group name and custom style class
//                TimelineEvent event = TimelineEvent.builder()
//                        .data(availability)
//                        .startDate(start)
//                        .endDate(end)
//                        .editable(true)
//                        .group(name)
//                        .styleClass(availability.toLowerCase())
//                        .build();
//
//                model.add(event);
//            }  
//        }  
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

}
