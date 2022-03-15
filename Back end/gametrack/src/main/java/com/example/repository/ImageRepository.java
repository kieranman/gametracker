package com.example.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Image;
import com.example.model.User;


@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

	Boolean existsByUserId(Long userId);

	Image findByUserId(Long id);
	
}