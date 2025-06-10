package back.controller.petwalk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.model.pet.Pet;
import back.model.common.CustomUserDetails;
import back.model.common.PostFile;
import back.service.petwalk.PetWalkService;
import back.util.ApiResponse;
import back.util.FileUploadUtil;
import back.util.SecurityUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/petWalk")
@Slf4j
public class PetWalkController {

	@Value("${myapp.apiBaseUrl}")
	private String apiBaseUrl;
	
	@Autowired
	private PetWalkService petWalkService;
	
	@Autowired
	private ServletContext servletContext;
	
	@PostMapping(value = "/petSave.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> petWalkSave(
			@ModelAttribute Pet pet, @AuthenticationPrincipal CustomUserDetails userDetails)
					throws NumberFormatException, IOException {
		
		SecurityUtil.checkAuthorization(userDetails);
				
		pet.setCreateId(userDetails.getUser().getUsersId());
		boolean isCreated = petWalkService.petWalkSave(pet);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "펫산책 저장 성공" : "펫산책 저장 실패", null));
		
	}
	
	
	
	@PostMapping(value = "/imgSave.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> imgSave(
			@ModelAttribute Pet pet,
			@RequestPart(value = "files", required = false) List<MultipartFile> files) throws NumberFormatException, IOException {
		log.info("이미지 파일 업로드 요청");
		
				
		pet.setFiles(files);
		boolean isCreated = petWalkService.imgSave(pet);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "게시글 등록 성공" : "게시글 등록 실패", null));
		
	}
	
	@PostMapping("/imgLoad.do")
    public List<PostFile> getAllImages(@ModelAttribute PostFile postFile) {
        return petWalkService.getAllFiles(postFile); // 모든 파일을 반환하는 쿼리 필요
    }
	
	
	
	
}