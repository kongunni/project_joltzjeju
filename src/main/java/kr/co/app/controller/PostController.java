package kr.co.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.co.app.post.entity.JoltzComment;
import kr.co.app.post.entity.JoltzPost;
import kr.co.app.post.service.CommentService;
import kr.co.app.post.service.PostService;
import kr.co.app.user.entity.JoltzUser;

@Controller
@RequestMapping(value = "/community")
public class PostController {
	
	@Autowired private PostService postService;
	@Autowired private CommentService commentService;
	
	@GetMapping("/list")
	public String allPostList(Model model, @RequestParam(name="page", defaultValue="0")int page, @RequestParam(name="size", defaultValue="10")int size) {
		Pageable pageable = PageRequest.of(Math.max(page-1, 0), size, Sort.by("createdAt").descending());
		// 페이징
		Page<JoltzPost> postPage = postService.getPosts(pageable);
		model.addAttribute("postPage", postPage);
		return "community/communityList";
	}
	
	/* 새글작성 */
	@GetMapping("/write")
	public String showInsertPostForm(HttpSession session, Model model) {
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		model.addAttribute("CURRENT_USER", CURRENT_USER);
		return "community/insertPost";
	}
	
	/* 새글작성 post */
	@PostMapping("/write")
	public String writePost(HttpSession session, RedirectAttributes attribute,
		   @RequestParam("title") String title, 
		   @RequestParam("contents")String contents) {
		
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		
		if (CURRENT_USER == null) {
			throw new RuntimeException("current_user is null ");
//			return "user/login";
		}
		System.out.println("[CURRENT_USER]"+CURRENT_USER);
		
		JoltzPost post = new JoltzPost();
		post.setTitle(title);
		post.setContents(contents);
		// 작성자 닉네임, 이메일
		post.setAuthor(CURRENT_USER.getNickname());
		post.setAuthorId(CURRENT_USER.getEmail());
		post.setCreatedAt(LocalDateTime.now());
		post.setDeleteYn("N");
//		post.setUpdatedAt(null);
//		post.setDeletedAt(null);
		
		postService.savePost(post);
		
		attribute.addFlashAttribute("message","create post");
		return "redirect:/community/list";
	}
	
	/* 게시글 삭제 - deleteYn update 'Y' */
	@PostMapping("/delete/{pid}")
	public String deletePost(@PathVariable("pid")Long pid) {
		postService.deletePost(pid);
		return "redirect:/community/list";
	}
	
	// GET 요청으로 게시글 삭제를 처리하는 핸들러
    @GetMapping("/delete/{pid}")
    public String deletePostByGet(@PathVariable("pid") Long pid) {
        postService.deletePost(pid);
        return "redirect:/community/list";
    }
    
    
    /************테스트 완료*************/
    /* 게시글 */
    // 목록보기
	@GetMapping("/test/list")
    public String list(Model model, @RequestParam(name="page", defaultValue="0")int page, @RequestParam(name="size", defaultValue="10")int size) {
		Pageable pageable = PageRequest.of(Math.max(page-1, 0), size, Sort.by("createdAt").descending());
		Page<JoltzPost> postPage = postService.getPosts(pageable);
		model.addAttribute("postPage", postPage);
		return "test/test_list";
	}
	
	//상세보기
	@GetMapping("/detail/{pid}")
	public String detail(@PathVariable("pid")Long pid, Model model, HttpSession session) {
		// 현재 로그인한 사용자 정보
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		// 게시글 찾아오기
		JoltzPost post = postService.getPostById(pid);
		// 해당 게시글 댓글 가져오기 
		List<JoltzComment> commentList = commentService.getCommentsByPid(post);
		model.addAttribute("CURRENT_USER",CURRENT_USER);
		model.addAttribute("post", post);
		model.addAttribute("commentList", commentList);
		
		return "community/detail";
	}
	
	/* 게시글 수정 GET */
	@GetMapping("/edit/{pid}")
	public String editForm(@PathVariable("pid")Long pid, Model model) {
		JoltzPost post = postService.getPostById(pid);
		model.addAttribute("post", post);
		return "community/update";
	}
	
	/* 게시글 수정 POST */
	@PostMapping("/edit/{pid}")
	public String edit(@PathVariable("pid")Long pid, @ModelAttribute JoltzPost post) {
		postService.updatePost(pid, post.getTitle(), post.getContents());
		return "redirect:/community/detail/" + pid;
	}
	
