package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.CourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherCourseEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.TeacherDto;
import com.iktpreobuka.elektronskidnevnik.entities.util.RESTError;
import com.iktpreobuka.elektronskidnevnik.repositories.AdminRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.CourseRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherCourseRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.services.CourseService;
import com.iktpreobuka.elektronskidnevnik.services.TeacherService;
import com.iktpreobuka.elektronskidnevnik.utils.Encryption;
import com.iktpreobuka.elektronskidnevnik.utils.TeacherCustomValidator;

@RestController
@RequestMapping(value = "/api/v1/teachers")
public class TeacherController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TeacherCourseRepository teacherCourseRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TeacherCustomValidator teacherValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(teacherValidator);
	}

	// Vrati sve nastavnike

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<TeacherEntity>>(
				((List<TeacherEntity>) teacherRepository.findAll()).stream()
						.filter(teacher -> !teacher.getDeleted().equals(true))
						.collect(Collectors.toList()),HttpStatus.OK);
	}

	// Dodaj novog nastavnika
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addnew")
	public ResponseEntity<?> createNew(@Valid @RequestBody TeacherDto newTeacher, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			teacherValidator.validate(newTeacher, result);
		}
		TeacherEntity teacher = new TeacherEntity();
		teacher.setDeleted(false);
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setUsername(newTeacher.getUsername());
		teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
		teacher.setRole(roleRepository.findById(2).get());
		teacherRepository.save(teacher);
		logger.info("Added teacher: " + newTeacher.toString());
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}
	
	// izmeni nastavnika
	
	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/{teacherId}")
	public ResponseEntity<?> updateTeacher(@PathVariable Integer teacherId, @Valid @RequestBody TeacherDto uteacher, 
			BindingResult result) {
		if(teacherRepository.existsById(teacherId) && teacherService.isActive(teacherId)) {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			TeacherEntity teacher = teacherRepository.findById(teacherId).get();
			teacher.setFirstName(uteacher.getFirstName());
			teacher.setLastName(uteacher.getLastName());
			teacherRepository.save(teacher);
			logger.info("Updated teacher with ID: " + teacherId.toString());
			return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
	}
	
	//	Obrisi nastavnika
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(value = "/{teacherId}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer teacherId) {
		if(teacherRepository.existsById(teacherId) && teacherService.isActive(teacherId)) {
			TeacherEntity teacher = teacherRepository.findById(teacherId).get();
			teacher.setDeleted(true);
			teacherRepository.save(teacher);
			logger.info("Deleted teacher with ID: " + teacherId.toString());
			return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
	}

	
	//  dodaj predmet nastavniku
	
		@Secured("ROLE_ADMIN")
		@PostMapping(value = "/{teacherId}/courses/{courseId}")
		public ResponseEntity<?> addCourseForTeacher(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
			if (teacherRepository.existsById(teacherId) && teacherService.isActive(teacherId)) {
				if (courseRepository.existsById(courseId) && courseService.isActive(courseId)) {
					TeacherEntity teacher = teacherRepository.findById(teacherId).get();
					CourseEntity course = courseRepository.findById(courseId).get();
					if (!teacherCourseRepository.existsByTeacherAndCourse(teacher, course)) {
						TeacherCourseEntity tce = new TeacherCourseEntity();
						tce.setDeleted(false);
						tce.setTeacher(teacherRepository.findById(teacherId).get());
						tce.setCourse(courseRepository.findById(courseId).get());
						teacherCourseRepository.save(tce);
						return new ResponseEntity<TeacherEntity>(teacherRepository.findById(teacherId).get(),
								HttpStatus.OK);
					} else if (teacherCourseRepository.existsByTeacherAndCourse(teacher, course)
								&& teacherCourseRepository.findByTeacherAndCourse(teacher, course).getDeleted().equals(true)) {
						TeacherCourseEntity tce = teacherCourseRepository.findByTeacherAndCourse(teacher, course);
						tce.setDeleted(false);
						teacherCourseRepository.save(tce);
						return new ResponseEntity<TeacherEntity>(teacherRepository.findById(teacherId).get(),
								HttpStatus.OK);
					}
					return new ResponseEntity<RESTError>(new RESTError(11, "Teacher course combination not found."),
							HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<RESTError>(new RESTError(2, "Course not found."), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
		}
	
		//vrati sve predmete nastavnika
		
		@Secured("ROLE_ADMIN")
		@GetMapping(value = "/{teacherId}/courses/")
		public ResponseEntity<?> getCoursesForTeacher(@PathVariable Integer teacherId) {
			if (teacherRepository.existsById(teacherId) && teacherService.isActive(teacherId)) {
				TeacherEntity teacher = teacherRepository.findById(teacherId).get();
				
				List<CourseEntity> courses = ((List<TeacherCourseEntity>) teacherCourseRepository.findByTeacher(teacher))
					.stream()
						.filter(teacherCourse -> !teacherCourse.getDeleted().equals(true))
						.map(course -> course.getCourse())
						.filter(course -> !course.getDeleted().equals(true))
						.collect(Collectors.toList());
				
				return new ResponseEntity<List<CourseEntity>>(courses, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(6, "Teacher not found."), HttpStatus.NOT_FOUND);
		}

	
	public String createErrorMessage(BindingResult result) {
		String errors = "";
		for (ObjectError error : result.getAllErrors()) {
			errors += error.getDefaultMessage();
			errors += "\n";
		}
		return errors;
	}
}
