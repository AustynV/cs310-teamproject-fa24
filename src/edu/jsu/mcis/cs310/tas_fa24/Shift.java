/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;


import java.util.Map;

public class Shift {
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
        return "Shift{" + "shiftStart='" + shiftStart + '\'' + ", shiftStop='" + shiftStop + '\'' +
                ", otherParameter='" + otherParameter + '\'' + ", lunchDuration=" + lunchDuration + 
                ", shiftDuration=" + shiftDuration +'}';
    }
}