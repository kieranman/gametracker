package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Exceptions.ResourceNotFoundException;
import com.example.model.Image;
import com.example.model.User;
import com.example.payload.response.MessageResponse;
import com.example.repository.ImageRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import org.springframework.http.MediaType;


import org.springframework.http.HttpHeaders;

@Controller
@CrossOrigin("http://localhost:3000")
public class FileController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/upload/{token}")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("token") String token){
		try {
		    String username = jwtUtils.getUserNameFromJwtToken(token);
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			
			if (imageRepository.existsByUserId(user.getId())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error you've already added a file before!"));
			}
			else {
				 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				 Image FileDB = new Image(user,fileName, file.getContentType(), file.getBytes());
				 imageRepository.save(FileDB);
				 return ResponseEntity.ok(new MessageResponse("Image uploaded successfully"));
			}
		}
		catch (Exception e) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error upload failed!"));
		}
	}
	
	@GetMapping(value="/files/{id}" , produces = MediaType.IMAGE_JPEG_VALUE)
	  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
	    Image fileDB = imageRepository.findByUserId(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	  }
	
}
