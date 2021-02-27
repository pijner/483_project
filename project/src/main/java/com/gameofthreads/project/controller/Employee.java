/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;

import java.util.ArrayList;

/**
 *
 * @author Prahar
 */
public class Employee {
    private String name;
    private String username;
    private Integer employeeID;
    private Integer managerID;
    private boolean employeeType;
    private boolean managerAccess;
    private WeeklyTimes available_hours;
    private ArrayList<Shift> employeeShifts;
    private ExceptTimes unavailableTimes;
    private Company employeeCompany;

    public Employee() {
        this.name = "";
        this.username = "";
        this.employeeID = null;
        this.employeeType = false;
        this.employeeShifts = null;
        this.managerAccess = false;
        this.managerID = null;
        this.unavailableTimes = null;
        this.employeeCompany = null;
        this.available_hours = null;
        
    }
    
    public ArrayList<Shift> getEmployeeShifts() {
        return employeeShifts;
    }

    public void setEmployeeShifts(ArrayList<Shift> employeeShifts) {
        this.employeeShifts = employeeShifts;
    }
    
    public void addShift(Shift shiftToAdd){
        this.employeeShifts.add(shiftToAdd);
    }

    public ExceptTimes getUnavailableTimes() {
        return unavailableTimes;
    }

    public void setUnavailableTimes(ExceptTimes unavailableTimes) {
        this.unavailableTimes = unavailableTimes;
    }

    public Company getEmployeeCompany() {
        return employeeCompany;
    }

    public void setEmployeeCompany(Company employeeCompany) {
        this.employeeCompany = employeeCompany;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public boolean isEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(boolean employeeType) {
        this.employeeType = employeeType;
    }

    public boolean isManagerAccess() {
        return managerAccess;
    }

    public void setManagerAccess(boolean managerAccess) {
        this.managerAccess = managerAccess;
    }

    public WeeklyTimes getAvailable_hours() {
        return available_hours;
    }

    public void setAvailable_hours(WeeklyTimes available_hours) {
        this.available_hours = available_hours;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }
}
