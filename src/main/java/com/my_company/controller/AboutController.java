package com.my_company.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_company.model.AboutContent;
import com.my_company.model.JumbotronContent;
import com.my_company.repository.AboutContentRepository;

@Controller
public class AboutController extends PermissionController {
	
	// instance of Repositories
	private AboutContentRepository about;
	// autowire the repository to the controller
	@Autowired
	public AboutController(AboutContentRepository about) {
		this.about = about;
	}

	
	@RequestMapping("/about")
	public String about(HttpSession session) {
		// adds last jumbo 
		JumbotronContent jumboMain = findLastJumbo();		
		if (jumboMain != null) {
			session.setAttribute("jumboMain", jumboMain);
		}
		// try just counting instead of returning full list to speed up times
		long size = about.count();
		if (size > 0) {
			AboutContent aboutCon = about.getOne(size);
			if (aboutCon != null) {
				session.setAttribute("aboutMain", aboutCon);
			}
		}
		
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "about/about";
	}
}
