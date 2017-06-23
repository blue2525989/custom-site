package com.my_company.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.my_company.controller.PermissionController;
import com.my_company.model.Image;
import com.my_company.repository.ImageRepository;

@Controller
public class FileService extends PermissionController {
	
	private ImageRepository imgRepo;
	private AmazonS3Client s3client;
	
	@Autowired
	public FileService(ImageRepository imgRepo,	AmazonS3Client s3client) {
		this.imgRepo = imgRepo;
		this.s3client = s3client;
	}

	// uses javaScript to upload, mainly just redirects to saved
	@RequestMapping("/upload-image")
	public String uploadImage(@RequestParam String type, @RequestParam File file,  
			HttpSession session) {
		
		Image img = new Image();
		img.setName(file.getName());
		img.setUrl("https://" + file.getName());
		img.setType(type);
		imgRepo.save(img);
		
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		String saved = "Image " + file.getName() + " has been saved.";
		session.setAttribute("saved", saved);
		return "redirect:/saved";
	}
	
	@RequestMapping("/upload-page")
	public String uploadImagePage(HttpSession session) {
		
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "admin/upload-images";
	}
	
	// delete element
	@GetMapping(path="/delete-image")
	public String deleteImages(Long ID, HttpSession session) {
		// need to add aws code to delete from bucket
		String file = imgRepo.findOne(ID).getName();
		imgRepo.delete(ID);
		s3client.deleteObject(new DeleteObjectRequest("blue-company-images", file));
		String saved = "The Image with ID " + ID + " has been deleted.";
		session.setAttribute("saved", saved);
		return "redirect:/saved";
	}
		
	// list all element
	@RequestMapping("/list-images")
	public String listImages(Model model, @RequestParam String type) {
		List<Image> imageList = imgRepo.findAll();
		List<Image> realList = new ArrayList<Image>();
		for (int i = imageList.size()-1; i >= 0; i--) {
			if (imageList.get(i).getType().equalsIgnoreCase(type)) {
				realList.add(imageList.get(i));
			}
		}
		if (realList != null) {
			model.addAttribute("listMain", realList);
		}	
		return "admin/list-all-images";
	}
}


