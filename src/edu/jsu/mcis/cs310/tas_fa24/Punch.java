/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;

public class Punch {
    
    private Integer id;
    private int terminalId;
    private Badge badge;
    private LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp;
    private EventType punchType;
    private PunchAdjustmentType adjustmentType;
    
    //constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType){
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
        this.adjustedTimestamp = null;
        this.adjustmentType = null;
        this.id = null;
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
    
    public int getTerminalId(){
        return terminalId;
    }
    
    public Badge getBadge(){
        return badge;
    }
    
    public LocalDateTime getOriginalTimestamp(){
        return originalTimestamp;
    }
    
    public LocalDateTime getAdjustedTimestamp(){
        return adjustedTimestamp;
    }
    
    public void setAdjustedTimestamp (LocalDateTime adjustedTimestamp){
        this.adjustedTimestamp = adjustedTimestamp;
    }
    
    
    
}
