package back.controller.pet;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import back.model.common.CustomUserDetails;
import back.model.pet.Pet;
import back.service.pet.PetService;
import back.util.ApiResponse;
import back.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pet")
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping(value = "/animalregister.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerPet(
            @RequestPart("data") Pet pet,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SecurityUtil.checkAuthorization(userDetails);

        pet.setCreateId(userDetails.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        pet.setCreateDt(LocalDateTime.now().format(formatter));

        if (imageFile != null && !imageFile.isEmpty()) {
            pet.setFiles(List.of(imageFile));
        }

        boolean isCreated = petService.registerPet(pet);

        return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "반려동물 등록 성공" : "반려동물 등록 실패", null));
    }
}