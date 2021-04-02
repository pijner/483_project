package com.gameofthreads.project.controller;

import java.time.LocalDateTime;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
public class Shift {
    private Integer shift_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Integer employee_id_worker;
    private Integer employee_id_manager;
    private String notes;

    public Shift() {
    }
    
    public Integer getShift_id() {
        return shift_id;
    }

    public void setShift_id(Integer shift_id) {
        this.shift_id = shift_id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Integer getEmployee_id_worker() {
        return employee_id_worker;
    }

    public void setEmployee_id_worker(Integer employee_id_worker) {
        this.employee_id_worker = employee_id_worker;
    }

    public Integer getEmployee_id_manager() {
        return employee_id_manager;
    }

    public void setEmployee_id_manager(Integer employee_id_manager) {
        this.employee_id_manager = employee_id_manager;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
