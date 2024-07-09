package kr.co.app.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.app.user.entity.JoltzUser;
import kr.co.app.user.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String main(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		JoltzUser currentUser = (JoltzUser)session.getAttribute("CURRENT_USER");
		model.addAttribute("CURRENT_USER", currentUser);
		
		if (currentUser == null ) {
			return "index";
		} else {
			return "index";
		}
	}
	
	

}
