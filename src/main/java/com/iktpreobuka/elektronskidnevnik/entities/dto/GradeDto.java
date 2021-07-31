package com.iktpreobuka.elektronskidnevnik.entities.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumGradeType;
import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumGradeValue;

public class GradeDto {
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Grade value null or invalid. Accepted values: [INSUFFICIENT, SUFFICIENT, GOOD, VERY_GOOD, EXCELLENT]")
	private EnumGradeValue value;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Grade type null or invalid. Accepted values: [TEST, ESSAY, ORAL, FINAL].")
	private EnumGradeType type;

	public GradeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnumGradeValue getValue() {
		return value;
	}

	public void setValue(EnumGradeValue value) {
		this.value = value;
	}

	public EnumGradeType getType() {
		return type;
	}

	public void setType(EnumGradeType type) {
		this.type = type;
	}
	
	
}
