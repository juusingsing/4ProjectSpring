package back.mapper.diary;

import org.apache.ibatis.annotations.Mapper;

import back.model.diary.Diary;

@Mapper
public interface DiaryMapper {
//	public Diary getDiaryById(String userId);
	public int create(Diary diary);
}
