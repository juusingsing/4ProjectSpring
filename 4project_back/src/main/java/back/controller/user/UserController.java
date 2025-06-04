package back.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.model.common.CustomUserDetails;
import back.model.user.User;
import back.service.user.UserService;
import back.util.ApiResponse;
import back.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/view.do")
	public ResponseEntity<?> view(@RequestBody User user) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
//		SecurityUtil.checkAuthorization(userDetails, userDetails.getUser().getUserId());
		
		String userId = "";
		if (user.getUsersId() == null || user.getUsersId().equals("") ) {
			userId = userDetails.getUser().getUsersId();
		} else {
			userId = user.getUsersId();
		}
		User selectUser = userService.getUserById(userId);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "조회 성공", selectUser));
	}
	
	
	   /**
     * 회원가입
     */
    @PostMapping("/register.do")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("회원가입 요청 : {}", user.getUsersId());
        user.setCreateId("SYSTEM");
        boolean success = userService.registerUser(user);

        return ResponseEntity.ok(new ApiResponse<>(success, success ? "회원가입 성공" : "회원가입 실패", null));
    }
    
    @PostMapping("/checkUsersId.do")
	public ResponseEntity<ApiResponse<Map<String, Object>>> checkUserId(@RequestBody User user) {
		log.info("아이디 중복 검사 : {}", user.getUsersId()); // Lombok의 로깅 기능을 사용하여 아이디 중복 검사 요청 정보를 로그로 남깁니다.

		Map<String, Object> data = new HashMap<>(); // 중복 확인 결과를 담을 HashMap 객체를 생성합니다.
		boolean exists = userService.usersIdCheck(user); // UserService를 통해 아이디 중복 여부를 확인합니다.
		data.put("exists", exists); // HashMap에 "exists"라는 키로 중복 여부(true 또는 false)를 저장합니다.

		// API 응답 객체를 생성합니다.
		ApiResponse<Map<String, Object>> response = new ApiResponse<>(true, "중복체크 성공", data);

		return ResponseEntity.ok(response); // HTTP 상태 코드 200 (성공)과 함께 ApiResponse를 반환합니다.
	}
    
//    
//	@PostMapping("/register.do")
//	public ResponseEntity<?> register(@RequestBody User user) {
//		log.info("회원가입 요청: {}", user.getUsersId());
//		
//		int userIdCheck = userService.userIdCheck(user);
//		
//		if (userIdCheck == 0) {
//			user.setCreateId("SYSTEM");
//			boolean success = userService.registerUser(user);
//			
//			return ResponseEntity.ok(new ApiResponse<>(success, success ? "회원가입 성공" : "회원가입 실패", null));
//		} else {
//			log.info(userIdCheck + "아이디중복");
//			return ResponseEntity.ok(new ApiResponse<>(false,  "abc", "아이디 중복", null));
//		}
//	}	
		
	
	
	@PostMapping("/update.do")
	public ResponseEntity<?> update(@RequestBody User user) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		log.info("회원정보 수정 요청 : {}", user.getUsersId());
		
		
			
			user.setUpdateId(userDetails.getUsername());
			
			boolean success = userService.updateUser(user);
			
			return ResponseEntity.ok(new ApiResponse<>(success, success ? "수정 성공" : "수정 실패", userDetails.getUser()));
		
		
		
		
	}
	
	@PostMapping("/delete.do")
	public ResponseEntity<?> delete(@RequestBody User user, HttpSession session) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		log.info("회원탈퇴 요청 : {}", user.getUsersId());
		SecurityUtil.checkAuthorization(userDetails, userDetails.getUser().getUsersId());
		user.setUpdateId(userDetails.getUsername());
		
		boolean success = userService.deleteUser(user);
		
		if (success) {
			session.invalidate();
			SecurityContextHolder.clearContext();
		}
		
		return ResponseEntity.ok(new ApiResponse<>(success, success ? "삭제 성공" : "삭제 실패", null));
	}
	
	@PostMapping("/login.do")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request) {
		log.info("로그인 시도 : {}", user.getUsersId());
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsersId(), user.getUsersPassword())
			);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			HttpSession session = request.getSession(true);
			session.setAttribute(
					HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext()
			);
			log.info("세션 ID: {}", session.getId());
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			return ResponseEntity.ok(new ApiResponse<>(true, "로그인 성공", userDetails.getUser()));
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			log.warn("로그인 실패: {}", user.getUsersId());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse<>(false, "아이디 또는 비밀번호가 일치하지 않습니다.", null));
		}
	}

	@PostMapping("/logout.do")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		log.info("로그아웃 요청");
		
		request.getSession().invalidate();
		SecurityContextHolder.clearContext();
		
		return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 완료", null));
		
	}
	
	/**
	 * 
	 * 게시글 목록 조회 (페이징 + 검색조건)
	 */
	@PostMapping("/list.do")
	public ResponseEntity<?> getUserList(@RequestBody User user) {
		log.info(user.toString());
		List<User> userList = userService.getUserList(user);
		Map dataMap = new HashMap();
		dataMap.put("list", userList);
		dataMap.put("user", user);
		return ResponseEntity.ok(new ApiResponse<>(true, "목록 조회 성공", dataMap));
	}
	
	@PostMapping("/userM.do")
	public ResponseEntity<?> userM(@RequestBody User user) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		log.info("회원관리 요청 : {}", user.getUsersId());
		
		user.setUpdateId(userDetails.getUsername());
		
		boolean success = userService.userM(user);
		
		
		return ResponseEntity.ok(new ApiResponse<>(success, success ? "회원관리 성공" : "회원관리 실패", null));
	}

}