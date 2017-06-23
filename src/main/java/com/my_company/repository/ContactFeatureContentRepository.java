package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.ContactFeatureContent;

public interface ContactFeatureContentRepository extends JpaRepository<ContactFeatureContent, Long> {
	
	List<ContactFeatureContent> findById(Long count);

}
