package back.mapper.combo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.board.Board;
import back.model.board.Comment;
import back.model.combo.Combo;

@Mapper
public interface ComboMapper {
	
	
	
	public int create(Combo combo);
	
	public int update(Combo combo);
	
	public int delete(Combo combo);
	
	public List<Combo> list();
	
	
}
