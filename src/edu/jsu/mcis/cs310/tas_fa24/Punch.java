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
    priavte PunchAdjustmentType adjustmenttype;
    private LunchStatus adjustedlunchstatus;
    
    public enum LunchStatus {
        HAPPENING, HAPPENED, NOT_HAPPENING, INAPPLICABLE
    }
    
    public Punch(){
        
    }
}
