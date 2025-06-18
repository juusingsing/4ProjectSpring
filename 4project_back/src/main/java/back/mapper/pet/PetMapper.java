package back.mapper.pet;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import back.model.pet.Pet;

@Mapper
public interface PetMapper {
	public int insertPet(Pet pet);
	public int updatePetFileId(@Param("fileId") Long fileId, @Param("animalId") int animalId);
	public int updatePet(Pet pet);
	int deletePetByIdAndUser(@Param("animalId") int animalId, @Param("usersId") String usersId);
	
	Pet getPetByIdAndUsername(@Param("animalId") int animalId, @Param("usersId") String usersId,  @Param("category") String category);
}
