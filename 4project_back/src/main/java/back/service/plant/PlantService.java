package back.service.plant;

import java.util.List;
import java.util.Map;

import back.exception.HException;
import back.model.plant.Plant;

public interface PlantService {
	
	//식물 일조량 개별 수정
	public boolean updatePlantSunlightLogs(Plant plant);
	
	//식물 일조량 개별 삭제
	public boolean deletePlantSunlightLogs(Plant plant);
	
	//식물 일조량 조회
	public List<Plant> findByPlantId(Plant plant);
	
	public List<Map<String, Object>> getPlantCheck(Integer plantId);
	
	public boolean saveSunlightingRecord(Plant plant);
    
	//식물 저장
	public boolean saveSunlightInfo(Plant plant);
	
	public Plant getPlantById(String plantId) throws HException;
	
    // 식물 등록
    public boolean create(Plant plant) throws HException;

    // 식물 수정
    public boolean update(Plant plant) throws HException;

    // 식물 삭제
//    public boolean delete(Plant plant) throws HException;  
    
}
