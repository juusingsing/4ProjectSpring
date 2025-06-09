package back.controller.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // 식물 등록
    @PostMapping(value = "/create.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @ModelAttribute Plant plant,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        plant.setUsersId(userDetails.getUsername());
        plant.setCreateId(userDetails.getUsername());
        boolean isCreated = plantService.create(plant);
        return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "식물 등록 성공" : "식물 등록 실패", null));
    }

    // 식물 단건 조회
    @GetMapping("/{plantId}")
    public ResponseEntity<?> getPlant(
            @PathVariable String plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        Plant plant = plantService.getPlantById(plantId);
        if (plant == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "식물을 찾을 수 없습니다.", null));
        }
        // 권한 체크: 조회하려는 식물의 usersId가 현재 사용자와 일치하는지 확인
        if (!plant.getUsersId().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "식물 조회 성공", plant));
    }

    // 식물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> getPlantList(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        List<Plant> plantList = plantService.getPlantList(userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse<>(true, "식물 목록 조회 성공", plantList));
    }

    // 식물 수정
    // 요청의 Content-Type이 multipart/form-data (파일+폼 데이터 전송)인 경우만 처리함
    @PutMapping(value = "/update.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @ModelAttribute Plant plant,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        // 수정 권한 체크: 본인 식물인지 확인 (필요시 추가)
        Plant existingPlant = plantService.getPlantById(String.valueOf(plant.getPlantId()));
        if (existingPlant == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "수정할 식물을 찾을 수 없습니다.", null));
        }
        if (!existingPlant.getUsersId().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }
        plant.setUpdateId(userDetails.getUsername());
        boolean isUpdated = plantService.update(plant);
        return ResponseEntity.ok(new ApiResponse<>(isUpdated, isUpdated ? "식물 수정 성공" : "식물 수정 실패", null));
    }

    // 식물 삭제
    @DeleteMapping("/{plantId}")
    public ResponseEntity<?> delete(
            @PathVariable String plantId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);
        Plant existingPlant = plantService.getPlantById(plantId);
        if (existingPlant == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "삭제할 식물을 찾을 수 없습니다.", null));
        }
        if (!existingPlant.getUsersId().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }
        boolean isDeleted = plantService.delete(plantId);
        return ResponseEntity.ok(new ApiResponse<>(isDeleted, isDeleted ? "식물 삭제 성공" : "식물 삭제 실패", null));
    }
}
