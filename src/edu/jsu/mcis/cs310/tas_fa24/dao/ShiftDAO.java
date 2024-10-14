package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ShiftDAO {
    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private final DAOFactory daoFactory;
    
    public ShiftDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Shift find (int id){
        Shift shift = null;
        
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_FIND)){
            
            ps.setInt(1, id);
            System.out.println(ps);
            try (ResultSet rs = ps.executeQuery()){
                
                if (rs.next()){
                    
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("startTime", rs.getString("startTime"));
                    parameters.put("stopTime", rs.getString("stopTime"));
                    parameters.put("otherParameter", rs.getString("otherParameter"));
                    parameters.put("lunchDuration", rs.getString("lunchDuration"));
                    parameters.put("shiftDuration", rs.getString("shiftDuration"));
                    System.out.println(parameters);
                    shift = new Shift(parameters);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding shift with ID: " + id, e);
        }
        
        return shift;
    }
}
