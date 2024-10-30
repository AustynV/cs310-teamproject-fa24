package edu.jsu.mcis.cs310.tas_fa24;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Shift {

    private final int id;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime stopTime;
    
    private final LocalDateTime roundInterval;
    private final LocalDateTime gracePeriod;
    private final LocalDateTime dockPenalty;
    private final LocalDateTime lunchStart;
    private final LocalDateTime lunchStop;
    private final LocalDateTime lunchDuration;
    private final LocalDateTime shiftDuration;
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");    
    
    public Shift(Map<String, Object> parameters){
        this.id = (int) parameters.get(Integer.valueOf("id"));
        this.description = parameters.get("description").toString();
        this.startTime = (LocalDateTime) parameters.get(LocalDate.parse("startTime", formatter));
        
        this.stopTime = (LocalDateTime) parameters.get(LocalDate.parse("stopTime", formatter));
        this.roundInterval = (LocalDateTime) parameters.get(LocalDate.parse("roundinterval",formatter));
        this.gracePeriod = (LocalDateTime) parameters.get(LocalDate.parse("graceperiod", formatter));
        this.dockPenalty = (LocalDateTime) parameters.get(LocalDate.parse("dockpenalty", formatter));
        //this.otherParameter = parameters.get("otherParameter");
        this.lunchStart = (LocalDateTime) parameters.get(LocalDate.parse("lunchstart", formatter));
        this.lunchStop = (LocalDateTime) parameters.get(LocalDate.parse("lunchstop", formatter));
        this.lunchDuration = (LocalDateTime) parameters.get(LocalDate.parse("lunchDuration", formatter));
        this.shiftDuration = (LocalDateTime) parameters.get(LocalDate.parse("shiftDuration", formatter));
        
    }
    
    public LocalDateTime getStarted(){
        System.out.println("here");
        return startTime;

    }
   
    public LocalDateTime getStopTime(){
        return stopTime;
    }
    
    public LocalDateTime getRoundedInterval(){
        return roundInterval;
    }
    
    public LocalDateTime getGracePeriod(){
        return gracePeriod;
    }
    
    public LocalDateTime getDockPenalty(){
        return dockPenalty;
    }
    
    public LocalDateTime getLunchStart(){
        return lunchStart;
    }
    
    public LocalDateTime getLunchStop(){
        return lunchStop;
    }
   
   // public String getOtherParameters(){
        //return otherParameter;
    //}
    
    public LocalDateTime getLunchDuration(){
        return lunchDuration;
    }
    
    public LocalDateTime getShiftDuration(){
        return shiftDuration;
    }
    
    @Override
    
    public String toString(){

        
        //Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)
        StringBuilder sb = new StringBuilder();
        sb.append("Shift ");
        sb.append(id).append(": ");
        sb.append(startTime).append(" - ").append(stopTime);
        sb.append(" (").append(shiftDuration).append(");");
        sb.append(" Lunch: ");
        sb.append(lunchStart).append(" - ").append(lunchStop);
        sb.append(" (").append(lunchDuration).append(")");
        System.out.println("hello");
        System.out.println(sb.toString());
        return sb.toString();




    }
}