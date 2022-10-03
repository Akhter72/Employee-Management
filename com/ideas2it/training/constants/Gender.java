package com.ideas2it.training.constants;

public enum Gender {
	Male(1),
	Female(2),
	Other(3);
	private final int value;
  
    private Gender(final int value) {
	this.value = value;

    }

    public static Gender getGender(int genderIndex) {
	Gender result = null;
	for (Gender gender:Gender.values()) {
	    if (gender.value == genderIndex) {
		result = gender;
            }
        }
	return result;
    }
}


