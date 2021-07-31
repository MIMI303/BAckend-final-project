package com.iktpreobuka.elektronskidnevnik.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.ClassEntity;
import com.iktpreobuka.elektronskidnevnik.entities.CourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentTeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.CourseRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentTeacherCourseRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherCourseRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;

@Service
public class ClassServiceImpl implements ClassService {
	
	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private TeacherCourseRepository teacherCourseRepository;

	@Autowired
	private StudentTeacherCourseRepository studentTeacherCourseRepository;

	@Override
	public List<StudentEntity> addCoursesForEntireClass(Integer classId, Integer courseId, Integer teacherId) {
		ClassEntity classEntity = classRepository.findById(classId).get();
		CourseEntity course = courseRepository.findById(courseId).get();
		TeacherEntity teacher = teacherRepository.findById(teacherId).get();
		TeacherCourseEntity teacherCourse = teacherCourseRepository.findByTeacherAndCourse(teacher, course);

		List<StudentEntity> studentsOfClass = studentRepository.findByAttendingClass(classEntity)
				.stream().filter(student -> !student.getDeleted().equals(true)).collect(Collectors.toList());
		List<StudentEntity> modifiedStudents = new ArrayList<>();

		for (StudentEntity studentEntity : studentsOfClass) {
			if (!studentTeacherCourseRepository.existsByStudentAndTeacherCourse(studentEntity, teacherCourse)) {
				StudentTeacherCourseEntity stce = new StudentTeacherCourseEntity();
				stce.setStudent(studentEntity);
				stce.setTeacherCourse(teacherCourse);
				studentTeacherCourseRepository.save(stce);
				modifiedStudents.add(studentEntity);
			}
		}

		return modifiedStudents;

	}
	
	@Override
	public Boolean isActive(Integer classId) {
		if(classRepository.existsById(classId)) {
			ClassEntity clazz = classRepository.findById(classId).get();
			if(clazz.getDeleted().equals(true)) {
				return false;
			}
			return true;
		}
		return false;
	}


}
