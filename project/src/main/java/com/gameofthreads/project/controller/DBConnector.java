package com.gameofthreads.project.controller;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;



/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
public class DBConnector {
    private final String serverName;
    private final int portNumber;
    private final String user;
    private final String password;
    private final String databaseName;
    private final MysqlDataSource dataSource;

    public DBConnector(){
        this.serverName = "localhost";
        this.portNumber = 3306;
        this.user = "root";
        this.password = "dbPassword";
        this.databaseName = "esdb";
        this.dataSource = new MysqlDataSource();
        connectDataSource();
    }
    
    public DBConnector(String serverName, int portNumber, String user, String password, String databaseName) {
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.user = user;
        this.password = password;
        this.databaseName = databaseName;
        this.dataSource = new MysqlDataSource();
        connectDataSource();
    }
    
    public final void connectDataSource(){
        String url = String.format(
                "jdbc:mysql://%s:%d/%s?allowPublicKeyRetrieval=true&useSSL=false",
                this.serverName,
                this.portNumber,
                this.databaseName);
        dataSource.setURL(url);
        dataSource.setUser(this.user);
        dataSource.setPassword(this.password);        
    }
    
    /**
     * Function to close result set, statement, and connection. Always call this after executing a query
     * @param rs ResultSet that reads the result of a query
     * @param st Statement that executes the query
     * @param cn Connection obtained from data source
     */
    private void close(ResultSet rs, Statement st, Connection cn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (st != null){
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (cn != null){
            try {
                cn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Company getCompanyByID(Integer companyID) {
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        Company queriedCompany = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT * FROM company WHERE "
                            + "company_id = %d;", 
                    companyID);
            result = dbStatement.executeQuery(query);

            queriedCompany = new Company();

            while (result.next()){
                queriedCompany.setCompany_id(result.getInt("company_id"));
                queriedCompany.setCompany_name(result.getString("company_name"));
                queriedCompany.setCompany_hours(new WeeklyTimes(result.getString("regular_hours")));
            }
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        
        return queriedCompany;
    }
    
    public ArrayList<Shift> getShiftsByEmployee(Integer employeeID) {
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        ArrayList<Shift> shifts = null;
        
        try{
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT * FROM shift WHERE "
                            + "employee_id_worker = %d;", 
                    employeeID);
            result = dbStatement.executeQuery(query);

            shifts = new ArrayList<>();

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
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        
        return shifts;
    }
    
    public Employee getEmployeeByID(Integer employeeID, Integer companyID) throws SQLException{
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        Employee queriedEmployee = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT * FROM employee WHERE "
                            + "employee_id = %d AND "
                            + "company_id = %d;", 
                    employeeID, companyID);
        
            result = dbStatement.executeQuery(query);
        
            queriedEmployee = new Employee();
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
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        return queriedEmployee;
    }
    
    public Employee getEmployeeByLogin(String username, String password) throws SQLException{
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        Employee queriedEmployee = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            System.out.println("Connection established and statement issued");
            String query = String.format(
                    "SELECT * FROM employee WHERE "
                            + "username = '%s' AND "
                            + "password = (SELECT SHA2('%s', 224));", 
                    username, password);

            System.out.println("Using query: " + query);

            result = dbStatement.executeQuery(query);

            queriedEmployee = new Employee();
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
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        
        return queriedEmployee;
    }
    
    public ArrayList<Employee> getEmployeesInCompany(Integer companyID){
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        ArrayList<Employee> employees = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT employee_id, employee_name FROM employee WHERE "
                            + "company_id = %d;", 
                    companyID);

            result = dbStatement.executeQuery(query);
            
            employees = new ArrayList<>();
            
            while (result.next()){
                Integer employeeID = result.getInt("employee_id");

                Employee currentEmployee = this.getEmployeeByID(employeeID, companyID);
                employees.add(currentEmployee);
        
            
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return employees;
    }
    
    public TimelineModel<String, ?> getCompanyShifts(Integer companyID, LocalDateTime startDate, LocalDateTime endDate) throws SQLException{
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        TimelineModel<String, ?> model = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT employee_id, employee_name FROM employee WHERE "
                            + "company_id = %d;", 
                    companyID);

            result = dbStatement.executeQuery(query);
            
            model = new TimelineModel<>();
            
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
                                .group(currentEmployee.getName() + " (ID: " + currentEmployee.getEmployeeID() + ")")
                                .editable(false)
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
                            .data("Scheduled (ID: " + s.getShift_id() + ")")
                            .startDate(s.getStartTime())
                            .endDate(s.getEndTime())
                            .group(currentEmployee.getName() + " (ID: " + currentEmployee.getEmployeeID() + ")")
                            .styleClass("scheduled")
                            .build();

                    model.add(e);
                }
            }    
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        return model;
    }
    
    public ArrayList<Shift> getCompanyShiftsList(Integer companyID, LocalDateTime startDate, LocalDateTime endDate){
        Connection dbConnection = null;
        Statement dbStatement = null;
        ResultSet result = null;
        ArrayList<Shift> shiftList = null;
        
        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "SELECT employee_id, employee_name FROM employee WHERE "
                            + "company_id = %d;", 
                    companyID);

            result = dbStatement.executeQuery(query);
            
            shiftList = new ArrayList<>();
            while (result.next()){
                Integer employeeID = result.getInt("employee_id");

                Employee currentEmployee = this.getEmployeeByID(employeeID, companyID);
                ArrayList<Shift> thisEmpShifts = currentEmployee.getEmployeeShifts();
                for(Shift s: thisEmpShifts){

                    if(s.getStartTime().isAfter(endDate) || s.getEndTime().isBefore(startDate))
                        continue;
                    
                    shiftList.add(s);
                }
            }
        } catch (SQLException ex){
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(result, dbStatement, dbConnection);
        }
        
        return shiftList;

    }
    
    public void insertShift(Shift s){
        Connection dbConnection = null;
        Statement dbStatement = null;

        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "INSERT INTO shift "
                    + "(start_time, end_time, employee_id_manager, employee_id_worker, notes) "
                    + "VALUES ('%s', '%s', %d, %d, '%s'); ",
                    s.getStartTime().toString(),
                    s.getEndTime().toString(),
                    s.getEmployee_id_manager(),
                    s.getEmployee_id_worker(),
                    s.getNotes()
            );
            dbStatement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            this.close(null, dbStatement, dbConnection);
            
        } 
    }
    
    public void deleteShift(Integer shiftID){
        Connection dbConnection = null;
        Statement dbStatement = null;

        try {
            dbConnection = dataSource.getConnection();
            dbStatement = dbConnection.createStatement();
            String query = String.format(
                    "DELETE FROM shift WHERE(shift_id = %d)",
                    shiftID
            );
            dbStatement.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            this.close(null, dbStatement, dbConnection);
            
        } 
    }
}
