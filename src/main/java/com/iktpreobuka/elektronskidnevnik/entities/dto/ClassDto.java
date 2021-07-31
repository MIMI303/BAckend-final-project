package com.iktpreobuka.elektronskidnevnik.entities.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumSchoolYear;

public class ClassDto {
	
	@NotNull(message = "Class number must not be null.")
	@Pattern(regexp = "^[1-3{n}]$", message = "Class number must be integer in range of [1-3].")
	private String classNumber;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Year is null or invalid. Accepted values: [FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH].")
	private EnumSchoolYear year;

	public ClassDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public EnumSchoolYear getYear() {
		return year;
	}

	public void setYear(EnumSchoolYear year) {
		this.year = year;
	}
	
	
}
