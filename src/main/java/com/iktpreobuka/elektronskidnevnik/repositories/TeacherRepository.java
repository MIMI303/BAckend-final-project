package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
	
	Boolean existsByUsername(String username);
	
	TeacherEntity findByUsername(String username);
}
