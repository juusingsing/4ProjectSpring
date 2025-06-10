package back.service.petwalk;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import back.model.pet.Pet;
import back.model.common.PostFile;

public interface PetWalkService {
    
	public boolean petWalkSave(Pet pet) throws NumberFormatException, IOException;
	
	public boolean imgSave(Pet pet) throws NumberFormatException, IOException;
	
    public PostFile getFileByFileId(PostFile file);
    
    public Map<String, Object> insertBoardFiles(PostFile file);
    
    public List<PostFile> getAllFiles(PostFile postFile);
}