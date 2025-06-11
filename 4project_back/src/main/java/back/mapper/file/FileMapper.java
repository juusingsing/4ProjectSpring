package back.mapper.file;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import back.model.common.PostFile;
import back.model.pet.Pet;

@Mapper
public interface FileMapper {
	
	public int insertFile(PostFile file);

	public PostFile getFileByFileId(int file);
	
	public List<PostFile> getFilesByFileKey(PostFile file);
	
	public int deleteFile(PostFile file);
	
	public List<PostFile> getAllFiles(PostFile postFile); // 파일 전체 가져오기

	Long selectLatestFileIdByRefId(@Param("refId") int refId, @Param("category") String category);
	
	
}
