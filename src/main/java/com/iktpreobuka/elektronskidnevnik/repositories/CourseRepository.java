package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.CourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumSchoolYear;
import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumSemester;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer>{
	
	Boolean existsByNameAndSemesterAndYear(String name, EnumSemester semester, EnumSchoolYear year);
}
