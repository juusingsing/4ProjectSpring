package back.mapper.diary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import back.model.diary.Diary;

@Mapper
public interface DiaryMapper {
//	public Diary getDiaryById(String userId);
	public int create(Diary diary);
	public List<Diary> getDiaryList(Diary diary);
	public List<Map<String, Object>> getDiaryTypes();
	
}
