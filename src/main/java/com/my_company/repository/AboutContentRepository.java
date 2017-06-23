package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.AboutContent;

public interface AboutContentRepository extends JpaRepository<AboutContent, Long> {
	
	List<AboutContent> findById(Long count);

}
