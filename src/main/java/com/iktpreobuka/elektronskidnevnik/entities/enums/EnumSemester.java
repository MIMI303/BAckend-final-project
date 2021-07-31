package com.iktpreobuka.elektronskidnevnik.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumSemester {
	

	FIRST_MIDTERM, SECOND_MIDTERM;
	
	@JsonCreator
	public static EnumSemester create(String value) {
	    if(value == null) {
	        return null;
	    }
	    for(EnumSemester v : values()) {
	        if(value.equals(v.toString())) {
	            return v;
	        }
	    }
	    return null;
	}
}
