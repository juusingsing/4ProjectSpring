package back.mapper.write;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.write.Write;
import back.model.write.Comment;

@Mapper
public interface WriteMapper {
	
	public List<Write> getWriteList(Write write);
	
	public int getTotalWriteCount(Write write);
	
	public Write getWriteById(String WriteId);
	
	public int create(Write write);
	
	public int update(Write write);
	
	public int delete(Write write);
	
	public List<Comment> getCommentsByWriteId(String WriteId);
	
	public int insertComment(Comment comment);
	
	public int updateComment(Comment comment);
	
	public int deleteComment(Comment comment);
	
}
