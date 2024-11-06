package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Shift;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import java.sql.*;
import java.util.HashMap;


public class ShiftDAO {
    private static final String QUERY_FIND_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT shiftid FROM employee WHERE badgeid = ?";
    private final DAOFactory daoFactory;
    private final int ID = 0;
    public ShiftDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Shift find (int id){
        
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{Connection conn = daoFactory.getConnection();
            if(conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);
                
                rs = ps.executeQuery();
                if(rs.next()){
                    HashMap<String, String> parameters = new HashMap<>();

                    parameters.put("id",rs.getString("id"));


                    
                    parameters.put("id", rs.getString("id"));

                    parameters.put("description", rs.getString("description"));
                    parameters.put("shiftstart", rs.getString("shiftstart"));
                    parameters.put("shiftstop", rs.getString("shiftstop"));                    
                    parameters.put("roundinterval", rs.getString("roundinterval"));
                    parameters.put("graceperiod", rs.getString("graceperiod"));
                    parameters.put("dockpenalty", rs.getString("dockpenalty"));
                    
                    parameters.put("lunchstart", rs.getString("lunchstart"));
                    parameters.put("lunchstop", rs.getString("lunchstop"));

                    parameters.put("lunchduration", rs.getString("lunchthreshold"));
                    
                    //System.out.println(parameters);
                   

                    System.out.println("here");
                   // parameters.put("otherParameter", rs.getString("otherParameter"));
                    parameters.put("lunchDuration", rs.getString("lunchthreshold"));
                    //parameters.put("shiftDuration", rs.getString("shiftDuration"));
                    //System.out.println(parameters)System.out.println(parameters);

                    shift = new Shift(parameters);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error finding shift with ID: " + id, e);
        }
        
        return shift;
    }
    public Shift find(Badge badge){

        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = ID;
        

        try {

            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());
                boolean hasresults = ps.execute();


                if (hasresults) {
                    
                    rs = ps.getResultSet();
                    rs.next();
                    id = rs.getInt("shiftid");
                   
                }

            }
                    } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }
        
        return find(id);
    }
    }    

