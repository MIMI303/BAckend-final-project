package com.iktpreobuka.elektronskidnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskidnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	ParentEntity findByUsername(String username);

}
