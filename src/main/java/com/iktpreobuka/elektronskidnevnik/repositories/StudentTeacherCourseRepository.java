package com.iktpreobuka.elektronskidnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentTeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherCourseEntity;

public interface StudentTeacherCourseRepository extends CrudRepository<StudentTeacherCourseEntity, Integer> {

	Boolean existsByStudentAndTeacherCourse(StudentEntity student, TeacherCourseEntity teacherCourse);
	
	StudentTeacherCourseEntity findByStudentAndTeacherCourse(StudentEntity student, TeacherCourseEntity teacherCourse);
	
	List<StudentTeacherCourseEntity> findByStudent(StudentEntity student);
}
