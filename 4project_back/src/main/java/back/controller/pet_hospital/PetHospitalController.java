package back.controller.pet_hospital;

import back.model.common.CustomUserDetails;
import back.model.pet_hospital.PetHospital;
import back.service.pet_hospital.PetHospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/petHospital")
@RequiredArgsConstructor
@Slf4j
public class PetHospitalController {

    private final PetHospitalService petHospitalService;

    @PostMapping(
    	    value = "/petHospital.do",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public ResponseEntity<?> insertPetHospital(
    		@RequestParam("animalId") int animalId,  // 추가	
    	    @RequestParam("animalVisitDate") String animalVisitDate,
    	    @RequestParam("animalHospitalName") String animalHospitalName,
    	    @RequestParam("animalMedication") String animalMedication,
    	    @RequestParam(value = "animalTreatmentMemo", required = false) String animalTreatmentMemo,
    	    @RequestParam(value = "animalTreatmentType", required = false) String animalTreatmentType,
    	    @RequestPart(value = "image", required = false) MultipartFile file,
    	    @AuthenticationPrincipal CustomUserDetails userDetails
    	) {
    	    try {
    	        PetHospital petHospital = new PetHospital();
    	        petHospital.setAnimalId(animalId);  // 필드 추가 세팅 필요
    	        petHospital.setAnimalVisitDate(animalVisitDate);
    	        petHospital.setAnimalHospitalName(animalHospitalName);
    	        petHospital.setAnimalMedication(animalMedication);
    	        petHospital.setAnimalTreatmentMemo(animalTreatmentMemo);
    	        petHospital.setAnimalTreatmentType(animalTreatmentType);
    	        petHospital.setCreateId(userDetails.getUsername());

    	        PetHospital saved = petHospitalService.registerHospitalRecord(petHospital, file);

    	        // 등록된 ID를 포함한 JSON 응답 반환
    	        return ResponseEntity.ok().body(
    	            Map.of(
    	                "message", "등록 성공",
    	                "animalHospitalTreatmentId", saved.getAnimalHospitalTreatmentId()
    	            )
    	        );
    	    } catch (Exception e) {
    	        return ResponseEntity.badRequest().body("등록 실패: " + e.getMessage());
    	    }
    	}

    // 추후에 진료 조회, 수정, 삭제 메서드도 이 구조에 맞게 추가 가능

} // 클래스 닫기