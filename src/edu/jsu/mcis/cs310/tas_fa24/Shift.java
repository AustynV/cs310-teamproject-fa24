/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.edu.jsu.mcis.cs310.tas_fa24;


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
    private final int shiftduration, lunchduration;
    

public Shift(int id, String description, LocalTime shiftstart,LocalTime shiftstop,LocalTime lunchstart, LocalTime lunchstop, int roundintreval, int graceperiod, int dockpenalty, int lunchthreshold, int roundinterval)
{
	this.id = id;
	this.description = description;
	this.shiftstart = shiftstart;
	this.shiftstop = shiftstop;
	this.lunchstart = lunchstart;
	this.lunchstop = lunchstop;
	this.roundinterval = roundinterval;
	this.graceperiod = graceperiod;
	this.dockpenalty = dockpenalty;
	this.lunchthreshold = lunchthreshold;
	
	
	this.shiftduration = (int) ChronoUnit.MINUTES.between(shiftstart, shiftstop);
	this.lunchduration = (int) ChronoUnit.MINUTES.between(lunchstart, lunchstop);
}

public int getId() {
	return id;
}
public  String getDescription() {
	return description;
}
public LocalTime getShiftStart() {
	return shiftstart;
}
public LocalTime getShiftStop() {
	return shiftstop;
}
public LocalTime getLunchStart() {
	return lunchstart;
}
public LocalTime getLunchStop() {
	return lunchstop;
}
public int getRoundInterval() {
	return roundinterval;
}
public int getGracePeriod() {
	return graceperiod;
}
public int getDockPentalty() {
	return dockpenalty;
}
public int getLunchThreshold() {
	return lunchthreshold;
}
public int getShiftDuration() {
	return shiftduration;
}
public int getLunchDuration() {
	return lunchduration;
}
@Override
public String toString() {
	return "Shift ID: " + id + "\n" +
"Description: " + description + "\n" +
"Shift Start: " + shiftstart + "\n" +
"Shift Stop: " + shiftstart + "\n" +
"Lunch Start:" + lunchstart + "\n" +
"Lunch Stop:"  + lunchstop + "\n" +
"Shift Duration: " + shiftduration + "minutes\n" +
"Lunch Duration: " + lunchduration + "minutes\n" +
"Round Interval: " + roundinterval + "minutes\n" +
"Grace Period: " + graceperiod + "minutes\n" +
"Dock Penalty:" + dockpenalty + "minutes\n" +
"Lunch Threshold:" + lunchthreshold + "minutes";
	
 }
}
