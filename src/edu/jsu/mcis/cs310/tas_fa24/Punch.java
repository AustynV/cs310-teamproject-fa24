/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;

public class Punch {
    
    private Integer id;
    private int terminalId;
    private Badge badge;
    private LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp;
    private EventType punchType;
    public static PunchAdjustmentType adjustmentType;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
    
    //constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType){
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
        this.adjustedTimestamp = null;
        this.adjustmentType = null;
        //this.id = null;
    }
    
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType){
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
        this.punchType = punchType;
        this.adjustedTimestamp = null;
        this.adjustmentType = null;
    }
    
    
    
    public Integer getId(){
        return id;
    }
    
    public int getTerminalid(){
        return terminalId;
    }
    
    public Badge getBadge(){
        return badge;
    }
    
    public LocalDateTime getOriginaltimestamp(){
        return originalTimestamp;
    }
    
    public LocalDateTime getAdjustedTimestamp(){
        return adjustedTimestamp;
    }
    
    public EventType getPunchtype(){
        return punchType;
    }
    
    public void setAdjustedTimestamp (LocalDateTime adjustedTimestamp){
        this.adjustedTimestamp = adjustedTimestamp;
    }
    
    public String printOriginal() {
        
        String formattedTimestamp = originalTimestamp.format(FORMATTER);
        
        String[] parts = formattedTimestamp.split(" ");
        parts[0] = parts[0].toUpperCase();
        
        return "#" + badge.getId() + " " + punchType + ": " + String.join(" ", parts);
        
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        //String formattedTimestamp = originalTimestamp.format(formatter);
        //return "#" + badge.getId() + " " + punchType + ": " + formattedTimestamp;
    }
    
    public String printAdjusted() {
        if (adjustedTimestamp != null) {
            String formattedAdjustedTimestamp = adjustedTimestamp.format(FORMATTER);
            return "#" + badge.getId() + " " + punchType + ": " + formattedAdjustedTimestamp + " (" + adjustmentType + ")";
        } else {
            return "Adjusted timestamp not set";
        }
    }
    
     private String formatPunchType() {
        switch (punchType) {
            case CLOCK_IN:
                return "CLOCK IN";
            case CLOCK_OUT:
                return "CLOCK OUT";
            case TIME_OUT:
                return "TIME OUT";
            default:
                return "UNKNOWN";
        }
    }
    
     public String toString() {
        return printOriginal();
    }
     
    public void adjust (Shift s){
        
        LocalDateTime original = originalTimestamp;
        DayOfWeek day = original.getDayOfWeek();
        
        
    }
    
}
