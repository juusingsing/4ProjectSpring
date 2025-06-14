package back.mapper.plant;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import back.model.plant.Plant;

@Mapper
public interface PlantMapper {
	
	//식물 분갈이 조회
	public List<Plant> pestlogs(Plant plant);
	
	//식물 병충해 저장
	public int savePestInfo(Plant plant);
	
	//식물 분갈이 개별 수정
	public int updatePlantRepottingLogs(Plant plant);
		
	//식물 분갈이 단건 조회
	public int getPlantRepottingLogsId(Plant plant);
	
	//식물 분갈이 로그 개별 삭제
	public int deletePlantRepottingLogs(Plant plant);
	
	//식물 분갈이 조회
	public List<Plant> repottinglogs(Plant plant);
	
	//식물 분갈이 저장
	public int saveRepottingInfo(Plant plant);
	
	//식물 일조량 단건 조회
	public int getPlantSunlightLogsId(Plant plant);
	
	//식물 일조량 개별 수정
	public int updatePlantSunlightLogs(Plant plant);
	
	//식물 일조량 개별 삭제
	public int deletePlantSunlightLogs(Plant plant);
	
	//식물 일조량 조회
	public List<Plant> findByPlantId(Plant plant);
	
	public List<Map<String, Object>> selectPlantCheck(int plant_id);
	
	public int updateSunlightInfo(
	        @Param("plantId") Integer plantId,
	        @Param("sunlightStatus") String sunlightStatus,
	        @Param("sunlightMemo") String sunlightMemo
	    );
	
	//식물 일조량 저장
	public int saveSunlightInfo(Plant plant);

	
	public Plant selectPlantById(String plantId);
	
	//식물 등록
	public int create(Plant plant);

    // 식물 수정
    public int update(Plant plant);

    // 식물 삭제
    public int delete(@Param("plantId") int plantId);
}
