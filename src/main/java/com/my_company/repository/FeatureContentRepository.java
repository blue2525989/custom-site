package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.FeatureContent;

public interface FeatureContentRepository extends JpaRepository<FeatureContent, Long> {
	
	List<FeatureContent> findById(Long count);

}
