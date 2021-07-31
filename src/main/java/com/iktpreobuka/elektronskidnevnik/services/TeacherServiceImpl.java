package com.iktpreobuka.elektronskidnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Override
	public Boolean isActive(Integer teacherId) {
		if(teacherRepository.existsById(teacherId)) {
			TeacherEntity teacher = teacherRepository.findById(teacherId).get();
			if(teacher.getDeleted().equals(true)) {
				return false;
			}
			return true;
		}
		return false;
	}
}
