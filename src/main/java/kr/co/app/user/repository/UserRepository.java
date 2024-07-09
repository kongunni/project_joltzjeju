package kr.co.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.co.app.user.entity.JoltzUser;


@Repository
public interface UserRepository extends JpaRepository<JoltzUser, Long> {

	/* 로그인 & 아이디 찾기 */
	JoltzUser findByEmail(String email);

}
