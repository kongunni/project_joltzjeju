package kr.co.app.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.transaction.Transactional;
import kr.co.app.post.entity.JoltzPost;
import kr.co.app.post.repository.PostRepository;

@Transactional
@Service
public class PostService {
	
	@Autowired PostRepository postRepository;


	
	/* 페이징 */
	public Page<JoltzPost> getPosts(Pageable pageable) {
		Pageable sortPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
		return postRepository.findAll(sortPageable);
	}
	
	/* 목록 */
	public Optional<JoltzPost> getPost(Long postId) {
		return postRepository.findById(postId);
	}

	/* 게시글 목록 불러오기  */
	public JoltzPost getPostById(Long pid) {
		return postRepository.findById(pid).orElse(null);
	}
	
	/* 게시글 저장 */
	public JoltzPost savePost(JoltzPost post) {
		return postRepository.save(post);
	}

	/* 게시글 수정*/
	public JoltzPost updatePost(Long pid, String title, String contents) {
		Optional<JoltzPost> post = postRepository.findById(pid);
		if (!post.isPresent()) {
			throw new RuntimeException("post not found");
		}

		JoltzPost editPost = post.get(); 
		editPost.setTitle(title);
		editPost.setContents(contents);
		editPost.setUpdatedAt(LocalDateTime.now());
		
		return postRepository.save(editPost);
	}
	
	/* 게시글 삭제 */
	@Transactional
	public JoltzPost deletePost(Long pid) {
		Optional<JoltzPost> post = postRepository.findById(pid);
		if (!post.isPresent()) {
			throw new RuntimeException("post not found");
		}
		
		JoltzPost deletePost = post.get();
		deletePost.setDeletedAt(LocalDateTime.now());
		deletePost.setDeleteYn("Y");
		return postRepository.save(deletePost);
	}
	
}
