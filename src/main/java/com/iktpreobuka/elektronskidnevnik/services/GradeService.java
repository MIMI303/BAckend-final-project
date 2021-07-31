package com.iktpreobuka.elektronskidnevnik.services;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronskidnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentTeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GradeDto;

public interface GradeService {
	
	ResponseEntity<?> gradeStudent(GradeDto newGrade, Integer studentId, Integer teacherId, Integer courseId) throws MessagingException;
	
	ResponseEntity<?> gradeStudent(GradeDto newGrade, Integer studentTeacherCourse) throws MessagingException;
	
	Boolean checkForFinalGrade(StudentTeacherCourseEntity stce);
	
	void sendEmailToParent(StudentTeacherCourseEntity stc, GradeEntity grade) throws MessagingException;

}
