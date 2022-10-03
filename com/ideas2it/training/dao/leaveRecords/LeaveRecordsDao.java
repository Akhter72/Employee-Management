package com.ideas2it.training.dao.leaveRecords;

import java.util.List;

import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Employee;

public interface LeaveRecordsDao {

    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecords);
    public List<LeaveRecords> getLeaveRecords() ;
    public List<LeaveRecords> getLeaveRecord(String employeeId) ;
    public boolean updateLeaveRecord(String employeeId,LeaveRecords leaveRecord) ;
    public boolean deleteLeaveRecord(String employeeId,LeaveRecords leaveRecords);
    
}