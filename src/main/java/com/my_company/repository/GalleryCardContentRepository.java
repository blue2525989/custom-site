package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.GalleryCardContent;


public interface GalleryCardContentRepository extends JpaRepository<GalleryCardContent, Long> {
	
	List<GalleryCardContent> findById(Long count);

	List<GalleryCardContent> findByType(String type);
}
