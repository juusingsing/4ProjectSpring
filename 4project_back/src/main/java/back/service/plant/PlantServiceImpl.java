package back.service.plant;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import back.exception.HException;
import back.mapper.plant.PlantMapper;
import back.model.plant.Plant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantMapper plantMapper;

    @Override
    public List<Map<String, Object>> getPlantCheck(String plantId) {
        return plantMapper.selectPlantCheck(plantId);
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

    @Override
    @Transactional
    public boolean delete(int plantId) {
        try {
            return plantMapper.delete(plantId) > 0;
        } catch (Exception e) {
            log.error("식물 삭제 중 오류 발생", e);
            throw new HException("식물 삭제 실패", e);
        }
    }
}
