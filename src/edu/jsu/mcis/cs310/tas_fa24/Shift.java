/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;


import java.util.Map;

public class Shift {

    private final String id;
    private final String description;
    private final String startTime;
    private final String stopTime;
    
    private final String roundinterval;
    private final String graceperiod;
    private final String dockpenalty;
    private final String lunchStart;
    private final String lunchStop;
    private final String lunchDuration;
    private final String shiftDuration;
    
    public Shift(Map<String, String> parameters){
        this.id = parameters.get("id");
        this.description = parameters.get("description");
        this.startTime = parameters.get("startTime");
        this.stopTime = parameters.get("stopTime");
        this.roundinterval = parameters.get("roundinterval");
        this.graceperiod = parameters.get("graceperiod");
        this.dockpenalty = parameters.get("dockpenalty");
        //this.otherParameter = parameters.get("otherParameter");
        this.lunchStart = parameters.get("lunchstart");
        this.lunchStop = parameters.get("lunchstop");
        this.lunchDuration = parameters.get("lunchDuration");
        this.shiftDuration = parameters.get("shiftDuration");
        
    }
    
    public String getStarted(){
        System.out.println("here");
        return startTime;

    private final String shiftStart;
    private final String shiftStop;
    private final String otherParameter;
    private final int lunchDuration;
    private final int shiftDuration;
    
    public Shift(Map<String, String> parameters){
        this.shiftStart = parameters.get("shiftStart");
        this.shiftStop = parameters.get("shiftStop");
        this.otherParameter = parameters.get("otherParameter");
        this.lunchDuration = Integer.parseInt(parameters.get("lunchDuration"));
        this.shiftDuration = Integer.parseInt(parameters.get("shiftDuration"));
    }
    
    public String getStarted(){
        return shiftStart;
    }
    
    public String getStopTime(){
        return shiftStop;
    }
    
   // public String getOtherParameters(){
        //return otherParameter;
    //}
    
    //public int getLunchDuration(){
    //    return lunchDuration;
    //}
    
    //public int getShiftDuration(){
     //   return shiftDuration;
    //}
    
    @Override
    
    public String toString(){

        
        //Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)
        StringBuilder sb = new StringBuilder();
        sb.append("Shift ");
        sb.append(id).append(": ");
        sb.append(startTime).append(" - ").append(stopTime);
        
        System.out.println(sb.toString());
        return sb.toString();

        //return "Shift{" + "startTime='" + startTime + '\'' + ", stopTime='" + stopTime + '\'' +
          //      ", otherParameter='" + '\'' + ", lunchDuration=" + lunchDuration + 
            //    ", shiftDuration=" + shiftDuration +'}';

        return "Shift{" + "shiftStart='" + shiftStart + '\'' + ", shiftStop='" + shiftStop + '\'' +
                ", otherParameter='" + otherParameter + '\'' + ", lunchDuration=" + lunchDuration + 
                ", shiftDuration=" + shiftDuration +'}';

    }
}