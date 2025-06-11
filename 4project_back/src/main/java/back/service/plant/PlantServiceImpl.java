package back.service.plant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import back.exception.HException;
import back.mapper.plant.PlantMapper;
import back.model.plant.Plant;
import back.model.write.Comment;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlantServiceImpl implements PlantService {
	
    @Autowired
    private PlantMapper plantMapper;
    
    //식물 일조량 단건 조회
    @Override
	public boolean getPlantSunlightLogsId(Plant plant) {
        try {
			return plantMapper.getPlantSunlightLogsId(plant) > 0;
		} catch (Exception e) {
			log.error("일조량 조회 실패", e);
			throw new HException("일조량 조회 실패", e);
		}
	}
    
    
    
    //식물 일조량 개별 수정
	@Override
	public boolean updatePlantSunlightLogs(Plant plant) {
        try {
			return plantMapper.updatePlantSunlightLogs(plant) > 0;
		} catch (Exception e) {
			log.error("댓글 일조량 실패", e);
			throw new HException("댓글 일조량 실패", e);
		}
	}

    //식물 일조량 개별 삭제
    @Override
    public boolean deletePlantSunlightLogs(Plant plant) {
    	try {
            return plantMapper.deletePlantSunlightLogs(plant) > 0;
        } catch (Exception e) {
            log.error("일지 삭제중 오류 발생", e);
            throw new HException("일지 삭제 실패", e);
        }
    }
    
    //식물 일조량 조회
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

    
    //식물저장
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
            return plantMapper.create(plant) > 0;
        } catch (Exception e) {
            log.error("식물 등록 중 오류 발생", e);
            throw new HException("식물 등록 실패", e);
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
