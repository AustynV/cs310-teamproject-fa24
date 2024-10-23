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


public class PunchDAO {
    private static final String QUERY_FIND = "SELECT p.*, b.description AS badge_description " + "FROM punch p " + "JOIN badge b ON p.badgeid = b.id " + "WHERE p.id = ?";
                                             
    private static final String QUERY_INSERT = "INSERT INTO punch (terminalid, badgeid, timestamp, punchtypeid) " + "VALUES (?, ?, ?, ?)";  
    
    private static final String QUERY_GET_EMPLOYEE_DEPARTMENT_TERMINAL = "Select d.clockterminalid FROM employee e "  +  "JOIN department d ON e. departmentid = d.id " + "WHERE e.badgeId = ?";
                                             
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
            ps.setInt(1, newPunch.getTerminalId());
            ps.setString(2, newPunch.getBadge().getId());
            ps.setTimestamp(3, Timestamp.valueOf(newPunch.getAdjustedTimestamp()));
            ps.setInt(4, newPunch.getPunchType().ordinal());
            
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
            throw new RuntimeException("Error creatign punch" , e);
        }
        return generatedId;
    }
    
    private boolean isAuthorized(Punch newPunch){
        if (newPunch.getTerminalId() == 0){
            return true;
        }
        String badgeId = newPunch.getBadge().getId();
        int departmentTerminalId = getDepartmentTerminalId(badgeId);
        
        return newPunch.getTerminalId() == departmentTerminalId;
    }
    
    private int getDepartmentTerminalId(String badgeId){
        int terminalId = 0;
        
        try (Connection conn = daoFactory.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(QUERY_GET_EMPLOYEE_DEPARTMENT_TERMINAL)){
            
            ps.setString(1, badgeId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    terminalId = rs.getInt("clockterminalid");
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error retrieving department terminal ID for badge: " + badgeId, e); 
        }
         
        return terminalId;
    }
}
