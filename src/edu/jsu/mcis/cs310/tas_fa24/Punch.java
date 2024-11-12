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
    
    public void adjust (Shift s){
        if (punchType == EventType.TIME_OUT || isWeekend(originalTimestamp)) {
            adjustedTimestamp = originalTimestamp;
            adjustmentType = PunchAdjustmentType.NONE;
            return;
        }
        
        LocalTime shiftStart = s.getStarted();
        LocalTime shiftStop = s.getStopTime();
        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchStop = s.getLunchStop();
        int roundInterval = s.getRoundedInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();
        
        LocalDateTime shiftStartTime = originalTimestamp.with(shiftStart);
        LocalDateTime shiftStopTime = originalTimestamp.with(shiftStop);
        LocalDateTime lunchStartTime = originalTimestamp.with(lunchStart);
        LocalDateTime lunchStopTime = originalTimestamp.with(lunchStop);
        
         // Shift Start and Stop adjustment
        if (isClockIn() && originalTimestamp.isBefore(shiftStartTime) &&
            originalTimestamp.isAfter(shiftStartTime.minusMinutes(roundInterval))) {
            adjustedTimestamp = shiftStartTime;
            adjustmentType = PunchAdjustmentType.SHIFT_START;
            return;
        }
        if (isClockOut() && originalTimestamp.isAfter(shiftStopTime) &&
            originalTimestamp.isBefore(shiftStopTime.plusMinutes(roundInterval))) {
            adjustedTimestamp = shiftStopTime;
            adjustmentType = PunchAdjustmentType.SHIFT_STOP;
            return;
        }
        
        //Lunch start and stop adjustments 
        if (isClockOut() && isWithinLunchBreak(originalTimestamp, lunchStartTime, lunchStopTime)) {
            adjustedTimestamp = lunchStartTime;
            adjustmentType = PunchAdjustmentType.LUNCH_START;
            return;
        }
        if (isClockIn() && isWithinLunchBreak(originalTimestamp, lunchStartTime, lunchStopTime)) {
            adjustedTimestamp = lunchStopTime;
            adjustmentType = PunchAdjustmentType.LUNCH_STOP;
            return;
        }
        
        //Grace Period adjustment
        if (isClockIn() && originalTimestamp.isAfter(shiftStartTime) &&
            originalTimestamp.isBefore(shiftStartTime.plusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStartTime;
            adjustmentType = PunchAdjustmentType.SHIFT_START;
            return;
        }
        if (isClockOut() && originalTimestamp.isBefore(shiftStopTime) &&
            originalTimestamp.isAfter(shiftStopTime.minusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStopTime;
            adjustmentType = PunchAdjustmentType.SHIFT_STOP;
            return;
        }
        
        //Dock Penalty adjustment
        if (isClockIn() && originalTimestamp.isAfter(shiftStartTime.plusMinutes(gracePeriod)) &&
            originalTimestamp.isBefore(shiftStartTime.plusMinutes(dockPenalty))) {
            adjustedTimestamp = shiftStartTime.plusMinutes(dockPenalty);
            adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
            return;
        }
        if (isClockOut() && originalTimestamp.isBefore(shiftStopTime.minusMinutes(gracePeriod)) &&
            originalTimestamp.isAfter(shiftStopTime.minusMinutes(dockPenalty))) {
            adjustedTimestamp = shiftStopTime.minusMinutes(dockPenalty);
            adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
            return;
        }
        
        adjustedTimestamp = roundToNearestInterval(originalTimestamp, roundInterval);
        adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
    }
    
    private boolean isClockIn() {
        return punchType == EventType.CLOCK_IN;
    }

    private boolean isClockOut() {
        return punchType == EventType.CLOCK_OUT;
    }

    private boolean isWeekend(LocalDateTime timestamp) {
        int dayOfWeek = timestamp.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7; // Saturday = 6, Sunday = 7
    }
    
    private boolean isWithinLunchBreak(LocalDateTime timestamp, LocalDateTime lunchStart, LocalDateTime lunchStop) {
        return !timestamp.isBefore(lunchStart) && !timestamp.isAfter(lunchStop);
    }

    private LocalDateTime roundToNearestInterval(LocalDateTime timestamp, int intervalMinutes) {
        LocalDateTime roundedTimestamp = timestamp.truncatedTo(ChronoUnit.MINUTES);
        int minutes = roundedTimestamp.getMinute();
        int remainder = minutes % intervalMinutes;
        
        if (remainder >= intervalMinutes / 2) {
            roundedTimestamp = roundedTimestamp.plusMinutes(intervalMinutes - remainder);
        } else {
            roundedTimestamp = roundedTimestamp.minusMinutes(remainder);
        }

        return roundedTimestamp.withSecond(0).withNano(0);
    }
    
    
    public String printOriginal() {
        
        String formattedTimestamp = originalTimestamp.format(FORMATTER);
        
        //out put was M[on] instead of M[ON]
        formattedTimestamp = formattedTimestamp.toUpperCase(); //this fixes the error
        
        return "#" + badge.getId() + " " + punchType + ": " + formattedTimestamp;
        
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
    
}
