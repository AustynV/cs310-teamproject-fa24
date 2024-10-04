package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
    int totalMinutes = 0;
    boolean isLunchDeducted = false;

    // Checks if punch list has a even number of punches, because their needs to be pairs of in and out punches.
    if(dailypunchlist.size() < 2){
        // No complete pair to compute time.
        return 0;
    }

    for(int i = 0; i < dailypunchlist.size() - 1; i += 2){
        Punch punchIn = dailypunchlist.get(i);
        Punch punchOut = dailypunchlist.get(i + 1);

        // Ignores the "time out" punches that occur.
        if(punchOut.getPunchtype() == Punch.PunchType.TIME_OUT){
            continue;
        }

        // Calculates the duration between the clock in and clock out punches.
        Duration duration = Duration.between(punchIn.getAdjustedTimestamp(), punchOut.getAdjustedTimestamp());
        totalMinutes += duration.toMinutes();
    }

    // If they have a lunch break, then it deducts it form their total time (totalMinutes).
    if(totalMinutes > shift.getLunchThreshold()){
        totalMinutes -= shift.getLunchDuration();
        isLunchDeducted = true;
    }
    
    // Returns value for totalMinutes back to variable.
    return totalMinutes;
}
}