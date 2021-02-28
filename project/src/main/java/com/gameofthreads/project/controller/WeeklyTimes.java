package com.gameofthreads.project.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Prahar Ijner
 * @author Travis MacDonald
 */
public class WeeklyTimes {
    public class StartEndTimes{

        public StartEndTimes(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
        
        public StartEndTimes(String startTime, String endTime) {
            this.startTime = LocalTime.parse(startTime);
            this.endTime = LocalTime.parse(endTime);
        }
        
        
        private LocalTime startTime;
        private LocalTime endTime;

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }
        
        
    }
    
    
    private Map<String, ArrayList<StartEndTimes>> times;

    public WeeklyTimes() {
        this.times = new HashMap<>();
        this.clearTimes();
    }
    
    public WeeklyTimes(String jsonString){
        this.times = new HashMap<>();
        this.clearTimes();
        
        String[] daysTimes = jsonString.split(",");
        for(String dT: daysTimes){
            String[] dayAndTimes = dT.split(": ");
            
            // thisDay represents the day of the week
            String thisDay = dayAndTimes[0].substring(2, dayAndTimes[0].length()-1);
            
            // the times for each day are in the format "start-end start2-end2"
            // quotes are included
            // times can be empty
            Pattern p = Pattern.compile("[0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2}");
            Matcher m = p.matcher(dayAndTimes[1]);
            while(m.find()){
                String[] startEnd = m.group().split("-");
                LocalTime startTime = LocalTime.parse(startEnd[0]);
                LocalTime endTime = LocalTime.parse(startEnd[1]);
                
                this.addTime(thisDay, startTime, endTime);
            }
        }
    }
    
    public final void clearTimes(){
        this.times.clear();
        this.times.put("Monday", new ArrayList<>());
        this.times.put("Tuesday", new ArrayList<>());
        this.times.put("Wednesday", new ArrayList<>());
        this.times.put("Thursday", new ArrayList<>());
        this.times.put("Friday", new ArrayList<>());
        this.times.put("Saturday", new ArrayList<>());
        this.times.put("Sunday", new ArrayList<>());
    }
    
    public final void addTime(String day, LocalTime startTime, LocalTime endTime){
        ArrayList<StartEndTimes> currentAvailablity = this.times.get(day);
        boolean mergeFlag = false;
        
        for(StartEndTimes seTime: currentAvailablity){
            if (seTime.getEndTime().isAfter(startTime)){
                seTime.setEndTime(endTime);
                mergeFlag = true;
                break;
            }
            else if (seTime.getStartTime().isBefore(endTime)){
                seTime.setStartTime(startTime);
                mergeFlag = true;
                break;
            }
        }
        if (!mergeFlag)
            currentAvailablity.add(new StartEndTimes(startTime, endTime));
    }
    
    public ArrayList<StartEndTimes> getTimesOnDay(String day){
        return this.times.get(day);
    }
    
}
