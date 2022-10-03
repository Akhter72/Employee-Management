package com.ideas2it.training.constants;

public enum LeaveType {
	Casual(1),
	Sick(2),
	Medical(3);
	private final int value;
  
    private LeaveType(final int value) {
	this.value = value;

    }

    public static LeaveType getLeaveType(int index) {
	LeaveType result = null;
	for (LeaveType leaveType:LeaveType.values()) {
	    if (leaveType.value == index) {
		result = leaveType;
            }
        }
	return result;
    }
}


