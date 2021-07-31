package com.iktpreobuka.elektronskidnevnik.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumGradeValue {

	INSUFFICIENT, SUFFICIENT, GOOD, VERY_GOOD, EXCELLENT;
	
	@JsonCreator
	public static EnumGradeValue create(String value) {
	    if(value == null) {
	        return null;
	    }
	    for(EnumGradeValue v : values()) {
	        if(value.equals(v.toString())) {
	            return v;
	        }
	    }
	    return null;
	}
}	
