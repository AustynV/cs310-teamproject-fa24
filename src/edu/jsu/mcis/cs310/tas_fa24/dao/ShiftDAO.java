package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ShiftDAO {
    private stattic final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private final DAOFactory daoFactory;
    
    public ShiftDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Shift find (int id){
        Shift shift = null;
        
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_FIND)){
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("startTime", rs.getString("startTime"));
                    parameters.put("stopTime", rs.getString("stopTime"));
                    parameters.put("otherParameter", rs.getString("otherParameter"));
                    parameters.put("lunchDuration", rs.getString("lunchDuration"));
                    parameters.put("shiftDuration", rs.getString("shiftDuration"));
                    
                    shift = new Shift(parameters);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding punch with ID: " + id, e);
        }
        
        return shift;
    }
}
