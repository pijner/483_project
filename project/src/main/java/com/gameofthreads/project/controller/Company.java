/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;


/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
public class Company {
    
    private String company_name;
    private Integer company_id;
    private WeeklyTimes company_hours;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public WeeklyTimes getCompany_hours() {
        return company_hours;
    }

    public void setCompany_hours(WeeklyTimes company_hours) {
        this.company_hours = company_hours;
    }
}
