package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

public interface AdminService {
	
	List<String> getAllUsernames();
	
	Integer findIdByUsername(String username);
}
