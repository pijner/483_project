/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project.controller;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;



/**
 *
 * @author Prahar
 */
public class DBConnector {
    private String serverName;
    private int portNumber;
    private String user;
    private String password;
    private String databaseName;

    public DBConnector(){
        this.serverName = "localhost";
        this.portNumber = 3306;
        this.user = "root";
        this.password = "dbPassword";
        this.databaseName = "esdb";
    }
    
    public DBConnector(String serverName, int portNumber, String user, String password, String databaseName) {
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
    }
    
    public Statement getStatement(){
        MysqlDataSource dataSource = new MysqlDataSource();
        
//        dataSource.setServerName(this.serverName);
//        dataSource.setPortNumber(this.portNumber);
//        dataSource.setDatabaseName(this.databaseName);

        // Temporary Solution
        dataSource.setURL("jdbc:mysql://localhost:3306/esdb?useSSL=false");
        dataSource.setUser(this.user);
        dataSource.setPassword(this.password);
        
        try {
            Connection dbConnection = dataSource.getConnection();
            Statement dbStatement = dbConnection.createStatement();
            
            return dbStatement;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Company getCompanyByID(Integer companyID) throws SQLException{
        
        Statement myStatement = this.getStatement();
        String query = String.format(
                "SELECT * FROM company WHERE "
                        + "company_id = %d;", 
                companyID);
        ResultSet result = myStatement.executeQuery(query);
        
        Company queriedCompany = new Company();
        
        while (result.next()){
            queriedCompany.setCompany_id(result.getInt("company_id"));
            queriedCompany.setCompany_name(result.getString("company_name"));
            queriedCompany.setCompany_hours(new WeeklyTimes(result.getString("regular_hours")));
            
            return queriedCompany;
        }
        return null;
    }
    
    public ArrayList<Shift> getShiftsByEmployee(Integer employeeID) throws SQLException{
        Statement myStatement = this.getStatement();
        String query = String.format(
                "SELECT * FROM shift WHERE "
                        + "employee_id_worker = %d;", 
                employeeID);
        ResultSet result = myStatement.executeQuery(query);
        
        ArrayList<Shift> shifts = new ArrayList<>();
        
        while (result.next()){
            Shift currentShift = new Shift();
            
            currentShift.setEmployee_id_worker(result.getInt("employee_id_worker"));
            currentShift.setEmployee_id_manager(result.getInt("employee_id_manager"));
            
            Timestamp startTime = result.getTimestamp("start_time");
            Timestamp endTime = result.getTimestamp("end_time");
            
            currentShift.setStartTime(startTime.toLocalDateTime());
            currentShift.setEndTime(endTime.toLocalDateTime());
            
            try{
                Timestamp checkIn = result.getTimestamp("check_in");
                Timestamp checkOut = result.getTimestamp("check_out");
                
                currentShift.setCheckInTime(checkIn.toLocalDateTime());
                currentShift.setCheckOutTime(checkOut.toLocalDateTime());
            } catch (NullPointerException e){
                // If there is no check in date, we don't set it in the object
            }
            
            currentShift.setShift_id(result.getInt("shift_id"));
            currentShift.setNotes(result.getString("notes"));
            
            shifts.add(currentShift);
        }
        System.out.println("Shifts added");
        
        return shifts;
    }
    
    public Employee getEmployeeByID(Integer employeeID, Integer companyID) throws SQLException{
        Statement myStatement = this.getStatement();
        String query = String.format(
                "SELECT * FROM employee WHERE "
                        + "employee_id = %d AND "
                        + "company_id = %d;", 
                employeeID, companyID);
        
        ResultSet result = myStatement.executeQuery(query);
        
        Employee queriedEmployee = new Employee();
        while (result.next()){
            queriedEmployee.setUsername(result.getString("username"));
            queriedEmployee.setEmployeeID(employeeID);
            queriedEmployee.setName(result.getString("employee_name"));
            queriedEmployee.setEmployeeType(result.getBoolean("employee_type"));
            queriedEmployee.setManagerAccess(result.getBoolean("manager_access"));
            queriedEmployee.setEmployeeCompany(this.getCompanyByID(companyID));
            queriedEmployee.setManagerID(result.getInt("manager_id"));
            
            // TODO: figure out a way to add ExceptionTimes
            
            ArrayList<Shift> employeeShifts = this.getShiftsByEmployee(queriedEmployee.getEmployeeID());
            queriedEmployee.setEmployeeShifts(employeeShifts);
            
            String availableTimes = result.getString("available_hours");
            WeeklyTimes weeklyTimes = new WeeklyTimes(availableTimes);
            queriedEmployee.setAvailable_hours(weeklyTimes);
        
        
        }
        
        return queriedEmployee;
    }
    
    public Employee getEmployeeByLogin(String username, String password) throws SQLException{
        Statement myStatement = this.getStatement();
        System.out.println("Connection established and statement issued");
        String query = String.format(
                "SELECT * FROM employee WHERE "
                        + "username = '%s' AND "
                        + "password = (SELECT SHA2('%s', 224));", 
                username, password);
        
        System.out.println("Using query: " + query);
        
        ResultSet result = myStatement.executeQuery(query);
        
        Employee queriedEmployee = new Employee();
        while (result.next()){
            System.out.println("Employee found!");
            queriedEmployee.setUsername(username);
            queriedEmployee.setEmployeeID(result.getInt("employee_id"));
            System.out.println("ID added!");
            queriedEmployee.setName(result.getString("employee_name"));
            System.out.println("Name added!");
            queriedEmployee.setEmployeeType(result.getBoolean("employee_type"));
            System.out.println("Type added!");
            queriedEmployee.setManagerAccess(result.getBoolean("manager_access"));
            System.out.println("Access level added!");
            
            queriedEmployee.setManagerID(result.getInt("manager_id"));
            System.out.println("Manager ID added!");
            
            Integer companyID = result.getInt("company_id");
            queriedEmployee.setEmployeeCompany(this.getCompanyByID(companyID));
            System.out.println("Company found!");
            // TODO: figure out a way to add ExceptionTimes
            
            ArrayList<Shift> employeeShifts = this.getShiftsByEmployee(queriedEmployee.getEmployeeID());
            queriedEmployee.setEmployeeShifts(employeeShifts);
            System.out.println("Shifts added!");
            
            String availableTimes = result.getString("available_hours");
            System.out.println(availableTimes);
            WeeklyTimes weeklyTimes = new WeeklyTimes(availableTimes);
            queriedEmployee.setAvailable_hours(weeklyTimes);
            System.out.println("Available hours added!");
        }
        
        return queriedEmployee;
    }
    
    public TimelineModel<String, ?> getCompanyShifts(Integer companyID, LocalDateTime startDate, LocalDateTime endDate) throws SQLException{
        TimelineModel<String, ?> model = new TimelineModel<>();
        
        Statement myStatement = this.getStatement();
        String query = String.format(
                "SELECT employee_id, employee_name FROM employee WHERE "
                        + "company_id = %d;", 
                companyID);
        
        ResultSet result = myStatement.executeQuery(query);
        
        while (result.next()){
            Integer employeeID = result.getInt("employee_id");
            
            Employee currentEmployee = this.getEmployeeByID(employeeID, companyID);
            
            // Add availablity
            LocalDateTime currentDate = startDate;
            long numDays = startDate.until(endDate, ChronoUnit.DAYS);
            for(long i=0; i<numDays; i++){
                String day = currentDate.getDayOfWeek().toString().toLowerCase();
                day = day.substring(0, 1).toUpperCase() + day.substring(1);
                
                ArrayList<WeeklyTimes.StartEndTimes> availablity = currentEmployee.getAvailable_hours().getTimesOnDay(day);
                for(WeeklyTimes.StartEndTimes currentAvailablity: availablity){
                    LocalDateTime availablityStart = currentAvailablity.getStartTime().atDate(currentDate.toLocalDate());
                    LocalDateTime availablityEnd = currentAvailablity.getEndTime().atDate(currentDate.toLocalDate());
                    
                    TimelineEvent e = TimelineEvent.builder()
                            .data("Available")
                            .startDate(availablityStart)
                            .endDate(availablityEnd)
                            .group(currentEmployee.getName())
                            .styleClass("available")
                            .build();
            
                    model.add(e);
                }
                
                currentDate = currentDate.plusDays(1);
            }
            
            ArrayList<Shift> thisEmpShifts = currentEmployee.getEmployeeShifts();
            for(Shift s: thisEmpShifts){
                
                if(s.getStartTime().isAfter(endDate) || s.getEndTime().isBefore(startDate))
                    continue;
                
                TimelineEvent e = TimelineEvent.builder()
                        .data("Scheduled")
                        .startDate(s.getStartTime())
                        .endDate(s.getEndTime())
                        .group(currentEmployee.getName())
                        .styleClass("scheduled")
                        .build();

                model.add(e);
            }
        }
        
        return model;
    }
}
