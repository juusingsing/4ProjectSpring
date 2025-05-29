package back.mapper.file;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.board.Board;
import back.model.board.Comment;
import back.model.common.PostFile;

@Mapper
public interface FileMapper {
	
	public int insertFile(PostFile file);
	
	public PostFile getFileByFileId(PostFile file);
	
	public List<PostFile> getFilesByBoardId(String BoardId);
	
	public int deleteFile(PostFile file);
	
	
}
