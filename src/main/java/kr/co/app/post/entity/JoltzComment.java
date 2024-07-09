package kr.co.app.post.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "joltz_comment")
public class JoltzComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid")
	private Long cid;

	@ManyToOne
	@JoinColumn(name = "pid")
	private JoltzPost pid;
	
	@ManyToOne
	@JoinColumn(name= "parentCid")
	private JoltzComment parentCid;
	
	// 답댓글 목록
    @OneToMany(mappedBy = "parentCid", cascade = CascadeType.ALL)
    private List<JoltzComment> replies;
	
	// author - nickname
	@Column(name = "author", nullable = false)
	private String author;
	
	// author - email 
	@Column(name = "author_id", nullable = false)
	private String authorId;

	@Column(name = "comment", nullable = false)
	private String comment;

	@DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	@Column(name = "updated_at", updatable = false)
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
		deletedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	

}
