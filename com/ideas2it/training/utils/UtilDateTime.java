package com.ideas2it.training.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilDateTime {

    public String getCurDateTime() {

        String curDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-uuuu HH:mm:ss"));
	return curDateTime;
    }  

}