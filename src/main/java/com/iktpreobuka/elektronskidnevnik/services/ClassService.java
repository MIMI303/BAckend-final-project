package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;

public interface ClassService {

	List<StudentEntity> addCoursesForEntireClass(Integer classId, Integer courseId, Integer teacherId);
	
	Boolean isActive(Integer classId);

}
