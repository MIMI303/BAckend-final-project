package com.iktpreobuka.elektronskidnevnik.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskidnevnik.entities.dto.CourseDto;
import com.iktpreobuka.elektronskidnevnik.repositories.CourseRepository;

@Component
public class CourseCustomValidator implements Validator {
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return CourseDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		CourseDto course = (CourseDto) target;
		
		if(courseRepository.existsByNameAndSemesterAndYear(course.getName(), course.getSemester(), course.getYear())) {
			errors.reject("400", "Course name-semester-year combination already in use.");
		}
		
	}
	
	
}
