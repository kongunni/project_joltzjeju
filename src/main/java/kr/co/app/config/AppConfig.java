package kr.co.app.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import kr.co.app.post.service.CommentService;
import kr.co.app.post.service.PostService;
import kr.co.app.user.service.UserService;

@Configuration
@EnableJpaRepositories(basePackages = {"kr.co.app.user.repository", "kr.co.app.post.repository", "kr.co.app.comment.repository"})
public class AppConfig {
	/*
	 * EnableJpa ->jpa 레파지토리 활성화
	 * */
	@Bean
	public UserService userService() {
		return new UserService();
	}
	
	@Bean
	public PostService postService() {
		return new PostService();
	}
	
	@Bean
	public CommentService commentService() {
		return new CommentService();
	}
	
}
