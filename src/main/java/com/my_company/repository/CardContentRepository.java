package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.CardContent;


public interface CardContentRepository extends JpaRepository<CardContent, Long> {
	
	List<CardContent> findById(Long count);

	List<CardContent> findByType(String type);
}
