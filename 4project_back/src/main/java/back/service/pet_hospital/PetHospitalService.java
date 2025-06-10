package back.service.pet_hospital;

import org.springframework.web.multipart.MultipartFile;

import back.model.pet_hospital.PetHospital;

public interface PetHospitalService {


	PetHospital registerHospitalRecord(PetHospital petHospital, MultipartFile file);

}
