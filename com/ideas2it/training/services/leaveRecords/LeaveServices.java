package com.ideas2it.training.services.leaveRecords;

import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Employee;

import java.util.List;
import java.util.ArrayList;

 
public interface LeaveServices {

    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecords);

    public List<LeaveRecords> getLeaveRecords() ;

    public List<LeaveRecords> getLeaveRecord(String employeeId);

    public boolean updateLeaveRecord(String employeeId,LeaveRecords leaveRecord);

    public int getLeaveCount(String employeeId) ;

    public boolean deleteLeaveRecord(String employeeId,LeaveRecords leaveRecord);


}