package kr.co.app.user.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import kr.co.app.user.entity.JoltzUser;
import kr.co.app.user.repository.UserRepository;


@Transactional
@Service
public class UserService{

	@Autowired
	private UserRepository userRepository;

	/* 이메일 중복확인 */
	public boolean isEmailAvailable(String email) {
		return userRepository.findByEmail(email) == null;
		//		return !userRepository.findByEmail(email).isPresent();
	}

	/* 회원가입 */
	public JoltzUser save(JoltzUser joltzUser) {
		
		JoltzUser isEmailAvaliable = userRepository.findByEmail(joltzUser.getEmail());
		
		if (isEmailAvaliable != null) {
			throw new IllegalStateException("email already taken");
		}
		return userRepository.save(joltzUser);
//		if(userRepository.findByEmail(joltzUser.getEmail()).isPresent()) {
//			throw new IllegalStateException("Email already taken.");
//		}
//		return userRepository.save(joltzUser);
	}


	/* 로그인처리 */
	public JoltzUser getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	

	
	
	
}