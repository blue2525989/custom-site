package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.Image;


public interface ImageRepository extends JpaRepository<Image, Long> {
	
	List<Image> findById(Long count);

	List<Image> findByType(String type);
}
