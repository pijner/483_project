/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.timeline.TimelineUpdater;
import org.primefaces.event.timeline.TimelineModificationEvent;
import org.primefaces.model.timeline.TimelineEvent;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
@SessionScoped
@Named("newShift")
public class NewShift implements Serializable{

    private Map<String, Integer> employeeNames;
    private ArrayList<Employee> employees;
    private Integer selectedEmployee = null;
    private Company company;
    private Shift s;
    private TimelineEvent event;
    private TimelineModificationEvent shiftEvent;
    
    public NewShift() {
        DBConnector dbc = new DBConnector();
        
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean u = context.getApplication().evaluateExpressionGet(context, "#{user}", UserBean.class);
        
        this.company = u.getEmployee().getEmployeeCompany();
        this.employees = dbc.getEmployeesInCompany(this.company.getCompany_id());
        
        this.employeeNames = new HashMap<>();
        
        this.employees.forEach(e -> {
            this.employeeNames.put(e.getName(), e.getEmployeeID());
        });
        
        s = new Shift();
        s.setEmployee_id_manager(u.getEmployee().getEmployeeID());
    }

    public Map<String, Integer> getEmployeeNames() {
        return employeeNames;
    }

    public void setEmployeeNames(Map<String, Integer> employeeNames) {
        this.employeeNames = employeeNames;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Shift getS() {
        return s;
    }

    public void setS(Shift s) {
        this.s = s;
    }

    public Integer getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Integer selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public TimelineEvent getEvent() {
        return event;
    }

    public void setEvent(TimelineEvent event) {
        this.event = event;
    }

    public TimelineModificationEvent getShiftEvent() {
        return shiftEvent;
    }

    public void setShiftEvent(TimelineModificationEvent shiftEvent) {
        this.shiftEvent = shiftEvent;
    }
    
    public String insertShift(){
        s.setEmployee_id_worker(this.selectedEmployee);
        DBConnector dbc = new DBConnector();
        dbc.insertShift(this.s);
        
        return "edit";
    }
    
    public void deleteShift(TimelineModificationEvent e){
        System.out.println("Method invoked");
        
        
        if (e == null)
            System.out.println("E is null");
        else{
            event = e.getTimelineEvent();
            if (event == null)
                System.out.println("Event is null");
        }
        
    }
    
}
