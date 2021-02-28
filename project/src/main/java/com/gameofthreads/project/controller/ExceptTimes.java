package com.gameofthreads.project.controller;

import java.time.LocalDateTime;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
public class ExceptTimes {
    private Integer et_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public Integer getEt_id() {
        return et_id;
    }

    public void setEt_id(Integer et_id) {
        this.et_id = et_id;
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
    
    // TODO: implement a from string method or constructor
    
}
