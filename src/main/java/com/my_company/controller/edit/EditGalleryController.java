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
import com.my_company.model.GalleryCardContent;
import com.my_company.model.Image;
import com.my_company.repository.GalleryCardContentRepository;
import com.my_company.repository.ImageRepository;

@Controller
public class EditGalleryController extends PermissionController {

	private GalleryCardContentRepository card;
	private ImageRepository imgRepo;
	
	@Autowired
	public EditGalleryController(GalleryCardContentRepository card, ImageRepository imgRepo) {
		this.card = card;
		this.imgRepo = imgRepo;
	}
	
	@RequestMapping("/edit-gallery")
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
		return "admin/edit-gallery";
	}
	
	@PostMapping(path="/edit-gallery/edit-card")
	// request params to save
	public String addNewCardGallery(Model model, @RequestParam String headline
			, @RequestParam String content, @RequestParam String url,
			@RequestParam String type) {

		GalleryCardContent cardNew = new GalleryCardContent();
		cardNew.setHeadline(headline);
		cardNew.setContent(content);
		cardNew.setType(type);
		card.save(cardNew);
		return "redirect:/edit-gallery";
	}
	

	// delete element
	@GetMapping(path="/delete-card-gallery")
	public String deleteCard(Long ID, HttpSession session) {
		card.delete(ID);
		String saved = "The card with ID " + ID + " has been deleted.";
		session.setAttribute("saved", saved);
		return "redirect:/saved";
	}
		
	// list all element
	@RequestMapping("/list-card-gallery")
	public String listAllcards(Model model) {
		List<GalleryCardContent> cardList = card.findAll();
		if (cardList != null) {
			model.addAttribute("listMain", cardList);
		}	
		return "admin/list-all-type";
	}
	
	
}
