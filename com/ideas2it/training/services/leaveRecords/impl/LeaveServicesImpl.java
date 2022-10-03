package com.ideas2it.training.services.leaveRecords.impl;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDate.*;
import java.time.temporal.ChronoUnit;


import com.ideas2it.training.dao.leaveRecords.LeaveRecordsDao;
import com.ideas2it.training.dao.leaveRecords.impl.LeaveRecordsDaoImpl;
import com.ideas2it.training.services.leaveRecords.LeaveServices;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Employee;

 

public class LeaveServicesImpl implements LeaveServices {

    private LeaveRecordsDao leaveDao = new LeaveRecordsDaoImpl();

    @Override
    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecords) {
        return leaveDao.addLeaveRecords(employeeId,leaveRecords);
    }


    @Override
    public List<LeaveRecords> getLeaveRecords() {
	return leaveDao.getLeaveRecords();
    }

    @Override
    public List<LeaveRecords> getLeaveRecord(String employeeId) {
	return leaveDao.getLeaveRecord(employeeId);
    }

    @Override
    public boolean updateLeaveRecord(String employeeId,LeaveRecords leaveRecord) {
	return leaveDao.updateLeaveRecord(employeeId,leaveRecord);
    } 

    public int getLeaveCount(String employeeId) {
	List<LeaveRecords> leaveRecords= getLeaveRecord(employeeId);
        int leaveSum = 0;
        int curYear = LocalDate.now().getYear();

        LocalDate min = LocalDate.parse(curYear-1+"-12-30"); //end of last year
        LocalDate max = LocalDate.parse(curYear+"-12-30");   //end of current year

        for (LeaveRecords record: leaveRecords) {
            LocalDate fromDate = LocalDate.parse(record.getFromDate());
            LocalDate toDate = LocalDate.parse(record.getToDate());

            //fromDate.isAfter(min) && toDate.isBefore(max) && toDate.isAfter(min) && fromDate.isBefore(max)

            if(fromDate.getYear() == toDate.getYear()){
                leaveSum +=ChronoUnit.DAYS.between(fromDate,toDate);
                
            }
            else if(fromDate.isBefore(min) && toDate.isAfter(min) && toDate.isBefore(max)) {
                leaveSum += ChronoUnit.DAYS.between(min, toDate);
                //System.out.println(leaveSum);
            }
            else if(toDate.isAfter(max) && fromDate.isBefore(max) && fromDate.isAfter(min)) {
                //System.out.println(max+" "+fromDate);
                leaveSum += ChronoUnit.DAYS.between(fromDate,max);
                //System.out.println(leaveSum);
            }
            else{
                leaveSum = leaveSum;
            }
            
        }
        return leaveSum;
    }

    public boolean deleteLeaveRecord(String employeeId,LeaveRecords leaveRecord) {

	return leaveDao.deleteLeaveRecord(employeeId,leaveRecord);
    }

}

