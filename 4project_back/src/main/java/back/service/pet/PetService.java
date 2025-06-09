package back.service.pet;
import org.springframework.web.multipart.MultipartFile;

import back.model.pet.Pet;

public interface PetService {
    public boolean registerPet(Pet pet);

	public boolean updatePet(Pet pet);

	public boolean deletePet(int animalId, String usersId);

	
}

