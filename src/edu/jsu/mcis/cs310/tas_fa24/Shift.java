package edu.jsu.mcis.cs310.tas_fa24;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Shift {

    private final String id;
    private final String description;
    private final LocalTime startTime;
    private final LocalTime stopTime;
    
    private final LocalTime roundInterval;
    private final LocalTime gracePeriod;
    private final LocalTime dockPenalty;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private final LocalTime lunchDuration;
    private final LocalTime shiftDuration;
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");    
    
    public Shift(Map<String, String> parameters){
        
        this.id = parameters.get("id");
        this.description = parameters.get("description").toString();
        this.startTime = LocalTime.parse(parameters.get("shiftstart"), formatter);
        
        
        this.stopTime = LocalTime.parse(parameters.get("stopTime"), formatter);
        this.roundInterval = LocalTime.parse(parameters.get("roundinterval"),formatter);
        this.gracePeriod = LocalTime.parse(parameters.get("graceperiod"), formatter);
        this.dockPenalty = LocalTime.parse(parameters.get("dockpenalty"), formatter);
        //this.otherParameter = parameters.get("otherParameter");
        this.lunchStart = LocalTime.parse(parameters.get("lunchstart"), formatter);
        this.lunchStop = LocalTime.parse(parameters.get("lunchstop"), formatter);
        this.lunchDuration = LocalTime.parse(parameters.get("lunchDuration"), formatter);
        this.shiftDuration = LocalTime.parse(parameters.get("shiftDuration"), formatter);
        
    }
    
    public LocalTime getStarted(){
        System.out.println("here");
        return startTime;

    }
   
    public LocalTime getStopTime(){
        return stopTime;
    }
    
    public LocalTime getRoundedInterval(){
        return roundInterval;
    }
    
    public LocalTime getGracePeriod(){
        return gracePeriod;
    }
    
    public LocalTime getDockPenalty(){
        return dockPenalty;
    }
    
    public LocalTime getLunchStart(){
        return lunchStart;
    }
    
    public LocalTime getLunchStop(){
        return lunchStop;
    }
   
    
    public LocalTime getLunchDuration(){
        return lunchDuration;
    }
    
    public LocalTime getShiftDuration(){
        return shiftDuration;
    }
    
    @Override
    
    public String toString(){
        System.out.println("heeee");
        String test = "Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)";
        //Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)
        StringBuilder sb = new StringBuilder();
        sb.append("Shift ");
        //sb.append(id).append(": ");
        sb.append(startTime).append(" - ").append(stopTime);
        sb.append(" (").append(shiftDuration).append(");");
        sb.append(" Lunch: ");
        sb.append(lunchStart).append(" - ").append(lunchStop);
        sb.append(" (").append(lunchDuration).append(")");
        System.out.println("hello");
        System.out.println(sb.toString());
        return test;




    }
}