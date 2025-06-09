package back.service.plant;

import java.util.List;

import back.exception.HException;
import back.model.plant.Plant;

public interface PlantService {
    
    // 식물 등록
    public boolean create(Plant plant) throws HException;

    // 식물 단건 조회
    public Plant getPlantById(String plantId) throws HException;

    // 사용자 ID로 식물 목록 조회
    public List<Plant> getPlantList(String userId) throws HException;

    // 식물 수정
    public boolean update(Plant plant) throws HException;

    // 식물 삭제 (soft delete)
    public boolean delete(String plantId) throws HException;
}
