package kr.co.app.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpSession;
import kr.co.app.user.entity.JoltzUser;
import kr.co.app.user.service.UserService;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private final UserService userService;
	
	/*이메일 중복 확인*/
	@GetMapping("/checkEmail")
	public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
		boolean isAvailable = userService.isEmailAvailable(email);
		return new ResponseEntity<>(isAvailable, HttpStatus.OK);
	}
	
	/* 회원가입 폼 */
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("newMember", new JoltzUser());
		return "user/register";
	}
	
	/* 회원가입 처리 */
	@PostMapping("/register")
	public JoltzUser registerMember(@RequestBody JoltzUser joltzUser) {
		try {
			return userService.save(joltzUser);
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already taken");
		}
	}
	
	/* 로그인폼 이동 */
	@GetMapping("/login")
	public String loginForm(Model model) {
		return "user/login";
	}
	
	/* 로그인 처리 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody JoltzUser joltzUser, HttpSession session, Model model) {
		
		JoltzUser userData = userService.getUserByEmail(joltzUser.getEmail());

		if(userData != null && userData.getPwd().equals(joltzUser.getPwd())) {
			session.setAttribute("CURRENT_USER", userData);
			session.setMaxInactiveInterval(3600);
			
			return ResponseEntity.ok(userData.getNickname()+", welcome!");
		} 
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid email or password");
	}
	
	/* 로그아웃 처리 */
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
}
