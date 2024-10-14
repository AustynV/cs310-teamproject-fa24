/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;


import java.util.Map;

public class Shift {
    private final String startTime;
    private final String stopTime;
    private final String otherParameter;
    private final int lunchDuration;
    private final int shiftDuration;
    
    public Shift(Map<String, String> parameters){
        this.startTime = parameters.get("startTime");
        this.stopTime = parameters.get("stopTime");
        this.otherParameter = parameters.get("otherParameter");
        this.lunchDuration = Integer.parseInt(parameters.get("lunchDuration"));
        this.shiftDuration = Integer.parseInt(parameters.get("shiftDuration"));
    }
    
    public String getStarted(){
        return startTime;
    }
    
    public String getStopTime(){
        return stopTime;
    }
    
    public String getOtherParameters(){
        return otherParameter;
    }
    
    public int getLunchDuration(){
        return lunchDuration;
    }
    
    public int getShiftDuration(){
        return shiftDuration;
    }
    
    public String toString(){
        return "Shift{" + "startTime='" + startTime + '\'' + ", stopTime='" + stopTime + '\'' +
                ", otherParameter='" + otherParameter + '\'' + ", lunchDuration=" + lunchDuration + 
                ", shiftDuration=" + shiftDuration +'}';
    }
}