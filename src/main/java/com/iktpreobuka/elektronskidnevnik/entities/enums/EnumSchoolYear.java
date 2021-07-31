package com.iktpreobuka.elektronskidnevnik.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumSchoolYear {
	
	FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH;
	
	@JsonCreator
	public static EnumSchoolYear create(String value) {
	    if(value == null) {
	        return null;
	    }
	    for(EnumSchoolYear v : values()) {
	        if(value.equals(v.toString())) {
	            return v;
	        }
	    }
	    return null;
	}
}
