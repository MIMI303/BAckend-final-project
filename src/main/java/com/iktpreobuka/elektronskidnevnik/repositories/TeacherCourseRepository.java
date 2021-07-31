package com.iktpreobuka.elektronskidnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.CourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;

public interface TeacherCourseRepository extends CrudRepository<TeacherCourseEntity, Integer> {
	
	Boolean existsByTeacherAndCourse(TeacherEntity teacher, CourseEntity course);
	
	TeacherCourseEntity findByTeacherAndCourse(TeacherEntity teacher, CourseEntity course);
	
	List<TeacherCourseEntity> findByTeacher(TeacherEntity teacher);
	
	List<TeacherCourseEntity> findByCourse(CourseEntity course);
}
