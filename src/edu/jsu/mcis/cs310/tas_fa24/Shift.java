package edu.jsu.mcis.cs310.tas_fa24;


import java.util.Map;

public class Shift {

    private final String id;
    private final String description;
    private final String startTime;
    private final String stopTime;
    
    private final String roundInterval;
    private final String gracePeriod;
    private final String dockPenalty;
    private final String lunchStart;
    private final String lunchStop;
    private final String lunchDuration;
    private final String shiftDuration;
    
    public Shift(Map<String, String> parameters){
        this.id = parameters.get("id");
        this.description = parameters.get("description");
        this.startTime = parameters.get("startTime");
        this.stopTime = parameters.get("stopTime");
        this.roundInterval = parameters.get("roundinterval");
        this.gracePeriod = parameters.get("graceperiod");
        this.dockPenalty = parameters.get("dockpenalty");
        //this.otherParameter = parameters.get("otherParameter");
        this.lunchStart = parameters.get("lunchstart");
        this.lunchStop = parameters.get("lunchstop");
        this.lunchDuration = parameters.get("lunchDuration");
        this.shiftDuration = parameters.get("shiftDuration");
        
    }
    
    public String getStarted(){
        System.out.println("here");
        return startTime;

    }
   
    public String getStopTime(){
        return stopTime;
    }
    
    public String getRoundedInterval(){
        return roundInterval;
    }
    
    public String getGracePeriod(){
        return gracePeriod;
    }
    
    public String getDockPenalty(){
        return dockPenalty;
    }
    
    public String getLunchStart(){
        return lunchStart;
    }
    
    public String getLunchStop(){
        return lunchStop;
    }
   
   // public String getOtherParameters(){
        //return otherParameter;
    //}
    
    public String getLunchDuration(){
        return lunchDuration;
    }
    
    public String getShiftDuration(){
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
        
        System.out.println(sb.toString());
        return sb.toString();




    }
}