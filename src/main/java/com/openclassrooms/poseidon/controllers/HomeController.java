package com.openclassrooms.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling general navigation routes like the home and admin home pages.
 */
@Controller
public class HomeController {

	/**
	 * Displays the public home page.
	 *
	 * @return the name of the view to display ("home")
	 */
	@RequestMapping("/")
	public String home() {
		return "home";
	}

	/**
	 * Redirects the admin home page to the bid list view.
	 *
	 * @return redirect to "/bidList/list"
	 */
	@RequestMapping("/admin/home")
	public String adminHome() {
		return "redirect:/bidList/list";
	}
}
