package back.service.pet_hospital;

import back.exception.HException;
import back.mapper.pet_hospital.PetHospitalMapper;
import back.model.pet_hospital.PetHospital;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetHospitalServiceImpl implements PetHospitalService {

    private final PetHospitalMapper petHospitalMapper;

    @Override
    @Transactional
    public PetHospital registerHospitalRecord(PetHospital petHospital, MultipartFile file) {
        try {
            petHospitalMapper.insertPetHospital(petHospital); // 여기서 ID가 petHospital에 set됨 (MyBatis useGeneratedKeys)
            
            // 이미지 저장이 필요하면 여기에 추가
            // 예: String filePath = fileStorageService.save(file);
            // petHospital.setImagePath(filePath);

            return petHospital;
        } catch (Exception e) {
            log.error("병원 진료 등록 실패", e);
            throw new HException("병원 진료 등록 실패", e);
        }
    }

    
}
