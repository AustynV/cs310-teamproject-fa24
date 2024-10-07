package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Department;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
/**
 *
 * @author David
 */
public class DepartmentDAO {
    /*
    +----+--------------+------------+
    | id | description  | terminalid |
    +----+--------------+------------+
    |  1 | Assembly     |        103 |
    |  2 | Cleaning     |        107 |
    |  3 | Warehouse    |        106 |
    |  4 | Grinding     |        104 |
    |  5 | Hafting      |        105 |
    |  6 | Office       |        102 |
    |  7 | Press        |        104 |
    |  8 | Shipping     |        107 |
    |  9 | Tool and Die |        104 |
    | 10 | Maintenance  |        104 |
    +----+--------------+------------+
    */
    
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
                
                result = new Department(Integer.toString(idNum), desc, Integer.toString(termID));

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
