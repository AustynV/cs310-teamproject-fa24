package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author David
 */
public class DepartmentDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";


    private final DAOFactory daoFactory;

    DepartmentDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }


    
    public Department find(int id){
        
        Department result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
               
                
                ps = conn.prepareStatement(QUERY_FIND);
                
                ps.setInt(1, id);
                
                rs = ps.executeQuery();
          
            if (rs.next()) {
                String desc = rs.getString("description");
                int idNum = rs.getInt("id");
                int termID = rs.getInt("terminalid");
                
                result = new Department(idNum, desc, termID);

                }


                


                
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    }
