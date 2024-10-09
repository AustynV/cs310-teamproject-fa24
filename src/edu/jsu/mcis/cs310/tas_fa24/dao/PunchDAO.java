/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.EventType;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
/**
 *
 * @author catuc
 */
public class PunchDAO {
    private static final String QUERY_FIND = "SELECT p.*, b.description AS badge_description " + "FROM punch p " + "JOIN badge b ON p.badgeid = b.id " + "WHERE p.id = ?";
                                             
                                             
                                             
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
    
}
