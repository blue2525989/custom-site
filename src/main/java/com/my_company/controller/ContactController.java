package com.my_company.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_company.model.JumbotronContent;
import com.my_company.repository.ContactFeatureContentRepository;

@Controller
public class ContactController extends PermissionController {

	// instance of Repositories
	private ContactFeatureContentRepository feature;
	
	// autowire the repository to the controller
	@Autowired
	public ContactController(ContactFeatureContentRepository feature) {
		this.feature = feature;
	}
	
	
	@RequestMapping("/contact")
	public String contact(HttpSession session) {
		// adds last jumbo 
		JumbotronContent jumboMain = findLastJumbo();		
		if (jumboMain != null) {
			session.setAttribute("jumboMain", jumboMain);
		}
		findLastContactFeature(session);
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "contact/contact";
	}
	
	public void findLastContactFeature(HttpSession session) {
		long size = feature.count();
		if (size > 0) {
			session.setAttribute("contactMain", feature.getOne(size));
		}
	}
}
