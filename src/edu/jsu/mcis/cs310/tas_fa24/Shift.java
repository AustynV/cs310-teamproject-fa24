/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;


import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 *
 * @author catuc.
 */
public class Shift {
    private final String description;
    private final int id, roundinterval, graceperiod, dockpenalty, lunchthreshold;
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private int lunchDuration;
    private int shiftDuration;
    
    public Shift(HashMap<String, String> shiftParams){
        this.id = Integer.parseInt(shiftParams.get("id"));
        this.roundinterval = Integer.parseInt(shiftParams.get("rouninerval"));
        this.graceperiod = 
    }
}
