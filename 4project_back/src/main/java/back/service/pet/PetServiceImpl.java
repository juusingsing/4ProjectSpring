
package back.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.mapper.file.FileMapper;
import back.mapper.pet.PetMapper;
import back.model.common.PostFile;
import back.model.pet.Pet;
import back.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;
    @Autowired
    private FileMapper fileMapper;
    // 반려동물 등록 처리
    @Override
    @Transactional
    public boolean registerPet(Pet pet) {
        try {
        	// String → java.util.Date 변환
            if (pet.getCreateDt() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // LocalDateTime.toString() 형식
                Date parsedDate = sdf.parse(pet.getCreateDt());
                // 필요하면 java.sql.Timestamp로 변환
                
            }

            boolean result = petMapper.insertPet(pet) > 0;

            List<MultipartFile> files = pet.getFiles();
            int animalId = pet.getAnimalId();

            if (result && files != null && animalId > 0) {
                log.info("파일 업로드 진입!");
                List<PostFile> fileList = FileUploadUtil.uploadFiles(
                        files,
                        "pet",
                        animalId,
                        "ANI", // 파일 카테고리 예시
                        pet.getCreateId()
                );

                for (PostFile postFile : fileList) {
                    
					boolean insertResult = fileMapper.insertFile(postFile) > 0;
                    if (!insertResult) throw new HException("파일 추가 실패");
                    log.info("업로드된 파일 수: {}", fileList.size());
                }
            }

            return result;
        } catch (Exception e) {
            log.error("반려동물 등록 실패 animalId={}, animalName={}", pet.getAnimalId(), pet.getAnimalName(), e);
            throw new HException("반려동물 등록 실패", e);
        }
    }
	


	
}

