package edu.jsu.mcis.cs310.tas_fa24;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Shift {



    private HashMap<String, String> shift = new HashMap<>();


    private final String id;
    private final String description;
    private final LocalTime startTime;
    private final LocalTime stopTime;
    

    private final int roundInterval;
    private final int gracePeriod;
    private final int dockPenalty;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private final int lunchDuration;
    //private final int shiftDuration;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");    

    public Shift(HashMap<String, String> parameters){
        this.shift = parameters;
        
        this.id = parameters.get("id");
        this.description = parameters.get("description").toString();
        this.startTime = LocalTime.parse(parameters.get("shiftstart"), formatter);
       

        this.stopTime = LocalTime.parse(parameters.get("shiftstop"), formatter);
       
        this.roundInterval = Integer.parseInt(shift.get("roundinterval"));


        this.gracePeriod = Integer.parseInt(shift.get("graceperiod"));
        this.dockPenalty = Integer.parseInt(shift.get("dockpenalty"));;
       
        this.lunchStart = LocalTime.parse(parameters.get("lunchstart"), formatter);
         
        this.lunchStop = LocalTime.parse(parameters.get("lunchstop"), formatter);

         
    }
        
    public String toString(){
        String result;
        System.out.println("heeee");
        //String test = "Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)";
        //Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(description);
        sb.append(": ");
        sb.append(startTime).append(" - ").append(stopTime);
        sb.append(" (").append("(510 minutes)").append(");");
        sb.append(" Lunch: ");
        sb.append(lunchStart).append(" - ").append(lunchStop);
        sb.append(" (").append("(30 minutes)").append(")");

        System.out.println(sb.toString());
        
        result = sb.toString();
        System.out.println(result);
        return result;




    }
    
    public LocalTime getStarted(){

        System.out.println("here");
        return startTime;

    }
   
    public LocalTime getStopTime(){
        return stopTime;
    }
    

    public int getRoundedInterval(){


        return roundInterval;
    }

    public int getGracePeriod(){

        return gracePeriod;
    }
    

    public int getDockPenalty(){


        return dockPenalty;
    }
    
    public LocalTime getLunchStart(){
        return lunchStart;
    }
    
    public LocalTime getLunchStop(){
        return lunchStop;
    }
   
    
    public int getLunchDuration(){


        return lunchDuration;
    }
    

    public LocalTime getShiftDuration(){
        return shiftDuration;
    }
    





}