package kr.co.app.post.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.app.post.entity.JoltzComment;
import kr.co.app.post.entity.JoltzPost;

@Repository
public interface CommentRepository extends JpaRepository<JoltzComment, Long> {

	// 게시글에 달린 댓글 조회
	List<JoltzComment> findByPid(JoltzPost post);

	// 답댓글 조회
	List<JoltzComment> findByCid(Long cid);

}
