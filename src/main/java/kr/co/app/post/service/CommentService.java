package kr.co.app.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.app.post.entity.JoltzComment;
import kr.co.app.post.entity.JoltzPost;
import kr.co.app.post.repository.CommentRepository;

@Transactional
@Service
public class CommentService {
	
	@Autowired CommentRepository commentRepository;

	 
	/* 게시글에 대한 댓글 목록 조회 */
	public List<JoltzComment> getCommentsByPid(JoltzPost post) {
		return commentRepository.findByPid(post);
	}

	/* 댓글 조회 */
	public JoltzComment getCommentsByCid(Long cid) {
	    Optional<JoltzComment> commentOptional = commentRepository.findById(cid);
	    return commentOptional.orElse(null); // 해당 ID에 해당하는 댓글이 없을 경우 null 반환
	}
	
	/* 댓글 작성 */
	public JoltzComment saveComment(JoltzComment comments) {
		return commentRepository.save(comments);
	}

	/* 댓글 수정 */
	public JoltzComment getCommentByCid(Long cid, String editComment) {
		Optional<JoltzComment> optionalComment = commentRepository.findById(cid);
		if(optionalComment.isPresent()) {
			JoltzComment existingComment = optionalComment.get();
			// 수정된 내용으로 댓글 반영
			existingComment.setComment(editComment);
			existingComment.setUpdatedAt(LocalDateTime.now());
			return commentRepository.save(existingComment);
		} else {
			throw new RuntimeException("comment with id:"+cid+"not found");
		}
	}

	/* 답댓글 조회 */
	public List<JoltzComment> getRepliesByCid(Long cid) {
		return commentRepository.findByCid(cid);
	}

	
}
