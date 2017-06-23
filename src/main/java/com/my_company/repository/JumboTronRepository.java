package com.my_company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my_company.model.JumbotronContent;


public interface JumboTronRepository extends JpaRepository<JumbotronContent, Long> {
	
	List<JumbotronContent> findById(Long id);
	
}
