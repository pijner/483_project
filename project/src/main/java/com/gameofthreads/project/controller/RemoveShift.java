/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
@SessionScoped
@Named()
public class RemoveShift implements Serializable {

    private ArrayList<Shift> listOfShifts;

    public RemoveShift() {
        
    }

    public ArrayList<Shift> getListOfShifts() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean u = context.getApplication().evaluateExpressionGet(context, "#{user}", UserBean.class);
        
        DBConnector dbc = new DBConnector();
        listOfShifts = dbc.getCompanyShiftsList(u.getEmployee().getEmployeeCompany().getCompany_id(), u.getStart(), u.getEnd());
        
        return listOfShifts;
    }

    public void setListOfShifts(ArrayList<Shift> listOfShifts) {
        this.listOfShifts = listOfShifts;
    }
    
    
    public void removeSelected(Shift ss){
        DBConnector dbc = new DBConnector();
        dbc.deleteShift(ss.getShift_id());
    }
    
}
