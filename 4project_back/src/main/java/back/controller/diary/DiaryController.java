package back.controller.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.model.common.CustomUserDetails;
import back.model.diary.Diary;
import back.service.diary.DiaryService;
import back.util.ApiResponse;
import back.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/diary")
@Slf4j
public class DiaryController {
	@Autowired
	private DiaryService diaryService;
	
	@PostMapping(value="/create.do", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createDiary(
			@ModelAttribute Diary diary,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		SecurityUtil.checkAuthorization(userDetails);
//		diary.setUserId(userDetails.getUser().getUserId());
		diary.setCreateId(userDetails.getUsername());
		boolean isCreated = diaryService.createDiary(diary);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "일기 저장 성공":"일기 저장 실패", null));
	}

}
