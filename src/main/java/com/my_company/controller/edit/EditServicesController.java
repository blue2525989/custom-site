package com.my_company.controller.edit;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my_company.controller.PermissionController;
import com.my_company.model.Image;
import com.my_company.model.ServicesContent;
import com.my_company.repository.ImageRepository;
import com.my_company.repository.ServicesRepository;

@Controller
public class EditServicesController extends PermissionController {


	// instance of Repositories
	private ServicesRepository services;
	private ImageRepository imgRepo;
	
	// autowire the repository to the controller
	@Autowired
	public EditServicesController(ServicesRepository services, ImageRepository imgRepo) {
		this.services = services;
		this.imgRepo = imgRepo;
	}
	
	@RequestMapping("/edit-services")
	public String admin(HttpSession session) {
		
		// adds full list from gallery
		// need to work on slimming down list.
		List<Image> imageList = imgRepo.findAll();
		if (imageList != null) {
			session.setAttribute("imageList", imageList);
		}
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}		
		return "admin/edit-services";
	}
	
	/* for editing services content */
	
	@PostMapping(path="/edit-services/edit-service1")
	// request params to save
	public String addNewService1(Model model, @RequestParam String headline
			, @RequestParam String content, @RequestParam String url) {

		ServicesContent service = new ServicesContent();
		service.setHeadline(headline); 
		service.setUrl(url);
		service.setContent(content);
		service.setType("top");
		services.save(service);
		return "redirect:/edit-services";
	}
	
	@PostMapping(path="/edit-services/edit-service2")
	// request params to save
	public String addNewService2(Model model, @RequestParam String headline
			, @RequestParam String content, @RequestParam String url) {

		ServicesContent service = new ServicesContent();
		service.setHeadline(headline);
		service.setContent(content);
		service.setUrl(url);
		service.setType("bottom");
		services.save(service);
		return "redirect:/edit-services";
	}
	
	// delete element
	@GetMapping(path="/delete-service")
	public String deleteService(Long ID, HttpSession session) {
		services.delete(ID);
		// add javaScript document pop notifcation
		String saved = "The user with ID " + ID + " has been deleted.";
		session.setAttribute("saved", saved);
		return "redirect:/saved";
	}
		
	// list all element
	@RequestMapping("/list-services")
	public String listAllServices(Model model, HttpSession session) {
		List<ServicesContent> featureList = services.findAll();
		// Iterator iter = jumboList.iterator();
		if (featureList != null) {
			model.addAttribute("listMain", featureList); /* all named list for uniformity */
		}	
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "admin/list-all";
	}
	
}
