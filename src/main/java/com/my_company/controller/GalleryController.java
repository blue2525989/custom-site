package com.my_company.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_company.model.GalleryCardContent;
import com.my_company.model.JumbotronContent;
import com.my_company.repository.GalleryCardContentRepository;

@Controller
public class GalleryController extends PermissionController {

	private GalleryCardContentRepository card;
	
	public GalleryController(GalleryCardContentRepository card) {
		this.card = card;
	}
	
	@RequestMapping("/gallery")
	public String gallery(HttpSession session) {
		// adds the three most current cards
		findLastNineCards(session);
		// adds last jumbo 
		JumbotronContent jumboMain = findLastJumbo();		
		if (jumboMain != null) {
			session.setAttribute("jumboMain", jumboMain);
		}
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "gallery/gallery";
	}
	
	private void findLastNineCards(HttpSession session) {
		long size = card.count();
		if (size > 0) {
			List<GalleryCardContent> cards = card.findAll();
			GalleryCardContent card1;
			GalleryCardContent card2;
			GalleryCardContent card3;
			GalleryCardContent card4;
			GalleryCardContent card5;
			GalleryCardContent card6;
			GalleryCardContent card7;
			GalleryCardContent card8;
			GalleryCardContent card9;
			// reverse list to get newest ones first
			for (int i = cards.size()-1; i >= 0; i--) {
				if (cards.get(i).getType().equals("card1")) {
					card1 = cards.get(i);
					session.setAttribute("card1", card1);
				} else if (cards.get(i).getType().equals("card2")) {
					card2 = cards.get(i);
					session.setAttribute("card2", card2);
				} else if (cards.get(i).getType().equals("card3")) {
					card3 = cards.get(i);
					session.setAttribute("card3", card3);
				} else if (cards.get(i).getType().equals("card4")) {
					card4 = cards.get(i);
					session.setAttribute("card4", card4);
				} else if (cards.get(i).getType().equals("card5")) {
					card5 = cards.get(i);
					session.setAttribute("card5", card5);
				} else if (cards.get(i).getType().equals("card6")) {
					card6 = cards.get(i);
					session.setAttribute("card6", card6);
				} else if (cards.get(i).getType().equals("card7")) {
					card7 = cards.get(i);
					session.setAttribute("card7", card7);
				} else if (cards.get(i).getType().equals("card8")) {
					card8 = cards.get(i);
					session.setAttribute("card8", card8);
				} else if (cards.get(i).getType().equals("card9")) {
					card9 = cards.get(i);
					session.setAttribute("card9", card9);
				}
			}
		}
	}
}
