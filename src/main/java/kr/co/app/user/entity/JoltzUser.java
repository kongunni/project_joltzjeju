package kr.co.app.user.entity;


import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Data
@Table(name="joltz_user")
public class JoltzUser  {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "username", nullable = false, length = 20, unique = true)
	    private String username;

	    @Column(name = "email", nullable = false, length = 60, unique = true)
	    private String email;

	    @Column(name = "pwd", nullable = false, length = 50)
	    private String pwd;

	    @Column(name = "nickname", nullable = false, length = 20)
	    private String nickname;
	    
	    @Column(name = "phone", nullable = false, length = 20)
	    private String phone;

	    @Column(name = "birthday", nullable = false)
	    private String birthday;

//	    @Column(name = "DELETE_YN", nullable = false)
//	    private int deleteYn;

	    @DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	    @Column(name = "reg_date")
	    @CreationTimestamp
	    private LocalDateTime regDate;

	    @DateTimeFormat(pattern = "yy.MM.dd HH:mm:ss")
	    @Column(name = "mod_date")
	    @UpdateTimestamp
	    private LocalDateTime modDate;
	    
//	    @ElementCollection(fetch = FetchType.EAGER)
//	    @CollectionTable(name = "JOLTZ_MEMBER_ROLES", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//	    @Column(name = "role")
//	    private List<String> roles; //GNR_USER, CORP_USER, ADMIN


	    
	    public JoltzUser() {
	    	
	    }

	    
		public JoltzUser(Long id, String username, String email, String pwd, String phone, String birthday,LocalDateTime regDate, LocalDateTime modDate) {
			this.id = id;
			this.username = username;
			this.email = email;
			this.pwd = pwd;
			this.phone  = phone;
			this.birthday = birthday;
			this.regDate = regDate;
			this.modDate = modDate;
		}
				
	    
	    

	    
	   
	    
	    
}

