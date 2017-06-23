package com.my_company.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my_company.model.CardContent;
import com.my_company.model.FeatureContent;
import com.my_company.model.JumbotronContent;
import com.my_company.repository.CardContentRepository;
import com.my_company.repository.FeatureContentRepository;

@Controller
public class MainController extends PermissionController {

	// instance of Repositories
	private CardContentRepository card;
	private FeatureContentRepository feature;
	
	// autowire the repository to the controller
	@Autowired
	public MainController(CardContentRepository card, FeatureContentRepository feature) {
		this.card = card;
		this.feature = feature;
	}
	
	@RequestMapping("/")
	public String index(HttpSession session) {
		// adds last jumbo 
		JumbotronContent jumboMain = findLastJumbo();		
		if (jumboMain != null) {
			session.setAttribute("jumboMain", jumboMain);
		}	
		// adds the three most current cards
		List<CardContent> cards = card.findAll();
		CardContent card1;
		CardContent card2;
		CardContent card3;
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
			}
		}
		// adds the feature at bottom
		FeatureContent featureMain = findLastFeature();
		if (featureMain != null) {
			session.setAttribute("featureMain", featureMain);
		}	
			
		// admin user
		if (hasAdminRole()) {
			session.setAttribute("adminrole", hasAdminRole());
		}
		// regular user
		else if (hasUserRole()) {
			session.setAttribute("userrole", hasUserRole());
		}
		return "main/index";
	}
	
	
	public CardContent findByType(String type) {
		List<CardContent> cards = card.findAll();
		// goes backwards to get newest.
		if (cards != null) {
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getType() == type) {
					return cards.get(i);
				}
			}
		}		
		return null;
	}
	
	
	public FeatureContent findLastFeature() {
		long size = feature.count();
		FeatureContent last = feature.getOne(size);
		return last;
	}


}
