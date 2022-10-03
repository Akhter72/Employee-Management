package com.ideas2it.training.constants;

public enum EmployeeType {
	Trainer(1),
	Trainee(2);
	private final int value;
  
    private EmployeeType(final int value) {
	this.value = value;

    }

    public static EmployeeType getEmployeeType(int index) {
	EmployeeType result = null;
	for (EmployeeType EType:EmployeeType.values()) {
	    if (EType.value == index) {
		result = EType;
            }
        }
	return result;
    }
}


