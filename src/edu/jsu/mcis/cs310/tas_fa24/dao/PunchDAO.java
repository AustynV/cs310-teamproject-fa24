/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneOffset;
import java.util.Optional;


public class PunchDAO {
    private static final String QUERY_FIND = "SELECT p.*, b.description AS badge_description " + "FROM punch p " + "JOIN badge b ON p.badgeid = b.id " + "WHERE p.id = ?";
                                             
    private static final String QUERY_INSERT = "INSERT INTO punch (terminalid, badgeid, timestamp, punchtypeid) " + "VALUES (?, ?, ?, ?)";
    
    private static final String QUERY_LIST = "SELECT p.*, b.description AS badge_description " + "FROM punch p " + "JOIN badge b ON p.badgeid = b.id " + "WHERE p.badgeid = ? AND DATE(p.timestamp) = ? " + "ORDER BY p.timestamp";
                                             
    private final DAOFactory daoFactory;
    
    public PunchDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Punch find(int id){
        Punch punch = null;
        
         try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_FIND)) {
             
             ps.setInt(1, id);
             
             try (ResultSet rs = ps.executeQuery()){
                 if (rs.next()){
                     int terminalId = rs.getInt("terminalid");
                     String badgeId = rs.getString("badgeid");
                     String badgeDescription = rs.getString("badge_description");
                     Timestamp timestamp = rs.getTimestamp("timestamp");
                     int punchTypeId = rs.getInt("punchtypeid");
                    
                     
                      LocalDateTime originalTimestamp = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                     
                     Badge badge = new Badge(badgeId, badgeDescription);
                     
                     EventType punchType = EventType.values()[punchTypeId];
                     
                     punch = new Punch(id, terminalId, badge, originalTimestamp, punchType);
                 }
             }
         } catch (SQLException e){
             throw new RuntimeException("Error finding punch with ID: " + id, e);
         }
         
         return punch;
    }
    
    public int create (Punch newPunch){
        int generatedId = 0;
        
        
        
        if (!isAuthorized(newPunch)){
            throw new IllegalArgumentException("Unathorized punch creation attempt for Badge ID: " + newPunch.getBadge().getId());
        }
        
        try(Connection conn = daoFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, newPunch.getTerminalid());
            ps.setString(2, newPunch.getBadge().getId());
            ps.setTimestamp(3, Timestamp.valueOf(newPunch.getOriginaltimestamp()));
            ps.setInt(4, newPunch.getPunchtype().ordinal());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0){
                try (ResultSet generatedKeys = ps.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating punch failed, no Id obtained");
                    }
                }
            } else {
                throw new SQLException("Creating punch failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating punch" , e);
        }
        return generatedId;
    }
    
    public List<Punch> list(Badge badge, LocalDate date){
        List<Punch> punches = new ArrayList<>();
        
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_LIST)) {
             
            ps.setString(1, badge.getId());
            ps.setDate(2, java.sql.Date.valueOf(date));
             
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int terminalId = rs.getInt("terminalid");
                    String badgeId = rs.getString("badgeid");
                    String badgeDescription = rs.getString("badge_description");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    int punchTypeId = rs.getInt("punchtypeid");
                    
                    LocalDateTime originalTimestamp = timestamp.toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime();
                    
                    Badge punchBadge = new Badge(badgeId, badgeDescription);
                    EventType punchType = EventType.values()[punchTypeId];
                    
                    punches.add(new Punch(id, terminalId, punchBadge, originalTimestamp, punchType));
                }
            }
            
            LocalDate nextDay = date.plusDays(1);
            try (PreparedStatement nextDayPs = conn.prepareStatement(QUERY_LIST)) {
                nextDayPs.setString(1, badge.getId());
                nextDayPs.setDate(2, java.sql.Date.valueOf(nextDay));
                ResultSet nextDayRs = nextDayPs.executeQuery();
                
                if (nextDayRs.next()) {
                    int terminalId = nextDayRs.getInt("terminalid");
                    String badgeId = nextDayRs.getString("badgeid");
                    String badgeDescription = nextDayRs.getString("badge_description");
                    Timestamp timestamp = nextDayRs.getTimestamp("timestamp");
                    int punchTypeId = nextDayRs.getInt("punchtypeid");
                    
                    // Only add if it's a clock out or time out punch
                    EventType nextDayPunchType = EventType.values()[punchTypeId];
                    if (nextDayPunchType == EventType.CLOCK_OUT || nextDayPunchType == EventType.TIME_OUT) {
                        LocalDateTime originalTimestamp = timestamp.toInstant()
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime();
                        punches.add(new Punch(-1, terminalId, new Badge(badgeId, badgeDescription), originalTimestamp, nextDayPunchType));
                    }
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error listing punches for badge " + badge.getId() + " on date " + date, e);
        }
        
        return punches;
        
    }
    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
        ArrayList<Punch> punchesInRange = new ArrayList<>();

        LocalDate currentDate = begin;
        while (!currentDate.isAfter(end)) {
            List<Punch> punchesForDay = list(badge, currentDate);
            punchesInRange.addAll(punchesForDay);
            currentDate = currentDate.plusDays(1);
    }

    return punchesInRange;
}

    
    private boolean isAuthorized(Punch newPunch){
        return true;
    }
    
}
