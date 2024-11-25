/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_fa24;
import java.math.BigDecimal;
import java.time.LocalDate;
/**
 *
 * @author catuc
 */
public class Absenteeism {
    private final Employee employee;
    private final LocalDate payPeriodStartDate;
    private final BigDecimal percentage;
    
    public Absenteesim(Employee employee, LocalDate payPeriodStartDate, BigDecimal percentage){
        this.employee = employee;
        this.payPeriodStartDate = payPeriodStartDate;
        this.percentage = percentage;
    }
    
    public Employee getEmployee(){
        return employee;
    }
    
    public LocalDate getPayPeriodStartDate(){
        return payPeriodStartDate;
    }
    
    public BigDecimal getPercentage(){
        return percentage;
    }
    
    @Override 
    public String toString(){
        return String.format("#%s (Pay Period Starting %s): %.2f%%",
                employee.getBadge().getId(),
                payPeriodStartDate.toString(),
                percentage);
    }
}
