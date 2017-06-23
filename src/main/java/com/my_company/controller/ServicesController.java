package com.my_company.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_company.model.JumbotronContent;
import com.my_company.model.ServicesContent;
import com.my_company.repository.ServicesRepository;


@Controller
public class ServicesController extends PermissionController {
	

	// instance of Repositories
	private ServicesRepository service;
	
	// autowire the repository to the controller
	@Autowired
	public ServicesController(ServicesRepository service) {
		this.service = service;
	}

	@RequestMapping("/services")
	public String services(HttpSession session) {
		// adds last jumbo 
		JumbotronContent jumboMain = findLastJumbo();		
		if (jumboMain != null) {
			session.setAttribute("jumboMain", jumboMain);
		}	
		// add last to services
		List<ServicesContent> mainTwo = findTwoLastServices();
		ServicesContent service1 = mainTwo.get(0);
		if (service1 != null) {
			session.setAttribute("service1", service1);
		}	
		ServicesContent service2 = mainTwo.get(1);
		if (service2 != null) {
			session.setAttribute("service2", service2);
		}	
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
			}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "services/services";
	}
	
	public List<ServicesContent> findTwoLastServices() {
		List<ServicesContent> services = service.findAll();
		if (services.size()-1 > 0) {
			List<ServicesContent> mainTwo = new ArrayList<>();
			for (int i = services.size()-1; i >= 0; i--) {
				if (services.get(i).getType().equals("top")) {
					mainTwo.add(services.get(i));
				} else if (services.get(i).getType().equals("bottom")) {
					mainTwo.add(services.get(i));
				}
			
			}
			return mainTwo;
		}
		return services;
	}
	
}
