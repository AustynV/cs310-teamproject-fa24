package edu.jsu.mcis.cs310.tas_fa24.dao;

import java.time.*;
import java.util.*;

import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
//import com.github.cliftonlabs.json_simple.*;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_fa24.Shift;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    
    
    // @author: Austyn
    public static int calculateTotalMinutes(List<Punch> dailypunchlist, Shift shift){
        
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
        if(Punch.adjustmentType == PunchAdjustmentType.INTERVAL_ROUND){
            continue;
        }

        // Calculates the duration between the clock in and clock out punches.
        Duration duration = Duration.between(punchIn.getAdjustedTimestamp(), punchOut.getAdjustedTimestamp());
        totalMinutes += duration.toMinutes();
    }

    // If they have a lunch break, then it deducts it form their total time (totalMinutes).
    if(totalMinutes > shift.getShiftDuration()){
        totalMinutes -= shift.getLunchDuration();
        isLunchDeducted = true;
    }
    
    // Returns value for totalMinutes back to variable.
    return totalMinutes;
    
    }

//Converts a list of punch to a JSON string

public static String getPunchListAsJSON(List<Punch> punchList) {
	JsonArray jsonArray = new JsonArray();
	for(Punch punch : punchList) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.put("id",punch.getId());
		jsonObject.put("terminalId", punch.getTerminalid());
		jsonObject.put("badgeId", punch.getBadge().getId());
		jsonObject.put("originalTimestamp", punch.getOriginaltimestamp().toString());
        jsonObject.put("adjustedTimestamp", punch.getAdjustedTimestamp() != null ? punch.getAdjustedTimestamp().toString() : null);
        jsonObject.put("punchType", punch.getPunchtype().toString());
        jsonObject.put("adjustmentType", punch.adjustmentType != null ? punch.adjustmentType.toString() : "NONE");

       jsonArray.add(jsonObject);
    }

    return jsonArray.toString();
}

	}
