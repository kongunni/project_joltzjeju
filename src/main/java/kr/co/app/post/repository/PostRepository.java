package kr.co.app.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.app.post.entity.JoltzPost;

@Repository
public interface PostRepository extends JpaRepository<JoltzPost, Long> {
	
}
