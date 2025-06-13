package back.service.plant;

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
import back.model.common.PostFile;
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

	// 식물 정보 조회
	@Override
	public List<Plant> plantInfo(Plant plant) {
		List result = plantMapper.plantInfo(plant);
		return result;
	}

	// 식물 병충해 개별 수정
	@Override
	public boolean updatePestLogs(Plant plant) {
		try {
			return plantMapper.updatePestLogs(plant) > 0;
		} catch (Exception e) {
			log.error("병충해 수정 실패", e);
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
	public boolean savePestInfo(Plant plant) {
		int result = plantMapper.savePestInfo(plant);
		return result > 0;
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
	                "P", // PostFileCategory는 'P' (plant)로 지정
	                plant.getCreateId()
	            );

	            if (!uploadedFiles.isEmpty()) {
	                PostFile file = uploadedFiles.get(0); // 하나만 있음

	                // 3. DB에 파일 정보 저장 (단일 insert)
	                fileMapper.insertFile(file); // insertFile: 단일 파일용 insert

	                // 4. fileId를 plant에 저장
	                Integer fileId = file.getPostFileId(); // selectKey로 채워져야 함
	                plantMapper.updateFileId(plantId, fileId);
	            }
	        }

	        return result;
	    } catch (Exception e) {
	        throw new HException("식물 저장 실패", e);
	    }
	}


	@Override
    @Transactional
    public boolean update(Plant plant) {
        try {
            return plantMapper.update(plant) > 0;
        } catch (Exception e) {
            log.error("식물 수정 중 오류 발생", e);
            throw new HException("식물 수정 실패", e);
        }
    }

//    @Override
//    @Transactional
//    public boolean delete(Plant plant) {
//        try {
//            return plantMapper.delete(plant) > 0;
//        } catch (Exception e) {
//            log.error("식물 삭제 중 오류 발생", e);
//            throw new HException("식물 삭제 실패", e);
//        }
//    }

}
