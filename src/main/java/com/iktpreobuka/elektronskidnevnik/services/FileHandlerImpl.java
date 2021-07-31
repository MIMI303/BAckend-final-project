package com.iktpreobuka.elektronskidnevnik.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileHandlerImpl implements FileHandler {

	@Override
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		// TODO proverimo da li je taj fajl prazan
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "No file selected! Select a file");
			return "redirect:uploadStatus";
		}

		
			byte[] bytes = file.getBytes();
			Path path = Paths.get("C:\\Users\\W541\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\elektronskiDnevnik\\logs" + file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message",
					"File" + file.getOriginalFilename() + "successfully uploded :)");
		

		return "redirect:uploadStatus";
	}

}
