package back.controller.diary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	private List<MultipartFile> files;
	
	@PostMapping(value="/create.do", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createDiary(
			@ModelAttribute Diary diary,
			@RequestPart(value ="files", required = false) List<MultipartFile>files,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		SecurityUtil.checkAuthorization(userDetails);
		log.info("받은 값: {}", diary);
		diary.setUsersId(userDetails.getUser().getUsersId());
		diary.setCreateId(userDetails.getUsername());
		boolean isCreated = diaryService.createDiary(diary);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "일기 저장 성공":"일기 저장 실패", null));
	}
	@PostMapping("/list.do")
	public ResponseEntity<?> getDiaryList(@RequestBody Diary diary){
		log.info(diary.toString());
		List diaryList= diaryService.getDiaryList(diary);
		Map dataMap = new HashMap();
		dataMap.put("list", diaryList);
		dataMap.put("diary",diary);
		return ResponseEntity.ok(new ApiResponse<>(true, "목록 조회 성공", dataMap));
	}

}
