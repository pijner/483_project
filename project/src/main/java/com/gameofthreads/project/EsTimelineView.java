/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@Named("esTimelineView")
@ViewScoped
public class EsTimelineView implements Serializable {  
  
    private TimelineModel<String, ?> model;
    private LocalDateTime start;
    private LocalDateTime end;
    
    private boolean isEditable;
  
    @PostConstruct  
    public void init() {  
        isEditable = false;
        
        // set initial start / end dates for the axis of the timeline  
        start = LocalDate.of(-140, 1, 1).atStartOfDay();
        end = LocalDate.of(-140, 1, 2).atStartOfDay();

        // groups  
        String[] NAMES = new String[] {"User 1", "User 2", "User 3", "User 4", "User 5", "User 6"};  
  
        // create timeline model  
        model = new TimelineModel<>();
  
        for (String name : NAMES) {
            LocalDateTime end = start.minusHours(12).withMinute(0).withSecond(0).withNano(0);

            for (int i = 0; i < 5; i++) {
                LocalDateTime start = end.plusHours(Math.round(Math.random() *10));
                end = start.plusHours(4 + Math.round(Math.random() *5));
                   
                Random rng = new Random();
                
                int r = rng.nextInt(2);
                String availability = (r == 0 ? "Scheduled" : "Unavailable");  
  
                // create an event with content, start / end dates, editable flag, group name and custom style class
                TimelineEvent event = TimelineEvent.builder()
                        .data(availability)
                        .startDate(start)
                        .endDate(end)
                        .editable(false)
                        .group(name)
                        .styleClass(availability.toLowerCase())
                        .build();

                model.add(event);
            }  
        }  
    }  
  
    public TimelineModel<String, ?> getModel() {
        return model;  
    }  
  
    public LocalDateTime getStart() {
        return start;  
    }  
  
    public LocalDateTime getEnd() {
        return end;  
    }
    
    public boolean getIsEditable() {
        return isEditable;
    }
    
    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
    
    public void onEditButtonClick() {
        isEditable = !isEditable;
    }
}