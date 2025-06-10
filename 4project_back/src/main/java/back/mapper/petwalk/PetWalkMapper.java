package back.mapper.petwalk;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.common.PostFile;
import back.model.pet.Pet;

@Mapper
public interface PetWalkMapper {
	
	public int petWalkSave(Pet pet);
	
	public int insertFile(PostFile file);
	
	public PostFile getFileByFileId(PostFile file);
	
	public List<PostFile> getFilesByBoardId(String BoardId);
	
	public int deleteFile(PostFile file);
	
	public List<PostFile> getAllFiles(); // 파일 전체 가져오기
	
}
