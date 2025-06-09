package back.controller.plant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import back.model.common.CustomUserDetails;
import back.model.plant.Plant;
import back.service.plant.PlantService;
import back.util.ApiResponse;
import back.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/plant")
@Slf4j
public class PlantController {
    @Autowired
    private PlantService plantService;
    
    // 식물이름 + 입수일만 조회
    @PostMapping("/simple-list.do")
    public ResponseEntity<?> getSimplePlantList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        SecurityUtil.checkAuthorization(userDetails);
        String plantId = userDetails.getUsername();

        List<Map<String, Object>> simpleList = plantService.getPlantCheck(plantId);

        Map<String, Object> data = new HashMap<>();
        data.put("list", simpleList);

        return ResponseEntity.ok(new ApiResponse<>(true, "간단 식물 목록 조회 성공", data));
    }
    
	 // 식물 상세 조회
	    @GetMapping("/{plantId}")
	    public ResponseEntity<?> getPlantById(
	            @PathVariable String plantId,
	            @AuthenticationPrincipal CustomUserDetails userDetails
	    ) {
	        SecurityUtil.checkAuthorization(userDetails);
	        Plant plant = plantService.getPlantById(plantId);
	
	        if (plant == null) {
	            return ResponseEntity.status(404).body(new ApiResponse<>(false, "식물을 찾을 수 없습니다.", null));
	        }
	
	        // 본인의 식물만 조회 가능하도록 제한
	        if (!plant.getUsersId().equals(userDetails.getUsername())) {
	            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
	        }
	
	        return ResponseEntity.ok(new ApiResponse<>(true, "식물 조회 성공", plant));
	    }
    
    // 식물 등록 (파일 업로드 포함)
    @PostMapping(value = "/create.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @ModelAttribute Plant plant,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        plant.setUsersId(userDetails.getUsername());
        plant.setCreateId(userDetails.getUsername());
        plant.setFile(file);  // MultipartFile 세팅

        boolean isCreated;
        try {
            isCreated = plantService.create(plant);
        } catch (Exception e) {
            log.error("식물 등록 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "식물 등록 실패: " + e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "식물 등록 성공" : "식물 등록 실패", null));
    }

    // 식물 수정 (파일 업로드 포함)
    @PutMapping(value = "/update.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @ModelAttribute Plant plant,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        Plant existingPlant;
        try {
            existingPlant = plantService.getPlantById(String.valueOf(plant.getPlantId()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "내부 서버 오류", null));
        }
        if (existingPlant == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "수정할 식물을 찾을 수 없습니다.", null));
        }
        if (!existingPlant.getUsersId().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }

        plant.setUpdateId(userDetails.getUsername());
        plant.setFile(file);

        boolean isUpdated;
        try {
            isUpdated = plantService.update(plant);
        } catch (Exception e) {
            log.error("식물 수정 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "식물 수정 실패: " + e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse<>(isUpdated, isUpdated ? "식물 수정 성공" : "식물 수정 실패", null));
    }

    // 식물 삭제 (파일 삭제 처리 필요 시 추가 가능)
    @DeleteMapping("/{plantId}")
    public ResponseEntity<?> delete(
            @PathVariable String plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        Plant existingPlant;
        try {
            existingPlant = plantService.getPlantById(plantId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "내부 서버 오류", null));
        }
        if (existingPlant == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "삭제할 식물을 찾을 수 없습니다.", null));
        }
        if (!existingPlant.getUsersId().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }
        boolean isDeleted;
        try {
            isDeleted = plantService.delete(Integer.parseInt(plantId));
        } catch (Exception e) {
            log.error("식물 삭제 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>(false, "식물 삭제 실패: " + e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse<>(isDeleted, isDeleted ? "식물 삭제 성공" : "식물 삭제 실패", null));
    }
}
