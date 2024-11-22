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
    
    // Constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType){
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now();
        this.adjustedTimestamp = null;
        this.adjustmentType = null;
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
    
    public void setAdjustedTimestamp(LocalDateTime adjustedTimestamp){
        this.adjustedTimestamp = adjustedTimestamp;
    }
    
    public void adjust(Shift s){
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

    // Calculate the corresponding LocalDateTime for shift start, shift stop, and lunch times
    LocalDateTime shiftStartTime = originalTimestamp.toLocalDate().atTime(shiftStart);
    LocalDateTime shiftStopTime = originalTimestamp.toLocalDate().atTime(shiftStop);
    LocalDateTime lunchStartTime = originalTimestamp.toLocalDate().atTime(lunchStart);
    LocalDateTime lunchStopTime = originalTimestamp.toLocalDate().atTime(lunchStop);

    // Clock-in handling (within grace period or round to nearest interval)
    if (isClockIn()) {
        if (originalTimestamp.isBefore(shiftStartTime) && originalTimestamp.isAfter(shiftStartTime.minusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStartTime;
            adjustmentType = PunchAdjustmentType.SHIFT_START;
            return;
        } 
        if (originalTimestamp.isAfter(shiftStartTime.minusMinutes(gracePeriod)) && originalTimestamp.isBefore(shiftStartTime.plusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStartTime;
            adjustmentType = PunchAdjustmentType.SHIFT_START;
            return;
        }
        if (originalTimestamp.isBefore(shiftStartTime.minusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStartTime.plusMinutes(dockPenalty); // Apply dock penalty if too early
            adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
            return;
        }
    }

    // Clock-out handling (within grace period or round to nearest interval)
    if (isClockOut()) {
        if (originalTimestamp.isAfter(shiftStopTime) && originalTimestamp.isBefore(shiftStopTime.plusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStopTime;
            adjustmentType = PunchAdjustmentType.SHIFT_STOP;
            return;
        } 
        if (originalTimestamp.isBefore(shiftStopTime.plusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStopTime;
            adjustmentType = PunchAdjustmentType.SHIFT_STOP;
            return;
        }
        if (originalTimestamp.isAfter(shiftStopTime.plusMinutes(gracePeriod))) {
            adjustedTimestamp = shiftStopTime.minusMinutes(dockPenalty); // Apply dock penalty if too late
            adjustmentType = PunchAdjustmentType.SHIFT_DOCK;
            return;
        }
    }

    // Lunch break handling (clock-out during lunch, clock-in after lunch)
    if (originalTimestamp.isAfter(lunchStartTime) && originalTimestamp.isBefore(lunchStopTime)) {
        if (isClockOut()) {
            adjustedTimestamp = lunchStopTime;
            adjustmentType = PunchAdjustmentType.LUNCH_STOP;
            return;
        }
        if (isClockIn()) {
            adjustedTimestamp = lunchStartTime;
            adjustmentType = PunchAdjustmentType.LUNCH_START;
            return;
        }
    }

    // Apply rounding to nearest interval if no other adjustments were made
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
        formattedTimestamp = formattedTimestamp.toUpperCase();
        return "#" + badge.getId() + " " + punchType + ": " + formattedTimestamp;
    }
    
    public String printAdjusted() {
        if (adjustedTimestamp != null) {
            String formattedAdjustedTimestamp = adjustedTimestamp.format(FORMATTER);
            return "#" + badge.getId() + " " + punchType + ": " + formattedAdjustedTimestamp + " (" + adjustmentType + ")";
        } else {
            return "Adjusted timestamp not set";
        }
    }
    
    public String toString() {
        return printOriginal();
    }
}