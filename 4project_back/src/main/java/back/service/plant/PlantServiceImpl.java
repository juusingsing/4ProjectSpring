package back.service.plant;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.mapper.file.FileMapper;
import back.mapper.plant.PlantMapper;
import back.model.alarm.Alarm;
import back.model.common.PostFile;
import back.model.diary.Diary;
import back.model.pet.Pet;
import back.model.plant.Plant;
import back.model.write.Comment;
import back.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlantServiceImpl implements PlantService {
	@Autowired
	private PlantMapper plantMapper;
	@Autowired
	private FileMapper fileMapper;

	// 식물 목록 리스트
	@Override
	public List<Plant> getPlantList(Plant plant) {
	    try {
	        // diaryList 대신 plantList로 받아야 일관됨
	        List<Plant> plantList = plantMapper.getPlantList(plant);
	        
	        for (Plant p : plantList) {
	            PostFile file = new PostFile();
	            file.setPostFileKey(p.getPlantId()); // 식물 고유키로 변경
	            
	            List<PostFile> files = fileMapper.getFilesByFileKey(file);
	            if (files != null && !files.isEmpty()) {
	                p.setPostFiles(List.of(files.get(0))); // 첫 번째 파일만 넣음 (썸네일 용)
	            }
	        }
	        
	        return plantList;
	    } catch(Exception e) {
	        log.error("식물 목록 조회 실패", e);
	        throw new HException("식물 목록 조회 실패", e);
	    }
	}

	
	// 식물 정보 조회
	@Override
	public List<Plant> plantInfo(Plant plant) {
		List result = plantMapper.plantInfo(plant);
		return result;
	}

	// 식물 병충해 로그 개별 수정
	@Override
	public boolean updatePestLogs(Plant plant) {
		try {
	        boolean result = plantMapper.updatePestLogs(plant) > 0;
	        List<MultipartFile> files = plant.getFiles();
	        Integer plantId = plant.getPlantId();

	        if (result && files != null && !files.isEmpty() && plantId != null && plantId > 0) {
	            List<PostFile> fileList = FileUploadUtil.uploadFiles(
	                files, "pest", plantId, "PES", plant.getUpdateId()
	            );
	            log.info("파일사이즈:"+fileList.size());
	            for (PostFile postFile : fileList) {
	            	postFile.setPostFileId(plant.getFileId());
	                boolean insertResult = fileMapper.updateFilesByKey(postFile) > 0;
	                if (!insertResult) throw new HException("파일 추가 실패");

	            }
	        }

	        return result;

	    } catch (Exception e) {
	        log.error("병충해 수정 실패: {}", e.getMessage(), e);
	        throw new HException("병충해 수정 실패", e);
	    }
		

	}

	// 식물 병충해 로그 개별 삭제
	@Override
	public boolean deletePestLogs(Plant plant) {
		try {
			return plantMapper.deletePestLogs(plant) > 0;
		} catch (Exception e) {
			log.error("일지 삭제 중 오류 발생", e);
			throw new HException("일지 삭제 실패", e);
		}
	}

	// 식물 병충해 조회
	@Override
	public List<Plant> pestlogs(Plant plant) {
		List result = plantMapper.pestlogs(plant);
		return result;
	}

	// 식물 병충해 저장
	@Override
	@Transactional
	public boolean savePestInfo(Plant plant) {
	    try {
	        boolean result = plantMapper.savePestInfo(plant) > 0;
	        List<MultipartFile> files = plant.getFiles();
	        Integer plantId = plant.getPlantId();

	        if (result && files != null && !files.isEmpty() && plantId != null && plantId > 0) {
	            List<PostFile> fileList = FileUploadUtil.uploadFiles(
	                files, "pest", plantId, "PES", plant.getCreateId()
	            );
	            log.info("파일사이즈:"+fileList.size());
	            for (PostFile postFile : fileList) {
	                boolean insertResult = fileMapper.insertFile(postFile) > 0;
	                if (!insertResult) throw new HException("파일 추가 실패");
	                plant.setFileId(postFile.getPostFileId());
	                plantMapper.updatePestFileId(plant);
	            }
	        }

	        return result;

	    } catch (Exception e) {
	        log.error("병충해 저장 실패: {}", e.getMessage(), e);
	        throw new HException("병충해 저장 실패", e);
	    }
	}





	// 식물 분갈이 개별 수정
	@Override
	public boolean updatePlantRepottingLogs(Plant plant) {
		try {
			return plantMapper.updatePlantRepottingLogs(plant) > 0;
		} catch (Exception e) {
			log.error("일조량 수정 실패", e);
			throw new HException("일조량 수정 실패", e);
		}
	}

	// 식물 분갈이 단건 조회
	@Override
	public boolean getPlantRepottingLogsId(Plant plant) {
		try {
			return plantMapper.getPlantRepottingLogsId(plant) > 0;
		} catch (Exception e) {
			log.error("분갈이 조회 실패", e);
			throw new HException("분갈이 조회 실패", e);
		}
	}

	// 식물 분갈이 로그 개별 삭제
	@Override
	public boolean deletePlantRepottingLogs(Plant plant) {
		try {
			return plantMapper.deletePlantRepottingLogs(plant) > 0;
		} catch (Exception e) {
			log.error("일지 삭제 중 오류 발생", e);
			throw new HException("일지 삭제 실패", e);
		}
	}

	// 식물 분갈이 조회
	@Override
	public List<Plant> repottinglogs(Plant plant) {
		List result = plantMapper.repottinglogs(plant);
		return result;
	}

	// 식물 분갈이 저장
	@Override
	public boolean saveRepottingInfo(Plant plant) {
		int result = plantMapper.saveRepottingInfo(plant);
		return result > 0;
	}

	// 식물 일조량 단건 조회
	@Override
	public boolean getPlantSunlightLogsId(Plant plant) {
		try {
			return plantMapper.getPlantSunlightLogsId(plant) > 0;
		} catch (Exception e) {
			log.error("일조량 조회 실패", e);
			throw new HException("일조량 조회 실패", e);
		}
	}

	// 식물 일조량 개별 수정
	@Override
	public boolean updatePlantSunlightLogs(Plant plant) {
		try {
			return plantMapper.updatePlantSunlightLogs(plant) > 0;
		} catch (Exception e) {
			log.error("일조량 수정 실패", e);
			throw new HException("일조량 수정 실패", e);
		}
	}

	// 식물 일조량 개별 삭제
	@Override
	public boolean deletePlantSunlightLogs(Plant plant) {
		try {
			return plantMapper.deletePlantSunlightLogs(plant) > 0;
		} catch (Exception e) {
			log.error("일지 삭제 중 오류 발생", e);
			throw new HException("일지 삭제 실패", e);
		}
	}

	// 식물 일조량 조회
	@Override
	public List<Plant> findByPlantId(Plant plant) {
		List result = plantMapper.findByPlantId(plant);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPlantCheck(Integer plantId) {
		return plantMapper.selectPlantCheck(plantId);
	}

	@Override
	@Transactional
	public boolean saveSunlightingRecord(Plant plant) {

		boolean result = plantMapper.saveSunlightInfo(plant) > 0;
		return result;
	}

	// 식물 일조량 저장
	@Override
	public boolean saveSunlightInfo(Plant plant) {
		int result = plantMapper.saveSunlightInfo(plant);
		return result > 0;
	}

	@Override
	public Plant getPlantById(String plantId) throws HException {
		try {
			return plantMapper.selectPlantById(plantId);
		} catch (Exception e) {
			throw new HException("식물 조회 중 오류 발생", e);
		}
	}

	@Override
	@Transactional
	public boolean create(Plant plant) {
	    try {
	        // 1. plant insert (fileId 없이)
	        boolean result = plantMapper.create(plant) > 0;

	        Integer plantId = plant.getPlantId();  // insert 후 selectKey로 채워져 있어야 함
	        List<MultipartFile> files = plant.getFiles(); // 단일 파일이라도 리스트로 감싸져 있음

	        if (result && files != null && !files.isEmpty() && plantId != null) {
	            // 2. 파일 업로드 (단일 이미지)
	            List<PostFile> uploadedFiles = FileUploadUtil.uploadFiles(
	                files,
	                "plant",
	                plantId,
	                "PLA", //'PLA'로 지정
	                plant.getCreateId()
	            );

	            if (!uploadedFiles.isEmpty()) {
	                PostFile file = uploadedFiles.get(0); // 하나만 있음

	                // 3. DB에 파일 정보 저장 (단일 insert)
	                fileMapper.insertFile(file); // insertFile: 단일 파일용 insert

	                // 4. fileId를 plant에 저장
	                Integer fileId = file.getPostFileId(); // selectKey로 채워져야 함
	                plant.setFileId(fileId);
	                plantMapper.updatePlantFileId(plant);
	            }
	        }

	        return result;
	    } catch (Exception e) {
	        throw new HException("식물 저장 실패", e);
	    }
	}

	//식물 수정
	@Override
    @Transactional
    public boolean updatePlant(Plant plant) {
		// 1. 식물 기본 정보 업데이트 시도
	    boolean updatedPlantCoreInfo = plantMapper.updatePlant(plant) > 0;
	    boolean finalUpdateResult = updatedPlantCoreInfo; // 최종 업데이트 결과 초기값

	    // 식물 기본 정보 업데이트가 성공했고, 새로운 파일이 첨부된 경우에만 파일 관련 로직 실행
	    if (updatedPlantCoreInfo && plant.getFiles() != null && !plant.getFiles().isEmpty()) {
	        try {
	            // !!! 여기에 있었던 기존 파일 비활성화 (DEL_YN='Y' 업데이트) 로직을 제거합니다. !!!
	            // fileMapper.selectLatestFileIdByRefId(...)
	            // PostFile oldFileToDeactivate = ...
	            // fileMapper.deleteFile(oldFileToDeactivate) ... // 이 부분이 제거됩니다.

	            // 1.1. 새로운 파일 물리적으로 업로드 및 PostFile 객체 생성
	            List<PostFile> uploadedFiles = FileUploadUtil.uploadFiles(
	                    plant.getFiles(),
	                    "plant",
	                    plant.getPlantId(), // 이 파일이 어떤 식물에 대한 것인지 참조 (POST_FILE_KEY)
	                    "PLA",              // 파일 카테고리
	                    plant.getUpdateId() // 파일 생성/수정자 ID
	            );

	            // 1.2. 새로 업로드된 파일의 메타데이터를 데이터베이스에 삽입
	            if (!uploadedFiles.isEmpty()) {
	                // 식물은 단일 이미지를 가정하므로, 리스트의 첫 번째 파일만 처리
	                PostFile newFileMetadata = uploadedFiles.get(0);

	                // fileMapper.insertFile은 새 레코드를 삽입하고 postFileId를 채워줍니다.
	                boolean insertNewFileResult = fileMapper.insertFile(newFileMetadata) > 0;
	                if (!insertNewFileResult) {
	                    throw new HException("새 파일 메타데이터 DB 저장 실패");
	                }

	                // 1.3. 식물 레코드에 새로 삽입된 파일의 ID 연결
	                // insertFile 호출 후 PostFile 객체에 postFileId가 selectKey로 채워져 있어야 합니다.
	                Integer newFileId = newFileMetadata.getPostFileId();
	                if (newFileId != null) {
	                    // Plant 객체의 fileId 필드를 업데이트하여 plantMapper.updateFileId에 전달
	                    plant.setFileId(newFileId);
	                    finalUpdateResult = plantMapper.updatePlantFileId(plant) > 0; // plantMapper에 추가할 메서드
	                } else {
	                    throw new HException("새로 삽입된 파일 ID 조회 실패 (insertFile 후 ID가 null)");
	                }
	            } else {
	                // 파일이 제공되었으나 FileUploadUtil.uploadFiles가 빈 리스트를 반환한 경우 (예: 잘못된 파일 형식 등)
	                log.warn("Plant ID {} 에 대한 파일 업로드는 요청되었으나, 실제 업로드된 파일 없음.", plant.getPlantId());
	                // 이 경우에도 식물 기본 정보 업데이트 결과는 유지
	            }

	        } catch (HException e) {
	            log.error("Plant ID {} 식물 수정 중 파일 처리 실패: {}", plant.getPlantId(), e.getMessage(), e);
	            throw e;
	        } catch (Exception e) {
	            log.error("Plant ID {} 식물 수정 중 예상치 못한 오류 발생", plant.getPlantId(), e);
	            throw new HException("식물 수정 실패", e);
	        }
	    } else {
	        // 파일이 첨부되지 않았거나 식물 기본 정보 업데이트가 실패한 경우
	        // finalUpdateResult는 updatedPlantCoreInfo 값을 그대로 유지
	    }

	    return finalUpdateResult;
	}
	
	//식물 삭제
    @Override
    @Transactional
    public boolean deletePlant(Plant plant) {
        try {
            return plantMapper.deletePlant(plant) > 0;
        } catch (Exception e) {
            log.error("식물 삭제 중 오류 발생", e);
            throw new HException("식물 삭제 실패", e);
        }
    }


	@Override
	public boolean WaterCreate(Plant plant) throws NumberFormatException, IOException {
		boolean result = plantMapper.WaterCreate(plant) > 0;
		
		return result;
	}
	
	@Override
	public boolean WaterDelete(Plant plant) throws NumberFormatException, IOException {
		boolean result = plantMapper.WaterDelete(plant) > 0;
		
		return result;
	}


	@Override
	public List<Plant> waterList(Plant plant) throws NumberFormatException, IOException {

		return  plantMapper.waterList(plant);
	}
	
	@Override
	public List<Plant> plantIdList(Plant plant) {
		return plantMapper.plantIdList(plant);
	}

}
