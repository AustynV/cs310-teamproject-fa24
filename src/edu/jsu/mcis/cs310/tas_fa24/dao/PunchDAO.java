/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Punch;
import java.sql.*;
/**
 *
 * @author catuc
 */
public class PunchDAO {
    private static final String QUERY_FIND = "SELECT * FROM punch WHERE id = ?";
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
                     Timestamp timestamp = rs.getTimestamp("timestamp");
                     int terminalId = rs.getInt("terminalid");
                     int punchTypeId = rs.getInt("punchtypeid");
                     int badgeId = rs.getInt("badgeid");
                     
                     punch = new Punch(id, timestamp, terminalId, punchTypeId, badgeId);
                 }
             }
         } catch (SQLException e){
             hrow new DAOException("Error finding punch with ID: " + id, e)
         }
         
         return punch;
    }
}
