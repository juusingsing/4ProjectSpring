package back.service.diary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.mapper.diary.DiaryMapper;
import back.mapper.file.FileMapper;
import back.model.common.PostFile;
import back.model.diary.Diary;

import back.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService{
	@Autowired
	private DiaryMapper diaryMapper;
	private FileMapper fileMapper;
//	@Autowired
//	private FileMapper fileMappre;
	
	@Override
	@Transactional
	public boolean createDiary(Diary diary) {
		try {
			boolean result = diaryMapper.create(diary)>0;
			List<MultipartFile>files=diary.getFiles();
			
			if(result && files !=null) {
				List<PostFile> fileList = FileUploadUtil.uploadFiles(
						files, 
						"diary",
						0,
						Integer.parseInt(diary.getDiaryId()), 
						"DIA",
						diary.getCreateId(),
						""
					);
			for(PostFile postFile : fileList) {
				boolean insertResult = fileMapper.insertFile(postFile)>0;
				if(!insertResult) throw new HException("파일 추가 실패");
			}
			}
			return result;
		}catch(Exception e) {
			log.error("일기 저장 실패 userId={}, title={}", diary.getUsersId(), diary.getDiaryTitle(), e);
			throw new HException("일기 저장 실패", e);
			}
	}

	@Override
	public List<Diary> getDiaryList(Diary diary) {
		try {
			return diaryMapper.getDiaryList(diary);
		}catch(Exception e) {
			log.error("게시글 목록 조회 실패", e);
			throw new HException("게시글 목록 조회 실패",e);
		}
	}
}
