package back.service.diary;

import back.exception.HException;
import back.model.diary.Diary;

public interface DiaryService{
	public boolean createDiary(Diary diary) throws HException;
}