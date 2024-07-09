package kr.co.app.post.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import kr.co.app.user.entity.JoltzUser;
import lombok.Data;

@Entity
@Data
@Table(name = "joltz_community")
public class JoltzPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid")
	private Long pid;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "contents", nullable = false)
	private String contents;

	@Column(name = "author", nullable = false)
	private String author;
	
	@Column(name="author_id", nullable = false)
	private String authorId;

	@DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	@Column(name = "updated_at", nullable = false, updatable = false)
	private LocalDateTime updatedAt;

	@DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	@Column(name = "deleted_at", updatable = false)
	private LocalDateTime deletedAt;
	
	@Column(name = "delete_yn", nullable = false)
	private String deleteYn = "N";

	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
//	
//	private int page;
//	private int size;

}
