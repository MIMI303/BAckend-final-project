package com.iktpreobuka.elektronskidnevnik.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;

@Component
public class DatabaseInit {
	
	@Autowired
	private RoleRepository roleRepository;
	
	private RoleEntity adminRole;
	private RoleEntity teacherRole;
	private RoleEntity studentRole;
	private RoleEntity parentRole;
	
	@PostConstruct
	public void init() {
		roleInit();

	}
	
	private void roleInit() {
		if (((List<RoleEntity>) roleRepository.findAll()).isEmpty()) {
			adminRole = new RoleEntity("ROLE_ADMIN");
			teacherRole = new RoleEntity("ROLE_TEACHER");
			studentRole = new RoleEntity("ROLE_STUDENT");
			parentRole = new RoleEntity("ROLE_PARENT");
			roleRepository.save(adminRole);
			roleRepository.save(teacherRole);
			roleRepository.save(studentRole);
			roleRepository.save(parentRole);
		} else {
			adminRole = roleRepository.findByName("ROLE_ADMIN");
			teacherRole = roleRepository.findByName("ROLE_TEACHER");
			studentRole = roleRepository.findByName("ROLE_STUDENT");
			parentRole = roleRepository.findByName("ROLE_PARENT");
		}
		
	}
	
}
