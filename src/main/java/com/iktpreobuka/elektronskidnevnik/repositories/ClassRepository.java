package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.enums.EnumSchoolYear;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer> {
	
	Boolean existsByClassNumberAndYear(String classNumber, EnumSchoolYear year);
	
	Boolean existsBySupervisorTeacher(TeacherEntity teacher);

}
