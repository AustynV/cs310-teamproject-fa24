/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Punch {
    private int id;
    private final int terminalId;
    private final Badge badge;
    private final LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private final EventTime punchtype;
    private PunchAdjustmentType adjustmenttype;
    private LunchStatus adjustedlunchstatus;
    
    public enum LunchStatus {
        HAPPENING, HAPPENED, NOT_HAPPENING, INAPPLICABLE
    
        
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originaltimestamp = originaltimestamp;
        this.punchtype = punchtype;   
    }
    
    public int getid(){
        return id;
    }
    
    public int getTerminalid(){
        return terminalId;
    }
    
    public Badge getBadge(){
        return badge;
    }
    
    public EventType getPunchtype(){
        return punchtype;
    }
    
    public LocalDateTime getOriginaltimestamp(){
        return originaltimestamp;
    }
    
    public PunchAdjustmentType getAdjustmentType(){
        return adjustedlunchstatus;
    }
    
    public String printAdjusted (){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String dateText = originaltimestamp.format(formatter);
        
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText_two = adjustedtimestamp.format(formatter);
        
        String dayOfWeek = originaltimestamp.getDayOfWeek().toString().substring(0, 3).toUpperCase();
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badge.getId()).append(" ");
        s.append(punchtype).append(": ").append(dayOfWeek).append(" ").append(dateText).append(" ").append(timeText_two).append(" (").append(adjustmenttype).append(")");
        
        return s.toString();
    }
    
    public String printOriginal(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String dateText = originaltimestamp.format(formatter);
        
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText_two = adjustedtimestamp.format(formatter);
        
        String dayOfWeek = originaltimestamp.getDayOfWeek().toString().substring(0, 3).toUpperCase();
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badge.getId()).append(" ");
        s.append(punchtype).append(": ").append(dayOfWeek).append(" ").append(dateText).append(" ").append(timeText_two).append(" (").append(adjustmenttype).append(")");
        
        return s.toString();
    }
    
}