	/* 댓글 조회 리스트 GET */
	@GetMapping("/detail/{pid}/comment")
	public String getPostDetail(@PathVariable("pid") Long pid, Model model) {
	    // 게시글 가져오기
	    JoltzPost post = postService.getPostById(pid);
	    // 해당 게시글의 댓글 가져오기
	    List<JoltzComment> commentList = commentService.getCommentsByPid(post);
	    // 모델에 게시글과 댓글 리스트 추가
	    model.addAttribute("post", post);
	    model.addAttribute("commentList", commentList);
	    return "community/detail";
	}

	/* 댓글 작성 */
	@PostMapping("/detail/{pid}/comment/insert")
	public String addComment(
			@PathVariable("pid")Long pid, 
			@RequestParam("comment")String comment, 
			Model model, HttpSession session) {
		
		// 현재 사용자 불러오기
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		// 현재 게시글 불러오기
		JoltzPost post = postService.getPostById(pid);
		// 새로운 댓글셋팅
		JoltzComment newComment = new JoltzComment();
		newComment.setPid(post);
		newComment.setComment(comment);
		newComment.setAuthor(CURRENT_USER.getNickname());
		newComment.setAuthorId(CURRENT_USER.getEmail());
		newComment.setCreatedAt(LocalDateTime.now());
		
		// 댓글 저장
		commentService.saveComment(newComment);
		return "redirect:/community/detail/" + pid;
	}
	
	/* 댓글 수정 */
	@PostMapping("/detail/{pid}/comment/update")
	public String updateComment(@PathVariable("pid")Long pid, @RequestParam("cid") Long cid, @RequestParam("comment")String comment, HttpSession session) {
		
		// 현재 사용자 불러오기 
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		// 해당 댓글 조회 
		JoltzComment existingComment = commentService.getCommentsByCid(cid);
		
		if (existingComment.getAuthorId().equals(CURRENT_USER.getEmail())) {
			//댓글 수정 셋팅
			existingComment.setComment(comment);
			existingComment.setUpdatedAt(LocalDateTime.now());
			//수정된 내용 저장
			commentService.saveComment(existingComment);
		} 
		return "redirect:/community/detail/"+pid;		
	}
	
	/* 댓글 삭제 */
	@PostMapping("/detail/{pid}/comment/delete")
	public String deleteComment(@PathVariable("pid")Long pid, @RequestParam("cid")Long cid,HttpSession session) {
		// 현재 사용자 불러오기 
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		// 해당 댓글 조회 
		JoltzComment existingComment = commentService.getCommentsByCid(cid);
		
		if (existingComment.getAuthorId().equals(CURRENT_USER.getEmail())) {
			//댓글 삭제 세팅 - 상태 변경
			existingComment.setDeletedAt(LocalDateTime.now());
			existingComment.setDeleteYn("Y");
			//수정된 내용 저장
			commentService.saveComment(existingComment);
		}
		return "redirect:/community/detail/"+pid;		
	}
	
 	/* 답댓글 조회 */
	@GetMapping("/comment/{cid}/reply")
	@ResponseBody
	public List<JoltzComment> getReplies(@PathVariable("cid")Long cid) {
		JoltzComment parentComment = commentService.getCommentsByCid(cid);
		if(parentComment == null) {
			return new ArrayList<>();
		}
		return commentService.getRepliesByCid(cid);
	}
	
	/* 답댓글 작성 */
	@PostMapping("/comment/{cid}/reply/insert")
	public String addReply(@PathVariable("cid")Long cid, @RequestParam("comment")String comment, HttpSession session) {
		//사용자 검증
		JoltzUser CURRENT_USER = (JoltzUser) session.getAttribute("CURRENT_USER");
		
//		If(CURRENT_USER==null){return "redirect:/user/login";}
		JoltzComment parentCid = commentService.getCommentsByCid(cid);
		

		if (parentCid == null){
			return "error";
		}		
		
		//parentCid != null
		JoltzComment newReply = new JoltzComment();
		newReply.setPid(parentCid.getPid());
		newReply.setParentCid(parentCid);
		newReply.setComment(comment);
		newReply.setAuthor(CURRENT_USER.getNickname());
		newReply.setAuthorId(CURRENT_USER.getEmail());
		newReply.setCreatedAt(LocalDateTime.now());
		
		commentService.saveComment(newReply);
		
		//게시물 가져오기
//		JoltzPost pid = parentCid.getPid();
		
		  return "redirect:/community/detail/" + parentCid.getPid().getPid();
	}
	

	/* 답댓글 수정 */
	
	
	/* 답댓글 삭제 */
	
    
	
	/*********************************************/

	
	
}
