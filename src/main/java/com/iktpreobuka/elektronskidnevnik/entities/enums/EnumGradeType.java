package com.iktpreobuka.elektronskidnevnik.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumGradeType {

	TEST, ESSAY, ORAL, FINAL;
	
	@JsonCreator
	public static EnumGradeType create(String value) {
	    if(value == null) {
	        return null;
	    }
	    for(EnumGradeType v : values()) {
	        if(value.equals(v.toString())) {
	            return v;
	        }
	    }
	    return null;
	}
}
