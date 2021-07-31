package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherCourseEntity;

public interface StudentService {
	
	StudentEntity addCourseForStudent(Integer studentId, Integer courseId, Integer teacherId);
	
	Boolean isActive(Integer studentId);
	
	List<TeacherCourseEntity> getCourses(Integer studentId);

}
