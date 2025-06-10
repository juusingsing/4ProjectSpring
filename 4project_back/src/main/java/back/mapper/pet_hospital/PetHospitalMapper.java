package back.mapper.pet_hospital;

import org.apache.ibatis.annotations.Mapper;

import back.model.pet_hospital.PetHospital;
@Mapper
public interface PetHospitalMapper {
	public int insertPetHospital(PetHospital petHospital);
}
