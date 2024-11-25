/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24.dao;
import edu.jsu.mcis.cs310.tas_fa24.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author catuc
 */
public class AbsenteeismDAO {
    private final Connection connection;
    
    public AbsenteeismDAO(Connection connection){
        this.connection = connection;
    }
    
    public Absenteeism find(Employee employee, LocalDate payPeriodStartDate){
        Absenteeism absenteeism = null;
        String query = "SELECT percentage FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, employee.getID());
            ps.setDate(2, Date.valueOf (payPeriodStartDate));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal percentage = rs.getBigDecimal("percentage");
                    absenteeism = new Absenteeism(employee, payPeriodStartDate, percentage);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return absenteeism;
    }
    
    public boolean create(Absenteeism absenteeism) {
        String query = """
            INSERT INTO absenteeism (employeeid, payperiod, percentage) 
            VALUES (?, ?, ?) 
            ON DUPLICATE KEY UPDATE percentage = VALUES(percentage)""";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, absenteeism.getEmployee().getID());
            ps.setDate(2, Date.valueOf(absenteeism.getPayPeriodStartDate()));
            ps.setBigDecimal(3, absenteeism.getPercentage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
