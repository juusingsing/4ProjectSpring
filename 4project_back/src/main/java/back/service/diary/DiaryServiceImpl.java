package back.service.diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import back.exception.HException;
import back.mapper.diary.DiaryMapper;
import back.model.diary.Diary;
import back.service.board.BoardServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService{
	@Autowired
	private DiaryMapper diaryMapper;
//	@Autowired
//	private FileMapper fileMappre;
	
	@Override
	@Transactional
	public boolean createDiary(Diary diary) {
		try {
			boolean result = diaryMapper.create(diary)>0;
			return result;
		}catch(Exception e) {
			log.error("일기 저장 실패 userId={}, title={}", e);
			throw new HException("일기 저장 실패", e);
			}
	}
}
