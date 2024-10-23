/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.Badge;
import edu.jsu.mcis.cs310.tas_fa24.Department;
import edu.jsu.mcis.cs310.tas_fa24.Employee;
import edu.jsu.mcis.cs310.tas_fa24.EmployeeType;
import java.sql.*;
import java.time.LocalDateTime;
import edu.jsu.mcis.cs310.tas_fa24.Shift;

/**
 *
 * @author devi
 */
public class EmployeeDAO {
    private Connection conn;
    private final DAOFactory daoFactory;
    EmployeeDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    String QUERY_FIND = "SELECT * FROM employee WHERE id = ?";    


    // Find Employee by ID
    public Employee find(int id) {
        
        Employee result = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Get connection from DAOFactory
            Connection conn = daoFactory.getConnection();

            
            if (conn.isValid(0)) {

                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                
                rs = ps.executeQuery();

               
                if (rs.next()) {
                    
                    String firstname = rs.getString("firstname");
                    
                    String middlename = rs.getString("middlename");
                    String lastname = rs.getString("lastname");
                    LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();
                    
                    
                    Badge badge = daoFactory.getBadgeDAO().find(rs.getString("badgeid"));
                    Department department = daoFactory.getDepartmentDAO().find(rs.getInt("departmentid"));
                    //System.out.println(rs.getInt("shiftid"));
                                                                                 
                    Shift shift = daoFactory.getShiftDAO().find(rs.getInt("shiftid"));
                    System.out.println("here");

            
                    EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid") - 1];

                    // Construct Employee object

                    result = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
                    System.out.println(result);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        
        return result;
    }

    
    public Employee find(Badge badge) {
        Employee result = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            
            if (conn != null && conn.isValid(0)) {
                
                String QUERY_FIND_BY_BADGE = "SELECT id FROM employee WHERE badgeid = ?";
                ps = conn.prepareStatement(QUERY_FIND_BY_BADGE);
                ps.setString(1, badge.getId());

                rs = ps.executeQuery();

                
                if (rs.next()) {
                    int id = rs.getInt("id");
                    result = find(id); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        System.out.println("here");
        return result;
    }
}