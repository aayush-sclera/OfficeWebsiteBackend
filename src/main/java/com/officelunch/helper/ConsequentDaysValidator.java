package com.officelunch.helper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConsequentDaysValidator {
    private final String[] arr = new String[] { "monday", "tuesday", "wednesday", "thursday", "friday" };
    public boolean isConsequent(LocalDate d1 , LocalDate d2 , LocalDate d3){
        if(!d1.isBefore(d2) || !d2.isBefore(d3)){
            return false;
        }

        int a = find( d1.getDayOfWeek().toString().toLowerCase());
        int b = find(d2.getDayOfWeek().toString().toLowerCase());
        int c = find(d3.getDayOfWeek().toString().toLowerCase());

        if(a == -1 || b == -1 || c==-1)
            return false;

        if (a <= 2 && b == a + 1 && c == b + 1) {
            return true;
        } else if (a == 3 && b == a + 1 && c == 0) {
            return true;
        } else if (a == 4 && b == 0 && c == 1) {
            return true;
        }
        return false;
    }
    private  int find(String day) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(day)) {
                return i;
            }
        }
        return -1;
    }
}
