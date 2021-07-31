package com.iktpreobuka.elektronskidnevnik.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleAll(HttpMessageNotReadableException e) {
        return new ResponseEntity<String>(e.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MultipartException.class)
	public String handleError (MultipartException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "File too large!");
		return "redirect:/uploadStatus";
	}
}
